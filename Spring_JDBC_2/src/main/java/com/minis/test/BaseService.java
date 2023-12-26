package com.minis.test;

import com.minis.beans.factory.annotation.AutoWired;

public class BaseService {
    @AutoWired
    private BaseBaseService basebaseservice;
    public BaseBaseService getBasebaseservice() {
        return basebaseservice;
    }

    public void setBasebaseservice(BaseBaseService basebaseservice) {
        this.basebaseservice = basebaseservice;
    }
    public BaseService() {
    }
    public void sayHello() {
        System.out.println("Base Service says hello");
        basebaseservice.sayHello();
    }

    public void init() {
        System.out.println("Base Service init method.");
    }
}
