/**
 * 
 * FlashCardDatabase class, used to access the database and return data 
 * 
 * how the databases are set up is by having one main table called flash_card_list, 
 * which contains the names of the flash cards stacks
 * each name creates a table under the same name (ex: test table) which stores the cards for the stack 
 * 
 * @author johnfu
 * 
 */
package jf.flashcards.address.model;

import java.sql.*;
import java.util.ArrayList;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import jf.flashcards.address.util.CardPair;


public class FlashCardDatabase {
	
	//default constructor
	public FlashCardDatabase() {};
	
	private static Connection db = null;
	private static Statement stmt = null;
	
	/**
	 * creates the connection to the sqlite database 
	 * called in mainApp Start()
	 * @return
	 */
	public static boolean establishConnection(){
		
		try {
	         Class.forName("org.sqlite.JDBC");
	         //the database is called flashcards.db
	         db = DriverManager.getConnection("jdbc:sqlite:flashcards.db");
	         stmt = db.createStatement();
	         
	         return true;
		}
		catch (Exception e) {
			System.err.println( e.getClass().getName() + ": " + e.getMessage());
			return false;
			
		}
	}
	

	
	/**
	 * closes the connections 
	 * called when program ends in Stop()
	 * @return
	 */
	public static boolean closeConnection() {
		try {
			//closes the connection and the statement
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
	
	
	/**
	 * Creates the FlashCardListTable which stores the flash card stack names
	 * if a duplicate name is inserted to the table, then the action will fail
	 * @return true if successful else false 
	 */
	public static boolean createFlashCardListTable() {

		try{

	        
			//I can change this instead of ON CONFLICT INGNORE, I can use ABORT 
			//and it will cause an exception which i can catch for debugging later 
			//name  TEXT UNIQUE ON CONFLICT IGNORE
	         String tableQuery = "CREATE TABLE IF NOT EXISTS flash_card_list " +
	        		 		"(id  INTEGER   PRIMARY KEY  AUTOINCREMENT NOT NULL, " +
	        		 		" name  TEXT UNIQUE ON CONFLICT FAIl NOT NULL )";
	         
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
	
	
	/**
	 * Inserts a new name into flash_card_list
	 * flash_card_list tells the program which flashCardStacks are available 
	 * and displays them on the table on the FlashCardOverview 
	 * 
	 * @param newFlashCardStack
	 * @return true if successful else false
	 */
	public static boolean insertIntoFlashCardList(String newFlashCardStack) {
		try {
			
			String insertQuery = "INSERT INTO flash_card_list(id, name) VALUES (?, ?)"; 
			PreparedStatement pstmt = db.prepareStatement(insertQuery);
			
			//id is an autoincrement int 
			pstmt.setString(1, null);
			pstmt.setString(2, newFlashCardStack.toLowerCase());
			
			pstmt.executeUpdate();
			
			pstmt.close();
			
			newFlashCardStackTable(newFlashCardStack);
			
			
			
			return true;
			
		}
		catch(Exception e)
		{
			System.out.println("insert failed, thus it must be in the list already");
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
			return false;
		}
		
	}
	
	/**
	 * private method, called when a new flash card stack is added which creates 
	 * a new table under the same name 
	 * 
	 * @param newStack
	 */
	private static void newFlashCardStackTable(String newStack) {
		
		try {
		
			String addStackQuery = "CREATE TABLE IF NOT EXISTS  " + newStack.toLowerCase() 
								+ "(id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "
								+ "front TEXT NOT NULL, "
								+ "back  TEXT NOT NULL) ";
			
			 stmt.executeUpdate(addStackQuery);	
			}
		catch(Exception e)
		{
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
		}
		
		
	}
	
	/**
	 * adds a new card entry to the specified table 
	 * called within FlashCardOverview through the add button
	 * @param table
	 * @param front
	 * @param back
	 * @return true if successful else false
	 */
	public static boolean addNewCardToStack(String table, String front, String back) {
		
		try {
			
			String addQuery = "INSERT INTO " + table.toLowerCase()
							 +"(id, front, back) VALUES(?,?,?)";
			
			PreparedStatement pstmt = db.prepareStatement(addQuery);
			pstmt.setString(1,null);
			pstmt.setString(2, front);
			pstmt.setString(3, back);
			
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
	
	/**
	 * removes specified card from the specified flash card stack 
	 * called within FlashCardOverview through the remove button
	 * @param cardId
	 * @param stackTable
	 * @return true if successful else false
	 */
	public static boolean removeCardFromStack(int cardId, String stackTable)
	{
		try {
			String deleteQuery = "DELETE FROM " + stackTable.toLowerCase()
								 + " WHERE id= " + cardId;
			

			stmt.executeUpdate(deleteQuery);
			
			return true;
			
		}
		catch(Exception e)
		{
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
			return false;
			
		}
	}
	
	
	/**
	 * gets the cards with in the flash card table 
	 * returns them as a ArrayList of CardPairs, which stores the cards information 
	 * @param getStack
	 * @return ArrayList<CardPair> 
	 */
	public static ArrayList<CardPair> getFlashCardStack(String getStack)
	{
		ArrayList<CardPair> returnArrayList = new ArrayList<CardPair>();
		
		
		try {
			String stackQuery = "SELECT id, front, back FROM  " + getStack.toLowerCase();
			ResultSet rs = stmt.executeQuery(stackQuery);

			//if there is nothing in the table, then it will return an empty ArrayList
			while(rs.next())
			{
				String cardFront = rs.getString("front");
				String cardBack = rs.getString("back");
				String cardId = rs.getString("id");
				int cardIdNum = Integer.parseInt(cardId);
				CardPair newPair = new CardPair(cardIdNum,cardFront, cardBack);
				returnArrayList.add(newPair);
				
				
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
	 * gets the name from the flash_card_list table 
	 * returns them as an ObservableList which is used to populate the table in FlashCardOverview
	 * @return ObservableList<SimpleStringProperty> 
	 */
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
	 * used for debugging purposes currently, but can be used in the future to delete 
	 * all the flash cards in the program
	 * 
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
	
	/**
	 * Drops the specified name from the flash_card_list table
	 * used when the delete flash card stack menu option is selected in the rootController 
	 * 
	 * @param removeStack
	 * @return boolean 
	 */
	public static boolean removeFlashCardStackFromList(String removeStack) {
		try {
			String removeTableQuery = "DELETE FROM flash_card_list WHERE name = '" + removeStack + "'";
			stmt.executeUpdate(removeTableQuery);
			
			return true;
			
		}
		catch(Exception e)
		{
			
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
			return false;
		}
	}
	
	/**
	 * removes the specified flash card stack table 
	 * used when the delete flash card stack menu option is selected in the rootController 
	 * 
	 * @param dropStack
	 * @return boolean 
	 */
	public static boolean dropFlashCardStack(String dropStack) {
		try {
			String dropTableQuery = "DROP TABLE IF EXISTS " + dropStack;
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
