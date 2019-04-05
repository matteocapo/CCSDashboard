package com.ccs.model;

import java.util.ArrayList;

public class IntermediateOeesModel {
	ArrayList<Integer>	oeeArray	=	null;
	ArrayList<String>	oeeNamesArr	=	null;
	
	public void setOeeArray(ArrayList<Integer> oeeArray) {
		this.oeeArray = oeeArray;
	}
	
	public void setOeeNamesArr(ArrayList<String> oeeNamesArr) {
		this.oeeNamesArr = oeeNamesArr;
	}
	
	public ArrayList<Integer> getOeeArray() {
		return this.oeeArray;
	}
	
	public ArrayList<String> getOeeNamesArr() {
		return this.oeeNamesArr;
	}
}
