package modifiers;

import java.util.*;

/** signifies that the extended class can be modified by an IModifier
 * @author Supalosa
 *
 */
public interface IModifiable {

	void addModifier (IModifier mod);
	void removeModifier (IModifier mod);
	List<IModifier> getModifiers();
	ModifierTarget getModifiableType();
	
}
