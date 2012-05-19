package modifiers;

import cards.*;

/**
 * Grim Reaper aura
 * @author Supalosa
 *
 */
public class GrimReaperAura implements IModifier {

	Card targetCard;
	IModifiable caster;
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
	public void apply() {
		//targetCard.setRealDefense(targetCard.getRealDefense() + 1);
	}

	/**
	 * Does not have to do anything
	 */
	@Override
	public void unapply() {
	//	targetCard.setRealDefense(targetCard.getRealDefense() - 1);
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
			System.err.println ("Error: GrimReaperAura applied to a " + target.getModifiableType().toString() + "!");
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
