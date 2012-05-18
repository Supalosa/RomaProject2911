package actions;

import java.util.*;

import actiontargets.*;
import roma.*;

public abstract class PlayerAction implements IActionTargetHolder {
	
	private GameVisor g;
	private Map<Integer, ActionTarget<?>> myTargets;
	
	public PlayerAction() {
		myTargets = new HashMap<Integer, ActionTarget<?>>();
	}
	
	
	public abstract void initialise();
	
	/**
	 * Sets up the appropriate pointer to the visor
	 */
	public void setVisor (GameVisor g) {
		this.g = g;
	}
	
	public GameVisor getVisor() {
		return g;
	}
	
	public abstract void execute ();
	
	public abstract String getDescription ();
	
	public abstract boolean isVisible ();
	
	public Map<Integer, ActionTarget<?>> getParameters() {
		return myTargets;
	}
	
	public ActionTarget<?> getParameter(int index) {
		return myTargets.get(index);
	}
	
	protected void addParameter(int index, ActionTarget<?> action) {
		myTargets.put(index, action);
	}
	
	/**
	 * Fires after a parameter has been set, gives the opportunity
	 * to add more parameters if necessary.
	 * @param paramIndex
	 * @param param
	 */
	public void onParameterSet (int paramIndex, ActionTarget<?> param) {
		
	}
	
	
}
