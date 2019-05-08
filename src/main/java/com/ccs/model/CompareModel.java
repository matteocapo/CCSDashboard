package com.ccs.model;

public class CompareModel {
	
	public int oee = 0;
	public int total = 0;
	public int scrap = 0;
	
	public void setOee(int oee) {
		this.oee = oee;
	}
	
	public void setTotPieces(int total) {
		this.total = total;
	}
	
	public void setScrapPieces(int scrap) {
		this.scrap = scrap;
	}
	
	public int getOee() {
		return this.oee;
	}
	
	public int getTotPieces() {
		return this.total;
	}
	
	public int getScrapPieces() {
		return this.scrap;
	}
}
