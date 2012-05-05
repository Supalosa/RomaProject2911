package actions;

import roma.*;

public interface PlayerAction {
	
	public void execute (GameVisor g);
	
	public String getDescription ();
	
}
