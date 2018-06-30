/**
 * Controller Class for the AddFlashCardStack view 
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
	

	
	private boolean checkValidInput()
	{
		if(newStackName.getText() == null || newStackName.getText().length() == 0)
		{
			ErrorMsgLabel.setText("Error: Not a valid input");
			return false;
		}
		return true;
	}
	
	
	@FXML
	private void handleOkButton() {
		if(checkValidInput())
		{
			okClicked = true;
			dialogStage.close();
			FlashCardDatabase.insertIntoFlashCardList(newStackName.getText());
			
		}
		
	}
	
	@FXML
	private void handleCancelButton(){
		
		dialogStage.close();
		
	}


	 
}
