package com.pccw.suggest.service;

import java.io.IOException;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pccw.suggest.bean.RecommendList;
import com.pccw.suggest.cons.CommonConstant;
import com.pccw.suggest.exception.SuggestEngineException;
import com.pccw.suggest.util.PropertiesUtil;
import com.pccw.suggest.util.StringUtil;


@Service
public class EstimateService {
	
	private static final Logger logger = LoggerFactory.getLogger(EstimateService.class);
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private ObjectMapper mapper;
	
	public String[] estimate(String url_param, Map<String, String> arguments) throws SuggestEngineException{
		try {
			

			String rawlist = restTemplate.getForObject(PropertiesUtil.getServingLayerPath() + PropertiesUtil.getUrlEstimate() + url_param
														, String.class, arguments);
			
			return StringUtil.convertFloatingPointList(rawlist);
			

			
		} catch (RestClientException e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
			logger.error(e.getMessage());
			String[] split = StringUtil.checkErrorCode(e.getMessage()).split(",");
			throw new SuggestEngineException(split[0],split[1]);
		}
	}
	
	
	public String estimateForAnonymous(String url_param, Map<String, String> arguments) throws SuggestEngineException{
		try {
			

			return restTemplate.getForObject(PropertiesUtil.getServingLayerPath() + PropertiesUtil.getUrlEstimateForAnonymous() + url_param
														, String.class, arguments);
			

		} catch (RestClientException e) {
			
			// TODO Auto-generated catch block
//			e.printStackTrace();
			logger.error(e.getMessage());
			String[] split = StringUtil.checkErrorCode(e.getMessage()).split(",");
			throw new SuggestEngineException(split[0],split[1]);
		}
	}
	
	public RecommendList because(Map<String, String> arguments) throws SuggestEngineException{
		try {
			
			RecommendList list = null;
			String rawlist = restTemplate.getForObject(PropertiesUtil.getServingLayerPath() + PropertiesUtil.getUrlBecause() + "{userId}" + "/" + "{itemId}" + "?howMany={size}"
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
