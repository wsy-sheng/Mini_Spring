package com.minis.web;

import com.minis.beans.factory.BeansException;
import com.minis.beans.factory.annotation.AutoWired;
import com.minis.web.servlet.HandlerAdapter;
import com.minis.web.servlet.HandlerMethod;
import com.minis.web.servlet.RequestMappingHandlerAdapter;
import com.minis.web.servlet.RequestMappingHandlerMapping;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class DispatcherServlet
 */
@Deprecated
public class DispatcherServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String WEB_APPLICATION_CONTEXT_ATTRIBUTE = DispatcherServlet.class.getName() + ".CONTEXT";

	private WebApplicationContext webApplicationContext;
	private WebApplicationContext parentApplicationContext;
	private String sContextConfigLocation;
	private List<String> packageNames = new ArrayList<>();
	private Map<String,Object> controllerObjs = new HashMap<>();
	private List<String> controllerNames = new ArrayList<>();
	private Map<String,Class<?>> controllerClasses = new HashMap<>();

	private List<String> urlMappingNames = new ArrayList<>();
	private Map<String,Object> mappingObjs = new HashMap<>();
	private Map<String,Method> mappingMethods = new HashMap<>();
	private RequestMappingHandlerAdapter handlerAdapter;
	private RequestMappingHandlerMapping handlerMapping;

	public DispatcherServlet() {
		super();
		System.out.println("DispatcherServlet.Constructor........");
	}

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		System.out.println("DispatcherServlet.init........");

		this.parentApplicationContext = (WebApplicationContext) this.getServletContext().getAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);

		sContextConfigLocation = config.getInitParameter("contextConfigLocation");

		this.webApplicationContext = new AnnotationConfigWebApplicationContext(sContextConfigLocation, this.parentApplicationContext);

		Refresh();

	}

	protected void Refresh() {
		initController();

		initHandlerMappings(this.webApplicationContext);
		initHandlerAdapters(this.webApplicationContext);

	}
	protected void initHandlerMappings(WebApplicationContext wac) {
		this.handlerMapping = new RequestMappingHandlerMapping(wac);
	}
	protected void initHandlerAdapters(WebApplicationContext wac) {
		this.handlerAdapter = new RequestMappingHandlerAdapter(wac);
	}

	protected void initController() {
		this.controllerNames = scanPackages(this.packageNames);

		for (String controllerName : this.controllerNames) {
			Object obj = null;
			Class<?> clz = null;

			try {
				clz = Class.forName(controllerName);
				this.controllerClasses.put(controllerName,clz);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			try {
				obj = clz.newInstance();

				populateBean(obj,controllerName);

				this.controllerObjs.put(controllerName, obj);
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (BeansException e) {
				throw new RuntimeException(e);
			}
		}
	}


	protected Object populateBean(Object bean, String beanName) throws BeansException {
		Object result = bean;

		Class<?> clazz = bean.getClass();
		Field[] fields = clazz.getDeclaredFields();
		if(fields!=null){
			for(Field field : fields){
				boolean isAutowired = field.isAnnotationPresent(AutoWired.class);
				if(isAutowired){
					String fieldName = field.getName();
					Object autowiredObj = this.webApplicationContext.getBean(fieldName);
					try {
						field.setAccessible(true);
						field.set(bean, autowiredObj);
					} catch (IllegalArgumentException e) {
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					}
				}
			}
		}

		return result;
	}

	private List<String> scanPackages(List<String> packages) {
		List<String> tempControllerNames = new ArrayList<>();
		for (String packageName : packages) {
			tempControllerNames.addAll(scanPackage(packageName));
		}
		return tempControllerNames;
	}

	private List<String> scanPackage(String packageName) {
		List<String> tempControllerNames = new ArrayList<>();
		String parsePageName = packageName.replaceAll("\\.", "/");
		URL url  = this.getClass().getClassLoader().getResource(parsePageName);
		File dir = new File(url.getFile());
		for (File file : dir.listFiles()) {
			if(file.isDirectory()){
				scanPackage(packageName+"."+file.getName());
			}else{
				String controllerName = packageName +"." +file.getName().replace(".class", "");
				tempControllerNames.add(controllerName);
			}
		}
		return tempControllerNames;
	}
	protected void service(HttpServletRequest request, HttpServletResponse response) {
		request.setAttribute(WEB_APPLICATION_CONTEXT_ATTRIBUTE, this.webApplicationContext);
		try {
			doDispatch(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
		}
	}
	protected void doDispatch(HttpServletRequest request, HttpServletResponse response) throws Exception{
		HttpServletRequest processedRequest = request;
		HandlerMethod handlerMethod = null;
		handlerMethod = this.handlerMapping.getHandler(processedRequest);
		if (handlerMethod == null) {
			return;
		}
		HandlerAdapter ha = this.handlerAdapter;
		ha.handle(processedRequest, response, handlerMethod);
	}
}
