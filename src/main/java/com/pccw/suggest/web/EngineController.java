package com.pccw.suggest.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.pccw.suggest.exception.SuggestEngineException;
import com.pccw.suggest.service.BaseService;
import com.pccw.suggest.service.EstimateService;
import com.pccw.suggest.service.UpdateModelService;


@Controller
public class EngineController {

	private static final Logger logger = LoggerFactory.getLogger(EngineController.class);
	
	@Autowired
	private BaseService baseService;
	
	@Autowired
	private EstimateService estimateService;

	@Autowired
	private UpdateModelService update_model_service;
	
	@RequestMapping(value = "/testRefresh", method = RequestMethod.GET)
	public void testRefresh() {
		
		ModelAndView mav = new ModelAndView();
		
		mav.setViewName("test");
		
		try {
			update_model_service.updateModel();
		} catch (SuggestEngineException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	
	
	
}
