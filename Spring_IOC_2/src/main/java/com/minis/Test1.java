package com.minis;


import com.minis.context.ClassPathXmlApplicationContext0;
import com.minis.test.AService;

public class Test1 {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext0 ctx = new
                ClassPathXmlApplicationContext0("beans.xml");
        AService aService = (AService)ctx.getBean("aservice");
        aService.sayHello();
    }
}