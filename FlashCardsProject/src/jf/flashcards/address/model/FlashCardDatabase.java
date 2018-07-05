/**
 * 
 * FlashCardDatabase class, used to access the database and return data 
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
	
	
	public static boolean insertIntoFlashCardList(String newFlashCardStack) {
		try {
			
			String insertQuery = "INSERT INTO flash_card_list(id, name) VALUES (?, ?)"; 
			PreparedStatement pstmt = db.prepareStatement(insertQuery);
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
	
	
	public static ArrayList<CardPair> getFlashCardStack(String getStack)
	{
		ArrayList<CardPair> returnArrayList = new ArrayList<CardPair>();
		
		
		try {
			String stackQuery = "SELECT id, front, back FROM  " + getStack.toLowerCase();
			ResultSet rs = stmt.executeQuery(stackQuery);

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
