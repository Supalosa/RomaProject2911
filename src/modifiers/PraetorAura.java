package modifiers;

import roma.*;

public class PraetorAura implements IModifier {

	Field targetField;
	IModifiable caster;
	public static final String NAME = "Praetor Block";
	int blockedPosition;
	int blockedPlayer;
	
	public PraetorAura(int blockedPlayer, int blockedPosition) {
		
		this.blockedPosition = blockedPosition;
		this.blockedPlayer = blockedPlayer;
		
	}
	
	@Override
	public String getName() {
		return NAME;
	}

	@Override
	public String getDescription() {
		return "Dice disc " + (blockedPosition+1) + " is blocked for Player " + (blockedPlayer+1) + "!"; 
	}

	@Override
	public void apply() {
		targetField.setBlock(blockedPlayer, blockedPosition);
	}

	@Override
	public void unapply() {
		targetField.removeBlock(blockedPlayer, blockedPosition);
	}

	@Override
	public void setCaster(IModifiable caster) {
		this.caster = caster;
	}

	@Override
	public void setTarget(IModifiable target) {
		
		if (target.getModifiableType() == ModifierTarget.Field) {
			targetField = (Field)target;
		} else {
			System.err.println ("Error: PraetorAura applied to a " + target.getModifiableType().toString() + "!");
			System.exit(0);
		}
		
	}

	@Override
	public IModifiable getCaster() {
		return caster;
	}

	@Override
	public IModifiable getTarget() {
		return targetField;
	}

}
