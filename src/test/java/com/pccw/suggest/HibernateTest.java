package com.pccw.suggest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.pccw.suggest.exception.SuggestEngineException;
import com.pccw.suggest.service.UpdateModelService;
import com.pccw.suggest.util.FileUtil;
import com.pccw.suggest.util.PropertiesUtil;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class HibernateTest {
	
	@Autowired
	private UpdateModelService updatemodelservcie;

	@Test
	public void testIDMap() throws Exception{
		
		updatemodelservcie.updateModel();
	}
	
//	@Test
	public void testFileUtil() throws SuggestEngineException{
		System.out.println(FileUtil.findLatestCsv().getName());
	}

}
