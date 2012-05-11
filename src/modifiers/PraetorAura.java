package modifiers;

import roma.*;

public class PraetorAura implements IModifier {

	Field targetField;
	IModifiable caster;
	public static final String NAME = "Praetor Block";
	String block;
	
	public PraetorAura(String s) {
		
		block = s;
		
	}
	
	@Override
	public String getName() {
		return NAME;
	}

	@Override
	public String getDescription() {
		return "Blocks an opponent's dice disc for one go";
	}

	@Override
	public void apply() {
		targetField.setBlock(block);
	}

	@Override
	public void unapply() {
		targetField.removeBlock(block);
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
		return targetField;
	}

}
