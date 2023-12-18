package com.minis.webtest;

import com.minis.web.RequestMapping;

public class HelloWorldBean {
    @RequestMapping("/hello")
    public String doTest() {
        return "hello world for doGet!";
    }
}