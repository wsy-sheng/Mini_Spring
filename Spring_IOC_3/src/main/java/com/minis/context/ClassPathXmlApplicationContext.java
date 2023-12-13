package com.minis.context;

import com.minis.beans.BeanDefinition;
import com.minis.beans.BeansException;
import com.minis.beans.BeanFactory;
import com.minis.beans.SimpleBeanFactory;
import com.minis.core.ClassPathXmlResource;
import com.minis.core.Resource;
import com.minis.beans.XmlBeanDefinitionReader;

public class ClassPathXmlApplicationContext implements BeanFactory,ApplicationEventPublisher{
    SimpleBeanFactory beanFactory;

    public ClassPathXmlApplicationContext(String fileName){
        Resource res = new ClassPathXmlResource(fileName);
        SimpleBeanFactory bf = new SimpleBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(bf);
        reader.loadBeanDefinitions(res);
        this.beanFactory = bf;
    }

    @Override
    public Object getBean(String beanName) throws BeansException {
        return this.beanFactory.getBean(beanName);
    }

    @Override
    public boolean containsBean(String name) {
        return this.beanFactory.containsBean(name);
    }

    public void registerBean(String beanName, Object obj) {
        this.beanFactory.registerBean(beanName, obj);
    }

    @Override
    public void publishEvent(ApplicationEvent event) {
    }

    @Override
    public boolean isSingleton(String name) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean isPrototype(String name) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public Class<?> getType(String name) {
        // TODO Auto-generated method stub
        return null;
    }

}
