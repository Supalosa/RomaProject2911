/**
 * 
 */
package roma;

import java.util.*;

import actions.IPlayerAction;
import cards.Card;

/**
 * @author Supalosa
 * Mocks the input to the game.
 * Used for testing (acceptance and unit testing)
 */
public class MockController implements IController {
	
	Game localGame;
	Queue<String> mockStdIn;
	
	public void setGame(Game g) {
		localGame = g;
		mockStdIn = new LinkedList<String>();
	}

	/**
	 * Insert a string to the queue!
	 */
	public void insertInput(String mockedInput) {
		mockStdIn.add(mockedInput);
	}
	
	public void runGame() {
		// don't do anything
	}

	public String getString(String message) {
		// get the last thing off the mockStdIn
		String out = null;
		showMessage(message);
		if (mockStdIn.isEmpty()) {
			System.err.println ("MockController::getString: queue ran out of messages!");
			try {
				throw new Exception ("FAIL, see above");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.exit(1);
			}
		} else {
			out = mockStdIn.poll();
			//System.out.println (" > Mock: '" + out + "'");
		}
		return out;
	}

	public int getInt(String message) {
		// get the last thing off mock stdin, casted to an int
		// error (and return 0) if no input
		String input = null;
		showMessage(message);
		if (mockStdIn.isEmpty()) {
			System.err.println ("MockController::getString: queue ran out of messages!");
		} else {
			input = mockStdIn.poll();
			//System.out.println (" > Mock: '" + input + "'");
		}
		int result = 0;
		if (input != null) {
			try {
				result = Integer.valueOf(input);
			} catch (NumberFormatException e) {
				System.err.println ("MockController::getInt: popped message not an integer!");
			}
		} else {
			System.err.println ("MockController::getInt: queue ran out of messages!");
			try {
				throw new Exception ("FAIL, see above");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.exit(1);
			}
		}
		return result;
	}

	public Card getCard(Player p, String message) {
		
		int i = 0;
		boolean valid = false;
		
		while(!valid) {
			
			i = getInt(message);
			
			if (i >= 0 && i < p.getHandSize()) {
				
				valid = true;
				
			} else {
				
				showMessage("You only have " + p.getHandSize() + " cards");
				
			}
			
		}
		
		return p.getCard(i);
		
	}
	
	/**
	 * Show all cards from the specified list, and prompt them to choose one.
	 * @param cardList List of cards to display
	 * @return
	 */
	public Card getCard(List<Card> cardList, String message) {
		
		int cardIndex = 0;
		Card result = null;
		for (Card c : cardList) {
			
			showMessage(cardIndex + ": " + c.getName());
			cardIndex ++;
		
		}
		
		cardIndex = getInt(message);
	
		if (cardIndex >= 0 && cardIndex < cardList.size()) {
			result = cardList.get(cardIndex);
		} else {
			result = null;
		}
		return result;
		
	}
	
	public int getCard(Card[][] field, String message) {
		
		int i = 0;
		boolean valid = false;
		
		showField();
		
		while(!valid) {
			
			i = getInt(message);
			
			if (i >= 0 && i < Game.FIELD_SIZE) {
				
				valid = true;
				
			} else {
				
				System.out.println("Not a valid card.");
				
			}
			
		}
		
		return i;
		
	}
	
	public boolean getWait (String message) {
		//System.out.println (message);
		getString (message);
		return true;
	}
	
	public boolean getBoolean (String message) {
		String answer = "";
		boolean result = false;
		while (answer.compareTo("Y") != 0 && answer.compareTo("N") != 0) {
			answer = getString (message + " (enter Y/N)");
			answer = answer.toUpperCase();				
		}
		if (answer.compareTo("Y") == 0) {
			result = true;
		}
		return result;
	}
	
	public void showMessage (String s) {
		// Mock: doesn't show anythign (suppress output)
		//System.out.println(s);
		
	}
	
	public void showScreen (Player p) {
		// Mock: doesn't do anything
	}
	
	public void showField () {
		// Mock: doesn't do anything
	}
	
	public void showHand (Player p) {
		// Mock: doesn't do anything
	}
	
	public void showDiceRolls() {
		// Mock: doesn't do anything
	}


	public IPlayerAction getAction(Player p) {
		// Mock: doesn't do anything (i.e. Game.run() should NEVER be called from tester)
		return null;
	}
	
	public void showCard(Card c) {
		// Mock: doesn't do anything
		
	}

}
