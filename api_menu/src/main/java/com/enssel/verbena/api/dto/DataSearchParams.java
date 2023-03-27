package com.enssel.verbena.api.dto;

import java.io.Serializable;

/**
 * SearchForm으로부터 전달받은 검색조건을 저장하는 Model(DTO)
 * 
 * @author 김혜민
 *
 */
public class DataSearchParams implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String userId;
	private String userNm;
	//private String userYn;
	private String updaUser;
	private String regiUser;
	private String regiDtFrom;
	private String regiDtTo;
	private String updaDtFrom;
	private String updaDtTo;
	

	@Override
	public String toString() {
		return String.format("userId: %s, userNm: %s, updaUser: %s, regiUser: %s, regiDtFrom: %s, regiDtTo: %s, updaDtFrom: %s, updaDtTo: %s",
				userId, userNm, updaUser, regiUser, regiDtFrom, regiDtTo, updaDtFrom, updaDtTo);
		
	}
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserNm() {
		return userNm;
	}
	public void setUserNm(String userNm) {
		this.userNm = userNm;
	}
	public String getUpdaUser() {
		return updaUser;
	}
	public void setUpdaUser(String updaUser) {
		this.updaUser = updaUser;
	}
	public String getRegiUser() {
		return regiUser;
	}
	public void setRegiUser(String regiUser) {
		this.regiUser = regiUser;
	}
	public String getRegiDtFrom() {
		return regiDtFrom;
	}
	public void setRegiDtFrom(String regiDtFrom) {
		this.regiDtFrom = regiDtFrom;
	}
	public String getRegiDtTo() {
		return regiDtTo;
	}
	public void setRegiDtTo(String regiDtTo) {
		this.regiDtTo = regiDtTo;
	}
	public String getUpdaDtFrom() {
		return updaDtFrom;
	}
	public void setUpdaDtFrom(String updaDtFrom) {
		this.updaDtFrom = updaDtFrom;
	}
	public String getUpdaDtTo() {
		return updaDtTo;
	}
	public void setUpdaDtTo(String updaDtTo) {
		this.updaDtTo = updaDtTo;
	}
	
}
