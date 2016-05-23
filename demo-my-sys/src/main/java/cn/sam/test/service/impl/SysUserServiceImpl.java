package cn.sam.test.service.impl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.sam.test.dao.Dao;
import cn.sam.test.entity.SysRole;
import cn.sam.test.entity.SysUser;
import cn.sam.test.service.SysUserService;

@Service("sysUserService")
//@Transactional
public class SysUserServiceImpl implements SysUserService {
	
//	@Autowired
//	private SysUserMapper sysUserMapper;
//	
//	@Autowired
//	private SqlSession sqlSession;
	
	@Autowired
	private Dao dao;

	@Override
	public SysUser getUserById(Integer id) {
//		return sysUserMapper.selectByPrimaryKey(id);
		return getUserByIdWithHibernate(id);
	}

	@Override
	public SysUser getUserByIdWithSqlSession(Integer id) {
		Map<String, Object> parameter = new HashMap<String, Object>();
		parameter.put("id", id);
//		return sqlSession.selectOne("cn.sam.test.entity.mapper.SysUserMapper.selectByPrimaryKey", parameter);
		return getUserByIdWithHibernate(id);
	}

	/**
	 * 
	 */
	@Override
	public SysUser getUserByIdWithHibernate(Integer id) {
		return dao.get(SysUser.class, id);
	}

	@Override
	public SysUser loadUserById(Integer id) {
		return dao.load(SysUser.class, id);
	}

	@Override
	public List<SysUser> listUsers(String userName) {
		String hql = "from SysUser where 1 = 1";
		Map<String, Object> params = new HashMap<String, Object>();
		if (!StringUtils.isEmpty(userName)) {
			hql = hql + " and userName = :userName";
			params.put("userName", userName);
		}
		return dao.list(hql, params);
	}

	@Override
	public Iterator<SysUser> iterateUsers(String userName) {
		String hql = "from SysUser where 1 = 1";
		Map<String, Object> params = new HashMap<String, Object>();
		if (!StringUtils.isEmpty(userName)) {
			hql = hql + " and userName = :userName";
			params.put("userName", userName);
		}
		return dao.iterator(hql, params);
	}

	@Override
	public SysRole getRoleById(Integer id) {
		return dao.get(SysRole.class, id);
	}

	@Override
	public SysRole loadRoleById(Integer id) {
		return dao.load(SysRole.class, id);
	}

	@Override
	public List<SysRole> listRoles() {
		return dao.list("from SysRole", null);
	}

	@Override
	public Iterator<SysRole> iterateRoles() {
		return dao.iterator("from SysRole", null);
	}

	@Override
	public List<Object> listPartOfUser() {
		return dao.list("select id, userName, password from SysUser", null);
	}

	@Override
	public Iterator<Object> iteratePartOfUser() {
		return dao.iterator("select id, userName, password from SysUser", null);
	}

	@Override
	public Iterator<SysUser> iteratePartOfUserObj() {
		return dao.iterator("select new SysUser(id, userName, password) from SysUser", null);
	}

}
