package cards.activators;

import java.util.ArrayList;
import java.util.List;

import cards.Card;
import roma.GameVisor;

public class TelephoneBoxParams extends CardParams {


	private int diceToSend; // which dice disc to send back in time
	private boolean goForward;
	private int diceToUse; // which dice to use the value of (go forward or backwards)
	
	private boolean valid;
	
	public TelephoneBoxParams() {
		
		valid = true;
	
	}
	

	public int getDiceToSend() {
	
		return diceToSend;
	
	}

	public void setDiceToSend(int diceToSend) {
	
		this.diceToSend = diceToSend;
	
	}

	public boolean isGoForward() {
	
		return goForward;
	
	}

	public void setGoForward(boolean goForward) {
	
		this.goForward = goForward;
	
	}

	public int getDiceToUse() {
	
		return diceToUse;
	
	}

	public void setDiceToUse(int diceToUse) {
	
		this.diceToUse = diceToUse;
	
	}
	

	@Override
	public void query(GameVisor g, int pos) {
		
		valid = false;
		
		boolean sendForward;
		
		List<Card> myField = g.getField().getSideAsList(g.whoseTurn());
		
		List<Card> characters = new ArrayList<Card>();
		
		for (Card c : myField) {
			
			characters.add(c);
		
		}
		
		Card target = null;
		
		target = g.getController().getCard(characters, "Which card do you wish to travel in time?"); 
		
		if (target != null) {
			
			setDiceToSend(g.getField().findCardPosition(target));
			valid = true;

			sendForward = g.getController().getBoolean("Do you wish to go forward (Y) or back (N) in time?");
			setGoForward(sendForward);

			g.getController().showDiceRolls();
			int add = g.getController().getInt("Which die do you want to use?");

			if (g.hasDiceRoll(add)) {
			
				setDiceToUse(add);
			
			}
			
		}

				
	}

	@Override
	public boolean isValid() {
		
		return valid;
	
	}

}
