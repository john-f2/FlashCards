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


public class FlashCardOverviewController {
	
	@FXML
	private TableView<SimpleStringProperty> FlashCardTable;
	
	@FXML
	private TableColumn<SimpleStringProperty,String> FlashCardListColumn;
	
	
    // Reference to the main application.
    private MainApp mainApp;
    
    private ObservableList<SimpleStringProperty> flashCardArrayList = FXCollections.observableArrayList();
    
    //private function used to test the arrayList
    private void addToArrayList() {
    	flashCardArrayList.add(new SimpleStringProperty("this"));
    	flashCardArrayList.add(new SimpleStringProperty("is"));
    	flashCardArrayList.add(new SimpleStringProperty("a"));
    	flashCardArrayList.add(new SimpleStringProperty("test"));
    }
    
    
    @FXML
    private void initialize() {
    	
    	System.out.println("does this do anything now");
    	//System.out.println(mainApp.getStackSize());
    	
    	FlashCardListColumn.setCellValueFactory(cellData -> cellData.getValue());
//    	for(int i=0; i<4; i++)
//    	{
//    		System.out.println("how many times is this even called?");
//    		
//    		FlashCardListColumn.setCellValueFactory(cellData -> cellData.getValue());
//    	}
    	
    	
    	
    }
    
    public void testFunction()
    {
    	System.out.println("does this even work when i do this stuff?");
    	
    }
    
    
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
        FlashCardTable.setItems(mainApp.getFlashCardStack());

    }
	

}
