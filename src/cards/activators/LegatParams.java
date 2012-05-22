package cards.activators;

import roma.*;

public class LegatParams extends CardParams {

	/**
	 * Legat has no parameters, it simply penalises the enemy for empty dice
	 * discs.
	 */
	@Override
	public void query(GameVisor g, int pos) {

		// no params
	}

	@Override
	public boolean isValid() {
		return true;
	}

}
