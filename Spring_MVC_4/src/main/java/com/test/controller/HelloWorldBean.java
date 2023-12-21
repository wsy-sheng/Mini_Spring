package com.test.controller;

import com.minis.beans.factory.annotation.AutoWired;
import com.minis.test.AService;
import com.minis.test.BaseBaseService;
import com.minis.web.RequestMapping;
import com.test.entity.User;
import com.test.service.BaseService;

public class HelloWorldBean {

    @AutoWired
    BaseService baseservice;

    @RequestMapping("/test1")
    public String doTest1() {
        return "test 1, hello world!";
    }
    @RequestMapping("/test2")
    public String doTest2() {
        return "test 2, hello world!";
    }
    @RequestMapping("/test3")
    public String doTest3() {
        baseservice.sayHello();
        return "do Test 3....";
    }
    @RequestMapping("/test4")
    public String doTest4(User user) { //  http://localhost:8080/test4?name=wsy&id=2&birthday=2023-12-02
        return user.getId() +" "+user.getName() + " " + user.getBirthday();
    }
    @RequestMapping("/test5")
    public String doTest5(Integer n) {
        return "test 5, hello world!"+n;
    }

}