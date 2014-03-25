package com.pccw.suggest.dao;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.pccw.suggest.po.ControlBatchLog;
import com.pccw.suggest.po.DataFull;

@Repository
public class DataFullDao extends BaseDao<DataFull> {

	public void findByDataFullId(){
		
	}
	
	public List<DataFull> findManyByBatchLogId(ControlBatchLog id){
		
		String hql = "from DataFull where ctl_batch_log = :ctl_batch_log and enable = 1";
		
		Query query = getSessionFactory().getCurrentSession().createQuery(hql)
					  .setEntity("ctl_batch_log", id);
		
		return this.findMany(query);
		
		
	}
	
	
	
}
