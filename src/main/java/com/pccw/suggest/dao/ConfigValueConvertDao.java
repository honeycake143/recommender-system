package com.pccw.suggest.dao;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.pccw.suggest.po.ConfigValueConvert;

@Repository
public class ConfigValueConvertDao extends BaseDao<ConfigValueConvert> {

	public void findById(){
		
	}
	
	public ConfigValueConvert findByType(String type){
		
		String hql = "from ConfigValueConvert where type = :type";
		
		Query query = getSessionFactory().getCurrentSession().createQuery(hql)
		.setString("type", type);
		
		return this.findOne(query);
	}
	
	public List<ConfigValueConvert> findAll(){
		
		String hql = "from ConfigValueConvert";
		
		Query query = getSessionFactory().getCurrentSession().createQuery(hql);
		
		return this.findMany(query);
	}
}
