package com.ccs.model;

public class ErrorDataModel {
	
	int error_code = -1;
	String timestamp = "";
	
	public void setErrorCode(int error_code) {
		this.error_code = error_code;
	}
	
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	
	public int getErrorCode () {
		return this.error_code;
	}
	
	public String getTimestamp() {
		return this.timestamp;
	}
	
	
}
