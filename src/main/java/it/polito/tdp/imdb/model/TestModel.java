package it.polito.tdp.imdb.model;


public class TestModel {

	public static void main(String[] args) {
		
		Model model = new Model();
		
		String string= model.creaGrafo("Animation");
		
		System.out.println(string);
		

	}

}
