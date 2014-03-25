package com.pccw.suggest.util;

import java.io.IOException;
import java.util.Properties;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import com.pccw.suggest.cons.CommonConstant;

public class PropertiesUtil {

	
	private static Properties props;
	
	static{
		
		Resource resource = new ClassPathResource("/suggest_engine.properties");
		try {
			props = PropertiesLoaderUtils.loadProperties(resource);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static String getServingLayerPath(){
		return props.getProperty(CommonConstant.SERVING_LAYER_PATH);
	}
	
	public static String getUrlReady(){
		return props.getProperty(CommonConstant.URL_READY);
	}
	
	public static String getUrlSetPref(){
		return props.getProperty(CommonConstant.URL_SET_PREF);
	}
	
	public static String getUrlRemovePref(){
		return props.getProperty(CommonConstant.URL_REMOVE_PREF);
	}
	
	public static String getUrlSetUserTag(){
		return props.getProperty(CommonConstant.URL_SET_USER_TAG);
	}
	
	public static String getUrlSetItemTag(){
		return props.getProperty(CommonConstant.URL_SET_ITEM_TAG);
	}
	
	public static String getUrlRecommend(){
		return props.getProperty(CommonConstant.URL_RECOMMEND);
	}
	
	public static String getUrlRecommendToMany(){
		return props.getProperty(CommonConstant.URL_RECOMMEND_TO_MANY);
	}
	
	public static String getUrlRecommendToAnonymous(){
		return props.getProperty(CommonConstant.URL_RECOMMEND_TO_ANONYMOUS);
	}
	
	public static String getUrlMostSimilarItems(){
		return props.getProperty(CommonConstant.URL_MOST_SIMILAR_ITEMS);
	}
	
	public static String getUrlSimilarityToItem(){
		return props.getProperty(CommonConstant.URL_SIMILARITY_TO_ITEM);
	}
	
	public static String getUrlEstimate(){
		return props.getProperty(CommonConstant.URL_ESTIMATE);
	}
	
	public static String getUrlEstimateForAnonymous(){
		return props.getProperty(CommonConstant.URL_ESTIMATE_FOR_ANONYMOUS);
	}
	
	
	public static String getUrlBecause(){
		return props.getProperty(CommonConstant.URL_BECAUSE);
	}
	
	
	public static String getUrlMostPopularItems(){
		return props.getProperty(CommonConstant.URL_MOST_POPULAR_ITEMS);
	}
	
	public static String getUrlRefresh(){
		return props.getProperty(CommonConstant.URL_REFRESH);
	}
	
	public static String getUrlGetAllUserIDS(){
		return props.getProperty(CommonConstant.URL_GET_ALL_USER_IDS);
	}
	
	public static String getUrlGetAllItemIDS(){
		return props.getProperty(CommonConstant.URL_GET_ALL_ITEM_IDS);
	}
	
	
	public static String getRecommendToAnonymousItemDefalutValue(){
		return props.getProperty(CommonConstant.RECOMMEND_TO_ANONYMOUS_ITEM_DEFALUT_VALUE);
	}

	public static String getRecommendListDefalutCount(){
		return props.getProperty(CommonConstant.RECOMMEND_LIST_DEFALUT_COUNT);
	}
	
	public static String getRecommendListDefalutCKI(){
		return props.getProperty(CommonConstant.RECOMMEND_LIST_DEFALUT_CKI);
	}
	
	public static String getRawCsvFilePath(){
		return props.getProperty(CommonConstant.RAW_CSV_FILE_PATH);
	}
	
	public static String getCompleteCsvFilePath(){
		return props.getProperty(CommonConstant.COMPLETE_CSV_FILE_PATH);
	}
	
	public static String getModelFilePath(){
		return props.getProperty(CommonConstant.MODEL_FILE_PATH);
	}
	
	public static String getModelFileBackupPath(){
		return props.getProperty(CommonConstant.MODEL_FILE_BACKUP_PATH);
	}
	
	public static String getModelTempCsv(){
		return props.getProperty(CommonConstant.MODEL_TEMP_CSV);
	}
	
	
}
