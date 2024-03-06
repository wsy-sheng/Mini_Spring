package com.minis.web.servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public interface View {
	default String getContentType() {
		return null;
	}

	/**获取 HTTP 请求的 request 和 response，以及中间产生的业务数据 Model，最后写到 response 里面。*/
	void render(Map<String, ?> model, HttpServletRequest request, HttpServletResponse response) throws Exception;
	void setContentType(String contentType);

	void setUrl(String url);
	String getUrl();

	void setRequestContextAttribute(String requestContextAttribute);
	String getRequestContextAttribute();
}
