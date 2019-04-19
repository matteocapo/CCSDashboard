package com.ccs.model;

public class RawDataModel {
	String nome;
	float materiale_consumato;
	float materiale_scartato;
	
	public void setName(String nome) {
		this.nome = nome;
	}
	
	public void setMaterialeConsumato(float materiale_consumato) {
		this.materiale_consumato = materiale_consumato;		
	}
	
	public void setMaterialeScartato(float materiale_scartato) {
		this.materiale_scartato = materiale_scartato;
	}
	
	public float getMaterialeConsumato() {
		return this.materiale_consumato;
	}
	
	public float getMaterialeScartato() {
		return this.materiale_scartato;
	}
	
	public String getName() {
		return this.nome;
	}

}
