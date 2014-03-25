package com.pccw.suggest.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pccw.suggest.bean.RecommendList;
import com.pccw.suggest.cons.CommonConstant;
import com.pccw.suggest.dao.IDMapDao;
import com.pccw.suggest.exception.SuggestEngineException;
import com.pccw.suggest.po.IDMap;
import com.pccw.suggest.util.PropertiesUtil;
import com.pccw.suggest.util.StringUtil;

@Service
public class RecommendService {
	private static final Logger logger = LoggerFactory.getLogger(RecommendService.class);
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private IDMapDao id_map_dao;
	
	@Autowired
	private ObjectMapper mapper;
	
	
	@Transactional
	public RecommendList recommendToSingle(Map<String, String> arguments) throws SuggestEngineException{
		try {
			
			RecommendList list = null;
			
			String ext_userid = arguments.get("userId");
			
			IDMap id = id_map_dao.findByExtId(ext_userid);
			
			if(id == null){
				throw new SuggestEngineException("user id " + ext_userid + " not found", "404");
			}
			
			arguments.put("userId", String.valueOf(id.getId()));     //change ext_id to internal id
			
			String rawlist = restTemplate.getForObject(PropertiesUtil.getServingLayerPath() + PropertiesUtil.getUrlRecommend() + "{userId}" + "?howMany={size}&considerKnownItems={cki}"
														, String.class, arguments);
			
			
			try {
				list = mapper.readValue(StringUtil.convertRecommandList(rawlist), RecommendList.class);
				
				Iterator<ArrayList<String>> iterator = list.getList().iterator();
				
				while(iterator.hasNext()){   //change internal id to ext_id
					
					ArrayList<String> next = iterator.next();
					
					String ext_id = id_map_dao.get(Integer.valueOf(next.get(0))).getExt_id();
					
					next.set(0, ext_id);
					
				}
				
			} catch (JsonParseException e) {
				// TODO Auto-generated catch block
//				e.printStackTrace();
				logger.error(e.getMessage());
//				throw new SuggestEngineException(e.getMessage());
				
			} catch (JsonMappingException e) {
				// TODO Auto-generated catch block
//				e.printStackTrace();
				logger.error(e.getMessage());
//				throw new SuggestEngineException(e.getMessage());
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
//				e.printStackTrace();
				logger.error(e.getMessage());
//				throw new SuggestEngineException(e.getMessage());
			}
			
			return list;
			
		} catch (RestClientException e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
			logger.error(e.getMessage());
			String[] split = StringUtil.checkErrorCode(e.getMessage()).split(",");
			throw new SuggestEngineException(split[0],split[1]);
		}
	}
	
	
	public RecommendList recommendToMany(String url_param, Map<String, String> arguments) throws SuggestEngineException{
		try {
			
			RecommendList list = null;
			String rawlist = restTemplate.getForObject(PropertiesUtil.getServingLayerPath() + PropertiesUtil.getUrlRecommendToMany() + url_param + "?howMany={size}&considerKnownItems={cki}"
														, String.class, arguments);
			
			
			try {
				list = mapper.readValue(StringUtil.convertRecommandList(rawlist), RecommendList.class);
			} catch (JsonParseException e) {
				// TODO Auto-generated catch block
//				e.printStackTrace();
				logger.error(e.getMessage());
//				throw new SuggestEngineException(e.getMessage());
				
			} catch (JsonMappingException e) {
				// TODO Auto-generated catch block
//				e.printStackTrace();
				logger.error(e.getMessage());
//				throw new SuggestEngineException(e.getMessage());
				 
			} catch (IOException e) {
				// TODO Auto-generated catch block
//				e.printStackTrace();
				logger.error(e.getMessage());
//				throw new SuggestEngineException(e.getMessage());
			}
			
			return list;
			
		} catch (RestClientException e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
			logger.error(e.getMessage());
			String[] split = StringUtil.checkErrorCode(e.getMessage()).split(",");
			throw new SuggestEngineException(split[0],split[1]);
		}
	}
	
	
	public RecommendList recommendToAnonymous(String url_param, Map<String, String> arguments) throws SuggestEngineException{
		try {
			
			RecommendList list = null;
			String rawlist = restTemplate.getForObject(PropertiesUtil.getServingLayerPath() + PropertiesUtil.getUrlRecommendToAnonymous() + url_param + "?howMany={size}"
														, String.class, arguments);
			
			
			try {
				list = mapper.readValue(StringUtil.convertRecommandList(rawlist), RecommendList.class);
			} catch (JsonParseException e) {
				// TODO Auto-generated catch block
//				e.printStackTrace();
				logger.error(e.getMessage());
//				throw new SuggestEngineException(e.getMessage());
				
			} catch (JsonMappingException e) {
				// TODO Auto-generated catch block
//				e.printStackTrace();
				logger.error(e.getMessage());
//				throw new SuggestEngineException(e.getMessage());
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
//				e.printStackTrace();
				logger.error(e.getMessage());
//				throw new SuggestEngineException(e.getMessage());
			}
			
			return list;
			
		} catch (RestClientException e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
			logger.error(e.getMessage());
			String[] split = StringUtil.checkErrorCode(e.getMessage()).split(",");
			throw new SuggestEngineException(split[0],split[1]);
		}
	}
	
	

}
