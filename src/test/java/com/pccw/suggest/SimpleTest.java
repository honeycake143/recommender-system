package com.pccw.suggest;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import org.junit.Test;




public class SimpleTest {

//	@Test
	public void simpleRun() throws ParseException{
		
		SimpleDateFormat dateformat = new SimpleDateFormat("yyyyMMdd");
		
		Date date = dateformat.parse(" 20130823");
		
		java.sql.Date sqlDate = new java.sql.Date(date.getTime());
		
		System.out.println(date);
		
	}
	
	
//	@Test
	public void genTestData(){
		
		 int START = 8000;
		 int END = 9999;
		 Random random = new Random();
		 
		
		 
		 Random randomGenerator = new Random();
		
		String[] type = {"click","like","rate","buy"};
		
		SimpleDateFormat dateformat_time = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		SimpleDateFormat dateformat_date = new SimpleDateFormat("yyyyMMdd");
		
		

		
		try {
			FileOutputStream outputStream = new FileOutputStream("F:\\csv\\process\\gen_sample.csv");
			OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream, "utf-8");
			BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
			
			for(int i = 0 ; i < 100000 ; i++){
				
				int nextInt = randomGenerator.nextInt(4);
				
				 int randomInteger = showRandomInteger(START, END, random);
				 
				 int randomInteger_2 = showRandomInteger(START, END, random);
				
				Date date = new Date();
				
				String str_time = dateformat_time.format(date);
				String str_date = dateformat_date.format(date);
				
				bufferedWriter.write(str_time + ",");
				
				bufferedWriter.write(str_date + ",");
				
				bufferedWriter.write(type[nextInt] + ",");
				
				bufferedWriter.write(randomInteger + ",");
				
				bufferedWriter.write(randomInteger_2 + ",");
				
				bufferedWriter.write("1");
				
				bufferedWriter.newLine();

			}
			
			bufferedWriter.flush();
			bufferedWriter.close();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	

	@Test
	public void testProp(){
		
		System.out.println("-------- MySQL JDBC Connection Testing ------------");
		 
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println("Where is your MySQL JDBC Driver?");
			e.printStackTrace();
			return;
		}
	 
		System.out.println("MySQL JDBC Driver Registered!");
		Connection connection = null;
	 
		try {
			connection = DriverManager
			.getConnection("jdbc:mysql://localhost:3306/suggest?rewriteBatchedStatements=true","root", "root");
	 
		} catch (SQLException e) {
			System.out.println("Connection Failed! Check output console");
			e.printStackTrace();
			return;
		}
	 
		if (connection != null) {
//			System.out.println("successful!");
			
			String sql = "insert into test (time, date, type, user_id, item_id, value) values (?, ?, ?, ?, ?, ?)";
			
			try {
				
				connection.setAutoCommit(false);
				FileInputStream inputStream = new FileInputStream("F:\\csv\\process\\gen_sample.csv");
				InputStreamReader inputStreamReader = new InputStreamReader(inputStream,"utf-8");
				BufferedReader reader = new BufferedReader(inputStreamReader);
				
				PreparedStatement ps = connection.prepareStatement(sql);
				
				String line = "";
				
				

				long start = System.currentTimeMillis();
				
				while((line = reader.readLine()) != null){
					 
					 String[] values = line.split(",");
					
					 ps.setString(1,values[0]);
					 ps.setString(2, values[1]);
					 ps.setString(3, values[2]);
					 ps.setString(4, values[3]);
					 ps.setString(5, values[4]);
					 ps.setInt(6, Integer.valueOf(values[5]));
					 ps.addBatch();
				}
				
				ps.executeBatch();
				connection.commit();
				ps.close();
				connection.close();
				
				long end = System.currentTimeMillis();
				
			
				
				reader.close();
				System.out.println(end-start);
				
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}   
			
			
		} else {
			System.out.println("Failed to make connection!");
		}
	}
	


		  
		  private static int showRandomInteger(int aStart, int aEnd, Random aRandom){
		    if ( aStart > aEnd ) {
		      throw new IllegalArgumentException("Start cannot exceed End.");
		    }
		    //get the range, casting to long to avoid overflow problems
		    long range = (long)aEnd - (long)aStart + 1;
		    // compute a fraction of the range, 0 <= frac < range
		    long fraction = (long)(range * aRandom.nextDouble());
		    
		    
		    return (int)(fraction + aStart);    

		  }
		  
		  
	
		  
		
}
