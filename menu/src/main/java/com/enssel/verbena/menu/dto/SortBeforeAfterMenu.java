package com.enssel.verbena.menu.dto;

import com.enssel.verbena.menu.model.TestNougat0Menu;

import jakarta.persistence.Entity;


public class SortBeforeAfterMenu {
	
	private TestNougat0Menu sourceMenu;
	private TestNougat0Menu targetMenu;

	public TestNougat0Menu getSourceMenu() {
		return sourceMenu;
	}
	public TestNougat0Menu getTargetMenu() {
		return targetMenu;
	}

	public void setSourceMenu(TestNougat0Menu sourceMenu) {
		this.sourceMenu = sourceMenu;
	}
	public void setTargetMenu(TestNougat0Menu targetMenu) {
		this.targetMenu = targetMenu;
	}


}
