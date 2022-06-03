/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.imdb;

import java.net.URL;
import java.util.ResourceBundle;

import it.polito.tdp.imdb.model.Actor;
import it.polito.tdp.imdb.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {
	
	private Model model;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="btnCreaGrafo"
    private Button btnCreaGrafo; // Value injected by FXMLLoader

    @FXML // fx:id="btnSimili"
    private Button btnSimili; // Value injected by FXMLLoader

    @FXML // fx:id="btnSimulazione"
    private Button btnSimulazione; // Value injected by FXMLLoader

    @FXML // fx:id="boxGenere"
    private ComboBox<String> boxGenere; // Value injected by FXMLLoader

    @FXML // fx:id="boxAttore"
    private ComboBox<Actor> boxAttore; // Value injected by FXMLLoader

    @FXML // fx:id="txtGiorni"
    private TextField txtGiorni; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void doAttoriSimili(ActionEvent event) {
    	
    	try {
    		
    		String string= this.model.getSimili(boxAttore.getValue());
        	
        	txtResult.clear();
        	txtResult.appendText(string);
    		
    	}catch (NullPointerException e) {
			txtResult.clear();
			txtResult.appendText("Selezionare un attore!\nSe non si visualizza l'elenco, allora crare prima il grafo!\n");
		}
    	
    	
    	
    	

    }

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	
    	try {
    		String s= this.model.creaGrafo(boxGenere.getValue());
        	
        	txtResult.clear();
        	txtResult.appendText(s);
        	
        	boxAttore.getItems().clear();
        	boxAttore.getItems().addAll(this.model.getVertici());
    		
    	}catch (NullPointerException e) {
			txtResult.clear();
			txtResult.appendText("Selezionare un genere!\n");
		}
    	
    }

    @FXML
    void doSimulazione(ActionEvent event) {
    	
    	
    	try {
    	
    		int n=Integer.parseInt(txtGiorni.getText());
        	
        	String string= this.model.Simula(n);
        	
        	txtResult.clear();
        	txtResult.appendText(string);
    		
    	}catch (NumberFormatException e) {
			txtResult.clear();
			txtResult.appendText("Inserire un numero nel formato corretto!\n");
		}
    	
    	

    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert btnCreaGrafo != null : "fx:id=\"btnCreaGrafo\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnSimili != null : "fx:id=\"btnSimili\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnSimulazione != null : "fx:id=\"btnSimulazione\" was not injected: check your FXML file 'Scene.fxml'.";
        assert boxGenere != null : "fx:id=\"boxGenere\" was not injected: check your FXML file 'Scene.fxml'.";
        assert boxAttore != null : "fx:id=\"boxAttore\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtGiorni != null : "fx:id=\"txtGiorni\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";

    }
    
    public void setModel(Model model) {
    	this.model = model;
    	this.boxGenere.getItems().clear();
    	this.boxGenere.getItems().addAll(this.model.getAllGeneri());
    }
}
