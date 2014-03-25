package com.pccw.suggest.dao;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.pccw.suggest.po.ControlBatchLog;

@Repository
public class ControlBatchLogDao extends BaseDao<ControlBatchLog> {

	public void findByFileName(){
		
	}
	
	public ControlBatchLog findByMaxCreateDate(){
		
		String hql = "from ControlBatchLog c where c.ctl_batch_log_id = "
				+ 	 "(select max(ctl_batch_log_id) from ControlBatchLog)";
		
		Query query = getSessionFactory().getCurrentSession().createQuery(hql);
		
		return this.findOne(query);
	}
	
	
}
