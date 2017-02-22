/*
 * %W% %E%
 *
 * Copyright (c) 2014, Biostime, Inc. All rights reserved.
 *
 */
package net.zhping.web.exception;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import net.zhping.web.bean.BaseResponse;
import net.zhping.web.constant.ResponseCode;

/**
 * 
 * @author SAM
 *
 */
@ControllerAdvice
public class WebExceptionHandler {

	private static final Logger LOG = LoggerFactory.getLogger(WebExceptionHandler.class);

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public @ResponseBody BaseResponse<String> handleMethodArgumentNotValidException(HttpServletRequest request,
			HttpServletResponse response, MethodArgumentNotValidException e) {
		LOG.error("请求" + request.getRequestURL().toString() + "异常");
		LOG.error(e.getMessage(), e);
		BindingResult result = e.getBindingResult();
		List<ObjectError> errors = result.getAllErrors();
		return new BaseResponse<>(ResponseCode.FAILURE.name(), errors.get(0).getDefaultMessage());
	}

	@ExceptionHandler(RuntimeException.class)
	public @ResponseBody BaseResponse<String> handleRuntimeException(HttpServletRequest request,
			HttpServletResponse response, RuntimeException e) {
		LOG.error("请求" + request.getRequestURL().toString() + "异常");
		LOG.error(e.getMessage(), e);
		return new BaseResponse<>(ResponseCode.FAILURE.name(), e.getMessage());
	}

	@ExceptionHandler(ProcessException.class)
	public @ResponseBody BaseResponse<String> handleOpProcessException(HttpServletRequest request,
			HttpServletResponse response, ProcessException e) {
		LOG.error("请求" + request.getRequestURL().toString() + "异常");
		LOG.error(e.getMessage(), e);
		return new BaseResponse<>(ResponseCode.FAILURE.name(), e.getMessage());
	}

	@ExceptionHandler(Exception.class)
	public @ResponseBody BaseResponse<String> handleException(HttpServletRequest request, HttpServletResponse response,
			Exception e) {
		LOG.error("请求" + request.getRequestURL().toString() + "异常");
		LOG.error(e.getMessage(), e);
		return new BaseResponse<>(ResponseCode.FAILURE.name(), e.getMessage());
	}

}
