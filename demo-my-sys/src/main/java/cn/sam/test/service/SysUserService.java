package cn.sam.test.service;

import java.util.Iterator;
import java.util.List;

import cn.sam.test.entity.SysRole;
import cn.sam.test.entity.SysUser;

public interface SysUserService {

	public SysUser getUserById(Integer id);

	public SysUser getUserByIdWithSqlSession(Integer id);

	public SysUser getUserByIdWithHibernate(Integer id);

	public SysUser loadUserById(Integer id);

	public List<SysUser> listUsers(String userName);

	public Iterator<SysUser> iterateUsers(String userName);
	
	public SysRole getRoleById(Integer id);

	public SysRole loadRoleById(Integer id);

	public List<SysRole> listRoles();

	public Iterator<SysRole> iterateRoles();

	public List<Object> listPartOfUser();
	
	public Iterator<Object> iteratePartOfUser();

	public Iterator<SysUser> iteratePartOfUserObj();
}
