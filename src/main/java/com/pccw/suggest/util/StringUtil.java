package com.pccw.suggest.util;

public class StringUtil {
	
	
	public static String convertRecommandList(String raw){
		StringBuilder builder = new StringBuilder();
		
		builder.append("{\"list\":");
		builder.append(raw);
		builder.append("}");
		
		return builder.toString();
	}
	
	public static String[] convertFloatingPointList(String raw){
		
		return raw.split("\n");
	}
	
	
	public static String checkErrorCode(String message){

		int err_code = Integer.valueOf(message.substring(0, 3));
		String err_message = "";
		
		switch (err_code) {
		case 404:  err_message = "Not Found";
				 break;
        case 302:  err_message = "Temporary Redirect";
                 break;
        case 400:  err_message = "Bad Request";
                 break;
        case 401:  err_message = "Unauthorized";
                 break;
        case 405:  err_message = "Method Not Allowed";
                 break;
        case 500:  err_message = "Internal Server Error";
                 break;
        case 503:  err_message = "Service Unavailable";
                 break;

        default: err_message = "Unknown Error";
                 break;
		}
		
		StringBuilder builder = new StringBuilder();
		builder.append(err_message);
		builder.append(",");
		builder.append(message.substring(0, 3));
		
        return builder.toString();
    }

	
}
