package com.minis.test;

import com.minis.beans.factory.annotation.AutoWired;

public class BaseService {
    @AutoWired
    private BaseBaseService basebaseservice;

    public BaseBaseService getBbs() {
        return basebaseservice;
    }
    public void setBbs(BaseBaseService bbs) {
        this.basebaseservice = bbs;
    }
    public BaseService() {
    }
    public void sayHello() {
        System.out.print("Base Service says hello");
        basebaseservice.sayHello();
    }

    public void init() {
        System.out.print("Base Service init method.");
    }
}
