package cards.activators;

import roma.*;

public class KatParams extends CardParams {
	
	/**
	 * Kat has no parameters, it simply activates itself
	 * and says Miaow at the start of each turn.
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
