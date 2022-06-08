package it.polito.tdp.imdb.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EventObject;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;

import it.polito.tdp.imdb.model.Evento.EventType;

public class Simulatore {
	
	
	
	//input
	private int nGiorni;
	
	//output
	private Set<Actor> attoriIntervistati;
	private int giorniPausa;
	
	//coda
	private PriorityQueue<Evento> queue;
	
	
	//stato del mondo
	private Graph<Actor, DefaultWeightedEdge> grafo;
	private List<Actor> daIntervistare;
	private List<Actor> intervistati;
	private int giorno;
	
	
	public Simulatore(Graph<Actor, DefaultWeightedEdge> grafo, List<Actor> daIntervistare) {
		this.grafo= grafo;
		this.daIntervistare= daIntervistare;
	}
	
	
	
	public void init(int nGiorni) {
		
		this.nGiorni= nGiorni;
		
		this.attoriIntervistati= new HashSet<>();
		
		this.intervistati= new ArrayList<>(); 
		
		this.queue= new PriorityQueue<Evento>();
		
		
		giorno=0;
		giorniPausa=0;
		
		Actor primo= scegliACaso();
		//setto il primo evento dato da attore a caso
		if(primo!=null) {
			Evento e= new Evento(primo, giorno+1, EventType.DA_INTERVISTARE);
			this.queue.add(e);
			intervistati.add(primo);
			daIntervistare.removeAll(intervistati);
		}
		
		
		
	}
	
	public void run() {
		
		while(!this.queue.isEmpty() && this.giorno<=nGiorni) {
			
			Evento e= this.queue.poll();
			
			processEvent(e);
		}
	}



	private void processEvent(Evento e) {
		
		if(!daIntervistare.isEmpty()) {
		
		switch (e.getTipo()) {
		
		case DA_INTERVISTARE:
			
			double caso= Math.random();
			
			if(caso<0.6) {
				Actor scelto= scegliACaso();
				if(!intervistati.contains(scelto)) {
				if(scelto!=null) {
					intervistati.add(scelto);
					daIntervistare.removeAll(intervistati);
				}
				giorno++;
				Evento evento= new Evento(scelto, giorno, EventType.DA_INTERVISTARE);
				this.queue.add(evento);
				if(scelto.getGender().equals(intervistati.get(intervistati.size()-2).getGender())) {   //ovvero se intervista due attori dello stesso genere consecutivamente
					
					if(Math.random()<0.9) {
						giorno++;
						Evento pausa= new Evento(null, giorno, EventType.FERIE);
						this.queue.add(pausa);
						this.giorniPausa++;
					}
						
				}
				}
				
			}
			else {
				Actor u= intervistati.get(intervistati.size()-1);
				Actor scelto2= scegliVicino(u);
				if(!intervistati.contains(scelto2)) {
				if(scelto2==null)
					scelto2= scegliACaso();
				intervistati.add(scelto2);
				daIntervistare.removeAll(intervistati);
				giorno++;
				Evento evento2= new Evento(scelto2, giorno, EventType.DA_INTERVISTARE);
				this.queue.add(evento2);
				}
			}
			
			
			
			break;

		case FERIE:
			giorno++;
			Actor aa=scegliACaso();
			if(!intervistati.contains(aa)) {
			Evento evento3= new Evento(aa, giorno, EventType.DA_INTERVISTARE);
			intervistati.add(evento3.getIntervistato());
			daIntervistare.removeAll(intervistati);
			this.queue.add(evento3);
			
			break;
			}
		}
		}
		
	}



	private Actor scegliVicino(Actor vecchio) {
		
		List<Actor> vicini= Graphs.neighborListOf(this.grafo, vecchio);
		List<Adiacenza> adiacenti= new ArrayList<>();
		for(Actor a: vicini) {
			adiacenti.add(new Adiacenza(a, (int) this.grafo.getEdgeWeight(this.grafo.getEdge(a, vecchio))));
		}
		
		Collections.sort(adiacenti);
		
		List<Adiacenza> daScegliere= new ArrayList<>();
		if(adiacenti.size()==0) {
			return scegliACaso();
		}
		int p= adiacenti.get(0).getPeso();
		
		for(Adiacenza a: adiacenti) {
			if(a.getPeso()==p)
				daScegliere.add(a);
		}
		
		if(daScegliere.size()==1)
			return adiacenti.get(0).getVicino();
		
		return daScegliere.get((int)Math.random()*daScegliere.size()).getVicino();
		
	}



	private Actor scegliACaso() {
		
		int scegli= (int) Math.random()*daIntervistare.size();
		
		return daIntervistare.get(scegli);
	}



	public int getGiorniPausa() {
		return giorniPausa;
	}



	public List<Actor> getIntervistati() {
		Collections.sort(intervistati);
		return intervistati;
	}
	
	
	

}
