package cards.activators;


/**
 * Required to keep the mappings in order.
 * Otherwise, due to the hashing function, cards will get laid over
 * in the inappropriate order.
 * @author Supalosa
 *
 */
public class PositionMapping {
	
	private final int initial_pos;
	private final int final_pos;
	
	public PositionMapping(int initial_pos, int final_pos) {
		
		this.initial_pos = initial_pos;
		this.final_pos = final_pos;
		
	}
	
	public int getInitialPos() {
	
		return initial_pos;
	
	}
	
	public int getFinalPos() {
	
		return final_pos;
	
	}
	
}