package cn.sam.test.dao;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public interface Dao {
	
	public void insert(Object obj);

	public void update(Object obj);

	public <T> void delete(Class<T> entityClass, Serializable id);

	public void delete(Object obj);

	public <T> T load(Class<T> entityClass, Serializable id);

	public <T> T get(Class<T> entityClass, Serializable id);

	public <T> List<T> list(String hql, Map<String, Object> params);

	public <T> Iterator<T> iterator(String hql, Map<String, Object> params);
	
}
