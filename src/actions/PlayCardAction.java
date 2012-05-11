package actions;

import java.util.List;

import roma.*;
import cards.*;


public class PlayCardAction implements IPlayerAction {
	
	Card targetCard;
	int diceDisc;
	GameVisor game;
	public boolean isValid(GameVisor g) {
		
		boolean isValid = true;
		
		int i = diceDisc; // DUMMY VALUES
		
		if (i < 1 || i > Game.FIELD_SIZE) {
			isValid = false;
			game.getController().showMessage("That Dice Disc value is not possible");
		}
		
		if (game.getCurrentPlayer().getMoney() < targetCard.getCostToPlay()) {
			isValid = false;
			game.getController().showMessage("You don't have enough Money to play " + targetCard.getName());
		}
		
		List<String> blocks = g.getField().getBlocks();
		
		for (String s : blocks) {
			
			if (s.charAt(0) == g.whoseTurn() && s.charAt(1) == diceDisc) {
				
				isValid = false;
				g.getController().showMessage("That dice disc is blocked for this turn");
			
			}
			
		}
		
		return isValid;
		
	}

	public void execute(GameVisor g) {
		game = g;
		query();
		
		if (isValid(g)) {

			game.getCurrentPlayer().getHand().removeElement(targetCard);
			if (game.getField().getCard(game.whoseTurn(), diceDisc - 1) != null) {
				game.discard(game.getField().getCard(game.whoseTurn(),diceDisc - 1));				
			}
			game.getField().setCard(game.whoseTurn(), diceDisc - 1, targetCard);
			game.getCurrentPlayer().setMoney(game.getCurrentPlayer().getMoney() - targetCard.getCostToPlay());
		
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

	// only visible if we have cards in hand and cards to play
	public boolean isVisible(GameVisor g) {
		boolean canPlay = false;
		
		for (Card cardInHand : g.getPlayer(g.whoseTurn()).getHand()) {
			if (g.getPlayer(g.whoseTurn()).getMoney() > cardInHand.getCostToPlay()) {
				canPlay = true;
			}
		}
		
		return canPlay;
	}
	
}

