package com.minis.context;

import com.minis.beans.factory.BeansException;

public interface ApplicationContextAware {
    void setApplicationContext(ApplicationContext applicationContext) throws BeansException;
}
