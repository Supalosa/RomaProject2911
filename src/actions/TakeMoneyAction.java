package actions;

import roma.*;


public class TakeMoneyAction implements PlayerAction {
	
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
		
		query(g);
		
		if (isValid(g)) {
			
			g.useDice(diceRoll);
			
			g.getCurrentPlayer().setMoney(g.getCurrentPlayer().getMoney() + diceRoll);
			
			
		} else {
			
			g.getController().showMessage("You cannot take Money");
			
		}
		
	}
	
	public String getDescription() {
		return "Take Money";
	}
	
	public void query(Game g) {
		
		g.getController().showDiceRolls();
		
		diceRoll = g.getController().getInt("Choose the Dice Roll you want to use");
		
	}

}
