package actions;

import java.util.*;

import roma.*;

import cards.*;
import enums.CardNames;


public class TakeCardAction implements IPlayerAction {
	
	private int diceRoll;
	private GameVisor game;
	private CardNames cardIndexTaken; // ID of the card that was taken
	
	public TakeCardAction() {
		
		cardIndexTaken = null;
		
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
			Card selected = null;
			// If a card has not been chosen yet, choose it
			if (cardIndexTaken == null) {
				selected = g.getController().getCard(temp, "Please select one Card that you want (the rest are discarded)");
			} else {
				
				// Find requested card
				boolean found = false;
				int position = -1;
				int index = 0;
				//System.out.println ("---- Looking for: " + cardIndexTaken + " -----");
				for (Card c : temp) {
					//System.out.println (c);
					if (c.getID() == cardIndexTaken) {
						position = index; 
						found = true;
						break;
					}
					
					index++;
					
				}
				
				//System.out.println (" ------- Finished --------");
				if (found) {
					selected = temp.get(position);
				} else {
					
					// taking cards.. error
					assert (false);
					
				}
			}
			
			if (selected != null) {
				g.getController().showMessage("You have added a " + selected + " to your hand!");
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
	
	public void setCardIndexTaken(CardNames index) {
		
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
