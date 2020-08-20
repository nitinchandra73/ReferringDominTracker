package com.nitin.java.referring.domain.tracker.constant;

public enum ErrorCodes {
	DOMAIN_EXIST	(001), 
	DOMAIN_DOES_NOT_EXIST(002),
	DOMAIN_WRONG_FORMAT(003),
	DOMAIN_IS_INACTIVE(004);
	 
	private Integer errorCode;
	
	 
	    private ErrorCodes(int errorCode) {
	        this.errorCode = errorCode;
	    }
	 
	    public int getErrorCode() {
	        return errorCode;
	    }
}
