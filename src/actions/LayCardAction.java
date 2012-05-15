package actions;

import java.util.List;

import roma.*;
import cards.*;


public class LayCardAction implements IPlayerAction {
	
	Card targetCard;
	int diceDisc;
	GameVisor game;
	 
	public boolean isValid(GameVisor g) {
		
		boolean isValid = true;
		
		int i = diceDisc; // DUMMY VALUES
		
		if (i < 1 || i > Game.FIELD_SIZE) {
			isValid = false;
			game.getController().showMessage("Cannot lay that card, that disc value is not possible");
		} else if (g.getField().isBlocked(g.whoseTurn(), diceDisc-1)) {
			game.getController().showMessage("Cannot lay that card, that disc is blocked!");
			isValid = false;
		}
		
		
		return isValid;
		
	}

	@Override
	public void execute(GameVisor g) {
		
		game = g;
		query();
		
		if (isValid(g)) {

			g.getCurrentPlayer().getHand().removeElement(targetCard);
			if (g.getField().getCard(g.whoseTurn(), diceDisc - 1) != null) {
				g.discard(g.getField().getCard(g.whoseTurn(), diceDisc - 1));				
			}
			g.getField().setCard(g.whoseTurn(), diceDisc - 1, targetCard);

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

	@Override
	public boolean isVisible(GameVisor g) {
		return false;
	}
	
}
