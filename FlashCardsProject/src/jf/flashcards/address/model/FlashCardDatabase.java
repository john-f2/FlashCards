/**
 * 
 * FlashCardDatabase class, used to access the database and return data 
 * 
 * @author johnfu
 * 
 */
package jf.flashcards.address.model;

import java.sql.*;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class FlashCardDatabase {
	
	//default constructor
	public FlashCardDatabase() {};
	
	private static Connection db = null;
	private static Statement stmt = null;
	
	public static boolean establishConnection(){
		
		try {
	         Class.forName("org.sqlite.JDBC");
	         db = DriverManager.getConnection("jdbc:sqlite:flashcards.db");
	         stmt = db.createStatement();
	         System.out.println("Opened database successfully");
	         
	         return true;
		}
		catch (Exception e) {
			System.err.println( e.getClass().getName() + ": " + e.getMessage());
			return false;
			
		}
	}
	

	
	public static boolean closeConnection() {
		try {
			stmt.close();
			db.close();
			return true;
			
		}
		catch (Exception e)
		{
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
			System.out.println("Could not close connections");
			return false;
			
		}	
	}
	
	public static boolean createFlashCardListTable() {

		try{

	         
	         
			//I can change this instead of ON CONFLICT INGNORE, I can use ABORT 
			//and it will cause an exception which i can catch for debugging later 
	         String tableQuery = "CREATE TABLE IF NOT EXISTS flash_card_list " +
	        		 		"(id  INTEGER   PRIMARY KEY  AUTOINCREMENT NOT NULL, " +
	        		 		" name  TEXT UNIQUE ON CONFLICT IGNORE NOT NULL )";
	         
	         stmt.executeUpdate(tableQuery);
	         
			
			return true;
		}
		catch( Exception e)
		{
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
			System.out.println("went to catch statment");
			return false;
		}
	}
	
	
	public static boolean insertIntoFlashCardList(String newFlashCardStack) {
		try {
			
			String insertQuery = "INSERT INTO flash_card_list(id, name) VALUES (?, ?)"; 
			PreparedStatement pstmt = db.prepareStatement(insertQuery);
			pstmt.setString(1, null);
			pstmt.setString(2, newFlashCardStack);
			
			pstmt.executeUpdate();
			
			pstmt.close();
			
			return true;
			
		}
		catch(Exception e)
		{
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
			return false;
		}
		
	}
	
	public static ObservableList<SimpleStringProperty> getFlashCardList()
	{
		ObservableList<SimpleStringProperty> returnArrayList = FXCollections.observableArrayList();
		
		try {
			String nameQuery = "SELECT name FROM flash_card_list ";
			ResultSet rs = stmt.executeQuery(nameQuery);

			while(rs.next())
			{
				String StackName = rs.getString("name");
				returnArrayList.add(new SimpleStringProperty(StackName));
			}
			
		}
		catch(Exception e)
		{
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
			System.out.println("Error");
		}
		
		return returnArrayList;
	}
	
	/**
	 * Drops the flash_card_list table 
	 */
	public static boolean dropFlashCardList() {
		try {
			String dropTableQuery = "DROP TABLE IF EXISTS flash_card_list";
			stmt.executeUpdate(dropTableQuery);			
			return true;
		}
		catch(Exception e)
		{
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
			System.out.println("did not drop table");
			return false;
		}
		
	}
	
	

}
