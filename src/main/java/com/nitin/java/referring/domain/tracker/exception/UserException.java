package com.nitin.java.referring.domain.tracker.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.nitin.java.referring.domain.tracker.constant.ErrorCodes;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class UserException extends RuntimeException {
	
	
	private static final long serialVersionUID = 1531896648222740607L;
	String exception;
	ErrorCodes errorCode;
	
	public UserException(String exception, ErrorCodes errorCode) {
		super(exception);
		this.exception = exception;
		this.errorCode = errorCode;
	}
	 @Override
	    public synchronized Throwable fillInStackTrace() {
	        return this;
	    }
	public String toString(){
	     return ("User exception: "+ exception+"; error code: "+errorCode.getErrorCode()) ;
	  }
}
