package actions;

import roma.Game;
import roma.GameVisor;
import roma.RandomGenerator;

public class EndTurnAction implements IPlayerAction {

	private int [] diceRolls;
	private boolean doEndTurn;
	private int turnNumber;
	
	public static final String DESCRIPTION = "End Turn";
	
	public EndTurnAction() {
		RandomGenerator gen = new RandomGenerator();
		diceRolls = new int[Game.NUM_DIE];
		for (int i = 0; i < Game.NUM_DIE; i ++) {
			
			diceRolls[i] = gen.randomInt(1, 6);
			
		}
		
	}
	
	@Override
	public String describeParameters() {
		String rolls = "";
		for (int i = 0; i < Game.NUM_DIE; i ++) {
			
			rolls += diceRolls[i] + ", ";
		}
		return "doEndTurn: " + doEndTurn + " (" + rolls + ")";
	}
	
	
	public void execute(GameVisor g) {

		if (doEndTurn) {
			g.onEndTurn(diceRolls[0], diceRolls[1], diceRolls[2]);
			
			turnNumber = g.getTurnNumber();
			// Log the action
			g.getActionLogger().addAction(this, g.getTurnNumber());
			
			
		}
		
		
	}
	
	/**
	 * Returns the turn number that this action started (i.e. after it incremented
	 * the turn counter)
	 * @return
	 */
	public int getTurnNumber() {
		return turnNumber;
	}

	public void setDiceRolls(int [] rolls) {
		diceRolls = rolls;
	}
	
	public void setEndTurn(boolean doEndTurn) {
		this.doEndTurn = doEndTurn;
	}
	
	@Override
	public String getDescription() {
		return DESCRIPTION;
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
