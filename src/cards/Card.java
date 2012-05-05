package cards;

import roma.GameVisor;
import enums.CardNames;
import enums.EffectTrigger;


public abstract class Card {
	
	private int ownerId; // possibly redundant, id of player 0..1 of owner
	private boolean isInPlay; // possibly redundant
	private boolean isFaceUp;
/*	private CardNames id;
	private int costToPlay;
	private int diceToActivate;
	private boolean isBuilding;
	private String name;
	private String description;
	private int defense;
	private EffectTrigger effectTrigger;*/
	
	
	public Card() {
		ownerId = -1;
		isInPlay = false;
		isFaceUp = false;
		
	}
	
	public int getOwnerID () {
		return ownerId;
	}
	
	public void setOwnerId (int playerId) {
		ownerId = playerId;
	}
	
	public boolean isInPlay () {
		return isInPlay;
	}
	
	public boolean isFaceUp () {
		return isFaceUp;
	}
	
	public abstract CardNames getID();
	public abstract int getCostToPlay();
	public abstract int getDiceToActivate();
	public abstract boolean isBuilding();
	public abstract String getName();
	public abstract String getDescription();
	public abstract int getDefense();
	public abstract EffectTrigger getEffectTrigger();
	
	public abstract boolean performEffect (GameVisor g, int pos);
	
	
}


