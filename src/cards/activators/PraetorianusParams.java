package cards.activators;

import java.util.ArrayList;
import java.util.List;

import cards.Card;
import roma.Game;
import roma.GameVisor;

public class PraetorianusParams extends CardParams {

	private int positionToAttack;
	private boolean valid;
	
	public PraetorianusParams() {
		valid = true;
	}
	

	public int getPositionToAttack() {
		return positionToAttack;
	}
	
	/**
	 * Sets the field position to attack (in terms of 0-6)
	 * @param pos
	 */
	public void setPositionToAttack(int pos) {
		this.positionToAttack = pos;
	}
	
	@Override
	public void query(GameVisor g, int pos) {
		
		valid = false;

		g.getController().showField();
		
		int diceDisc = g.getController().getInt("Which dice disc do you wish to block?");
		
		if (diceDisc < 1 || diceDisc > 7) {
			setError("Invalid dice disc. Which dice disc do you wish to block?");
		} else {
			valid = true;
			setPositionToAttack(diceDisc-1);
		}
		
		

		
	}

	@Override
	public boolean isValid() {
		return valid;
	}


}
