package modifiers;

public interface IModifier {
	
	String getName();
	String getDescription();
	
	void apply (); // Apply the effect
	void unapply (); // Unapply the effect
	
	void setCaster (IModifiable caster); // what applied this effect?
	void setTarget (IModifiable target); // what does this affect?
	
	IModifiable getCaster(); 
	IModifiable getTarget();
	
}
