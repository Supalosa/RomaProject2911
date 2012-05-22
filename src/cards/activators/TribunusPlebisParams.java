package cards.activators;

import roma.*;

public class TribunusPlebisParams extends CardParams {
	
	/**
	 * Tribunus Plebis has no parameters
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
