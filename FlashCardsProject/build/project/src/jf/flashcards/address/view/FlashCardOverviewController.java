/**
 * Controller Class for the FlashCardOverview View
 * gives functionality to all the buttons on the FlashCardOverview view 
 * 
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
	
	//view table and column variable 
	@FXML
	private TableView<SimpleStringProperty> FlashCardTable;
	@FXML
	private TableColumn<SimpleStringProperty,String> FlashCardListColumn;
	
	//labels for the flashcard 
	@FXML
	private Label backCardLabel;
	@FXML
	private Label frontBackLabel;
	@FXML
	private Label frontCardLabel;
	
    // Reference to the main application.
    private MainApp mainApp;
    
    private String currentSelectedString = "";
    
    //ObervableList variable which sets the table's items 
    private ObservableList<SimpleStringProperty> flashCardArrayList = FXCollections.observableArrayList();
    
    //Variable representing the cards in a list 
    private ArrayList<CardPair> currentFlashCardStack = new ArrayList<CardPair>();
    
    private int listIndex = 0;
    
    private boolean frontShowing = true;
    
    
    @FXML
    private void initialize() {
    	
    	//initially sets the labels to blank
    	frontCardLabel.setText("");
    	backCardLabel.setText("");
    	frontBackLabel.setText("");
    	

    	//sets the table to the flashCardArrayList items 
    	FlashCardListColumn.setCellValueFactory(cellData -> cellData.getValue());
    	
    	//listener, when an item in the table is selected, call displayStackDetails() 
    	FlashCardTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> displayStackDetails(newValue));
        
    	
    }
    
    
    /**
     * when a new item is selected from the list, displayStackDetails is called, which changes 
     * what is displayed and what the currentFlashCardStack is 
     * @param selectedValue
     */
    private void displayStackDetails(SimpleStringProperty selectedValue)
    {
    	currentSelectedString = selectedValue.get();
    	
    	//gets the currently selected flash card stack 
    	currentFlashCardStack = FlashCardDatabase.getFlashCardStack(currentSelectedString);
    	
    	if(!currentFlashCardStack.isEmpty()){
    		//if the stack is not empty, set the first item in the list to the label
    		frontCardLabel.setText(currentFlashCardStack.get(0).getFront());
    		frontBackLabel.setText("Front");
    		
    	}
    	else
    	{
    		//if the stack is empty, set all the labels to empty 
    		frontBackLabel.setText("");
    		frontCardLabel.setText("");
    		backCardLabel.setText("");
    	}
    	
    }
    
    

    
    /**
     * gives functionality to the "add" button
     * if there is a selected item from the table, then this method will
     * call mainApp.showAddNewCardToStack() which will display the showAddNewCardToStack view 
     * after the card(s) have been added through the showAddNewCardToStack view 
     * the currentFlashCardStack is updated and the labels are set
     */
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
    				//sets the labels if the stack was empty before 
    				frontCardLabel.setText(currentFlashCardStack.get(listIndex).getFront());
    				frontShowing = true;
    				frontBackLabel.setText("Front");
    				

    				
    			}
    		}
    	}
    	
    }
    
    
    /**
     * gives functionality to the next card button
     * sets the labels to the next card 
     * 
     */
    @FXML
    private void nextCard() {
    	
    	if(!currentFlashCardStack.isEmpty())
    	{
    		//if the listIndex goes beyond the currentFlashCardStack size, then it will go back to the start
    		if((listIndex + 1) >= currentFlashCardStack.size())
    		{
    			
    			listIndex = 0;
    		}
    		else
    		{
    			//goes to the next index 
    			listIndex +=1;
    		}
    		
    		//sets the labels 
    		frontCardLabel.setText(currentFlashCardStack.get(listIndex).getFront());
    		frontShowing = true;
    		frontBackLabel.setText("Front");
    		
    	}
    	
    	
    }
    
    /**
     * 
     * gives functionality to the previous card button
     * sets the labels to the previous card
     * 
     */
    @FXML
    private void previousCard() {
    	
    	if(!currentFlashCardStack.isEmpty())
    	{
    		//sets the listIndex to the back of the list if the listIndex is less than 0
    		if((listIndex -1) < 0 )
    		{
    			listIndex = currentFlashCardStack.size()-1;
    		}
    		else
    		{
    			//sets the listIndex to the previous index 
    			listIndex = listIndex - 1;
    		}
    		
    		//sets the card labels 
    		frontCardLabel.setText(currentFlashCardStack.get(listIndex).getFront());
    		frontShowing = true;
    		frontBackLabel.setText("Front");
    	}
    }
    
    
    /**
     * "Flips the card" by changing the front and back labels
     * flip to back-> change front labels to "" and change back label to the card pair at index 
     */
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
    	//changes labels to show back 
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
    	//changes labels to show front 
    	else if(frontShowing == false) {
    		frontCardLabel.setText(currentFlashCardStack.get(listIndex).getFront());
    		backCardLabel.setText("");
    		frontShowing = true;
    		frontBackLabel.setText("Front");
    		
    	}
    	
    }
    
    /**
     * 
     * shuffles the currentFlashCardStack by using Collections.Shuffle() 
     * resets the labels and index after shuffling 
     * 
     */
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
    
    
    /**
     * 
     * adds functionality to the delete button
     * deletes the current card the user is on
     * 
     */
    @FXML
    private void deleteCard() {
    	
    	if(!currentFlashCardStack.isEmpty())
    	{
    		
    		//before deleting, an confirmation alert is set to confirm the action
    		Alert alert = new Alert(AlertType.CONFIRMATION);
    		alert.setTitle("Confirmation");
    		alert.setHeaderText("Are you sure you want to delete this card");
    		alert.setContentText("Once you delete this card, this action cannot be undone");
    		
    		Optional<ButtonType> result = alert.showAndWait();
    		if(result.get() == ButtonType.OK)
    		{
    			FlashCardDatabase.removeCardFromStack(currentFlashCardStack.get(listIndex).getId(), currentSelectedString);
    			currentFlashCardStack = FlashCardDatabase.getFlashCardStack(currentSelectedString);
    			
    			//if the stack still has cards after removing one
    			if(!currentFlashCardStack.isEmpty())
    			{
        			listIndex = 0;
            		frontCardLabel.setText(currentFlashCardStack.get(listIndex).getFront());
            		frontShowing = true;
    			}
    			//if the stack is empty after removing the card 
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
    
    /**
     * sets the mainApp of the controller to the mainApp of the project
     * allows references to be given back 
     * 
     * @param mainApp
     */
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
        //testing this out
        FlashCardTable.setItems(FlashCardDatabase.getFlashCardList());
        
        //original
        //FlashCardTable.setItems(mainApp.getFlashCardStack());

    }
	

}
