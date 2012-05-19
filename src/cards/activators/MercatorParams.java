package cards.activators;

import roma.*;

public class MercatorParams extends CardParams {
	
	/**
	 * amount of money to spend on VP
	 */
	private int moneyToSpend;
	
	
	public MercatorParams() {
		moneyToSpend = 0;
	}
	
	public int getMoneyToSpend() {
		return moneyToSpend;
	}
	
	public void setMoneyToSpend(int money) {
		this.moneyToSpend = money;
	}
	
	@Override
	public void query(GameVisor g, int pos) {
		
		boolean valid = false;
		int money;
		int enemy = (g.whoseTurn() + 1) % Game.MAX_PLAYERS;
		
		while (!valid) {
			
			money = g.getController().getInt("How much sestertii do you want to spend?");
			
			if (money <= 3) { //"This fix comes directly from Stefan Feld, the gameÕs designer."
				
				if (money / 2 <= g.getPlayer(enemy).getVP()) {
					
					setMoneyToSpend(money);
				
				} else {
					
					setError("Can be used to purchase a maximum of 3 VPs (for 6 gold) per activation"); 
					
				}
				
			} else {
				
				setError("You don't have that much sestertii");
			
			}
			
		}
		
	}
	
	public boolean isValid() {
		return (moneyToSpend > 0);
	}

	
	
}
