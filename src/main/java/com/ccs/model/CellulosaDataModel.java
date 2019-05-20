package com.ccs.model;

public class CellulosaDataModel {

	float kg_reali = 0;
	float kg_stimati = 0;
	float pezzi_prodotti = 0;
	float pezzi_teorici = 0;

	
	public void setKgReali (float kg_reali) {
		this.kg_reali = kg_reali;
	}
	
	public void setKgStimati (float kg_stimati) {
		this.kg_stimati = kg_stimati;
	}
	
	public void setPezziProdotti (float pezzi_prodotti) {
		this.pezzi_prodotti = pezzi_prodotti;
	}
	
	public void setPezziTeorici (float pezzi_teorici) {
		this.pezzi_teorici = pezzi_teorici;
	}
	
	public float getKgReali () {
		return this.kg_reali;
	}
	
	public float getKgStimati () {
		return this.kg_stimati;
	}
	
	public float getPezziProdotti() {
		return this.pezzi_prodotti;
	}
	
	public float getPezziTeorici () {
		return this.pezzi_teorici;
	}
}
