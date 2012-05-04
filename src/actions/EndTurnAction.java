package actions;

import roma.Game;

public class EndTurnAction implements PlayerAction {

	public void execute(Game g) {
		boolean doEndTurn = g.getController().getBoolean("Are you sure you want to end turn?");
		if (doEndTurn) {
			g.setTurnEnded(true);
		}
	}

	@Override
	public String getDescription() {
		return "End Turn";
	}


}
