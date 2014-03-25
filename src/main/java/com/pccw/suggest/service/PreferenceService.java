package com.pccw.suggest.service;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.pccw.suggest.cons.CommonConstant;
import com.pccw.suggest.exception.SuggestEngineException;
import com.pccw.suggest.util.PropertiesUtil;
import com.pccw.suggest.util.StringUtil;

@Service
public class PreferenceService {
	
	private static final Logger logger = LoggerFactory.getLogger(PreferenceService.class);
	
	@Autowired
	private RestTemplate restTemplate;
	
	public void setPreference(Map<String, String> arguments, String strength) throws SuggestEngineException{
		
		try {
			restTemplate.postForLocation(PropertiesUtil.getServingLayerPath() + PropertiesUtil.getUrlSetPref() + "{userId}" + "/" + "{itemId}", strength, arguments);
		} catch (RestClientException e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
			
			logger.error(e.getMessage());
			String[] split = StringUtil.checkErrorCode(e.getMessage()).split(",");
			throw new SuggestEngineException(split[0],split[1]);
		}
	}
	
	public void removePreference(Map<String, String> arguments) throws SuggestEngineException{
		try {
			restTemplate.delete(PropertiesUtil.getServingLayerPath() + PropertiesUtil.getUrlRemovePref() + "{userId}" + "/" + "{itemId}", arguments);
		} catch (RestClientException e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
			logger.error(e.getMessage());
			String[] split = StringUtil.checkErrorCode(e.getMessage()).split(",");
			throw new SuggestEngineException(split[0],split[1]);
		}
	}
	
	public void setUserTag(Map<String, String> arguments, String strength) throws SuggestEngineException{
		try {
			restTemplate.postForLocation(PropertiesUtil.getServingLayerPath() + PropertiesUtil.getUrlSetUserTag() + "{userId}" + "/" + "{tag}", strength, arguments);
		} catch (RestClientException e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
			
			logger.error(e.getMessage());
			String[] split = StringUtil.checkErrorCode(e.getMessage()).split(",");
			throw new SuggestEngineException(split[0],split[1]);
		}
	}
	
	public void setItemTag(Map<String, String> arguments, String strength) throws SuggestEngineException{
		try {
			restTemplate.postForLocation(PropertiesUtil.getServingLayerPath() + PropertiesUtil.getUrlSetItemTag() + "{itemId}" + "/" + "{tag}", strength, arguments);
		} catch (RestClientException e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
			
			logger.error(e.getMessage());
			String[] split = StringUtil.checkErrorCode(e.getMessage()).split(",");
			throw new SuggestEngineException(split[0],split[1]);
		}
	}
	
	
}
