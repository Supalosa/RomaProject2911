package actiontargets;

import roma.*;


/**
 * Represents an abstract parameter to a card's effect.
 * The ActionTarget is sent to the controller, which asks the appropriate
 * question in the appropriate manner (depending on Controller implementation)
 * @author Supalosa
 *
 */
public abstract class ActionTarget<T> {
	
	private T value;
	private String question;
	
	public ActionTarget(T value, String question) {
		this.value = value;
		this.question = question;
	}
	
	public ActionTarget(String question) {
		this.value = null;
		this.question = question;
	}
	
	/**
	 * Returns the value of this ActionTarget, provided it has been queried to the controller.
	 * @return Value of response
	 */
	public T getValue() {
		return value;
	}
	
	public void setValue(T value) {
		this.value = value;
	}
	
	public String getQuestion() {
		return question;
	}
	
	/**
	 * Returns whether the answer for this ICardTarget has been defined.
	 * @return 
	 */
	public boolean isDefined() {
		return (this.value != null);
	}
	
	/**
	 * Queries the provided controller for the value for this ActionTarget
	 * Implementing methods should be able to be "cancelled" by the user, and return
	 * false.
	 * @param visor The visor to the game
	 * @param controller The controller to send/receive input from
	 * @return Whether a value was successfully retrieved
	 */
	public abstract boolean queryController(GameVisor visor, IController controller);
	
}
