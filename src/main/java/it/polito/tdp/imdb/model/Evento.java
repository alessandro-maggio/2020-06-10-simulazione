package it.polito.tdp.imdb.model;

public class Evento implements Comparable<Evento>{
	
	
	public enum EventType{
		
		DA_INTERVISTARE,
		FERIE
	}
	
	
	private Actor intervistato;
	private int giorno;
	private EventType tipo;
	
	
	
	public Evento(Actor intervistato, int giorno, EventType tipo) {
		super();
		this.intervistato = intervistato;
		this.giorno = giorno;
		this.tipo = tipo;
	}





	public Actor getIntervistato() {
		return intervistato;
	}


	public int getGiorno() {
		return giorno;
	}



	public EventType getTipo() {
		return tipo;
	}

	


	@Override
	public int compareTo(Evento o) {
		return this.giorno-o.giorno;
	}

}
