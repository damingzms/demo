package cn.sam.test.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.sam.test.entity.SysRole;
import cn.sam.test.entity.SysUser;

@Controller
@RequestMapping("")
@Transactional
public class DemoController {
	
	private static final Logger LOG = LoggerFactory.getLogger(DemoController.class);

	@RequestMapping("/index")
	public String index(HttpServletRequest request, ModelMap model) {
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		model.put("date", format.format(new Date()));
		model.put("name", request.getParameter("name"));
		model.put("host", request.getLocalAddr());
		model.put("port", request.getLocalPort());
		return "index";
	}

	@ResponseBody
	@RequestMapping("/testComplexObject")
	public Object testComplexObject(@RequestBody SysUser user) {
		return user.getRole();
	}

	@ResponseBody
	@RequestMapping("/testComplexObject2")
	public Object testComplexObject2(@RequestBody SysRole role) {
		SysUser user = role.getUsers().iterator().next();
		return user;
	}
	
}
