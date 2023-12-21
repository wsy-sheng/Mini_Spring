package com.minis.beans;

/**
 * 类型转换器
 */
public interface PropertyEditor {
    /**
     * 输入。将String转换为属性值类型输入
     */
    void setAsText(String text);

    /**
     * 设置属性值
     */
    void setValue(Object value);

    /**
     * 获取属性值
     */
    Object getValue();

    /**
     * 输出。把属性值转换成String输出
     */
    Object getAsText();
}