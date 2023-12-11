package com.minis;


import com.minis.beans.BeansException;
import com.minis.context.ClassPathXmlApplicationContext;
import com.minis.test.AService;


public class Test2 {
    public static void main(String[] args) throws BeansException {
        // com.minis.context.ClassPathXmlApplicationContext
        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("beans.xml");
        AService aService = (AService)ctx.getBeans("aservice");
        aService.sayHello();
    }
}