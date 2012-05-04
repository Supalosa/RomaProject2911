package actions;

import java.util.*;

import roma.*;

import cards.*;


public class TakeCardAction implements PlayerAction {
	
	int diceRoll;
	
	public boolean isValid(Game g) {
		
		boolean isValid = true;
		
		if (diceRoll > 6 || diceRoll < 1) {
			
			isValid = false;
			g.getController().showMessage("That Dice Roll is not possible");
		}
		
		boolean found = false;
		
		for (int i : g.getDiceRolls()) {
			
			if (i == diceRoll) {
				
				found = true;
				
			}
			
		}
		
		if (!found) {
			
			isValid = false;
			g.getController().showMessage("You dont have a Dice Roll of that value");
			
		}
		
		return isValid;
	
	}
	
	public void execute(Game g) {
		
		List<Card> temp = new ArrayList<Card>();
		
		query(g);
		
		if (isValid(g)) {
			
			g.useDice(diceRoll);
			
			for (int i = 0; i < diceRoll; i++) {
				
				temp.add(g.drawCard());
				
			}
			
			Card selected = g.getController().getCard(temp, "Please select one Card that you want (the rest are discarded)");
			
			g.getCurrentPlayer().addCard(selected);
			temp.remove(selected);
			
			for (Card c : temp) {
				
				g.discard(c);
				
			}
			
		} else {
			
			g.getController().showMessage("You cannot take Cards");
			
		}
		
	}


	@Override
	public String getDescription() {
		return "Take Card";
	}

	public void query(Game g) {
		
		g.getController().showDiceRolls();
		
		diceRoll = g.getController().getInt("Choose the Dice Roll you want to use");
		
	}

}
