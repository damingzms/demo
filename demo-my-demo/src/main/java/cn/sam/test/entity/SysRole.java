package cn.sam.test.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

public class SysRole implements Serializable {

	private static final long serialVersionUID = -8039915820220693097L;

	private Integer id;

	private String name;

	private String description;

	private Date createTime;

	private Set<SysUser> users;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Set<SysUser> getUsers() {
		return users;
	}

	public void setUsers(Set<SysUser> users) {
		this.users = users;
	}
}