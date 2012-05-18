package actions;

import java.util.HashMap;
import java.util.Map;

import actiontargets.*;
import roma.*;

public class EndTurnAction extends PlayerAction {
	Map<Integer, ActionTarget<?>> myTargets;
	
	public static final int CONFIRM_END = 0;
	
	@Override
	public void initialise() {
		myTargets.put(CONFIRM_END, new BooleanActionTarget("Are you sure you want to end turn?"));

	}
	
	@Override
	public String getDescription() {
		return "End Turn";
	}

	
	@Override
	public void execute() {
		if ((Boolean) myTargets.get(CONFIRM_END).getValue() == true) {
			getVisor().onEndTurn();
		}
	}

	// End Turn is always visible
	@Override
	public boolean isVisible() {
		return true;
	}


}
