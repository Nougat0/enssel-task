package com.enssel.verbena.menu.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="TEST_NOUGAT0_MENU")
public class TestNougat0Menu implements Serializable{
	private static final long serialVersionUID = 1L;
	
	public TestNougat0Menu() {
		super();
	}
	
	@Id
	@Column(name="MENU_ID")
	private int menuId;
	
	@Column(name="MENU_NM")
	private String menuNm;
	
	@Column(name="URL")
	private String url;
	
	@Column(name="USE_YN")
	private String useYn;
	
	@Column(name="REGI_USER")
	private String regiUser;
	
	@Column(name="UPDA_USER")
	private String updaUser;
	
	@Column(name="SORT")
	private int sort;
	
	@Column(name="UPR_MENU_ID")
	private int uprMenuId;
	
	@Column(name="REGI_DT")
	private LocalDateTime regiDt;
	
	@Column(name="UPDA_DT")
	private LocalDateTime updaDt;

	public int getMenuId() {
		return menuId;
	}

	public void setMenuId(int menuId) {
		this.menuId = menuId;
	}

	public String getMenuNm() {
		return menuNm;
	}

	public void setMenuNm(String menuNm) {
		this.menuNm = menuNm;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUseYn() {
		return useYn;
	}

	public void setUseYn(String useYn) {
		this.useYn = useYn;
	}

	public String getRegiUser() {
		return regiUser;
	}

	public void setRegiUser(String regiUser) {
		this.regiUser = regiUser;
	}

	public String getUpdaUser() {
		return updaUser;
	}

	public void setUpdaUser(String updaUser) {
		this.updaUser = updaUser;
	}

	public int getSort() {
		return sort;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}

	public int getUprMenuId() {
		return uprMenuId;
	}

	public void setUprMenuId(int uprMenuId) {
		this.uprMenuId = uprMenuId;
	}

	public LocalDateTime getRegiDt() {
		return regiDt;
	}

	public void setRegiDt(LocalDateTime regiDt) {
		this.regiDt = regiDt;
	}

	public LocalDateTime getUpdaDt() {
		return updaDt;
	}

	public void setUpdaDt(LocalDateTime updaDt) {
		this.updaDt = updaDt;
	}

	
}
