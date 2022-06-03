package it.polito.tdp.imdb.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.print.attribute.standard.JobMessageFromOperator;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.alg.connectivity.ConnectivityInspector;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.imdb.db.Coppia;
import it.polito.tdp.imdb.db.ImdbDAO;

public class Model {
	
	private ImdbDAO dao;
	private List<String> generi;
	private Graph<Actor, DefaultWeightedEdge> grafo;
	private List<Actor> vertici;
	private Map<Integer, Actor> idMap;
	private List<Coppia> archi;
	
	public Model() {
		this.dao= new ImdbDAO();
		this.generi= this.dao.getAllGeneri();
		this.idMap= this.dao.listAllActors();
	}
	
	
	public String creaGrafo(String genere) {
		
		this.grafo= new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		
		this.vertici= this.dao.getVertici(idMap, genere);
		
		Collections.sort(vertici);
		
		Graphs.addAllVertices(this.grafo, vertici);
		
		archi= this.dao.getArchi(genere, idMap);
		
		for(Coppia c: archi) {
			Graphs.addEdge(this.grafo, c.getA1(), c.getA2(), c.getPeso());
		}
		
		
		String s= "Grafo creato!\n# VERTICI: "+this.grafo.vertexSet().size()+"\n#ARCHI: "+this.grafo.edgeSet().size();
		
		return s;
		
		
	}
	
	public String getSimili(Actor a) {
		
		List<Actor> stamp= new ArrayList<>();
		ConnectivityInspector<Actor, DefaultWeightedEdge> inspector = new ConnectivityInspector<>(this.grafo);
		Set<Actor> componenteConnessa = inspector.connectedSetOf(a);
		
		for(Actor aa: componenteConnessa) {
			if(!aa.equals(a))
				stamp.add(aa);
		}
		
		Collections.sort(stamp);
		
		String s="ATTORI SIMILI A:  "+a.toString()+"\n";
		
		for(Actor aa: stamp) {
			s+=aa.toString()+"\n";
		}
		
		return s;
		
	}
	
	
	public String Simula(int nGiorni) {
		
		Simulatore sim = new Simulatore(this.grafo, this.vertici);
		
		sim.init(nGiorni);
		sim.run();
		
		String s="Attori intervistati dal prouttore: \n";
		
		for(Actor a: sim.getIntervistati()) {
			s+=a.toString()+"\n";
		}
		
		s+="Numero di pause fatte dal produttore:  "+sim.getGiorniPausa()+"\n";
		
		return s;
		
	}

	
	
	public List<String> getAllGeneri(){
		
		return generi;
	}


	public List<Actor> getVertici() {
		 return vertici;
	}
	
	

}
