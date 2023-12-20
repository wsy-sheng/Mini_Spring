package com.minis.beans.factory.support;


import com.minis.beans.factory.BeansException;
import com.minis.beans.factory.config.AbstractAutowireCapableBeanFactory;
import com.minis.beans.factory.config.BeanDefinition;
import com.minis.beans.factory.config.BeanPostProcessor;
import com.minis.beans.factory.config.ConfigurableListableBeanFactory;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 它继承了其他 BeanFactory 类来实现 Bean 的创建管理功能。
 */


public class DefaultListableBeanFactory extends AbstractAutowireCapableBeanFactory implements ConfigurableListableBeanFactory{

    private ConfigurableListableBeanFactory parentBeanFctory;

    @Override
    public int getBeanDefinitionCount() {
        return this.beanDefinitionMap.size();
    }

    @Override
    public String[] getBeanDefinitionNames() {
//        return (String[]) this.beanDefinitionNames.toArray();
        return this.beanDefinitionNames.toArray(new String[0]);
    }

    @Override
    public String[] getBeanNamesForType(Class<?> type) {
        List<String> result = new ArrayList<>();

        for (String beanName : this.beanDefinitionNames) {
            boolean matchFound = false;
            BeanDefinition mbd = this.getBeanDefinition(beanName);
            Class<?> classToMatch = mbd.getClass();
            if (type.isAssignableFrom(classToMatch)) {
                matchFound = true;
            }
            else {
                matchFound = false;
            }

            if (matchFound) {
                result.add(beanName);
            }
        }
        return (String[]) result.toArray();

    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> Map<String, T> getBeansOfType(Class<T> type) throws BeansException {
        String[] beanNames = getBeanNamesForType(type);
        Map<String, T> result = new LinkedHashMap<>(beanNames.length);
        for (String beanName : beanNames) {
            Object beanInstance = getBean(beanName);
            result.put(beanName, (T) beanInstance);
        }
        return result;
    }

    public void setParent(ConfigurableListableBeanFactory beanFactory) {
        this.parentBeanFctory = beanFactory;
    }

    @Override
    public Object getBean(String beanName) throws BeansException{
        Object result = super.getBean(beanName);
        if (result == null) {
            result = this.parentBeanFctory.getBean(beanName);
        }

        return result;
    }
}
