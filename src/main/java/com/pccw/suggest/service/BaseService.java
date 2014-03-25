package com.pccw.suggest.service;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pccw.suggest.bean.IDList;
import com.pccw.suggest.bean.RecommendList;
import com.pccw.suggest.cons.CommonConstant;
import com.pccw.suggest.exception.SuggestEngineException;
import com.pccw.suggest.util.PropertiesUtil;
import com.pccw.suggest.util.StringUtil;

@Service
public class BaseService {
	
	private static final Logger logger = LoggerFactory.getLogger(BaseService.class);
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private ObjectMapper mapper;

	public boolean isEngineReady() throws SuggestEngineException{
		
		try {
			
			restTemplate.getForEntity(PropertiesUtil.getServingLayerPath() + PropertiesUtil.getUrlReady(), String.class);
			
		} catch (RestClientException e) {
			// TODO Auto-generated catch block
			
			logger.error(e.getMessage());
			String[] split = StringUtil.checkErrorCode(e.getMessage()).split(",");
			throw new SuggestEngineException(split[0],split[1]);

		}
		
		return true;
		
	}
	
	public void refreshModel() throws SuggestEngineException{
		try {
			restTemplate.postForLocation(PropertiesUtil.getServingLayerPath() + PropertiesUtil.getUrlRefresh(), null);
		} catch (RestClientException e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage());
			String[] split = StringUtil.checkErrorCode(e.getMessage()).split(",");
			throw new SuggestEngineException(split[0],split[1]);
		}
	}
	
	
	public IDList getAllUserIds() throws SuggestEngineException{
		
			try {
				IDList list = null;

				String rawlist = restTemplate.getForObject(PropertiesUtil.getServingLayerPath() + PropertiesUtil.getUrlGetAllUserIDS(), String.class);
				
				try {
					list = mapper.readValue(StringUtil.convertRecommandList(rawlist), IDList.class);
				} catch (JsonParseException e) {
					// TODO Auto-generated catch block
//				e.printStackTrace();
					
					logger.error(e.getMessage());
				} catch (JsonMappingException e) {
					// TODO Auto-generated catch block
//				e.printStackTrace();
					
					logger.error(e.getMessage());
				} catch (IOException e) {
					// TODO Auto-generated catch block
//				e.printStackTrace();
					logger.error(e.getMessage());
				}
				
				return list;
			} catch (RestClientException e) {
				// TODO Auto-generated catch block
//				e.printStackTrace();
				logger.error(e.getMessage());
				String[] split = StringUtil.checkErrorCode(e.getMessage()).split(",");
				throw new SuggestEngineException(split[0],split[1]);
			}
	}
	
	public IDList getAllItemIds() throws SuggestEngineException{
		try {
			IDList list = null;

			String rawlist = restTemplate.getForObject(PropertiesUtil.getServingLayerPath() + PropertiesUtil.getUrlGetAllItemIDS(), String.class);
			
			try {
				list = mapper.readValue(StringUtil.convertRecommandList(rawlist), IDList.class);
			} catch (JsonParseException e) {
				// TODO Auto-generated catch block
//			e.printStackTrace();
				
				logger.error(e.getMessage());
			} catch (JsonMappingException e) {
				// TODO Auto-generated catch block
//			e.printStackTrace();
				
				logger.error(e.getMessage());
			} catch (IOException e) {
				// TODO Auto-generated catch block
//			e.printStackTrace();
				logger.error(e.getMessage());
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
	
	public void sayhello(){
		System.out.println("baseService");
	}
	

	
	

	
}
