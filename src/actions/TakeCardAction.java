package actions;

import java.util.*;

import roma.*;

import cards.*;


public class TakeCardAction implements IPlayerAction {
	
	private int diceRoll;
	private GameVisor game;
	private int cardIndexTaken; // index of the card that was taken
	
	public TakeCardAction() {
		
		cardIndexTaken = -1;
		
	}
	
	@Override
	public String describeParameters() {
		return "diceRoll: " + diceRoll + ", cardIndexTaken: " + cardIndexTaken;
	}
	
	public boolean isValid() {
		
		boolean isValid = true;
		
		if (diceRoll > 6 || diceRoll < 1) {
			
			isValid = false;
			game.getController().showMessage("That Dice Roll is not possible");
		}
		
		boolean found = false;
		
		for (int i : game.getDiceRolls()) {
			
			if (i == diceRoll) {
				
				found = true;
				
			}
			
		}
		
		if (!found) {
			
			isValid = false;
			game.getController().showMessage("You dont have a Dice Roll of that value");
			
		}
		
		return isValid;
	
	}
	
	public void execute(GameVisor g) throws AssertionError {
		game = g;
		List<Card> temp = new ArrayList<Card>();
		int i = 0;
		
		if (isValid()) {
			
			for (i = 0; i < diceRoll; i++) {
				
				temp.add(g.drawCard());
				
			}
			Card selected;
			// If a card has not been chosen yet, choose it
			if (cardIndexTaken == -1) {
				selected = g.getController().getCard(temp, "Please select one Card that you want (the rest are discarded)");
			} else {
				selected = temp.get(cardIndexTaken);
			}
			
			if (selected != null) {
				g.useDice(diceRoll);
				
				g.getCurrentPlayer().addCard(selected);
				
				temp.remove(selected);
				
				for (Card c : temp) {
					
					g.discard(c);
	
				}
			
			} else {
				g.getController().showMessage("Invalid card, action cancelled.");
				// return the cards to the deck
				for (Card c : temp) {
					g.getDeck().addCard(c);
				}
			}
			
		} else {
			
			g.getController().showMessage("You cannot take Cards");
			
		}
		
		// Log the action
		g.getActionLogger().addAction(this,  g.getTurnNumber());
		
	}


	@Override
	public String getDescription() {
		return "Take Card";
	}

	public void query(GameVisor g) {
		
		g.getController().showDiceRolls();

		diceRoll = g.getController().getInt("Choose the Dice Roll you want to use");

		
	}
	
	public void setDiceRoll(int dice) {
		diceRoll = dice;
	}
	
	public void setCardIndexTaken(int index) {
		
		cardIndexTaken = index;
		
	}

	// only visible if we have dice
	public boolean isVisible(GameVisor g) {
		boolean hasDice = false;
		
		for (int i = 0; i < g.getDiceRolls().length; i++) {
			if (g.getDiceRolls()[i] != 0) {
				hasDice = true;
			}
		}
		return hasDice;
	}

}
