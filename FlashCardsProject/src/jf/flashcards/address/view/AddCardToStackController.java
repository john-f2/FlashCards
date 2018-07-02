package jf.flashcards.address.view;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import jf.flashcards.address.model.FlashCardDatabase;

public class AddCardToStackController {
	
	
	 @FXML
	 private TextField newCardFront;
	 
	 @FXML
	 private TextArea newCardBack;
	 
	 @FXML
	 private Label msgLabel;
	
	 private Stage dialogStage;
	 
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
	 
	public void setCurrentStackTable(String setCurrentTable)
	{
		currentTable = setCurrentTable;
	}
	
	public boolean returnCancelClicked() {
		return cancelClicked;
	}
	 
	 
		
	@FXML
	private void handleCancelButton(){
			
			dialogStage.close();
			cancelClicked = true;
			
	}
	
	@FXML 
	private void handleAddButton()
	{
		if(checkValidInputs())
		{
			FlashCardDatabase.addNewCardToStack(currentTable, newCardFront.getText(), newCardBack.getText());
			newCardFront.clear();
			newCardBack.clear();
			msgLabel.setText("New Card Successfully added!");
			
		}
		else {
			msgLabel.setText("Card could not be added");
		}
	}
	
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
