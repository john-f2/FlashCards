
/**
 * controller class for the DeleteStackDialog view 
 * 
 * @author johnfu
 * 
 */

package jf.flashcards.address.view;
import java.util.Optional;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import jf.flashcards.address.MainApp;
import jf.flashcards.address.model.FlashCardDatabase;

public class DeleteStackDialogController {
	
	@FXML
	private ComboBox<String> deleteComboBox;
	@FXML
	private Label errorMsg;
	
	private Stage dialogStage;

	

	
	private String selectedTable = "";
	
	 /**
	  * used to set up a new window for this scene
	  * @param dialogStage
	  */
	public void setDialogStage(Stage dialogStage) {
	        this.dialogStage = dialogStage;
	   
	}
	

	
	
	/**
	 * the DeleteStackDialog view uses a comboBox (a drop down menu) to select which table to delete
	 * to set up the combo box, I first get the flash_card_list names then convert each SimpleStringProperty to 
	 * a normal string and add them to a ObservableList<String> which is then set to the comboBox 
	 */
	private void setComboBox() {
    	ObservableList<SimpleStringProperty> flashCardList = FlashCardDatabase.getFlashCardList();
    	
    	ObservableList<String> flashCardListAsString = FXCollections.observableArrayList();
    	
    	if(!flashCardList.isEmpty())
    	{
    		for(int i=0; i<flashCardList.size(); i++)
    		{
    			flashCardListAsString.add(flashCardList.get(i).get());
    		}
    	}
      	deleteComboBox.setItems(flashCardListAsString);
	}
	
    
	/**
	 * initialize() function
	 * sets the errorMsg label to blank and calls the setComboBox
	 * 
	 */
    @FXML 
    private void initialize() {
    	errorMsg.setText("");
    	
    	setComboBox();
    	
  
    	//listener, when an item is selected from the comboBox, 
    	//it sets the selectedTable String variable to the new selected item 
    	deleteComboBox.setOnAction((event) -> {
    	     selectedTable = deleteComboBox.getSelectionModel().getSelectedItem();
  
    	});
    	
    }
    
    
    /**
     * closes the dialogStage when cancel is pressed
     * 
     */
    @FXML
    private void handleCancel() {
    	dialogStage.close();
    }
    
    
    /**
     * handles when Ok button is pressed
     * gives the user an alert before deleting the table and removing the table name from 
     * the flash_card_list
     * 
     */
    @FXML
    private void handleOk() {
    	if(selectedTable.equals("")) {
    		errorMsg.setText("No Stack Selected to Delete");
    		
    	}
    	else {
    		errorMsg.setText("");
    		
    		//confirmation alert for the user 
       		Alert alert = new Alert(AlertType.CONFIRMATION);
    		alert.setTitle("Confirmation");
    		alert.setHeaderText("Are you sure you want to delete this stack");
    		alert.setContentText("Once you delete this stack, all the cards within will be lost");
    		
    		Optional<ButtonType> result = alert.showAndWait();
    		if(result.get() == ButtonType.OK)
    		{
    			
    			//removes the name from the flash_card_list
    			FlashCardDatabase.removeFlashCardStackFromList(selectedTable);
    			//drops the actual table 
    			FlashCardDatabase.dropFlashCardStack(selectedTable);
    			
    			//there is an msg label that will tell the user which table has been removed, currently not in use 
    			//String msgText = selectedTable + " has been successfully removed";
    			//errorMsg.setText(msgText);
    			
    			setComboBox();
    			dialogStage.close();
  
    			
    			
    		}
    	}
    }
	
	

}



