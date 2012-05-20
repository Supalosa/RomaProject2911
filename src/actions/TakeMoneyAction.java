package actions;

import roma.*;


public class TakeMoneyAction implements IPlayerAction {
	
	int diceRoll;
	GameVisor game;
	
	@Override
	public String describeParameters() {
		return "diceRoll: " + diceRoll;
	}
	
	
	public boolean isValid() {
		
		boolean isValid = true;
		
		if (diceRoll > 6 || diceRoll < 1) {
			
			isValid = false;
			game.getController().showMessage("That Dice Roll is not possible");
		
		}
		
		boolean found = false;
		
		for (int i : game.getDiceRolls()) {
			
			if (i == diceRoll) {
				
				found = true;
				
			}
			
		}
		
		if (!found) {
			
			isValid = false;
			game.getController().showMessage("You dont have a Dice Roll of that value");
			
		}
		
		return isValid;
	
	}

	
	public void execute(GameVisor g) {
		
		game = g;
		
		if (isValid()) {
			
			g.useDice(diceRoll);
			
			g.getCurrentPlayer().setMoney(g.getCurrentPlayer().getMoney() + diceRoll);
			
			
		} else {
			
			g.getController().showMessage("You cannot take Money");
			
		}
		
		// Log the action
		g.getActionLogger().addAction(this,  g.getTurnNumber());
		
	}
	
	public String getDescription() {
		return "Take Money";
	}
	
	public void setDiceToUse(int diceRoll) {
		
	
		this.diceRoll = diceRoll;
		
	}
	
	public void query(GameVisor g) {
		
		g.getController().showDiceRolls();
		
		diceRoll = g.getController().getInt("Choose the Dice Roll you want to use");
		
	}


	// only visible if we have dice
	public boolean isVisible(GameVisor g) {
		boolean hasDice = false;
		
		for (int i = 0; i < g.getDiceRolls().length; i++) {
			if (g.getDiceRolls()[i] != 0) {
				hasDice = true;
			}
		}
		return hasDice;
	}

}
