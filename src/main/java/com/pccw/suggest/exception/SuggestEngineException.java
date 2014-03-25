package com.pccw.suggest.exception;

public class SuggestEngineException extends Exception {

	private String exceptionMessage;
	
	private String errorCode;

	public SuggestEngineException(String message, String error_code)
	  {
	    super(message);
	    
	    this.exceptionMessage = message;
	    this.errorCode = error_code;
	  }
	

	public String getExceptionMessage() {
		return exceptionMessage;
	}

	public void setExceptionMessage(String exceptionMessage) {
		this.exceptionMessage = exceptionMessage;
	}


	public String getErrorCode() {
		return errorCode;
	}


	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	
	
	
	
}
