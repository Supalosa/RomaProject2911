package roma;
import java.io.*;
import java.util.*;

import modifiers.IModifier;

import cards.Card;

import actions.IPlayerAction;

public class ControllerConsole implements IController {
	
	Game g;
	
	public void setGame (Game g) {
		this.g = g;
	}

	public void runGame() {
		g.run();
	}
	
	public String getString(String message) {
		
		InputStreamReader converter = new InputStreamReader(System.in);
		BufferedReader in = new BufferedReader(converter);
		String s = "";
		boolean valid = false;
		
		while (valid == false) {
			
			try {
				
				System.out.println(message);
				s = in.readLine();
				
				valid = true;
				
			} catch (IOException e) {
				
				System.out.println("blah");
			
			}
		
		}
		
		return s;
		
	}
	
	public int getInt(String message) {
	
		InputStreamReader converter = new InputStreamReader(System.in);
		BufferedReader in = new BufferedReader(converter);
		String s = "";
		
		boolean valid = false;
		int i = 0;
		
		while (valid == false) {
			
			try {
				if (message.length() > 0) {
					System.out.println(message);
				}
				s = in.readLine();
				i = Integer.parseInt(s);
				
				valid = true;
			
			} catch (NumberFormatException e) {
				
				showMessage("Please input a number");
				
			} catch (IOException e) {
				
				e.printStackTrace();
				
			}
			
		}
		
		return i;
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
		
		showMessage(message);
		
		for (Card c : cardList) {
			
			showMessage(cardIndex + ": " + c.getName());
			cardIndex ++;
		
		}
		
		cardIndex = getInt("[Enter 0-" + cardList.size() + "]: ");
	
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
		
		System.out.println(s);
		
	}
	
	public void showScreen (Player p) {

		showMessage("It is player " + (g.getCurrentPlayer().getPlayerId()+1) + "'s turn.");
		showMessage("------------------------ FIELD --------------------");
		showField();
		showMessage("---------------------- YOUR HAND ------------------");
		showHand(p);
		showMessage("---------------------------------------------------");
		showDiceRolls();
		showMessage("---------------------------------------------------");

		int VP = p.getVP();
		int money = p.getMoney();
		
		System.out.println("You have " +VP+ " Victory Points and " +money+ " Sesterii");
		showMessage("---------------------------");
		System.out.println ("Discard pile: " + g.getDiscardPile().asList().size());
		System.out.println ("Deck: " + g.getDeck().asList().size());
		showMessage("---------------------------");

		
		
	}
	
	public void showField () {
		
		String s = String.format("%-25s | %25s", "Player 1", "Player 2");
		showMessage(s);
		
		Field field = g.getField();
		
		for (int i = 0; i < Game.FIELD_SIZE; i++) {
			String cardLeft = "";
			String cardRight = "";
			if (field.getCard(0,i) != null) {
				cardLeft = field.getCard(0,i).getName();
				List<IModifier> mods = g.getModifiersOn(0, i);
				if (mods.size() > 0) {
					cardLeft += "*";
				}
			}
			if (field.getCard(1,i) != null) {
				cardRight = field.getCard(1,i).getName();
				List<IModifier> mods = g.getModifiersOn(1, i);
				if (mods.size() > 0) {
					cardLeft += "*";
				}
			}
			s = String.format("%-25s %d %25s", cardLeft, i+1, cardRight);

			showMessage(s);
			
			
		}
		
		
	}
	
	public void showHand (Player p) {
		
		CardVector cv = p.getHand();
		
		for (int i = 0; i < p.getHandSize(); i++) {
			System.out.format("%d: %s\n", i, cv.elementAt(i).getName());
		}
		
		System.out.println();
		
	}
	
	public void showDiceRolls() {
		
		String s = "Available dice rolls: ";
		
		int[] rolls = g.getDiceRolls();
		
		for (int i = 0; i < 3; i++) {
			
			if (rolls[i] != 0) {
				
				s = s + rolls[i] + " ";
			
			}
			
		}
		
		System.out.println(s);
		
	}


	public IPlayerAction getAction(Player p) {
		IPlayerAction action = null;
		Vector<IPlayerAction> actions = new Vector<IPlayerAction>();
		int selectedAction = -1;
		for (IPlayerAction act : g.generateActions(p)) {
			actions.add(act);
		}
		
		System.out.println("List of your possible actions:");
		for (int i = 0; i < actions.size(); i++) {
			System.out.println(i + ": " + actions.get(i).getDescription());
		}

		
		selectedAction = getInt ("Enter next action code:");
		while (selectedAction < 0 || selectedAction >= actions.size()) {
			selectedAction = getInt ("Invalid action code, please try again:");
		}
		
		action = actions.get(selectedAction);
		return action;
	}
	
	public void showCard(Card c) {
		System.out.println ("-------------------");
		System.out.println (c.getName().toUpperCase());
		System.out.println ("-------------------");
		System.out.println (c.getDescription());
		System.out.println ("-------------------");
		System.out.format ("- COST: %2d ---- DEF: %2d [%+2d] -\n", c.getCostToPlay(), c.getDefense(), (c.getRealDefense() - c.getDefense()));
		System.out.println ("-------------------");
		// show active effects
		int ownerId = g.getField().findCardOwner(c);
		int pos = g.getField().findCardPosition(c);
		
		List<IModifier> mods = g.getModifiersOn(ownerId, pos);
		
		if (mods.size() > 0) {
			System.out.println ("- Active Effects: -");
			System.out.println ("-------------------");
			
			for (IModifier mod : mods) {
				System.out.println (mod.getName() + ": " + mod.getDescription());
			}
			System.out.println ("-------------------");
		}
		
		System.out.println();
		System.out.println();
		
		getWait ("-- press any key to continue --");
		
	}


}
