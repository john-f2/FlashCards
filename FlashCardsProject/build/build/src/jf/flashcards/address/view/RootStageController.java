/**
 * 
 * RootStageController 
 * used to give functionality to the RootStage menu bar
 * 
 * 
 * @author johnfu
 * 
 * 
 * 
 */
package jf.flashcards.address.view;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import jf.flashcards.address.MainApp;
import jf.flashcards.address.model.FlashCardDatabase;

public class RootStageController {
	
	//reference to the mainApp 
	private MainApp mainApp; 
	
    /**
     * this is called by the main application to give a reference back to itself.
     * 
     * @param mainApp
     */
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }
    
    
    
    /**
     * 
     * gives functionality to the menu option "add new flash card stack"
     * handleNewStack() calls the mainApp function showAddNewFlashCardStack() which displays
     * the view to add a new flash card stack 
     * 
     */
    @FXML
    private void handleNewStack(){
    	
    	boolean okClicked = mainApp.showAddNewFlashCardStack();
    	
    	//this okClicked is not necessary, but is being used for debugging purposes 
    	if(okClicked){
    		
    		mainApp.setFlashCardStack(FlashCardDatabase.getFlashCardList());
    		
    		//I call this here because this allows me to update my list of flash cards
    		//the FlashCardOverview isn't updating to the new stack list thats why i call this here again
    		mainApp.showFlashCardOverview();
    		
    		
    	}
    	
    }
    
    /**
     * 
     * displays the about alert 
     * 
     * 
     */
    @FXML
    private void handleAbout() {
    	//displays the about information using Alert
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("FlashCard Application");
        alert.setHeaderText("About Application");
        alert.setContentText("Flash Card Application v1.0\nAuthor: John Fu");

        alert.showAndWait();
    }
    
    
    /**
     * 
     * gives functionality to the "Delete Flash Card Stack" menu option
     * calls showDeleteFlashCardStack() method in the mainApp which displays the 
     * showDeleteFlashCardStack view 
     * 
     */
    @FXML
    private void handleDeleteStack(){
    	
    	mainApp.showDeleteFlashCardStack();
    	mainApp.showFlashCardOverview();
    	
    }

}
