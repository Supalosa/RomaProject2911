package actiontargets;

import java.util.*;
import roma.*;
import cards.*;

/**
 * Returns a position on the field.
 * @author Supalosa
 *
 * @param <T>
 */
public class DiceDiscActionTarget extends ActionTarget<Integer> {

	
	public DiceDiscActionTarget(Integer value, String question) {
		super(value, question);
	}

	public DiceDiscActionTarget(String question) {
		super(question);
	}

	/**
	 * Build the side list and show it to controller.
	 */
	@Override
	public boolean queryController(GameVisor g, IController controller) {
		boolean hasResult = false;
		
		int pos = controller.getInt(getQuestion());
		if (pos > 0 && pos <= Game.FIELD_SIZE) {
			hasResult = true;
			setValue(pos);
		}
		
		return hasResult;
	}

}
