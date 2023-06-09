//package com.keep.common.core.handler;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.keep.common.core.domain.constants.CommanConstants;
//import com.keep.common.core.domain.entity.ResponseResult;
//import com.keep.common.core.expection.CustomExpection;
//import com.keep.common.core.xss.XssUtil;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.MediaType;
//import org.springframework.stereotype.Component;
//import org.springframework.web.context.request.RequestContextHolder;
//import org.springframework.web.context.request.ServletRequestAttributes;
//
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.io.PrintWriter;
//
//
////@Component
//public class HttpHandler {
//
//	private static final Logger logger = LoggerFactory.getLogger(HttpHandler.class);
//
//	@Autowired
//	private ObjectMapper objectMapper;
//
//	public <T> void printServerResponseToWeb(ResponseResult<T> responseResult) {
//		if (responseResult == null) {
//			logger.info("print obj is null");
//			return;
//		}
//
//		ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder
//				.getRequestAttributes();
//		if (requestAttributes == null) {
//			logger.error("requestAttributes is null, can not print to web");
//			return;
//		}
//		HttpServletResponse response = requestAttributes.getResponse();
//		if (response == null) {
//			logger.error("httpServletResponse is null, can not print to web");
//			return;
//		}
//		logger.error("response error: " + responseResult.getMsg());
//		response.setCharacterEncoding(CommanConstants.UTF_8);
//		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
//		PrintWriter printWriter = null;
//		try {
//			printWriter = response.getWriter();
//			printWriter.write(XssUtil.clean(objectMapper.writeValueAsString(responseResult)));
//		}
//		catch (IOException e) {
//			throw new CustomExpection("io 异常");
//		}
//	}
//
//}
