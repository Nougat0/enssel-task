package com.enssel.verbena.api.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import org.hibernate.annotations.DynamicInsert;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * test.test.test_nougat0 테이블 Entity로 등록하는 클래스 
 * @author hmKim
 * @since 23-03-13
 *
 */
@Entity
@DynamicInsert
@Table(name="TEST_NOUGAT0")
//@IdClass(String.class) //복합키의 경우 필요하다
public class TestNougat0 implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//org.springframework.orm.jpa.JpaSystemException: No default constructor for entity 에러 방지용
	//기본생성자
	public TestNougat0() {
		super();
	}
	
	@Id
	@Column(name="USER_ID")
	private String userId;

	@Column(name="USE_YN")
	private String useYn;

	@Column(name="USER_NM")
	private String userNm;

	@Column(name="PW")
	private String pw;
	
	@Column(name="UPDA_USER")
	private String updaUser;
	
	@Column(name="UPDA_DT")
	private LocalDateTime updaDt;
	
	@Column(name="REGI_USER")
	private String regiUser;
	
	@Column(name="REGI_DT")
	private LocalDateTime regiDt;
	

	public String getUseYn() {
		return useYn;
	}


	public void setUseYn(String useYn) {
		this.useYn = useYn;
	}


	public String getUserNm() {
		return userNm;
	}


	public void setUserNm(String userNm) {
		this.userNm = userNm;
	}


	public String getUserId() {
		return userId;
	}


	public void setUserId(String userId) {
		this.userId = userId;
	}


	public String getPw() {
		return pw;
	}


	public void setPw(String pw) {
		this.pw = pw;
	}


	public String getUpdaUser() {
		return updaUser;
	}


	public void setUpdaUser(String updaUser) {
		this.updaUser = updaUser;
	}


	public LocalDateTime getUpdaDt() {
		return updaDt;
	}


	public void setUpdaDt(LocalDateTime updaDt) {
		this.updaDt = updaDt;
	}


	public String getRegiUser() {
		return regiUser;
	}


	public void setRegiUser(String regiUser) {
		this.regiUser = regiUser;
	}


	public LocalDateTime getRegiDt() {
		return regiDt;
	}


	public void setRegiDt(LocalDateTime regiDt) {
		this.regiDt = regiDt;
	}

	
	
}
