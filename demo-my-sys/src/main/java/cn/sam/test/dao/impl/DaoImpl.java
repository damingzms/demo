package cn.sam.test.dao.impl;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import cn.sam.test.dao.Dao;

@SuppressWarnings("unchecked")
@Repository("dao")
public class DaoImpl implements Dao {
	
	@Autowired
	SessionFactory sessionFactory;

    /**
     * gerCurrentSession 会自动关闭session
     * @return
     */
    public Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    /**
     * openSession 需要手动关闭session 意思是打开一个新的session
     * @return
     */
    public Session getNewSession() {
        return sessionFactory.openSession();
    }

	@Override
	public void insert(Object obj) {
		getSession().save(obj);
	}

	@Override
	public void update(Object obj) {
		getSession().update(obj);
	}

	@Override
	public <T> void delete(Class<T> entityClass, Serializable id) {
		T t = load(entityClass, id);
		delete(t);
	}

	@Override
	public void delete(Object obj) {
		getSession().delete(obj);
	}

	/**
	 * 类级别延迟加载：支持
	 * 关联级别延迟加载：支持
	 * 
	 * 一级缓存：使用；填充
	 * 二级缓存：使用；填充
	 */
	@Override
	public <T> T load(Class<T> entityClass, Serializable id) {
		return (T) getSession().load(entityClass, id);
	}

	/**
	 * 类级别延迟加载：不支持
	 * 关联级别延迟加载：支持
	 * 
	 * 一级缓存：使用；填充
	 * 二级缓存：使用；填充
	 */
	@Override
	public <T> T get(Class<T> entityClass, Serializable id) {
		return (T) getSession().get(entityClass, id);
	}

	/**
	 * 类级别延迟加载：不支持，一次性查询所有对象
	 * 关联级别延迟加载：支持
	 * 
	 * 一级缓存：不使用；填充
	 * 二级缓存：不使用；填充
	 * 查询缓存：使用；但是当只查询部分字段，而且在hql中调用构造函数封装成对象时，好像不使用查询缓存
	 */
	@Override
	public <T> List<T> list(String hql, Map<String, Object> params) {
		Query query = getSession().createQuery(hql);
		if (!CollectionUtils.isEmpty(params)) {
			for (Iterator<String> it = params.keySet().iterator(); it.hasNext();) {
				String key = it.next();
				query.setParameter(key, params.get(key));
			}
		}
		query.setCacheable(true);
		return query.list();
	}

	/**
	 * 类级别延迟加载：首先查询出所有id，然后用到哪个对象就根据id查哪个，类似于延迟加载，N+1问题
	 * 关联级别延迟加载：支持
	 * 
	 * 一级缓存：使用，每次总会查询所有id，根据id查询单个对象时使用缓存；填充
	 * 二级缓存：使用，每次总会查询所有id，根据id查询单个对象时使用缓存；填充
	 * 查询缓存：貌似不使用查询缓存
	 */
	@Override
	public <T> Iterator<T> iterator(String hql, Map<String, Object> params) {
		Query query = getSession().createQuery(hql);
		if (!CollectionUtils.isEmpty(params)) {
			for (Iterator<String> it = params.keySet().iterator(); it.hasNext();) {
				String key = it.next();
				query.setParameter(key, params.get(key));
			}
		}
		query.setCacheable(true);
		return query.iterate();
	}
	
	/**
	 * 一、二级缓存：
	 * 
	 * 以用户与角色为例：
	 * 用户的角色字段，使用一级缓存，而且只要角色类配置了使用二级缓存，用户的角色字段也将使用二级缓存
	 * 角色的用户字段，是一个set，不使用一级缓存，而且只有用户类以及角色的用户字段同时配置了使用二级缓存，用户字段才将使用二级缓存，
	 * 		如果只是角色的用户字段配置了使用二级缓存，而用户类没有配置，查询效率将更加差，类似iterator方法，一个用户一条查询sql，
	 * 		角色的用户字段是否使用二级缓存，不依赖于角色类是否配置了使用二级缓存
	 * 
	 * 一二级缓存只保存完整的对象，如果只查询部分字段信息，将不会保存到缓存里面，就算在hql里面封装成对象
	 * 
	 */
	
	/**
	 * 查询缓存
	 * 
	 * 只有当 HQL 查询语句完全相同时，连参数设置都要相同，此时查询缓存才有效
	 */
}
