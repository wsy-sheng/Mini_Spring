package com.minis.beans;


public class BeanDefinition {
    private String id;
    private String className;
    public BeanDefinition(String id, String className) {
        this.id = id;
        this.className = className;
    }

    public String getId() {
        return id;
    }
    public String getClassName() {
        return className;
    }
    public void setId(String id) {
        this.id = id;
    }

    public void setClassName(String className) {
        this.className = className;
    }
}