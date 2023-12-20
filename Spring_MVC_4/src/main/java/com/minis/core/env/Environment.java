package com.minis.core.env;

/**
 * Environment 则继承 PropertyResoulver 接口，用于获取属性。
 */
public interface Environment extends PropertyResolver {
    String[] getActiveProfiles();
    String[] getDefaultProfiles();
    boolean acceptsProfiles(String... profiles);
}
