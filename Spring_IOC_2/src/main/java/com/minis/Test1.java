package com.minis;


import com.minis.context.ClassPathXmlApplicationContext1;
import com.minis.test.AService;

public class Test1 {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext1 ctx = new
                ClassPathXmlApplicationContext1("beans.xml");
        AService aService = (AService)ctx.getBean("aservice");
        aService.sayHello();
    }
}