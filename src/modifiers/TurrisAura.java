package modifiers;

import cards.*;

public class TurrisAura extends IModifier {

	public static final String NAME = "Turris Aura";
	
	boolean applied = false;
	@Override
	public String getName() {
		return NAME;
	}
	
	@Override
	public String getDescription() {
		return "+1 defense. Applies until Turris leaves the field.";
	}
	
	/**
	 * Applies +1 defense to the card
	 */
	@Override
	public void apply(Card c) {
		/*if (applied == false) {
			applied = true;
		*/
			System.out.println("TURRIS APPLY " + c.getName() + " [" + c.getRealDefense() + "]");
			c.setRealDefense(c.getRealDefense() + 1);
		//}
	}

	/**
	 * Applies -1 defense to the card
	 */
	@Override
	public void unapply(Card c) {
		/*if (applied == true) {
			applied = false;
		*/
			System.out.println("TURRIS UNAPPLY " + c.getName() + " [" + c.getRealDefense() + "]");
			c.setRealDefense(c.getRealDefense() - 1);
		//}
	}

}
