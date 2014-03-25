package com.pccw.suggest.service;

import java.io.File;

import org.hibernate.FlushMode;
import org.hibernate.annotations.FlushModeType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.pccw.suggest.dao.ControlBatchLogDao;
import com.pccw.suggest.dao.DataFullDao;
import com.pccw.suggest.dao.IDMapDao;
import com.pccw.suggest.exception.SuggestEngineException;
import com.pccw.suggest.po.ControlBatchLog;
import com.pccw.suggest.po.IDMap;
import com.pccw.suggest.util.FileUtil;



@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
public class UpdateModelService {
	
	private static final Logger logger = LoggerFactory.getLogger(UpdateModelService.class);

	private static final String STATUS_START = "START";
	private static final String STATUS_FAILED = "FAILED";
	private static final String STATUS_END = "END";

	@Autowired
	private BaseService baseService;
	
	@Autowired
	private ControlBatchLogDao con_batch_log_dao;
	
	@Autowired
	private NestedService ns;
	
	
	//for test
//	@Autowired
//	private DataFullDao data_full_dao;
//	
//	@Autowired
//	private IDMapDao idmapdao;
	


	public void updateModel() throws SuggestEngineException{
		
//		System.out.println("now updating");

		while(true){
			
		ControlBatchLog current_log = con_batch_log_dao.findByMaxCreateDate();
			
		if(current_log != null && (current_log.getLast_process_status().equals(STATUS_FAILED) || current_log.getLast_process_status().equals(STATUS_START))){
			
			logger.warn("last process not finished, please check");
			break;
		}
			
		// Start step 1
		if(current_log == null || (current_log.getLast_process_step() == 4 && current_log.getLast_process_status().equals(STATUS_END))){
		
		File latestCsv = null;

		latestCsv = FileUtil.findLatestCsv();
		
		if(latestCsv == null)
			logger.warn("no csv file found in process folder");
		
		
		if(null != latestCsv){

			//delete data_batch
			
			
			if(!ns.importData(latestCsv)){
				
				logger.error("Step 1 import data failed");
				
				break;
			}

			continue;

			
		}else{
			break;
		}
		
		}
		
		
		//Start Step 2

		
		if(current_log != null && (current_log.getLast_process_step() == 1 && current_log.getLast_process_status().equals(STATUS_END))){
			
			
			if(!ns.sumFilter(current_log)){

				logger.error("SumFilter failed, batch log id = " + current_log.getCtl_batch_log_id());
				break;
			}
			
			
		}
		
		
		//Start step 3
		

		
		if(current_log != null && (current_log.getLast_process_step() == 2 && current_log.getLast_process_status().equals(STATUS_END))){
			

			
			if(!ns.valueConvert(current_log)){

				logger.error("ValueConvert failed, batch log id = " + current_log.getCtl_batch_log_id());
				break;
			}
			
			
		}
		
		
		//Start step 4
		

		
		if(current_log != null && (current_log.getLast_process_step() == 3 && current_log.getLast_process_status().equals(STATUS_END))){
			
			
			if(!ns.feedMahout(current_log)){

				logger.error("FeedMahout failed, batch log id = " + current_log.getCtl_batch_log_id());
				break;
			}
			
			
		}

		}
		
	}
	
	
	public void test(){
	
//		IDMap map = idmapdao.load(8288);
//		
//		IDMap map2 = idmapdao.load(8482);
		
//	  List<DataFull> list = data_full_dao.getSessionFactory().getCurrentSession()
////													.createQuery("from DataFull where user_id = :user and item_id = :item")
//													.createQuery("from DataFull where data_full_id > 36330")
////													.setEntity("user", map)
////													.setEntity("item", map2)
//													.list();
	 
//	 iterate.next();
	 
//	 DataFull result_2 = data_full_dao.get(36125);
	  
//	  for(DataFull df : list){
//		  
//		  
//		  df.getEnable();
//	  }
		
//		DataFull object = (DataFull)list.iterator().next();
		
		
//		data_full_dao.getSessionFactory().getCurrentSession().clear();
		
//		idmapdao.getSessionFactory().getCurrentSession().setFlushMode(FlushMode.COMMIT);
		
//		IDMap findByExtId = idmapdao.findByExtId("ggg");
		
//		IDMap idMap = idmapdao.get(20139);
//		
//		
//		IDMap map = new IDMap();
//		
//		map.setExt_id("zzz");
//		
//		idmapdao.save(map);
		
		
//		data_full_dao.getSessionFactory().getCurrentSession().flush();
//		ControlBatchLog batchLog = new ControlBatchLog();
//		
//		batchLog.setFile_name("");
//		batchLog.setStatus(STATUS_START);
//		
//
//		batchLog.setLast_process_step(1);
//		batchLog.setLast_process_name("IMPORT_DATA");
//		batchLog.setLast_process_status(STATUS_START);
//
////		con_batch_log_dao.save(batchLog);
//		
//		DataBatch batch = new DataBatch();
//		batch.setCtl_batch_log(batchLog);
//		batch.setStatus("");
//		batch.setEnable(1);
//		batch.setUser_id_map(findByExtId);
//		batch.setValue(10.0F);
//		batch.setItem_id_map(findByExtId);
//		batch.setType("");
//
//		
//		try {
//			batch.setRecord_date(new SimpleDateFormat("yyyyMMdd").parse("20130506"));
//			batch.setRecord_datetime(new SimpleDateFormat("yyyyMMddHHmmssSSS").parse("20130506141718111"));
//		} catch (ParseException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		data_batch_dao.save(batch);

		
//		IDMap findByExtId_2 = idmapdao.findByExtId("ggg");
		
//		System.out.println(findByExtId_2.getId());
		
		

	}
	

	


}
