package actions;

import roma.*;
import cards.*;

public class ActivateCardAction implements PlayerAction {
	
	int targetPos;
	Card targetCard;
	GameVisor game;
	
	public boolean isValid(GameVisor g) {
		
		boolean valid = false;
		
		for (int i : game.getDiceRolls()) {
			
			if (i == targetPos) {
				
				valid = true;
				
			}
			
		}
		
		if (targetCard == null) {
			valid = false;
		}

		return valid;
	}

	public void execute(GameVisor g) {
		game = g;
		query();
		
		if(isValid(game)) {
			
			if (targetCard.performEffect(game)) {
				
				game.useDice(targetPos);
				
			}
			
		} else {
			game.getController().showMessage("Invalid dice roll.");
		}
		
	}

	public String getDescription() {
		return "Activate Card";
	}

	public void query() {
		
		game.getController().showField();
		
		targetPos = game.getController().getInt("Choose the Dice Disc you want to activate");
		while (targetPos < 1 || targetPos > 6) {
			targetPos = game.getController().getInt("Invalid dice disc. Choose the Dice Disc you want to activate");
		}
		targetCard = game.getField()[game.whoseTurn()][targetPos-1];
		
	}

}
