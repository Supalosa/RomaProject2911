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
public class CardOnFieldActionTarget extends ActionTarget<Card> {

	/**
	 * Specifies whether the cards should be on my side, or the opponent's side.
	 */
	private final boolean mySide;
	
	public CardOnFieldActionTarget(Card value, String question, boolean mySide) {
		super(value, question);
		this.mySide = mySide;
	}

	public CardOnFieldActionTarget(String question, boolean mySide) {
		super(question);
		this.mySide = mySide;
	}

	/**
	 * Build the side list and show it to controller.
	 */
	@Override
	public boolean queryController(GameVisor g, IController controller) {
		boolean hasResult = false;
		List<Card> sideList = new ArrayList<Card>();
		Card result = null;
		int playerId;
		
		if (mySide) {
			playerId = g.whoseTurn();
		} else {
			playerId = (g.whoseTurn() + 1) % Game.MAX_PLAYERS;
		}
		
		for (Card c : g.getField().getSideAsList(playerId)) {
			sideList.add(c);
		}
		
		result = controller.getCard(sideList, getQuestion());
		
		if (result != null) {
			hasResult = true;
			setValue(result);
		}
		
		return hasResult;
	}

}
