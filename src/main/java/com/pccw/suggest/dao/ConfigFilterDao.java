package com.pccw.suggest.dao;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.pccw.suggest.po.ConfigFilter;

@Repository
public class ConfigFilterDao extends BaseDao<ConfigFilter> {

	public void findById(){
		
		
	}
	
	public ConfigFilter findByType(String type){
		
		String hql = "from ConfigFilter cf where cf.type = :type";
		
		Query query = getSessionFactory().getCurrentSession().createQuery(hql)
		.setString("type", type);
		
		return this.findOne(query);
	}
	
	
}
