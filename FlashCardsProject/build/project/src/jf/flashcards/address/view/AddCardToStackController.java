/**
 * 
 * AddCardToStackController class
 * provides functionality to the AddCardToStackController view
 * 
 * @author johnfu
 * 
 * 
 */
package jf.flashcards.address.view;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import jf.flashcards.address.model.FlashCardDatabase;

public class AddCardToStackController {
	
	 //FXML private variables 
	 @FXML
	 private TextField newCardFront;
	 
	 @FXML
	 private TextArea newCardBack;
	 
	 @FXML
	 private Label msgLabel;
	
	 //sets the dialogStage 
	 private Stage dialogStage;
	 
	 //currentTable string to indicate which table we are adding a new Card into
	 private String currentTable = "";
	
	 
	 private boolean cancelClicked = false;
	 
	 
	 @FXML
	 private void initialize()
	 {
		 msgLabel.setText("");
	 }
	
	
	 /**
	  * used to set up a new window for this scene
	  * @param dialogStage
	  */
	 public void setDialogStage(Stage dialogStage) {
	        this.dialogStage = dialogStage;
	   
	}
	 
	 /**
	  * sets the currentTable string
	  * @param setCurrentTable
	  */
	public void setCurrentStackTable(String setCurrentTable)
	{
		currentTable = setCurrentTable;
	}
	
	
	/**
	 * returns the cancelClicked boolean 
	 * @return
	 */
	public boolean returnCancelClicked() {
		return cancelClicked;
	}
	 
	 
	
	/**
	 * closes the dialogStage 
	 */
	@FXML
	private void handleCancelButton(){
			
			dialogStage.close();
			cancelClicked = true;
			
	}
	
	
	/**
	 * checks whether the inputs are valid and then adds the card to the database
	 * allows the user to continue adding cards after a successful addition  
	 */
	@FXML 
	private void handleAddButton()
	{
		if(checkValidInputs())
		{
			FlashCardDatabase.addNewCardToStack(currentTable, newCardFront.getText(), newCardBack.getText());
			String msgText = newCardFront.getText() + " has been succesfully added!";
			newCardFront.clear();
			newCardBack.clear();
			msgLabel.setText(msgText);
			
		}
		else {
			msgLabel.setText("Card could not be added");
		}
	}
	
	/**
	 * private method to check if the input boxes have a valid input, 
	 * currently checking if they are null or empty
	 * @return true if valid, else false 
	 */
	private boolean checkValidInputs() {
		if(newCardFront.getText() == null || newCardFront.getText().length() ==0)
		{
			return false;
		}
		else if(newCardBack.getText() == null || newCardBack.getText().length() == 0)
		{
			return false;
		}
		
		return true;
	}

}
