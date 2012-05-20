package actions;

import roma.Game;
import roma.GameVisor;
import roma.RandomGenerator;

public class EndTurnAction implements IPlayerAction {

	int [] diceRolls;
	boolean doEndTurn;
	
	public EndTurnAction() {
		RandomGenerator gen = new RandomGenerator();
		diceRolls = new int[Game.NUM_DIE];
		for (int i = 0; i < Game.NUM_DIE; i ++) {
			
			diceRolls[i] = gen.randomInt(1, 6);
			
		}
		
	}
	
	@Override
	public String describeParameters() {
		return "doEndTurn: " + doEndTurn;
	}
	
	
	public void execute(GameVisor g) {

		if (doEndTurn) {
			g.onEndTurn(diceRolls[0], diceRolls[1], diceRolls[2]);
			
			// Log the action
			g.getActionLogger().addAction(this);
		}
		
		
	}

	public void setEndTurn(boolean doEndTurn) {
		this.doEndTurn = doEndTurn;
	}
	
	@Override
	public String getDescription() {
		return "End Turn";
	}
	
	

	// action is always visible
	public boolean isVisible(GameVisor g) {
		return true;
	}
	
	@Override
	public void query(GameVisor g) {
		
		doEndTurn = g.getController().getBoolean("Are you sure you want to end turn?");
		
	}


}
