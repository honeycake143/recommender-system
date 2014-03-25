package com.pccw.suggest.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.zip.GZIPOutputStream;

import org.springframework.util.FileCopyUtils;

import com.pccw.suggest.cons.CommonConstant;
import com.pccw.suggest.exception.SuggestEngineException;

public class FileUtil {
	


	public static File findLatestCsv() throws SuggestEngineException{
		
		File csv_dir = new File(PropertiesUtil.getRawCsvFilePath());
		
		if(csv_dir.isDirectory()){
			File[] files = csv_dir.listFiles();
			
			if(files.length == 0){
				
				return null;
			}
			
			long min_mod_date = Long.MAX_VALUE;
			File earliest_file = null;
			
			//validate config file name
			
			for(File f : files){
				if(f.lastModified() < min_mod_date){
					min_mod_date = f.lastModified();
					earliest_file = f;
				}
			}
			
			return earliest_file;
			
		}else{
			throw new SuggestEngineException("Incomming csv file path error","500");
		}
	}
	
	public static boolean moveFile(File src){
		
 	   if(src.renameTo(new File(PropertiesUtil.getCompleteCsvFilePath() + src.getName()))){
 		
 		   System.out.println("File is moved successful!");
 		
 		   return true;
 		   
 	   }else{
 		   
 		System.out.println("File is failed to move!");
 		
 		 return false;
 	   }
		
	}
	
	public static boolean backUpModelFile() throws IOException{
		
		File model_dir = new File(PropertiesUtil.getModelFilePath());
		
		File model_backup_dir = new File(PropertiesUtil.getModelFileBackupPath() + new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
		
		if(model_backup_dir.exists()){
			
			if(model_backup_dir.isDirectory()){
				File[] files = model_backup_dir.listFiles();
				
				for(File f : files){
					f.delete();
				}
			}
			
			model_backup_dir.delete();
		}
		
		model_backup_dir.mkdir();
		
		if(model_dir.isDirectory()){
			
			File[] files = model_dir.listFiles();
			
			for(File f : files){
				
				File backup_file = new File(model_backup_dir + File.separator + f.getName());
				
			
				FileCopyUtils.copy(f, backup_file);

			}
		}
		
		return true;

	}
	
    public static void compressGzipFile(String file, String gzipFile) {
        
    	  FileInputStream fis = null;
          FileOutputStream fos = null;
          GZIPOutputStream gzipOS = null;
    	
    	try {
    		fis = new FileInputStream(file);
            fos = new FileOutputStream(gzipFile);
            gzipOS = new GZIPOutputStream(fos);
            byte[] buffer = new byte[1024];
            int len;
            while((len=fis.read(buffer)) != -1){
                gzipOS.write(buffer, 0, len);
            }
            //close resources
            gzipOS.close();
            fos.close();
            fis.close();
        } catch (IOException e) {
        
        	e.printStackTrace();
       
        }finally{
        	
        	try {
				if(fis!=null)
					fis.close();
				
				if(fos!=null)
					fos.close();
				
				if(gzipOS!=null)
					gzipOS.close();
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
         
    }
	
	
}
