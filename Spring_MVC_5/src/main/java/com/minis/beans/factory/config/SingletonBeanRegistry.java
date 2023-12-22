package com.minis.beans.factory.config;

/**
 * 单例的注册、获取、判断是否存在，以及获取所有的单例 Bean
 */
public interface SingletonBeanRegistry {
    void registerSingleton(String beanName, Object singletonObject);
    Object getSingleton(String beanName);
    boolean containsSingleton(String beanName);
    String[] getSingletonNames();
}