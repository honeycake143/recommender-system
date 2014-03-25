package com.pccw.suggest.dao;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.pccw.suggest.po.IDMap;

@Repository
public class IDMapDao extends BaseDao<IDMap> {
	
	
	public IDMap findByExtId(String ext_id){
		
		String hql = "from IDMap where ext_id = :ext_id";
		
		Query query = getSessionFactory().getCurrentSession().createQuery(hql)
					  .setString("ext_id", ext_id)
					  .setCacheable(true);
		
		return this.findOne(query);
	}
	
//	public void sqlInsertIdMap(IDMap map){
//		
//		String sql = "INSERT INTO sug_util_id_map(ext_id) VALUES(" + map.getExt_id() + ")";
//		
//		this.nativeInsert(sql);
//	}
	
}
