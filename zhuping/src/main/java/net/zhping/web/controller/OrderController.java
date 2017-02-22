package net.zhping.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import net.zhping.web.bean.BaseResponse;
import net.zhping.web.exception.ProcessException;
import net.zhping.web.service.OrderService;

/**
 * 
 * @author SAM
 *
 */
@Controller
@RequestMapping("/order/")
public class OrderController {
	
	@Autowired
	OrderService orderService;

	@RequestMapping("updateReceiverInfo")
	@ResponseBody
	public Object updateReceiverInfo(HttpServletRequest request, HttpServletResponse response) throws ProcessException {
		BaseResponse<?> baseResponse = new BaseResponse<>();
		return baseResponse;
	}
	
}
