package com.enssel.verbena.api.dto;

import java.time.LocalDateTime;
import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class GroupByGridRows {
	//groupBy를 위한 필드
	private Long accountSum;
	
	@Id
	private String regiDate;
	

//	public GroupByGridRows(Integer accountSum, LocalDateTime regiDate) {
//		super();
//		this.accountSum = accountSum;
//		this.regiDate = regiDate;
//	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return String.format("accountSum: %s, regiDate: %s",accountSum, regiDate);
	}
	
	public Long getAccountSum() {
		return accountSum;
	}
	public void setAccountSum(Long accountSum) {
		this.accountSum = accountSum;
	}
	public String getRegiDate() {
		return regiDate;
	}
	public void setRegiDate(String regiDate) {
		this.regiDate = regiDate;
	}
	
	
}
