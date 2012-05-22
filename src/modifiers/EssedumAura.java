package modifiers;

import cards.*;

public class EssedumAura extends IModifier {
	
	public static final String NAME = "Essedum Trample";
	
	@Override
	public String getName() {
	
		return NAME;
	
	}
	
	@Override
	public String getDescription() {
	
		return "-2 defense. Applies until end of turn, or Essedum removed from field.";
	
	}
	
	/**
	 * Applies -2 defense to the card
	 */
	@Override
	public void apply(Card c) {
	
		c.setRealDefense(c.getRealDefense() - 2);
	
	}

	/**
	 * Applies +2 defense to the card
	 */
	@Override
	public void unapply(Card c) {
	
		c.setRealDefense(c.getRealDefense() + 2);
	
	}

}
