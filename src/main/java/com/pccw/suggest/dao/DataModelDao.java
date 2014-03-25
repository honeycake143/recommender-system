package com.pccw.suggest.dao;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.pccw.suggest.po.ControlBatchLog;
import com.pccw.suggest.po.DataModel;

@Repository
public class DataModelDao extends BaseDao<DataModel> {
	
	public void findById(){
		
	}
	
	public List<DataModel> findByBatchLogId(ControlBatchLog id){
		
		String hql = "from DataModel where ctl_batch_log = :ctl_batch_log and enable = 1";
		
		Query query = getSessionFactory().getCurrentSession().createQuery(hql)
					  .setEntity("ctl_batch_log", id);
		
		return this.findMany(query);
		
	}
	
	public void findByDataFullId(){
		
	}
}
