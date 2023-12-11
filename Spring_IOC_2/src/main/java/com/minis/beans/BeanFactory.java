package com.minis.beans;

public interface BeanFactory {
    Object getBeans(String beanName) throws BeansException;
    void registerBeanDefinition(BeanDefinition beanDefinition);
}
