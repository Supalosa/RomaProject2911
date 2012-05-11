package modifiers;

import cards.*;

public class EssedumAura implements IModifier {

	Card targetCard;
	IModifiable caster;
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
	public void apply() {
		targetCard.setRealDefense(targetCard.getRealDefense() - 2);
	}

	/**
	 * Applies +2 defense to the card
	 */
	@Override
	public void unapply() {
		targetCard.setRealDefense(targetCard.getRealDefense() + 2);
	}


	@Override
	public void setCaster(IModifiable caster) {
		this.caster = caster;
	}

	/**
	 * Set the target of this aura, ensuring it is the correct type (Card)
	 */
	@Override
	public void setTarget(IModifiable target) {
		if (target.getModifiableType() == ModifierTarget.Card) {
			targetCard = (Card)target;
		} else {
			System.err.println ("Error: EssedumAura applied to a " + target.getModifiableType().toString() + "!");
			System.exit(0);
		}

	}

	@Override
	public IModifiable getCaster() {
		return caster;
	}

	@Override
	public IModifiable getTarget() {
		return (IModifiable)targetCard;
	}



}