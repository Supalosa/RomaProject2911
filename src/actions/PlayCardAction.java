package actions;

import java.util.List;

import roma.*;
import cards.*;


public class PlayCardAction implements IPlayerAction {
	
	int targetCard;
	int diceDisc;
	//GameVisor game;
	
	@Override
	public String describeParameters() {
		return "targetCard: " + targetCard + ", diceDisc: " + diceDisc;
	}
	
	
	public boolean isValid(GameVisor g) {
		
		boolean isValid = true;
		
		int i = diceDisc; // DUMMY VALUES
		
		if (i < 1 || i > Game.FIELD_SIZE) {
			isValid = false;
			g.getController().showMessage("Cannot play that card, that disc value is not possible");
		} else if (g.getField().isBlocked(g.whoseTurn(), diceDisc-1)) {
			g.getController().showMessage("Cannot play that card, that disc is blocked!");
			isValid = false;
		}
		
		if (targetCard == -1) { // no card picked
			g.getController().showMessage("No card selected.");
		} else {
			if (targetCard >= g.getCurrentPlayer().getHandSize()) {
				System.out.println ("PlayCardAction ERROR: " + targetCard + " > " + g.getCurrentPlayer().getHandSize());
				isValid = false;
			} else {
				Card playedCard = g.getCurrentPlayer().getCard(targetCard);
				
				if (playedCard == null) {
					isValid = false;
					g.getController().showMessage("Invalid card index.");
				} else if (g.getCurrentPlayer().getMoney() < playedCard.getCostToPlay()) {
					isValid = false;
					g.getController().showMessage("You don't have enough Money to play " + playedCard.getName());
				}
			}
		}
		
	
		
		return isValid;
		
	}

	public void execute(GameVisor g) {

		//System.out.println(g.getCurrentPlayer().getHand().toString());
		//System.out.println ("EXECUTE PLAYCARD: " + targetCard + " -> " + diceDisc);
		if (isValid(g)) {
			//System.out.println("[valid]");
			Card playedCard = g.getCurrentPlayer().getCard(targetCard);

			g.getCurrentPlayer().getHand().removeElement(playedCard);
			if (g.getField().getCard(g.whoseTurn(), diceDisc - 1) != null) {
				g.discard(g.getField().getCard(g.whoseTurn(),diceDisc - 1));				
			}
			g.getField().setCard(g.whoseTurn(), diceDisc - 1, playedCard);
			g.getCurrentPlayer().setMoney(g.getCurrentPlayer().getMoney() - playedCard.getCostToPlay());
		
		}
		
		// Log the action
		g.getActionLogger().addAction(this);
		
	}

	@Override
	public String getDescription() {
		return "Play Card";
	}
	
	public void setTargetHandCard(int pos) {
		
		targetCard = pos;
		
	}
	
	public void setTargetDisc(int disc) {
		
		diceDisc = disc;
		
	}
	
	public void query(GameVisor g) {
		
		g.getController().showHand(g.getCurrentPlayer());
		Card playedCard = g.getController().getCard(g.getCurrentPlayer(), "Choose the Card you want to play");
		if (playedCard != null) {
			targetCard = g.getCurrentPlayer().getHand().indexOf(playedCard);
			diceDisc = g.getController().getInt("And the dice disc you want to place it next to");
		} else {
			targetCard = -1;
		}
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

