package com.minis.beans;

import com.minis.beans.factory.BeanFactory;

public interface BeanFactoryAware {
    void setBeanFactory(BeanFactory beanFactory);
}
