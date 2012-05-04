package roma;
import java.util.Stack;

import cards.Card;


public class Pile {
	
	Stack<Card> s;
	
	public Pile() {
		
		s = new Stack<Card>();
		
	}

	public void shuffle () {
		RandomGenerator r = new RandomGenerator();
		int n = s.size() * 10;
		Card first;
		Card second;
		//Card temp;
		int pos;
		while (n > 0) {
			first = s.firstElement(); // get head card
			pos = r.randomInt(2, s.size() - 1); // choose a random card affter this one
			second = s.get (pos);
			
			// swap positions of pos and second
			
			s.setElementAt(first, pos);
			s.setElementAt(second, 0);
			n--;
		}
	}
	
	public Stack<Card> getStack() {
		
		return s;
		
	}
	
}
