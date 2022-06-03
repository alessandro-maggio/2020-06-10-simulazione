package it.polito.tdp.imdb.db;

import it.polito.tdp.imdb.model.Actor;

public class Coppia {
	
	private Actor a1;
	private Actor a2;
	private int peso;
	
	
	public Coppia(Actor a1, Actor a2, int peso) {
		super();
		this.a1 = a1;
		this.a2 = a2;
		this.peso = peso;
	}


	public Actor getA1() {
		return a1;
	}


	public Actor getA2() {
		return a2;
	}


	public int getPeso() {
		return peso;
	}
	
	
	
	
	

}
