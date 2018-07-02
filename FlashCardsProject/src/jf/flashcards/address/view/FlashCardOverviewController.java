/**
 * Controller Class for the FlashCardOverview View
 * 
 * @author John Fu
 * 
 */

package jf.flashcards.address.view;


import java.util.ArrayList;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import jf.flashcards.address.MainApp;
import jf.flashcards.address.model.FlashCardDatabase;
import jf.flashcards.address.util.CardPair;


public class FlashCardOverviewController {
	
	@FXML
	private TableView<SimpleStringProperty> FlashCardTable;
	
	@FXML
	private TableColumn<SimpleStringProperty,String> FlashCardListColumn;
	
	@FXML
	private Label currentCardLabel;
	
	
    // Reference to the main application.
    private MainApp mainApp;
    
    private String currentSelectedString = "";
    
    private ObservableList<SimpleStringProperty> flashCardArrayList = FXCollections.observableArrayList();
    
    private ArrayList<CardPair> currentFlashCardStack = null;
    
    
    //private function used to test the arrayList
    private void addToArrayList() {
    	flashCardArrayList.add(new SimpleStringProperty("this"));
    	flashCardArrayList.add(new SimpleStringProperty("is"));
    	flashCardArrayList.add(new SimpleStringProperty("a"));
    	flashCardArrayList.add(new SimpleStringProperty("test"));
    }
    
    
    @FXML
    private void initialize() {
    	

    	//System.out.println(mainApp.getStackSize());
    	
    	currentCardLabel.setText("");
    	
    	FlashCardListColumn.setCellValueFactory(cellData -> cellData.getValue());
    	
    	FlashCardTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> displayStackDetails(newValue));
        
    	   
    	
//    	for(int i=0; i<4; i++)
//    	{
//    		System.out.println("how many times is this even called?");
//    		
//    		FlashCardListColumn.setCellValueFactory(cellData -> cellData.getValue());
//    	}
    	
    }
    
    
    public void displayStackDetails(SimpleStringProperty selectedValue)
    {
    	currentSelectedString = selectedValue.get();
    	
    	currentFlashCardStack = FlashCardDatabase.getFlashCardStack(currentSelectedString);
    	
    	if(!currentFlashCardStack.isEmpty()){
    		currentCardLabel.setText(currentFlashCardStack.get(0).getFront());
    		
    	}
    	else
    	{
    		currentCardLabel.setText("");
    	}
    	
    }
    
    

    
    
    @FXML
    private void addNewCard() {
    	
    	if(currentSelectedString.equals(""))
    	{
    		 Alert alert = new Alert(AlertType.WARNING);
             alert.initOwner(mainApp.getPrimaryStage());
             alert.setTitle("No Selection");
             alert.setHeaderText("No Flash Card Stack Selected");
             alert.setContentText("Please select a stack in the table.");

             alert.showAndWait();
    	}
    	else
    	{
    		boolean cancelClicked = mainApp.showAddNewCardToStack(currentSelectedString);
    		if(cancelClicked)
    		{
    			currentFlashCardStack = FlashCardDatabase.getFlashCardStack(currentSelectedString);
    			if(currentCardLabel.getText().equals(""))
    			{
    				//this get(0) is temporary, i will have a pointer to the cards later!
    				currentCardLabel.setText(currentFlashCardStack.get(0).getFront());
    			}
    		}
    	}
    	
    	

    	
    }
    
    
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
        FlashCardTable.setItems(mainApp.getFlashCardStack());

    }
	

}
