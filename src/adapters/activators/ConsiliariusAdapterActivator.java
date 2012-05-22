package adapters.activators;

import adapters.*;
import roma.*;
import cards.*;
import cards.activators.*;
import framework.interfaces.activators.*;

/**
 * Adapter to activate the Consiliarius.
 * 
 * @author Supalosa
 * 
 */
public class ConsiliariusAdapterActivator extends GenericAdapterActivator
		implements ConsiliariusActivator {

	ConsiliariusParams params;

	Card[] fieldList;

	public ConsiliariusAdapterActivator(int fieldPosition, Game game,
			Card theCard) {

		super(fieldPosition, game, theCard);
		;

		this.params = (ConsiliariusParams) theCard.getParams();

		// Need a copy of fieldList
		// so we can destroy the values (i.e. avoid doubling
		// up of cards with same name)
		fieldList = game.getField().getSide(game.whoseTurn());

	}

	@Override
	public void complete() {

		execute(params);

	}

	/**
	 * Sets the first instance of 'card' in the field to be placed on position
	 * diceDisc.
	 * 
	 * The actual movement of cards occurs only in PerformEffect.
	 * 
	 * There is no 'floating card' state, so if a test is added to test this,
	 * this card will need to be changed.
	 * 
	 */
	@Override
	public void placeCard(framework.cards.Card card, int diceDisc) {

		boolean foundCard = false;

		for (int pos = 0; pos < Game.FIELD_SIZE && foundCard == false; pos++) {
			// Get the card in that position
			Card romaCard = fieldList[pos];

			if (romaCard != null) {
				// Get its acceptance name name
				framework.cards.Card acceptanceName = CardNameAdapter
						.getAcceptanceCard(romaCard.getName());

				// Found the match!
				if (acceptanceName == card) {

					params.addPosition(pos, diceDisc - 1);
					// Remove the entry in the field copy (so it won't get
					// picked up again)
					fieldList[pos] = null;
					foundCard = true;

				}

			}

		}

	}

}
