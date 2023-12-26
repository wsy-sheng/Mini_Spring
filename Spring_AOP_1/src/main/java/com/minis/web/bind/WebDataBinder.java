package com.minis.web.bind;


import com.minis.beans.AbstractPropertyAccessor;
import com.minis.beans.BeanWrapperImpl;
import com.minis.beans.PropertyEditor;
import com.minis.beans.factory.config.PropertyValues;
import com.minis.util.WebUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 代表的是一个内部的目标对象，用于将 Request 请求内的字符串参数转换成不同类型的参数，来进行适配。
 */
public class WebDataBinder {
    /**目标对象*/
    private Object target;
    private Class<?> clz;

    private String objectName;
    AbstractPropertyAccessor propertyAccessor;

    public WebDataBinder(Object target) {
        this(target,"");
    }
    public WebDataBinder(Object target, String targetName) {
        this.target = target;
        this.objectName = targetName;
        this.clz = this.target.getClass();
        this.propertyAccessor = new BeanWrapperImpl(this.target);
    }
    /**核心绑定方法，将request里面的参数值绑定到目标对象的属性上*/
    public void bind(HttpServletRequest request) {
        //把 Request 里的参数解析成 PropertyValues
        PropertyValues mpvs = assignParameters(request);
        //把 Request 里的参数值添加到绑定参数中
        addBindValues(mpvs, request);
        //把两者绑定在一起
        doBind(mpvs);
    }

    private void doBind(PropertyValues mpvs) {
        applyPropertyValues(mpvs);

    }
    /**实际将参数值与对象属性进行绑定的方法*/
    protected void applyPropertyValues(PropertyValues mpvs) {
        getPropertyAccessor().setPropertyValues(mpvs);
    }
    /**设置属性值的工具*/
    protected AbstractPropertyAccessor getPropertyAccessor() {
        return this.propertyAccessor;
    }
    /**请求参数解析为ProperValues */
    private PropertyValues assignParameters(HttpServletRequest request) {
        Map<String,Object> map = WebUtils.getParametersStartingWith(request, "");       //解析请求参数为map键值对形式

        return new PropertyValues(map);     //将解析好的map转化为PropertyValue形式
    }

    public void registerCustomEditor(Class<?> requiredType, PropertyEditor propertyEditor) {
        getPropertyAccessor().registerCustomEditor(requiredType, propertyEditor);
    }

    protected void addBindValues(PropertyValues mpvs, HttpServletRequest request) {
    }

}
