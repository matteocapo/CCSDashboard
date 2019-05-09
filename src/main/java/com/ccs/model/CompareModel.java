package com.ccs.model;

public class CompareModel {
	
	public String name = "";
	public String asset = "";
	public int oee = 0;
	public int total = 0;
	public int scrap = 0;
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setAsset(String asset) {
		this.asset = asset;
	}
	
	public void setOee(int oee) {
		this.oee = oee;
	}
	
	public void setTotPieces(int total) {
		this.total = total;
	}
	
	public void setScrapPieces(int scrap) {
		this.scrap = scrap;
	}
	
	public String getName() {
		return this.name;
	}
	
	public String getAsset() {
		return this.asset;
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
