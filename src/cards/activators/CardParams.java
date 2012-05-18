package cards.activators;

import roma.*;

public abstract class CardParams {

	private String error;
	
	public abstract void query(GameVisor g, int pos);
	public abstract boolean isValid();
	
	
	public String getError() {
		return error;
	}
	
	public void setError(String s) {
		error = s;
	}
	
}
