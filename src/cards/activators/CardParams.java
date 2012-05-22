package cards.activators;

import roma.*;

/**
 * Represents a (hopefully) mutable instance of parameters that is attached to
 * an ActivateCardAction action.
 * 
 * Since it is attached to the action, the action can be replayed at-will.
 * 
 * There is a Params child for each card that can be activated. It is not
 * necessary to implement a Params set for unactivatable cards, like Turris. In
 * this case, you will simply return null on the getParams() method of the card.
 * 
 * @author Randal
 * @author Peter
 * 
 */
public abstract class CardParams {

	private String error;

	public abstract void query(GameVisor g, int pos);

	public abstract boolean isValid();

	public String getError() {
		
		return error;
	
	}

	public void setError(String s) {
	
		error = s;
	
	}

}
