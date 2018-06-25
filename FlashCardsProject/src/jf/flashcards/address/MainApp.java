/**
 * Main Class for the FlashCard Application
 * 
 * @author John Fu
 * 
 */
package jf.flashcards.address;


import java.io.IOException;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class MainApp extends Application {
	
    private Stage primaryStage;
    private BorderPane rootStage;
	

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
		
        //calls these two void methods that display the rootStage and the flashCardOverview 
		initRootStage();
		showFlashCardOverview();
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
            
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
	

	public static void main(String[] args) {
		launch(args);
	}
}
