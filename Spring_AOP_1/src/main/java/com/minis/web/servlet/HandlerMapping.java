package com.minis.web.servlet;

import com.minis.web.method.HandlerMethod;

import javax.servlet.http.HttpServletRequest;

/**
 * 通过 URL 映射到某个实例方法
 */
public interface HandlerMapping {
    HandlerMethod getHandler(HttpServletRequest request) throws Exception;
}
