
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
	
    
    @FXML 
    private void initialize() {
    	errorMsg.setText("");
    	
    	setComboBox();
    	
  
    	
    	deleteComboBox.setOnAction((event) -> {
    	     selectedTable = deleteComboBox.getSelectionModel().getSelectedItem();
  
    	     
    	    
    	});
    	
    	
    	
    	
    }
    
    @FXML
    private void handleCancel() {
    	dialogStage.close();
    }
    
    @FXML
    private void handleOk() {
    	if(selectedTable.equals("")) {
    		errorMsg.setText("No Stack Selected to Delete");
    		
    	}
    	else {
    		errorMsg.setText("");
    		
       		Alert alert = new Alert(AlertType.CONFIRMATION);
    		alert.setTitle("Confirmation");
    		alert.setHeaderText("Are you sure you want to delete this stack");
    		alert.setContentText("Once you delete this stack, all the cards within will be lost");
    		
    		Optional<ButtonType> result = alert.showAndWait();
    		if(result.get() == ButtonType.OK)
    		{
    			

    			FlashCardDatabase.removeFlashCardStackFromList(selectedTable);
    			FlashCardDatabase.dropFlashCardStack(selectedTable);
    			String msgText = selectedTable + " has been successfully removed";
    			//errorMsg.setText(msgText);
    			setComboBox();
    			dialogStage.close();
  
    			
    			
    		}
    	}
    }
	
	

}



