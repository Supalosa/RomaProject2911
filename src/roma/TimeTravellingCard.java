package roma;

import cards.*;

public class TimeTravellingCard {

	private final int turnNumber;
	private final int position;
	private final int ownerId;
	private final Card theCard;
	
	public TimeTravellingCard(Card theCard, int turnNumber, int position, int ownerId) {
		
		this.theCard = theCard;
		this.turnNumber = turnNumber;
		this.position = position;
		this.ownerId = ownerId;
		
	}
	
	public int getTurnNumber() {
		return turnNumber;
	}

	public int getPosition() {
		return position;
	}

	public int getOwnerId() {
		return ownerId;
	}

	public Card getTheCard() {
		return theCard;
	}
	
	
}
