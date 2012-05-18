package actiontargets;

import java.util.*;
import roma.*;
import cards.*;

/**
 * Returns a card from the deck.
 * @author Supalosa
 *
 * @param <T>
 */
public class CardInDeckActionTarget extends ActionTarget<Card> {

	public CardInDeckActionTarget(Card value, String question) {
		super(value, question);
	}

	public CardInDeckActionTarget(String question) {
		super(question);
	}

	/**
	 * Build the deck list and show it to the controller.
	 */
	@Override
	public boolean queryController(GameVisor g, IController controller) {
		boolean hasResult = false;
		List<Card> deckList = new ArrayList<Card>();
		Card result = null;
		for (Card c : g.getDeck().asList()) {
			deckList.add(c);
		}
		
		result = controller.getCard(deckList, getQuestion());
		
		if (result != null) {
			hasResult = true;
			setValue(result);
		}
		
		return hasResult;
	}

}
