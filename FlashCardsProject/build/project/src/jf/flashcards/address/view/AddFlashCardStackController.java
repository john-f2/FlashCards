/**
 * Controller Class for the AddFlashCardStack view 
 * adds functionality to the AddFlashCardStack 
 * 
 * @author johnfu
 * 
 */
package jf.flashcards.address.view;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import jf.flashcards.address.model.FlashCardDatabase;

public class AddFlashCardStackController {
	
	 
	 @FXML
	 private TextField newStackName;
	 
	 @FXML
	 private Label ErrorMsgLabel;
	 
	 private boolean okClicked = false;
	 private Stage dialogStage;
	 
	 @FXML
	 private void initialize() {
		 //sets the errorMsgLabel to blank initially 
		 ErrorMsgLabel.setText("");
	 }
	 
	 /**
	  * used to set up a new window for this scene
	  * @param dialogStage
	  */
	 public void setDialogStage(Stage dialogStage) {
	        this.dialogStage = dialogStage;
	   
	}
	 
	/**
	 * returns if the ok button is clicked or not, true if it was 
	 * @return boolean
	 */
	public boolean isOkClicked() {
	        return okClicked;
	 } 
	

	/**
	 * checks if the text input is valid, currently to see if it is not false 
	 * @return boolean, true if valid else false
	 */
	private boolean checkValidInput()
	{
		if(newStackName.getText() == null || newStackName.getText().length() == 0)
		{
			ErrorMsgLabel.setText("Error: Not a valid input");
			return false;
		}
		return true;
	}
	
	
	/**
	 * 
	 * handles the ok button 
	 * when pressed calls insertIntoFlashCardList() to add a new stack 
	 *
	 */
	@FXML
	private void handleOkButton() {
		if(checkValidInput())
		{
			okClicked = true;
			dialogStage.close();
			FlashCardDatabase.insertIntoFlashCardList(newStackName.getText());
			
		}
		
	}
	
	
	/**
	 * closes the dialogStage when cancel button is pressed
	 */
	@FXML
	private void handleCancelButton(){
		
		dialogStage.close();
		
	}


	 
}
