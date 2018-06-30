/**
 * Main Class for the FlashCard Application
 * 
 * @author John Fu
 * 
 */
package jf.flashcards.address;


import java.io.IOException;

import java.sql.*;

import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import jf.flashcards.address.model.FlashCardDatabase;
import jf.flashcards.address.view.AddFlashCardStackController;
import jf.flashcards.address.view.FlashCardOverviewController;
import jf.flashcards.address.view.RootStageController;

public class MainApp extends Application {
	
    private Stage primaryStage;
    private BorderPane rootStage;
    
    private ObservableList<SimpleStringProperty> flashCardStack = FXCollections.observableArrayList();;
    
    
    public ObservableList<SimpleStringProperty> getFlashCardStack()
    {
    	return flashCardStack;
    }
    

    
    public void setFlashCardStack(ObservableList<SimpleStringProperty> updatedStack)
    {
    	flashCardStack = updatedStack;
    	
    	for(SimpleStringProperty i : flashCardStack)
    	{
    		System.out.println(i);
    	}
    }
	

    /**
     * Starting point for javaFx applications
     */
	@Override
	public void start(Stage primaryStage) {
		
		//Sets the primaryStage and the title for the stage
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("FlashCards!");
        
        //adds an icon next to the title at the top ***Remember to switch out for your own picture***
        //***Currently a placeholder image****
        this.primaryStage.getIcons().add(new Image("file:resources/images/if_Task_1737367.png"));
        
        //sets up the sqlite table if it doesn't exists
        System.out.println(FlashCardDatabase.establishConnection());
        System.out.println(FlashCardDatabase.dropFlashCardList());
        System.out.println(FlashCardDatabase.createFlashCardListTable());
        System.out.println(FlashCardDatabase.insertIntoFlashCardList("jump"));
        System.out.println(FlashCardDatabase.insertIntoFlashCardList("up"));
        flashCardStack = FlashCardDatabase.getFlashCardList();
//        flashCardStack = FlashCardDatabase.getFlashCardList();
//        
//        for(SimpleStringProperty i : flashCardStack)
//        {
//        	System.out.println(i);
//        }
        

        
        //calls these two void methods that display the rootStage and the flashCardOverview 
		initRootStage();
		showFlashCardOverview();
	}
	
	
    @Override
    public void stop() {
        System.out.println("Stop");
        FlashCardDatabase.closeConnection();

    }
	
	
	/**
	 * Initializes the RootStage view of our application 
	 */
	public void initRootStage()
	{
		try {
			//loads the RootStage xml
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/RootStage.fxml"));
        
			//sets the rootStage variable to the loader (remember the rootStage fxml is a borderPane) 
			rootStage = (BorderPane) loader.load(); 
			
			//sets the scene to the rootStage
			Scene scene = new Scene(rootStage);
			primaryStage.setScene(scene);
			
			//allows the RootStageController to be referenced back to the main class
            RootStageController controller = loader.getController();
            controller.setMainApp(this);
			
			
			primaryStage.show();
			
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
		
	}
	
	public void showFlashCardOverview()
	{
		try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/FlashCardOverview.fxml"));
            
            //you have to load the root class for the fxml, in this case it is an AnchorPane 
            AnchorPane flashcardOverview = (AnchorPane) loader.load();
            
            //sets the FlashCardOverview xml to the center of the rootStage BorderPane 
            rootStage.setCenter(flashcardOverview);
            
            FlashCardOverviewController controller = loader.getController();
            controller.setMainApp(this);
            
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
	
	
	
	public boolean showAddNewFlashCardStack()
	{
		try {
			
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/AddFlashCardStack.fxml"));
            AnchorPane page = (AnchorPane) loader.load();
            
            
            //sets the Stage and Scene
            //Allows the new Scene to be shown in a window 
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Edit Person");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            //create scene object and give it the page variable 
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);
            
            //References the AddFlashCardStackController class 
            //so that it can be connected to the main class
            AddFlashCardStackController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            
            //waits until the dialogStage is closed
            dialogStage.showAndWait();
            
            return controller.isOkClicked();
			
			
		}
		catch(IOException e)
		{
            e.printStackTrace();
            return false;
		}
	}
	

	public static void main(String[] args) {
		launch(args);
		
	}
}
