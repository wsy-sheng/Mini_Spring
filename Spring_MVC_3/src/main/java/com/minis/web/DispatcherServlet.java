package com.minis.web;

import com.minis.beans.factory.BeansException;
import com.minis.beans.factory.annotation.AutoWired;

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
public class DispatcherServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

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

		URL xmlPath = null;
		try {
			xmlPath = this.getServletContext().getResource(sContextConfigLocation);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}

		this.packageNames = XmlScanComponentHelper.getNodeValue(xmlPath);

		this.webApplicationContext = new AnnotationConfigWebApplicationContext(sContextConfigLocation, this.parentApplicationContext);
		Refresh();

	}

	protected void Refresh() {
		initController();
		initMapping();
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

	protected void initMapping() {
		for (String controllerName : this.controllerNames) {
			Class<?> clazz = this.controllerClasses.get(controllerName);
			Object obj = this.controllerObjs.get(controllerName);
			Method[] methods = clazz.getDeclaredMethods();
			if(methods!=null){
				for(Method method : methods){
					boolean isRequestMapping = method.isAnnotationPresent(RequestMapping.class);
					if (isRequestMapping){
						String methodName = method.getName();
						String urlmapping = method.getAnnotation(RequestMapping.class).value();
						this.urlMappingNames.add(urlmapping);
						this.mappingObjs.put(urlmapping, obj);
						this.mappingMethods.put(urlmapping, method);
					}
				}
			}
		}
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String sPath = request.getServletPath();
		System.out.println(sPath);
		if (!this.urlMappingNames.contains(sPath)) {
			return;
		}

		Object obj = null;
		Object objResult = null;
		try {
			Method method = this.mappingMethods.get(sPath);
			obj = this.mappingObjs.get(sPath);
			objResult = method.invoke(obj);
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}

		response.getWriter().append(objResult.toString());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
