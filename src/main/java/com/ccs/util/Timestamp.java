package com.ccs.util;

public class Timestamp {
	
	char[] date = new char[24];

	public Timestamp() {
		date = "0000-00-00T00:00:00.000Z".toCharArray();
	}
	
	//Set Functions
	public void setYear(String Year) {
		char[] yyyy = new char[4];
		yyyy = Year.toCharArray();
		this.date[0] = yyyy[0];
		this.date[1] = yyyy[1];
		this.date[2] = yyyy[2];
		this.date[3] = yyyy[3];
	}
	
	public void setMonth(String Month) {
		char[] MM = new char[2];
		MM = Month.toCharArray();
		this.date[5] = MM[0];
		this.date[6] = MM[1];
	}
	
	public void setDay(String Day) {
		char[] dd = new char[2];
		dd = Day.toCharArray();
		this.date[8] = dd[0];
		this.date[9] = dd[1];
	}
	
	public void setHours(String Hours) {
		char[] hh = new char[2];
		hh = Hours.toCharArray();
		this.date[11] = hh[0];
		this.date[12] = hh[1];
	}
	
	public void setMinutes(String Minutes) {
		char[] mm = new char[2];
		mm = Minutes.toCharArray();
		this.date[14] = mm[0];
		this.date[15] = mm[1];
	}
	
	public void setSeconds(String Seconds) {
		char[] ss = new char[2];
		ss = Seconds.toCharArray();
		this.date[17] = ss[0];
		this.date[18] = ss[1];
	}
	
	public void clear() {
		date = "0000-00-00T00:00:00.000Z".toCharArray();
	}
	
	public void setDate(String date) {
		this.date = date.toCharArray();
	}
	
	//Get Functions
	
	public void print() {
		System.out.println(this.date);
	}
	
	// Controls between dates
	public boolean before(Timestamp controlDate) {
		if(this.date.toString().compareTo(controlDate.toString()) <  0) return true;
		return false;
	}
	
	public boolean after(Timestamp controlDate) {
		if(this.date.toString().compareTo(controlDate.toString()) >  0) return true;
		return false;
	}
	
	public boolean equal(Timestamp controlDate) {
		if(this.date.toString().compareTo(controlDate.toString()) == 0) return true;
		return false;
	}
	
}
