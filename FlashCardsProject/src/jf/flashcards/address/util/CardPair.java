package jf.flashcards.address.util;

/**
 * 
 * pair class used to store the front and back of the flashcard together
 * Each pair value is a string
 * 
 * @author johnfu
 *
 */
public class CardPair {

	private String front;
	private String back;
	
	public CardPair(String frontSide, String backSide)
	{
		this.front = frontSide;
		this.back = backSide;
				
	}
	
	public String getFront() {
		return front;
	}
	
	public String getBack() {
		return back;
	}
	
	public void setFront(String newFront) {
		front = newFront;
	}
	
	public void setBack(String newBack) {
		back = newBack;
	}
	
	
}
