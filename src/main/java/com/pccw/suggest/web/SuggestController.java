package com.pccw.suggest.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.pccw.suggest.bean.ExceptionResponse;
import com.pccw.suggest.bean.RecommandItem;
import com.pccw.suggest.bean.RecommandResponse;
import com.pccw.suggest.bean.RecommendList;
import com.pccw.suggest.cons.CommonConstant;
import com.pccw.suggest.exception.SuggestEngineException;
import com.pccw.suggest.service.ItemService;
import com.pccw.suggest.service.RecommendService;
import com.pccw.suggest.util.PropertiesUtil;


@Controller
public class SuggestController {

	private static final Logger logger = LoggerFactory.getLogger(SuggestController.class);
	
	@Autowired
	private ItemService itemService;
	
	@Autowired
	private RecommendService recommendService;
	
	//example /userbase/single/12345
	@RequestMapping(value = "/userbase/single/{userId}", method = RequestMethod.GET)
	public ModelAndView recommandToSingle(@PathVariable("userId") String userId,  @RequestParam(value = "count", required = false) Integer count,
										  @RequestParam(value = "cki", required = false) Boolean cki) throws SuggestEngineException {
			
		ModelAndView mav = new ModelAndView();
		mav.setViewName("responseXml");
		
		
		HashMap<String, String> map = new HashMap<String, String>();
		
		map.put("userId", userId);
		
		if(null != count)
			map.put("size", String.valueOf(count));
		else
			map.put("size", PropertiesUtil.getRecommendListDefalutCount());
		
		if(null != cki)
			map.put("cki", String.valueOf(cki));
		else
			map.put("cki", PropertiesUtil.getRecommendListDefalutCKI());
		
		
		RecommendList list = recommendService.recommendToSingle(map);   //throw exception
		
		RecommandResponse recommandResponse = new RecommandResponse();
		List<RecommandItem> reclist = new ArrayList<RecommandItem>();
		
		for(ArrayList<String> temp: list.getList()){
			RecommandItem recommandItem = new RecommandItem();
			recommandItem.setId(temp.get(0));
			recommandItem.setScore(temp.get(1));
			reclist.add(recommandItem);
		}
		
		
		recommandResponse.setStatus("200");
		recommandResponse.setCount(map.get("size"));
		recommandResponse.setCki(map.get("cki"));
		recommandResponse.setRecommandlist(reclist);
		
		
		mav.addObject("responseList", recommandResponse);
		
		return mav;
	}
	
	
	//example /userbase/multi/1345,abc,efg?count=20&cki=true
	@RequestMapping(value = "/userbase/multi/{params}", method = RequestMethod.GET)
	public ModelAndView recommandToMany(@PathVariable("params") String params, @RequestParam(value = "count", required = false) Integer count,
			  							@RequestParam(value = "cki", required = false) Boolean cki) throws SuggestEngineException {
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("home");
		
		HashMap<String, String> map = new HashMap<String, String>();
		StringBuilder url_param = new StringBuilder();
		
		String[] userId = params.split(",");
		
		logger.info("Id num " + userId.length);
		
		
		
		for(int i = 0; i < userId.length; i++){
			
			logger.info("userId: " + userId[i]);
			
			url_param.append("{userId_" + i + "}/");
			
			map.put("userId_" + i, userId[i]);
			
		}
		
		
		if(null != count)
			map.put("size", String.valueOf(count));
		else
			map.put("size", PropertiesUtil.getRecommendListDefalutCount());
		
		if(null != cki)
			map.put("cki", String.valueOf(cki));
		else
			map.put("cki", PropertiesUtil.getRecommendListDefalutCKI());
		
		logger.info("count " + count);
		logger.info("cki " + cki);
		
		RecommendList list = recommendService.recommendToMany(url_param.toString(), map);
		
		String str = list.getList().get(0).get(0);
		
		mav.addObject("list", str);
		
		return mav;
	}
	
	
	//need to inform motion to value
	//example /userbase/anonymous/abc=123,bcd=234?count=20
	@RequestMapping(value = "/userbase/anonymous/{params}", method = RequestMethod.GET)
	public ModelAndView recommandToAnonymous(@PathVariable("params") String params, @RequestParam(value = "count", required = false) Integer count) throws SuggestEngineException {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("home");
		
		HashMap<String, String> map = new HashMap<String, String>();
		StringBuilder url_param = new StringBuilder();
		
		String[] item_value = params.split(",");
		
		logger.info("Id num " + item_value.length);
		
		for(int i = 0; i < item_value.length; i++){
			
			logger.info("item_value: " + item_value[i]);
			
			String[] pair = item_value[i].split("=");
			
			url_param.append("{itemId_" + i + "}={value_" + i + "}/");
			
			
			map.put("itemId_" + i, pair[0]);
			logger.info("itemId: " + pair[0]);
			
			if(pair.length == 2){
				
				map.put("value_" + i, pair[1]);
				
				logger.info("value: " + pair[1]);
			}else{
				map.put("value_" + i, PropertiesUtil.getRecommendToAnonymousItemDefalutValue());
//				logger.info("itemId: " + pair[1]);
			}
	
		}
		
		if(null != count)
			map.put("size", String.valueOf(count));
		else
			map.put("size", PropertiesUtil.getRecommendListDefalutCount());
		
		RecommendList list = recommendService.recommendToAnonymous(url_param.toString(), map);
		
		String str = list.getList().get(0).get(0);
		
		mav.addObject("list", str);
		
		return mav;
	}
	
	
	//example /itembase/similarItems/abc,bcd,efg
	@RequestMapping(value = "/itembase/similarItems/{params}", method = RequestMethod.GET)
	public ModelAndView findSimilarItems(@PathVariable("params") String params, @RequestParam(value = "count", required = false) Integer count) throws SuggestEngineException {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("home");
		
		HashMap<String, String> map = new HashMap<String, String>();
		StringBuilder url_param = new StringBuilder();
		
		
		String[] itemId = params.split(",");
		
		logger.info("Id num " + itemId.length);
		
		map.put("item_count", String.valueOf(itemId.length));
		
		for(int i = 0; i < itemId.length; i++){
			
			logger.info("itemId: " + itemId[i]);
			
			url_param.append("{itemId_" + i + "}/");
			
			map.put("itemId_" + i, itemId[i]);
			
		}
		
		if(null != count)
			map.put("size", String.valueOf(count));
		else
			map.put("size", PropertiesUtil.getRecommendListDefalutCount());
		
		
		
		logger.info("count " + count);
		
		RecommendList list = itemService.mostSimilarItems(url_param.toString(), map); // throw exception
		
		RecommandResponse recommandResponse = new RecommandResponse();
		List<RecommandItem> reclist = new ArrayList<RecommandItem>();
		
		for(ArrayList<String> temp: list.getList()){
			RecommandItem recommandItem = new RecommandItem();
			recommandItem.setId(temp.get(0));
			recommandItem.setScore(temp.get(1));
			reclist.add(recommandItem);
		}
		
		
		recommandResponse.setStatus("200");
		recommandResponse.setCount(map.get("size"));
//		recommandResponse.setCki(map.get("cki"));
		recommandResponse.setRecommandlist(reclist);
		
		
		mav.addObject("responseList", recommandResponse);
		
		return mav;	
	}
	
	
	//example /itembase/itemSimilarity/abc/def,efg,123
	@RequestMapping(value = "/itembase/itemSimilarity/{toItemId}/{params}", method = RequestMethod.GET)
	public ModelAndView getItemSimilarity(@PathVariable("toItemId") String toItemId, @PathVariable("params") String params) throws SuggestEngineException {
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("home");
		
		HashMap<String, String> map = new HashMap<String, String>();
		StringBuilder url_param = new StringBuilder();
		
		String[] itemId = params.split(",");
		
		logger.info("Id num " + itemId.length);
		
		url_param.append("{toItemId}/");
		map.put("toItemId", toItemId);
		
		for(int i = 0; i < itemId.length; i++){
			
			logger.info("itemId: " + itemId[i]);
			
			url_param.append("{itemId_" + i + "}/");
			
			map.put("itemId_" + i, itemId[i]);
			
		}
		
		String[] items = itemService.similarityToItem(url_param.toString(), map);
		
		mav.addObject("list", items);
		
		return mav;
		
	}
	
	
	//example /itembase/mostPopular?count=20
	@RequestMapping(value = "/itembase/mostPopular", method = RequestMethod.GET)
	public ModelAndView findMostPopularItems(@RequestParam(value = "count", required = false) Integer count) throws SuggestEngineException {
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("home");
		
		HashMap<String, String> map = new HashMap<String, String>();
		
		
		if(null != count)
			map.put("size", String.valueOf(count));
		else
			map.put("size", PropertiesUtil.getRecommendListDefalutCount());
		
		RecommendList list = itemService.mostPopularItems(map);			// throw exception
		
		
		RecommandResponse recommandResponse = new RecommandResponse();
		List<RecommandItem> reclist = new ArrayList<RecommandItem>();
		
		for(ArrayList<String> temp: list.getList()){
			RecommandItem recommandItem = new RecommandItem();
			recommandItem.setId(temp.get(0));
			recommandItem.setScore(temp.get(1));
			reclist.add(recommandItem);
		}
		
		
		recommandResponse.setStatus("200");
//		recommandResponse.setCount(map.get("size"));
//		recommandResponse.setCki(map.get("cki"));
		recommandResponse.setRecommandlist(reclist);
		
		
		mav.addObject("responseList", recommandResponse);
		
		return mav;
	}
	
	
	//ExceptionHandling
	@ExceptionHandler(SuggestEngineException.class)
	public ModelAndView handleException(SuggestEngineException re, HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("responseXml");
		
		ExceptionResponse response = new ExceptionResponse();
		response.setStatus(re.getErrorCode());
		response.setMessage(re.getMessage());
		
		
		mav.addObject("responseList", response);
		return mav;
	}
}
