package com.minis;


import com.minis.beans.factory.BeansException;
import com.minis.beans.factory.annotation.AutoWired;
import com.minis.context.ClassPathXmlApplicationContext;
import com.minis.test.AService;
import com.minis.test.BaseService;


public class Test {

    public static void main(String[] args) throws BeansException {
        // com.minis.context.ClassPathXmlApplicationContext
        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("beans.xml");
//        AService aService = (AService)ctx.getBean("aservice");
//        aService.sayHello();
        BaseService baseService = (BaseService) ctx.getBean("baseservice");
        baseService.sayHello();
    }
}