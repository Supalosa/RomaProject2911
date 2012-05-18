package actiontargets;

import java.util.*;
import roma.*;
import cards.*;

/**
 * Returns a boolean yes/no.
 * @author Supalosa
 *
 * @param <T>
 */
public class BooleanActionTarget extends ActionTarget<Boolean> {

	public BooleanActionTarget(Boolean value, String question) {
		super(value, question);
	}

	public BooleanActionTarget(String question) {
		super(question);
	}

	/**
	 * Ask the question, very simple.
	 */
	@Override
	public boolean queryController(GameVisor g, IController controller) {
		
		setValue(controller.getBoolean(getQuestion()));
		
		// Always has a result.
		return true;
	}

}
