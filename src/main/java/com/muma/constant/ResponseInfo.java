package com.muma.constant;

public enum ResponseInfo {
	
	SUCCESS("0000", "Success"), 
	PARAM_ERROR("0001", "Request_parameter_invalid"),
	AUTH_ERROR("0002", "User_invalid"),
	NO_CONTENT("0003", "No_content"),	
	SERVER_ERROR("0004", "Server_error. Please contact administrator"), 
	SERVER_MAINTAINACE("0005", "Server_maintainace");

	private String resultCode;
	private String message;
	
	ResponseInfo(String resultCode, String message) {
		this.resultCode = resultCode;
		this.message = message;
	}
	
	public String getResultCode() {
		return this.resultCode;
	}
	
	public String getMessage() {
		return this.message;
	}
}
