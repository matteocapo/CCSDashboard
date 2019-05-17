package com.ccs.model;

/*
 * Classe per la gestione del modello che tiene traccia delle materie prime consumate e utilizzate
 */

public class RawDataModel {
	
	int unita = -1;
	String nome;
	float materiale_consumato = 0;
	float materiale_scartato = 0;
	
	public void setUnita(int unita) {
		this.unita = unita;
	}
	
	public void setName(String nome) {
		this.nome = nome;
	}
	
	public void setMaterialeConsumato(float materiale_consumato) {
		this.materiale_consumato = materiale_consumato;		
	}
	
	public void setMaterialeScartato(float materiale_scartato) {
		this.materiale_scartato = materiale_scartato;
	}
	
	public void addMaterialeConsumato(float materiale_consumato) {
		this.materiale_consumato += materiale_consumato;		
	}
	
	public void addMaterialeScartato(float materiale_scartato) {
		this.materiale_scartato += materiale_scartato;
	}
	
	public float getMaterialeConsumato() {
		return this.materiale_consumato;
	}
	
	public float getMaterialeScartato() {
		return this.materiale_scartato;
	}
	
	public int getUnita() {
		return this.unita;
	}
	
	public String getName() {
		return this.nome;
	}

}
