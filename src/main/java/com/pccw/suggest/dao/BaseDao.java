package com.pccw.suggest.dao;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class BaseDao<T> {
	
	private Class<T> entityClass;
	
	@Autowired
    private SessionFactory sessionFactory;
	
	public BaseDao() {
		Type genType = getClass().getGenericSuperclass();
		Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
		entityClass = (Class) params[0];
	}
	
	public T load(Serializable id) {
		return (T) getSessionFactory().getCurrentSession().load(entityClass, id);
	}
	
	public T get(Serializable id) {
		return (T) getSessionFactory().getCurrentSession().get(entityClass, id);
	}
	
	public void save(T entity) {
		getSessionFactory().getCurrentSession().save(entity);
	}
	
	
	public void remove(T entity) {
		getSessionFactory().getCurrentSession().delete(entity);
	}
	
	public void update(T entity) {
		getSessionFactory().getCurrentSession().update(entity);
	}
	
	public List<T> findMany(Query query) {
		List<T> t;
//		Query query = getSessionFactory().getCurrentSession().createQuery(hql);
        t = (List<T>) query.list();
        return t;
	}
	
	
	public T findOne(Query query) {
		T t;
//		Query query = getSessionFactory().getCurrentSession().createQuery(hql);
        t = (T) query.uniqueResult();
        return t;
	}
	
	public Object findOneBySQL(Query query){
		
		return query.list();
	}
	
	public List findManyBySQL(Query query){
		
		return query.list();
		
		
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	
	
}
