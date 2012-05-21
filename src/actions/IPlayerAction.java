package actions;

import roma.*;

public interface IPlayerAction {
	
	void execute (GameVisor g) throws AssertionError;
	
	String getDescription ();
	
	boolean isVisible (GameVisor g);
	
	void query(GameVisor g);
	
	/**
	 * DEBUG, used for debugging replay
	 * @return
	 */
	String describeParameters(); 
	
}
