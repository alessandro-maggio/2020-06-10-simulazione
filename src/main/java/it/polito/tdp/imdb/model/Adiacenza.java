package it.polito.tdp.imdb.model;

public class Adiacenza implements Comparable<Adiacenza>{
	
	private Actor vicino;
	private int peso;
	
	
	public Adiacenza(Actor vicino, int peso) {
		super();
		this.vicino = vicino;
		this.peso = peso;
	}

	
	
	

	public Actor getVicino() {
		return vicino;
	}





	public int getPeso() {
		return peso;
	}





	@Override
	public int compareTo(Adiacenza o) {
		return o.peso-this.peso;
	}
	
	
	
	

}
