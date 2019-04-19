package com.ccs.model;

public class ErrorDataModel {
	
	public String error_code = "-1";
	public String timestamp = "";
	
	public void setErrorCode(String error_code) {
		this.error_code = error_code;
	}
	
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	
	public String getErrorCode() {
		return this.error_code;
	}
	
	public String getTimestamp() {
		return this.timestamp;
	}
	
	
}
