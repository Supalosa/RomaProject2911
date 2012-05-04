package actions;

import roma.*;
import cards.*;


public class LayCardAction implements PlayerAction {
	
	Card targetCard;
	int diceDisc;

	public boolean isValid(Game g) {
		
		boolean isValid = true;
		
		int i = diceDisc; // DUMMY VALUES
		
		if (i < 1 || i > Game.FIELD_SIZE) {
			isValid = false;
			g.getController().showMessage("That Dice Disc value is not possible");
		}
		
		return isValid;
		
	}

	@Override
	public void execute(Game g) {
		
		query(g);
		
		if (isValid(g)) {

			g.getCurrentPlayer().getHand().removeElement(targetCard);
			if (g.getField()[g.whoseTurn()][diceDisc - 1] != null) {
				g.discard(g.getField()[g.whoseTurn()][diceDisc - 1]);				
			}
			g.getField()[g.whoseTurn()][diceDisc - 1] = targetCard;

		}
		
	}

	@Override
	public String getDescription() {
		return "Lay Card";
	}
	
	public void query(Game g) {
		
		g.getController().showHand(g.getCurrentPlayer());
		
		targetCard = g.getController().getCard(g.getCurrentPlayer(), "Choose the Card you want to play");
		
		diceDisc = g.getController().getInt("And the dice disc you want to place it next to");
		
	}
	
}
