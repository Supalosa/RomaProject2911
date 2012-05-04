package actions;

import roma.*;
import cards.*;

public class ActivateCardAction implements PlayerAction {
	
	int targetPos;
	Card targetCard;
	
	public boolean isValid(Game g) {
		
		boolean valid = false;
		
		for (int i : g.getDiceRolls()) {
			
			if (i == targetPos) {
				
				valid = true;
				
			}
			
		}
		
		return valid;
	}

	public void execute(Game g) {
		
		query(g);
		
		if(isValid(g)) {
			
			if (targetCard.performEffect(g)) {
				
				g.useDice(targetPos);
				
			}
			
		}
		
	}

	public String getDescription() {
		return "Activate Card";
	}

	public void query(Game g) {
		
		g.getController().showField(g);
		
		targetPos = g.getController().getInt("Choose the Card you want to activate");
		
		targetCard = g.getField()[g.whoseTurn()][targetPos];
		
	}

}
