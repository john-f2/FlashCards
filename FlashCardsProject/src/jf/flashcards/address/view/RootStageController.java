package jf.flashcards.address.view;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import jf.flashcards.address.MainApp;
import jf.flashcards.address.model.FlashCardDatabase;

public class RootStageController {
	
	private MainApp mainApp; 
	
    /**
     * this is called by the main application to give a reference back to itself.
     * 
     * @param mainApp
     */
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }
    
    
    @FXML
    private void handleNewStack(){
    	
    	boolean okClicked = mainApp.showAddNewFlashCardStack();
    	
    	//this okClicked is not necessary, but is being used for debugging purposes 
    	if(okClicked){
    		
    		mainApp.setFlashCardStack(FlashCardDatabase.getFlashCardList());
    		
    		//I call this here because this allows me to update my list of flash cards
    		//the FlashCardOverview isn't updating to the new stack list thats why i call this here again
    		//it doesnt really seem like a good method but it works 
    		mainApp.showFlashCardOverview();
    		
//    		ObservableList<SimpleStringProperty> flashCardStack = FlashCardDatabase.getFlashCardList();
//            
//            for(SimpleStringProperty i : flashCardStack)
//            {
//            	System.out.println(i);
//            }
    		
    	}
    	
    }

}
