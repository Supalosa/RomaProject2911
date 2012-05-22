package enums;

/**
 * Denotes the type of argument that this Action expects.
 * The controller will handle how it queries this information from the user.
 * @author rivaloflief
 *
 */
public enum TargetType {
	
	CardFieldAny, // Expects any card on the field
	CardFieldEnemy, // Expects enemy card
	CardFieldMine, // Expects my card
	CardInHand,
	CardInList,
	CardInDeck,
	DiceDisc,
	DiceRoll,
	
}
