package com.minis.web.method;

import com.minis.web.servlet.MethodParameter;

import java.lang.reflect.Method;

public class HandlerMethod {
    private  Object bean;
    private  Class<?> beanType;
    private  Method method;
    private  MethodParameter[] parameters;
    private  Class<?> returnType;
    private  String description;
    private  String className;
    private  String methodName;

    public HandlerMethod(Method method, Object obj, Class<?> clz, String methodName) {
        this.method = method;
        this.bean = obj;
        this.beanType = clz;
        this.methodName = methodName;

    }
    public Method getMethod() {
        return method;
    }
    public void setMethod(Method method) {
        this.method = method;
    }
    public Object getBean() {
        return bean;
    }
    public void setBean(Object bean) {
        this.bean = bean;
    }

    public void setBeanType(Class<?> beanType) {
        this.beanType = beanType;
    }

    public void setParameters(MethodParameter[] parameters) {
        this.parameters = parameters;
    }

    public void setReturnType(Class<?> returnType) {
        this.returnType = returnType;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Class<?> getBeanType() {
        return beanType;
    }

    public MethodParameter[] getParameters() {
        return parameters;
    }

    public Class<?> getReturnType() {
        return returnType;
    }

    public String getDescription() {
        return description;
    }

    public String getClassName() {
        return className;
    }

    public String getMethodName() {
        return methodName;
    }
}