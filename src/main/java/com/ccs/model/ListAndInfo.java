package com.ccs.model;

import java.util.List;

import com.siemens.mindsphere.sdk.iot.timeseries.model.TimeseriesData;

public class ListAndInfo {
	
	//tipo finale e tipo iniziale possono prendere come valore solamente due tipologie di stringhe: {run, stop}
	List<TimeseriesData> timeseriesList = null;	
	String tipo_iniziale;
	String tipo_finale;
	String data_iniziale;
	String data_finale;
	int lunghezza_lista;
	
	public void setTimeseriesList(List<TimeseriesData> timeseriesList) {
		this.timeseriesList = timeseriesList;
	}
	
	public void setTipo_iniziale(String tipo_iniziale) {
		this.tipo_iniziale = tipo_iniziale;
	}

	public void setTipo_finale(String tipo_finale) {
		this.tipo_finale = tipo_finale;
	}

	public void setData_iniziale(String data_iniziale) {
		this.data_iniziale = data_iniziale;
	}

	public void setData_finale(String data_finale) {
		this.data_finale = data_finale;
	}
	
	public void setLunghezza_lista(int lunghezza) {
		this.lunghezza_lista = lunghezza;
	}
	
	public List<TimeseriesData> getTimeseriesList() {
		return this.timeseriesList;
	}
	
	public String getTipo_iniziale() {
		return tipo_iniziale;
	}

	public String getTipo_finale() {
		return tipo_finale;
	}

	public String getData_iniziale() {
		return  data_iniziale;
	}

	public String getData_finale() {
		return data_finale;
	}
	public int getLunghezza_lista() {
		return lunghezza_lista;
	}
}
