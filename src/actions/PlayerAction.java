package actions;

import roma.Game;

public interface PlayerAction {
	
	public void execute (Game g);
	
	public String getDescription ();
	
}
