package cn.sam.test.view;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.sam.test.entity.SysRole;
import cn.sam.test.entity.SysUser;
import cn.sam.test.service.SysUserService;
import cn.sam.test.service.TestCacheService;

@Controller
@RequestMapping("/demo")
@Transactional
public class DemoController {
	
	@Autowired
	private SysUserService sysUserService;
	
	@Autowired
	private TestCacheService testCacheService;

	@RequestMapping("")
	public String toIndex() {
		return "forward:/demo/index";
	}

	@RequestMapping("/")
	public String toIndex1() {
		return "forward:/demo/index";
	}

	@RequestMapping("/index")
	public String index(HttpServletRequest request, ModelMap model) {
		SysUser user = sysUserService.getUserByIdWithHibernate(1);
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		model.put("msg", "Welcome " + user.getUserName() + "(" + user.getRole().getName() + ")" + "!");
		model.put("date", format.format(new Date()));
		model.put("name", request.getParameter("name"));
		model.put("host", request.getLocalAddr());
		model.put("port", request.getLocalPort());
		return "index";
	}

	@RequestMapping("/getUserByMethod")
	public String getUserByMethod(HttpServletRequest request, ModelMap model) {
		String method = request.getParameter("method");
		String isTest = request.getParameter("isTest");
		String userName = request.getParameter("userName");
		SysUser user = null;
		Integer id = 1;
		switch (method == null ? "" : method) {
		case "load":
			user = sysUserService.loadUserById(id);
			break;
		case "list":
			List<SysUser> users = sysUserService.listUsers(userName);
			for (SysUser u : users) {
				if (u.getId().equals(id)) {
					user = u;
				}
			}
			break;
		case "iterate":
			Iterator<SysUser> it = sysUserService.iterateUsers(userName);
			while (it.hasNext()) {
				SysUser u = it.next();
				if (u.getId().equals(id)) {
					user = u;
				}
			}
			break;

		default:
			user = sysUserService.getUserById(id);
			break;
		}
		
		if ("true".equalsIgnoreCase(isTest)) {
			switch (method == null ? "" : method) {
			case "load":
				user = sysUserService.loadUserById(id);
				break;
			case "list1":
				List<SysUser> users = sysUserService.listUsers(userName);
				for (SysUser u : users) {
					if (u.getId().equals(id)) {
						user = u;
					}
				}
				break;
			case "iterate1":
				Iterator<SysUser> it = sysUserService.iterateUsers(userName);
				while (it.hasNext()) {
					SysUser u = it.next();
					if (u.getId().equals(id)) {
						user = u;
					}
				}
				break;

			default:
				user = sysUserService.getUserById(id);
				break;
			}
		}
		
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		model.put("msg", "Welcome " + user.getUserName() + "(" + user.getRole().getName() + ")" + "!");
		model.put("date", format.format(new Date()));
		return "index";
	}

	@RequestMapping("/getRoleByMethod")
	public String getRoleByMethod(HttpServletRequest request, ModelMap model) {
		String method = request.getParameter("method");
		String isTest = request.getParameter("isTest");
		/*SysUser user = null;
		Integer uId = 2;
		switch (method == null ? "" : method) {
		case "load":
			user = sysUserService.loadUserById(uId);
			break;
		case "list":
			List<SysUser> users = sysUserService.listUsers();
			for (SysUser u : users) {
				if (u.getId().equals(uId)) {
					user = u;
				}
			}
			break;
		case "iterate":
			Iterator<SysUser> it = sysUserService.iterateUsers();
			while (it.hasNext()) {
				SysUser u = it.next();
				if (u.getId().equals(uId)) {
					user = u;
				}
			}
			break;

		default:
			user = sysUserService.getUserById(uId);
			break;
		}*/

		SysRole role = null;
		Integer id = 1;
		if ("true".equalsIgnoreCase(isTest)) {
			switch (method == null ? "" : method) {
			case "load":
				role = sysUserService.loadRoleById(id);
				break;
			case "list":
				List<SysRole> users = sysUserService.listRoles();
				for (SysRole u : users) {
					if (u.getId().equals(id)) {
						role = u;
					}
				}
				break;
			case "iterate":
				Iterator<SysRole> it = sysUserService.iterateRoles();
				while (it.hasNext()) {
					SysRole u = it.next();
					if (u.getId().equals(id)) {
						role = u;
					}
				}
				break;

			default:
				role = sysUserService.getRoleById(id);
				break;
			}
		}
		
		SysUser u1 = role.getUsers().iterator().next();
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		model.put("msg", "Welcome " + u1.getUserName() + "(" + role.getName() + ")" + "!");
		model.put("date", format.format(new Date()));
		return "index";
	}

	@RequestMapping("/getUserNameAndPassword")
	public String getUserNameAndPassword(HttpServletRequest request, ModelMap model) {
		String method = request.getParameter("method");
		String isObj = request.getParameter("isObj");
		if ("true".equals(isObj)) {
			SysUser user = null;
			Iterator<SysUser> it = sysUserService.iteratePartOfUserObj();
			while (it.hasNext()) {
				user = it.next();
			}
			model.put("msg", user.getUserName() + ":" + user.getPassword());
		} else {
			Object obj = null;
			if ("list".equals(method)) {
				List<Object> list = sysUserService.listPartOfUser();
				obj = list.get(1);
			} else {
				Iterator<Object> it = sysUserService.iteratePartOfUser();
				while (it.hasNext()) {
					obj = it.next();
				}
			}
			Object[] arr = (Object[]) obj;
			model.put("msg", arr[0] + ":" + arr[1]);
		}
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		model.put("date", format.format(new Date()));
		return "index";
	}

	@RequestMapping("/index.cache")
	public String indexCachePage(ModelMap model) {
		SysUser user = sysUserService.getUserById(1);
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		model.put("msg", "Welcome " + user.getUserName() + "!");
		model.put("date", format.format(new Date()));
		return "index";
	}

	@RequestMapping("/indexCacheMethod")
	public String indexCacheMethod(ModelMap model) {
		model.put("date", testCacheService.getDateStr());
		return "index";
	}
}
