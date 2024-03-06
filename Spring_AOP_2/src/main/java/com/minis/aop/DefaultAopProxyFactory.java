package com.minis.aop;

public class DefaultAopProxyFactory implements AopProxyFactory {
    @Override
    public AopProxy createAopProxy(Object target, Advisor advisor) {
        //if (targetClass.isInterface() || Proxy.isProxyClass(targetClass)) {
        return new JdkDynamicAopProxy(target, advisor);
        //}
        //return new CglibAopProxy(config);
    }
}
