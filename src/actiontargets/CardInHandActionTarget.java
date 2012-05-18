package actiontargets;

import java.util.*;
import roma.*;
import cards.*;

/**
 * Returns a card from the current player's hand
 * @author Supalosa
 *
 * @param <T>
 */
public class CardInHandActionTarget extends ActionTarget<Card> {

	public CardInHandActionTarget(Card value, String question) {
		super(value, question);
	}

	public CardInHandActionTarget(String question) {
		super(question);
	}

	/**
	 * Build the hand list and show it to the controller.
	 */
	@Override
	public boolean queryController(GameVisor g, IController controller) {
		boolean hasResult = false;
		List<Card> handList = new ArrayList<Card>();
		Card result = null;
		for (Card c : g.getPlayer(g.whoseTurn()).getHand()) {
			handList.add(c);
		}
		
		result = controller.getCard(handList, getQuestion());
		
		if (result != null) {
			hasResult = true;
			setValue(result);
		}
		
		return hasResult;
	}

}
