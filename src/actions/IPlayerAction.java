package actions;

import roma.*;

public interface IPlayerAction {
	
	public void execute (GameVisor g);
	
	public String getDescription ();
	
	public boolean isVisible (GameVisor g);
	
}
