package modifiers;

import cards.*;

/**
 * Grim Reaper aura
 * @author Supalosa
 *
 */
public class GrimReaperAura extends IModifier {

	public static final String NAME = "Grim Reaper Aura";
	
	@Override
	public String getName() {
	
		return NAME;
	
	}
	
	@Override
	public String getDescription() {
	
		return "Returns to hand when killed. Applies until Grim Reaper leaves the field.";
	
	}
	
	/**
	 * Does not have to do anything
	 */
	@Override
	public void apply(Card c) {
	
	}

	/**
	 * Does not have to do anything
	 */
	@Override
	public void unapply(Card c) {

	}


}
