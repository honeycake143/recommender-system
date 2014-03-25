package com.pccw.suggest.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.pccw.suggest.cons.CommonConstant;
import com.pccw.suggest.dao.ConfigFilterDao;
import com.pccw.suggest.dao.ConfigValueConvertDao;
import com.pccw.suggest.dao.ControlBatchLogDao;
import com.pccw.suggest.dao.ControlProcessLogDao;
import com.pccw.suggest.dao.DataBatchDao;
import com.pccw.suggest.dao.DataFullDao;
import com.pccw.suggest.dao.DataModelDao;
import com.pccw.suggest.dao.IDMapDao;
import com.pccw.suggest.po.ConfigFilter;
import com.pccw.suggest.po.ConfigValueConvert;
import com.pccw.suggest.po.ControlBatchLog;
import com.pccw.suggest.po.ControlProcessLog;
import com.pccw.suggest.po.DataBatch;
import com.pccw.suggest.po.DataFull;
import com.pccw.suggest.po.DataModel;
import com.pccw.suggest.po.IDMap;
import com.pccw.suggest.util.FileUtil;
import com.pccw.suggest.util.PropertiesUtil;

@Service
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class NestedService {
	
	private static final Logger logger = LoggerFactory.getLogger(NestedService.class);
	
	private static final int TIME = 0;
	private static final int DATE = 1;
	private static final int TYPE = 2;
	private static final int USER_ID = 3;
	private static final int ITEM_ID = 4;
	private static final int VALUE = 5;
	
	
	private static final String STATUS_START = "START";
	private static final String STATUS_FAILED = "FAILED";
	private static final String STATUS_END = "END";
	
	private static final String BATCH_STATUS_READY = "READY";
	private static final String BATCH_STATUS_PROCESS = "PROCESS";
	private static final String BATCH_STATUS_DONE = "DONE";
	
	@Autowired
	private BaseService bs;
	
	@Autowired
	private IDMapDao id_map_dao;
	
	@Autowired
	private DataModelDao data_model_dao; 
	
	@Autowired
	private DataFullDao data_full_dao;
	
	@Autowired
	private DataBatchDao data_batch_dao;

	@Autowired
	private ControlBatchLogDao con_batch_log_dao;
	
	@Autowired
	private ConfigFilterDao conf_filter_dao;
	
	@Autowired
	private ConfigValueConvertDao conf_value_convert_dao;
	
	@Autowired
	private ControlProcessLogDao ctl_process_log_dao;

	
	
	
	//Step_1
	

	public Boolean importData(File latestCsv){

		ControlBatchLog batchLog = new ControlBatchLog();
		
		batchLog.setFile_name(latestCsv.getName());
		batchLog.setStatus(STATUS_START);
		

		batchLog.setLast_process_step(1);
		batchLog.setLast_process_name("IMPORT_DATA");
		batchLog.setLast_process_status(STATUS_START);

		con_batch_log_dao.save(batchLog);
		
		ControlProcessLog log_start = new ControlProcessLog();    //insert every steps to ctl_process_log
		log_start.setCtl_batch_log(batchLog);
		log_start.setProcess_step(1);
		log_start.setProcess_name("IMPORT_DATA");
		log_start.setProcess_status(STATUS_START);
		
		ctl_process_log_dao.save(log_start);
		
		
		try {

			FileInputStream inputStream = new FileInputStream(latestCsv);
			InputStreamReader inputStreamReader = new InputStreamReader(inputStream,"utf-8");
			BufferedReader reader = new BufferedReader(inputStreamReader);
			
			
			
			String line = "";
			
			
			int count = 0;
			
			while((line = reader.readLine()) != null){

				count++;
				
				String[] values = line.split(",");
				
				
				IDMap user_temp_map = id_map_dao.findByExtId(values[USER_ID].trim());
				
				
				
				if(user_temp_map == null){
					user_temp_map = new IDMap();
					user_temp_map.setExt_id(values[USER_ID].trim());
					id_map_dao.save(user_temp_map);
//					con_batch_log_dao.getSessionFactory().getCurrentSession().flush();
				}
				
				
				IDMap item_temp_map = id_map_dao.findByExtId(values[ITEM_ID].trim());
				
				if(item_temp_map == null){
					item_temp_map = new IDMap();
					item_temp_map.setExt_id(values[ITEM_ID].trim());
					id_map_dao.save(item_temp_map);
//					con_batch_log_dao.getSessionFactory().getCurrentSession().flush();
				}
				
			
					
					DataBatch batch = new DataBatch();
					batch.setCtl_batch_log(batchLog);
					batch.setStatus(BATCH_STATUS_READY);
					batch.setEnable(1);
					batch.setUser_id_map(user_temp_map);
					batch.setValue(Float.valueOf(values[VALUE]));
					batch.setItem_id_map(item_temp_map);
					batch.setType(values[TYPE].trim());

					
					batch.setRecord_date(new SimpleDateFormat("yyyyMMdd").parse(values[DATE].trim()));
					batch.setRecord_datetime(new SimpleDateFormat("yyyyMMddHHmmssSSS").parse(values[TIME].trim()));
					
					data_batch_dao.save(batch);
					
					if ( count % 20 == 0 ) { //20, same as the JDBC batch size
				        //flush a batch of inserts and release memory:
						con_batch_log_dao.getSessionFactory().getCurrentSession().flush();
						con_batch_log_dao.getSessionFactory().getCurrentSession().clear();
				    }

			}
			
			
			reader.close();
			
			// mv file
			
			FileUtil.moveFile(latestCsv);
			
			batchLog.setLast_process_status(STATUS_END);
			con_batch_log_dao.update(batchLog);
			
			ControlProcessLog log = new ControlProcessLog();
			log.setCtl_batch_log(batchLog);
			log.setProcess_step(1);
			log.setProcess_name("IMPORT_DATA");
			log.setProcess_status(STATUS_END);
			
			ctl_process_log_dao.save(log);
			
			return true;

							
		} catch (Exception e) {
			
			batchLog.setLast_process_status(STATUS_FAILED);
			batchLog.setStatus(STATUS_FAILED);
			con_batch_log_dao.update(batchLog);
			
			ControlProcessLog log = new ControlProcessLog();
			log.setCtl_batch_log(batchLog);
			log.setProcess_step(1);
			log.setProcess_name("IMPORT_DATA");
			log.setProcess_status(STATUS_FAILED);
			
			ctl_process_log_dao.save(log);
			
			logger.error(e.getMessage());
			return false;
		}


	
	}
	
	
	//Step_2

	public Boolean sumFilter(ControlBatchLog batchLog){
		
		try {
			
			batchLog.setLast_process_step(2);
			batchLog.setLast_process_name("SUM_FILTER");
			batchLog.setLast_process_status(STATUS_START);
			con_batch_log_dao.update(batchLog);
			
			ControlProcessLog log_start = new ControlProcessLog();
			log_start.setCtl_batch_log(batchLog);
			log_start.setProcess_step(2);
			log_start.setProcess_name("SUM_FILTER");
			log_start.setProcess_status(STATUS_START);
			
			ctl_process_log_dao.save(log_start);
			
			
			List list = data_batch_dao.sumValueGroupByFields(batchLog.getCtl_batch_log_id());
			
			int count = 0;
			
			List<DataBatch> data_batch_list = data_batch_dao.findByBatchLogId(batchLog);
			
			for(DataBatch record : data_batch_list){
				
				count++;
				
				record.setStatus(BATCH_STATUS_PROCESS);     //data batch "process"
				data_batch_dao.update(record);
				
				if ( count % 20 == 0 ) { //20, same as the JDBC batch size
			        //flush a batch of inserts and release memory:
					con_batch_log_dao.getSessionFactory().getCurrentSession().flush();
					con_batch_log_dao.getSessionFactory().getCurrentSession().clear();
			    }
			}
			
			for(Object object  : list){
				
				count++;
				
				Map row = (HashMap)object;
				
				DataFull dataFull = new DataFull();
				
				dataFull.setType((String) row.get("type"));
				dataFull.setCtl_batch_log(batchLog);
				dataFull.setRecord_date((Date) row.get("record_date"));
				dataFull.setRecord_datetime((Date) row.get("max_record_datetime"));
				
				IDMap user_idMap = id_map_dao.get((Integer) row.get("user_id"));
				IDMap item_idMap = id_map_dao.get((Integer) row.get("item_id"));
				
				if(user_idMap != null)
					dataFull.setUser_id_map(user_idMap);
				else{
					logger.error("sumFilter : user id not found from id_map");
					return false;
				}
					
				if(item_idMap != null)
					dataFull.setItem_id_map(item_idMap);
				else{
					logger.error("sumFilter : item id not found from id_map");
					return false;
				}
				
				dataFull.setValue((Float) row.get("sum_value"));
				dataFull.setStatus(BATCH_STATUS_READY);
				
				ConfigFilter filter = conf_filter_dao.findByType((String) row.get("type"));
				
				if(filter != null){
					
				if(dataFull.getValue() > conf_filter_dao.findByType((String) row.get("type")).getMax_sum())
					dataFull.setEnable(0);
				else
					dataFull.setEnable(1);
				
				}
				
				else
					dataFull.setEnable(1);
				
				data_full_dao.save(dataFull);
				
				if ( count % 20 == 0 ) { //20, same as the JDBC batch size
			        //flush a batch of inserts and release memory:
					con_batch_log_dao.getSessionFactory().getCurrentSession().flush();
					con_batch_log_dao.getSessionFactory().getCurrentSession().clear();
			    }
			}
			
			
			List<DataBatch> data_batch_list_process = data_batch_dao.findByBatchLogId(batchLog);
			
			for(DataBatch record : data_batch_list_process){
				
				count++;
				
				record.setStatus(BATCH_STATUS_DONE);     //data batch "done"
				data_batch_dao.update(record);
				
				if ( count % 20 == 0 ) { //20, same as the JDBC batch size
			        //flush a batch of inserts and release memory:
					con_batch_log_dao.getSessionFactory().getCurrentSession().flush();
					con_batch_log_dao.getSessionFactory().getCurrentSession().clear();
			    }
			}
			
			batchLog.setLast_process_status(STATUS_END);
			con_batch_log_dao.update(batchLog);
			
			ControlProcessLog log = new ControlProcessLog();
			log.setCtl_batch_log(batchLog);
			log.setProcess_step(2);
			log.setProcess_name("SUM_FILTER");
			log.setProcess_status(STATUS_END);
			
			ctl_process_log_dao.save(log);

			return true;
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			
			batchLog.setLast_process_status(STATUS_FAILED);
			batchLog.setStatus(STATUS_FAILED);
			con_batch_log_dao.update(batchLog);
			
			ControlProcessLog log = new ControlProcessLog();
			log.setCtl_batch_log(batchLog);
			log.setProcess_step(2);
			log.setProcess_name("SUM_FILTER");
			log.setProcess_status(STATUS_FAILED);
			
			ctl_process_log_dao.save(log);
			
			logger.error(e.getMessage());
			return false;
		}
	}
	
	
	//Step_3

	public Boolean valueConvert(ControlBatchLog batchLog){
		
		try {
			
			batchLog.setLast_process_step(3);
			batchLog.setLast_process_name("VALUE_CONVERT");
			batchLog.setLast_process_status(STATUS_START);
			con_batch_log_dao.update(batchLog);
			
			ControlProcessLog log_start = new ControlProcessLog();
			log_start.setCtl_batch_log(batchLog);
			log_start.setProcess_step(3);
			log_start.setProcess_name("VALUE_CONVERT");
			log_start.setProcess_status(STATUS_START);
			
			ctl_process_log_dao.save(log_start);
			
			List<DataFull> list = data_full_dao.findManyByBatchLogId(batchLog);
			
			int count = 0;
			
			for(DataFull df : list){
				
				count++;
				
				ConfigValueConvert convert = conf_value_convert_dao.findByType(df.getType());
				
				if(convert != null){
					
					
					df.setStatus(BATCH_STATUS_PROCESS);
					data_full_dao.update(df);
					
					float conv_value = df.getValue() * convert.getMultiply_factor() + convert.getAddition_factor();
					
					if(conv_value > convert.getMax_value())
						conv_value = convert.getMax_value();
					
					if(conv_value < convert.getMin_value())
						conv_value = convert.getMax_value();
					
					DataModel model = new DataModel();
					
					model.setCtl_batch_log(batchLog);
					model.setData_full(df);
					model.setEnable(1);
					model.setRecord_date(df.getRecord_date());
					model.setRecord_datetime(df.getRecord_datetime());
					model.setItem_id_map(df.getItem_id_map());
					model.setUser_id_map(df.getUser_id_map());
					model.setStatus(BATCH_STATUS_READY);
					model.setValue(conv_value);
					
					data_model_dao.save(model);
					
					df.setStatus(BATCH_STATUS_DONE);
					data_full_dao.update(df);
					
				}else{
					
					df.setStatus(BATCH_STATUS_PROCESS);
					data_full_dao.update(df);
					
					DataModel model = new DataModel();
					
					model.setCtl_batch_log(batchLog);
					model.setData_full(df);
					model.setEnable(1);
					model.setRecord_date(df.getRecord_date());
					model.setRecord_datetime(df.getRecord_datetime());
					model.setItem_id_map(df.getItem_id_map());
					model.setUser_id_map(df.getUser_id_map());
					model.setStatus(BATCH_STATUS_READY);
					model.setValue(10.0F);
					
					data_model_dao.save(model);
					
					df.setStatus(BATCH_STATUS_DONE);
					data_full_dao.update(df);
				}
				
				if ( count % 20 == 0 ) { //20, same as the JDBC batch size
			        //flush a batch of inserts and release memory:
					con_batch_log_dao.getSessionFactory().getCurrentSession().flush();
					con_batch_log_dao.getSessionFactory().getCurrentSession().clear();
			    }
			}
			
			batchLog.setLast_process_status(STATUS_END);
			con_batch_log_dao.update(batchLog);
			
			ControlProcessLog log = new ControlProcessLog();
			log.setCtl_batch_log(batchLog);
			log.setProcess_step(3);
			log.setProcess_name("VALUE_CONVERT");
			log.setProcess_status(STATUS_END);
			
			ctl_process_log_dao.save(log);
			
			return true;
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			
			batchLog.setLast_process_status(STATUS_FAILED);
			batchLog.setStatus(STATUS_FAILED);
			con_batch_log_dao.update(batchLog);
			
			ControlProcessLog log = new ControlProcessLog();
			log.setCtl_batch_log(batchLog);
			log.setProcess_step(3);
			log.setProcess_name("VALUE_CONVERT");
			log.setProcess_status(STATUS_FAILED);
			
			ctl_process_log_dao.save(log);
			
			logger.error(e.getMessage());
			return false;
		}
	}
	
	//Step_4

	public Boolean feedMahout(ControlBatchLog batchLog){
		
		try {
			
			batchLog.setLast_process_step(4);
			batchLog.setLast_process_name("FEED_MAHOUT");
			batchLog.setLast_process_status(STATUS_START);
			con_batch_log_dao.update(batchLog);
			
			ControlProcessLog log_start = new ControlProcessLog();
			log_start.setCtl_batch_log(batchLog);
			log_start.setProcess_step(4);
			log_start.setProcess_name("FEED_MAHOUT");
			log_start.setProcess_status(STATUS_START);
			
			ctl_process_log_dao.save(log_start);
			
			FileUtil.backUpModelFile();
			
			List<DataModel> list = data_model_dao.findByBatchLogId(batchLog);
			
			FileOutputStream outputStream = new FileOutputStream(PropertiesUtil.getModelTempCsv() + batchLog.getCtl_batch_log_id() + ".csv");
			OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream, "utf-8");
			BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
			
			for(DataModel dm : list){
				
				dm.setStatus(BATCH_STATUS_PROCESS);
				
				bufferedWriter.write(dm.getUser_id_map().getId() + ",");
				
				bufferedWriter.write(dm.getItem_id_map().getId() + ",");
				
				bufferedWriter.write(String.valueOf(dm.getValue()));
				
				bufferedWriter.newLine();
				
				dm.setStatus(BATCH_STATUS_DONE);
			}
			
			bufferedWriter.flush();
			bufferedWriter.close();
			
			FileUtil.compressGzipFile(PropertiesUtil.getModelTempCsv() + batchLog.getCtl_batch_log_id() + ".csv", PropertiesUtil.getModelFilePath() + System.currentTimeMillis() + ".csv.gz");
			
			bs.refreshModel();
			
			batchLog.setLast_process_status(STATUS_END);
			batchLog.setStatus(STATUS_END);
			con_batch_log_dao.update(batchLog);
			
			ControlProcessLog log = new ControlProcessLog();
			log.setCtl_batch_log(batchLog);
			log.setProcess_step(4);
			log.setProcess_name("FEED_MAHOUT");
			log.setProcess_status(STATUS_END);
			
			ctl_process_log_dao.save(log);
			
			return true;
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			
			batchLog.setLast_process_status(STATUS_FAILED);
			batchLog.setStatus(STATUS_FAILED);
			con_batch_log_dao.update(batchLog);
			
			ControlProcessLog log = new ControlProcessLog();
			log.setCtl_batch_log(batchLog);
			log.setProcess_step(4);
			log.setProcess_name("FEED_MAHOUT");
			log.setProcess_status(STATUS_FAILED);
			
			ctl_process_log_dao.save(log);
			
			logger.error(e.getMessage());
			return false;
		}
	}
	
	
}
