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
    
    private ObservableList<SimpleStringProperty> testArrayList = FXCollections.observableArrayList();
    
    private void addToArrayList() {
    	testArrayList.add(new SimpleStringProperty("this"));
    	testArrayList.add(new SimpleStringProperty("is"));
    	testArrayList.add(new SimpleStringProperty("a"));
    	testArrayList.add(new SimpleStringProperty("test"));
    }
    
    
    @FXML
    private void initialize() {
    	
    	for(int i=0; i<3; i++)
    	{
    		FlashCardListColumn.setCellValueFactory(cellData -> cellData.getValue());
    	}
    	
    	
    	
    }
    
    
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
        addToArrayList();
        FlashCardTable.setItems(testArrayList);

    }
	

}
