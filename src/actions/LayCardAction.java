package actions;

import roma.*;
import cards.*;


public class LayCardAction implements PlayerAction {
	
	Card targetCard;
	int diceDisc;
	GameVisor game;
	 
	public boolean isValid() {
		
		boolean isValid = true;
		
		int i = diceDisc; // DUMMY VALUES
		
		if (i < 1 || i > Game.FIELD_SIZE) {
			isValid = false;
			game.getController().showMessage("That Dice Disc value is not possible");
		}
		
		return isValid;
		
	}

	@Override
	public void execute(GameVisor g) {
		game = g;
		query();
		
		if (isValid()) {

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
	
	public void query() {
		
		game.getController().showHand(game.getCurrentPlayer());
		
		targetCard = game.getController().getCard(game.getCurrentPlayer(), "Choose the Card you want to play");
		
		diceDisc = game.getController().getInt("And the dice disc you want to place it next to");
		
	}
	
}
