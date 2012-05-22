package roma;
import java.util.*;

import cards.Card;
import enums.CardNames;


public abstract class Pile implements Cloneable {
	
	private List<Card> s;
	
	public Pile() {
		
		s = new LinkedList<Card>();
		
	}

	public void shuffle () {
		
		Collections.shuffle((LinkedList<Card>)s);
		
	}
	
	/**
	 * Adds a card to the end of the pile.
	 * @param c
	 */
	public void addCard (Card c) {

		s.add(c);

	}
	
	/**
	 * Adds a card to the front of the pile.
	 * Necessary for acceptance compliance, because the discard pile apparently is a stack...
	 * @param c
	 */
	public void addCardToFront (Card c) {

		s.add(0, c);

	}
	
	
	
	public void removeCard (Card c) {
	
		s.remove(c);
	
	}
	
	public Card getCard () {
	
		Card result = s.get(0);
		removeCard(result);
		
		return result;
	
	}
	
	/**
	 * Return this pile as an immutable list
	 * @return
	 */
	public List<Card> asList() {
		
		List<Card> myList = new ArrayList<Card>();
		
		for (Card c : s) {
		
			myList.add(c);
		
		}
		
		return myList;
	
	}
	
	/**
	 * Create an immutable, deep copy of this pile
	 * @return
	 */
	public Pile getCopy() {
		
		Pile copy = null;
		
		try {
		
			copy = (Pile) this.clone();
			copy.s = new LinkedList<Card>(s);
			
			for (int i = 0; i < copy.s.size(); i++) {
				
				copy.s.set(i, copy.s.get(i).getCopy());
				
			}
			
			assert (copy.hashCode() != this.hashCode());
			assert (copy.s.size() == this.s.size());
		
		} catch (CloneNotSupportedException e) {
		
			System.err.println("Error while cloning pile: " + e.getMessage());
			e.printStackTrace();
			System.exit(1);
		
		}
		
		return copy;
	
	}
	
	/**
	 * return the first instance of specified card name
	 * @return
	 * -1 if could not find card
	 */
	public int findCard(CardNames name) {
		
		int result = -1;
		
		int index = 0;
		
		for (Card c : s) {	
			
			if (c.getID() == name) {
				
				result = index;
				break;
				
			}
			
			index ++;		
			
		}
		
		return result;
		
	}
	
	
	public boolean isEmpty() {
	
		return s.isEmpty();
	
	}
	
	
	public void emptyPile() {
	
		s.clear();
	
	}
	
	public Card getIndex (int index) {
	
		return s.get(index);
	
	}
	
	public int getSize() {
	
		return s.size();
	
	}
	
}
