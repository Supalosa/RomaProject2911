package cards.activators;

import enums.CardNames;


/**
 * Required to keep the mappings in order.
 * Otherwise, due to the hashing function, cards will get laid over
 * in the inappropriate order.
 * 
 * Maps a CardNames entry to a Position, used for Architectus and Senator
 * @author Supalosa
 *
 */
public class CardMapping {
	private final CardNames initial_card;
	private final int final_pos;
	
	public CardMapping(CardNames initial_card, int final_pos) {
		
		this.initial_card = initial_card;
		this.final_pos = final_pos;
		
	}
	
	public CardNames getInitialCard() {
		return initial_card;
	}
	
	public int getFinalPos() {
		return final_pos;
	}
	
	
}