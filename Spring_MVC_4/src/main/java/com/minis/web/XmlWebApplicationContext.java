package com.minis.web;

import com.minis.beans.factory.BeansException;
import com.minis.beans.factory.config.BeanFactoryPostProcessor;
import com.minis.beans.factory.config.BeanPostProcessor;
import com.minis.beans.factory.config.ConfigurableListableBeanFactory;
import com.minis.context.ApplicationEvent;
import com.minis.context.ApplicationListener;
import com.minis.context.ClassPathXmlApplicationContext;
import com.minis.core.env.Environment;

import javax.servlet.ServletContext;
import java.util.Map;

public class XmlWebApplicationContext  extends ClassPathXmlApplicationContext implements WebApplicationContext {

    private ServletContext servletContext;
    public XmlWebApplicationContext(String fileName) {
        super(fileName);
    }

    @Override
    public ServletContext getServletContext() {
        return this.servletContext;
    }

    @Override
    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }
}
