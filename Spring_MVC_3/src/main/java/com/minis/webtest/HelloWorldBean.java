package com.minis.webtest;

import com.minis.beans.factory.annotation.AutoWired;
import com.minis.test.AService;
import com.minis.test.BaseBaseService;
import com.minis.web.RequestMapping;

public class HelloWorldBean {

    @AutoWired
    private AService aservice;

    @RequestMapping("/hello")
    public String doTest() {
        aservice.sayHello();
        return "hello world for doGet!";
    }
}