package jf.flashcards.address.util;

/**
 * 
 * pair class used to store the front and back of the flashcard together
 * Each pair value is a string
 * Technically not a pair value, but I added in id to make it easier to remove the flashCard later 
 * 
 * @author johnfu
 *
 */
public class CardPair {

	private String front;
	private String back;
	private int id;
	
	/**
	 * constructor 
	 * @param id
	 * @param frontSide
	 * @param backSide
	 */
	public CardPair(int id, String frontSide, String backSide)
	{
		this.id = id;
		this.front = frontSide;
		this.back = backSide;
				
	}
	
	/**
	 * gets the front of the card
	 * @return String
	 */
	public String getFront() {
		return front;
	}
	
	/**
	 * gets the back of the card
	 * @return String
	 */
	public String getBack() {
		return back;
	}
	
	/**
	 * gets the Id of the card
	 * @return String
	 */
	public int getId()
	{
		return id;
	}
	
	/**
	 * set the card front
	 * @param newFront
	 */
	public void setFront(String newFront) {
		front = newFront;
	}
	
	/**
	 * sets the card front 
	 * @param newBack
	 */
	public void setBack(String newBack) {
		back = newBack;
	}
	
	/**
	 * set the card front 
	 * @param id
	 */
	public void setId(int id)
	{
		this.id = id;
	}
	
	
}
