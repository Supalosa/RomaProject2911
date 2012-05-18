package actiontargets;

import java.util.*;
import roma.*;
import cards.*;

/**
 * Returns an available dice roll.
 * @author Supalosa
 *
 * @param <T>
 */
public class DiceRollActionTarget extends ActionTarget<Integer> {

	
	public DiceRollActionTarget(Integer value, String question) {
		super(value, question);
	}

	public DiceRollActionTarget(String question) {
		super(question);
	}

	/**
	 * Build the dice list
	 */
	@Override
	public boolean queryController(GameVisor g, IController controller) {
		boolean hasResult = false;
		
		int roll = controller.getInt(getQuestion());
		if (roll > 0 && roll <= 6 && g.hasDiceRoll(roll)) {
			hasResult = true;
			setValue(roll);
		}
		
		return hasResult;
	}

}
