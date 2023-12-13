package com.minis.beans;


import com.minis.beans.BeanDefinition;
import com.minis.beans.BeanFactory;
import com.minis.core.Resource;
import org.dom4j.Element;

import java.util.List;

public class XmlBeanDefinitionReader {
//    BeanFactory beanFactory;

    SimpleBeanFactory beanFactory;

    public XmlBeanDefinitionReader(SimpleBeanFactory simpleBeanFactory) {
        this.beanFactory = simpleBeanFactory;
    }

//    public void loadBeanDefinitions(Resource resource){
//        while(resource.hasNext()){
//            Element element = (Element) resource.next();
//            String beanID = element.attributeValue("id");
//            String beanClassName = element.attributeValue("class");
//            BeanDefinition beanDefinition = new BeanDefinition(beanID, beanClassName);
////            this.beanFactory.registerBeanDefinition(beanDefinition);
//            this.simpleBeanFactory.registerBeanDefinition(beanID, beanDefinition);
//
//        }
//    }
    public void loadBeanDefinitions(Resource res) {
        while (res.hasNext()) {
            Element element = (Element)res.next();
            String beanID=element.attributeValue("id");
            String beanClassName=element.attributeValue("class");

            BeanDefinition beanDefinition=new BeanDefinition(beanID,beanClassName);

            //handle properties
            List<Element> propertyElements = element.elements("property");
            PropertyValues PVS = new PropertyValues();
            for (Element e : propertyElements) {
                String pType = e.attributeValue("type");
                String pName = e.attributeValue("name");
                String pValue = e.attributeValue("value");
                PVS.addPropertyValue(new PropertyValue(pType, pName, pValue));
            }
            beanDefinition.setPropertyValues(PVS);
            //end of handle properties

            //get constructor
            List<Element> constructorElements = element.elements("constructor-arg");
            ArgumentValues AVS = new ArgumentValues();
            for (Element e : constructorElements) {
                String pType = e.attributeValue("type");
                String pName = e.attributeValue("name");
                String pValue = e.attributeValue("value");
                AVS.addArgumentValue(new ArgumentValue(pType,pName,pValue));
            }
            beanDefinition.setConstructorArgumentValues(AVS);
            //end of handle constructor

            this.beanFactory.registerBeanDefinition(beanID,beanDefinition);
        }

    }

}
