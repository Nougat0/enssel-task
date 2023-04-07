package com.enssel.verbena.api.dto;

public class UserIdToNmData {

	private String userNm;
	private String userId;
	
	public UserIdToNmData(String userNm, String userId) {
		super();
		this.userNm = userNm;
		this.userId = userId;
	}
	public String getUserNm() {
		return userNm;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserNm(String userNm) {
		this.userNm = userNm;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	
}
