package actions;

import roma.GameVisor;

public class EndTurnAction implements IPlayerAction {

	public void execute(GameVisor g) {
		boolean doEndTurn = g.getController().getBoolean("Are you sure you want to end turn?");
		if (doEndTurn) {
			g.onEndTurn();
		}
	}

	@Override
	public String getDescription() {
		return "End Turn";
	}

	// action is always visible
	public boolean isVisible(GameVisor g) {
		return true;
	}


}
