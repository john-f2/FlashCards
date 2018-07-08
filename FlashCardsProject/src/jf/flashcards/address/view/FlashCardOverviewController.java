/**
 * Controller Class for the FlashCardOverview View
 * 
 * @author John Fu
 * 
 */

package jf.flashcards.address.view;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
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
	private Label backCardLabel;
	
	@FXML
	private Label frontBackLabel;
	
	@FXML
	private Label frontCardLabel;
	
    // Reference to the main application.
    private MainApp mainApp;
    
    private String currentSelectedString = "";
    
    private ObservableList<SimpleStringProperty> flashCardArrayList = FXCollections.observableArrayList();
    
    private ArrayList<CardPair> currentFlashCardStack = new ArrayList<CardPair>();
    
    private int listIndex = 0;
    
    private boolean frontShowing = true;
    
    
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
    	
    	frontCardLabel.setText("");
    	backCardLabel.setText("");
    	frontBackLabel.setText("");
    	

    	
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
    		frontCardLabel.setText(currentFlashCardStack.get(0).getFront());
    		frontBackLabel.setText("Front");
    		
    	}
    	else
    	{
    		frontBackLabel.setText("");
    		frontCardLabel.setText("");
    		backCardLabel.setText("");
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
    			
    			if(!currentFlashCardStack.isEmpty() && frontCardLabel.getText().equals(""))
    			{
    				//this get(0) is temporary, i will have a pointer to the cards later!
    				frontCardLabel.setText(currentFlashCardStack.get(listIndex).getFront());
    				frontShowing = true;
    				frontBackLabel.setText("Front");
    				

    				
    			}
    		}
    	}
    	
    	

    	
    }
    
    @FXML
    private void nextCard() {
    	
    	if(!currentFlashCardStack.isEmpty())
    	{
    		if((listIndex + 1) >= currentFlashCardStack.size())
    		{
    			
    			listIndex = 0;
    		}
    		else
    		{
    		
    			listIndex +=1;
    		}
    		frontCardLabel.setText(currentFlashCardStack.get(listIndex).getFront());
    		frontShowing = true;
    		frontBackLabel.setText("Front");
    		
    	}
    	
    	
    }
    
    @FXML
    private void previousCard() {
    	if(!currentFlashCardStack.isEmpty())
    	{
    		if((listIndex -1) < 0 )
    		{
    			listIndex = currentFlashCardStack.size()-1;
    		}
    		else
    		{
    			listIndex = listIndex - 1;
    		}
    		frontCardLabel.setText(currentFlashCardStack.get(listIndex).getFront());
    		frontShowing = true;
    		frontBackLabel.setText("Front");
    	}
    }
    
    @FXML
    private void flipCard() {
    	
    	if(currentFlashCardStack.isEmpty())
    	{
    		Alert alert = new Alert(AlertType.WARNING);
    		alert.initOwner(mainApp.getPrimaryStage());
    		alert.setTitle("No Selection");
    		alert.setHeaderText("No cards are in the stack");
    		alert.setContentText("Please add cards to the current stack.");

    		alert.showAndWait();
    	}
    	else if(frontShowing)
    	{
    		backCardLabel.setText(currentFlashCardStack.get(listIndex).getBack());
    		frontCardLabel.setText("");
    		frontShowing = false;
    		frontBackLabel.setText("Back");
    		

	    	backCardLabel.setMaxWidth(280);
	    	backCardLabel.setMaxHeight(160);
	    	backCardLabel.setWrapText(true);
    		
    	}
    	else if(frontShowing == false) {
    		frontCardLabel.setText(currentFlashCardStack.get(listIndex).getFront());
    		backCardLabel.setText("");
    		frontShowing = true;
    		frontBackLabel.setText("Front");
    		
    	}
    	
    }
    
    @FXML
    private void shuffleList() {
    	
    	if(!currentFlashCardStack.isEmpty())
    	{
        	Collections.shuffle(currentFlashCardStack);
        	listIndex = 0;
    		frontCardLabel.setText(currentFlashCardStack.get(listIndex).getFront());
    		frontShowing = true;
        	
    	}
    	else
    	{
       		Alert alert = new Alert(AlertType.WARNING);
    		alert.initOwner(mainApp.getPrimaryStage());
    		alert.setTitle("No Selection");
    		alert.setHeaderText("No cards are in the stack");
    		alert.setContentText("Cannot shuffle cards");

    		alert.showAndWait();
    	}

    }
    
    @FXML
    private void deleteCard() {
    	
    	if(!currentFlashCardStack.isEmpty())
    	{
    		
    		
    		Alert alert = new Alert(AlertType.CONFIRMATION);
    		alert.setTitle("Confirmation");
    		alert.setHeaderText("Are you sure you want to delete this card");
    		alert.setContentText("Once you delete this card, this action cannot be undone");
    		
    		Optional<ButtonType> result = alert.showAndWait();
    		if(result.get() == ButtonType.OK)
    		{
    			FlashCardDatabase.removeCardFromStack(currentFlashCardStack.get(listIndex).getId(), currentSelectedString);
    			currentFlashCardStack = FlashCardDatabase.getFlashCardStack(currentSelectedString);
    			
    			if(!currentFlashCardStack.isEmpty())
    			{
        			listIndex = 0;
            		frontCardLabel.setText(currentFlashCardStack.get(listIndex).getFront());
            		frontShowing = true;
    			}
    			else
    			{
    				listIndex =0;
    				frontShowing = true;
    				frontCardLabel.setText("");
    				backCardLabel.setText("");
    				
    			}

    			
    		}

    		
    	}
    	else
    	{
       		Alert alert = new Alert(AlertType.WARNING);
    		alert.initOwner(mainApp.getPrimaryStage());
    		alert.setTitle("No Selection");
    		alert.setHeaderText("No cards are in the stack");
    		alert.setContentText("Cannot remove any cards");

    		alert.showAndWait();
    	}
    	
    	
    }
    
    
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
        //testing this out
        FlashCardTable.setItems(FlashCardDatabase.getFlashCardList());
        
        //original
        //FlashCardTable.setItems(mainApp.getFlashCardStack());

    }
	

}
