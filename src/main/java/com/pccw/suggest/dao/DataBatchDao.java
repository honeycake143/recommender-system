package com.pccw.suggest.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.stereotype.Repository;

import com.pccw.suggest.po.ControlBatchLog;
import com.pccw.suggest.po.DataBatch;

@Repository
public class DataBatchDao extends BaseDao<DataBatch> {
	
	public void findById(){
		
	}
	
	public List<DataBatch> findByBatchLogId(ControlBatchLog id){
		
		String hql = "from DataBatch where ctl_batch_log = :ctl_batch_log_id";
		
		Query query = getSessionFactory().getCurrentSession().createQuery(hql)
				      .setEntity("ctl_batch_log_id", id);
		
		return this.findMany(query);
	}
	
	
	public List sumValueGroupByFields(Integer ctl_batch_log_id){
		
		String sql = "select sum(d.value) as sum_value, type, user_id, item_id, record_date, max(d.record_datetime) as max_record_datetime from sug_data_batch d where d.ctl_batch_log_id = " + ctl_batch_log_id + 
					 " group by d.type, d.user_id, d.item_id, d.record_date";
		
		Query query = getSessionFactory().getCurrentSession().createSQLQuery(sql)
							.addScalar("sum_value", StandardBasicTypes.FLOAT)
							.addScalar("type", StandardBasicTypes.STRING)
							.addScalar("user_id", StandardBasicTypes.INTEGER)
							.addScalar("item_id", StandardBasicTypes.INTEGER)
							.addScalar("record_date", StandardBasicTypes.DATE)
							.addScalar("max_record_datetime", StandardBasicTypes.TIMESTAMP)
							.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		
		return this.findManyBySQL(query);
		
		
	}
}
