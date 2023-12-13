package com.minis.context;

import com.minis.beans.BeanDefinition;
import com.minis.beans.BeansException;
import com.minis.beans.BeanFactory;
import com.minis.beans.SimpleBeanFactory;
import com.minis.core.ClassPathXmlResource;
import com.minis.core.Resource;
import com.minis.beans.XmlBeanDefinitionReader;

public class ClassPathXmlApplicationContext implements BeanFactory {
    BeanFactory beanFactory;
    // context 负责整合容器的启动过程，读外部配置，解析Bean定义，创建BeanFactory

    public ClassPathXmlApplicationContext(String fileName) {
        // 解析 XML 文件中的内容。
        // 加载解析的内容构建 BeanDefinition。
        // 读取 BeanDefinition 的配置信息，实例化 Bean，然后把它注入到 BeanFactory 容器中。
        Resource resource = new ClassPathXmlResource(fileName);
        SimpleBeanFactory beanFactory = new SimpleBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
        reader.loadBeanDefinitions(resource);
        this.beanFactory = beanFactory;
    }

    //context再对外提供一个getBean，底下就是调用的BeanFactory对应的方法
    @Override
    public Object getBean(String beanName) throws BeansException {
        return this.beanFactory.getBean(beanName);
    }

    @Override
    public void registerBeanDefinition(BeanDefinition beanDefinition) {
        this.beanFactory.registerBeanDefinition(beanDefinition);
    }
}
