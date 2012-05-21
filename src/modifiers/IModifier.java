package modifiers;

import cards.*;

public abstract class IModifier {
	
	private int casterOwnerId;
	private int casterPos;
	
	private int targetOwnerId;
	private int targetPos;
	
	public abstract String getName();
	public abstract String getDescription();
	
	public abstract void apply (Card c); // Apply the effect
	public abstract void unapply (Card c); // Unapply the effect
	
	public void setCaster(int ownerId, int pos) {
		this.casterOwnerId = ownerId;
		this.casterPos = pos;
	}

	/**
	 * Set the target of this aura, ensuring it is the correct type (Card)
	 */
	public void setTarget(int ownerId, int pos) {
		
		this.targetOwnerId = ownerId;
		this.targetPos = pos;

	}
	
	public int getCasterOwnerId() {
		return casterOwnerId;
	}
	
	public int getCasterPos() {
		return casterPos;
	}
	
	public int getTargetOwnerId() {
		return targetOwnerId;
	}
	
	public int getTargetPos() {
		return targetPos;
	}
	

	
}
