package cards.activators;

import java.util.ArrayList;
import java.util.List;

import roma.*;
import enums.CardNames;

public class ForumParams extends CardParams {
	
	/**
	 * The dice to use to gain points in Forum
	 */
	int forumDie;
	
	boolean useTemplum;
	
	public ForumParams() {
		
		forumDie = 0;
		
		useTemplum = false;
	
	}
	
	public int getForumDie() {
	
		return forumDie;
	
	}

	public void setForumDie(int forumDie) {
	
		this.forumDie = forumDie;
	
	}

	public boolean isUseTemplum() {
	
		return useTemplum;
	
	}

	public void setUseTemplum(boolean useTemplum) {
	
		this.useTemplum = useTemplum;
	
	}

	@Override
	public void query(GameVisor g, int pos) {

		if (g.getNumDiceRolls() > 1) {	

			// dice rolls that exclude the one that activated this card.
			List<Integer> diceRolls = new ArrayList<Integer>();
			boolean removedActivator = false;
			
			for (int i = 0; i < g.getNumDiceRolls(); i++) {
			
				if (g.getDiceRoll(i) != pos || removedActivator == true) {
				
					diceRolls.add(g.getDiceRoll(i));
				
				} else {
				
					removedActivator = true;
				
				}
			
			}

			g.getController().showMessage("Available dice: " + diceRolls.toString());

			int diceRoll;
			diceRoll = g.getController().getInt("Enter of the value of the dice roll " +
					"corresponding to the amount of VP you want to gain:");
			
			while (diceRolls.contains(diceRoll) == false) {
			
				diceRoll = g.getController().getInt("You don't have that die. Enter the dice roll:");

			}

			setForumDie(diceRoll);

			if ((pos < Game.FIELD_SIZE - 1 && g.getField().getCard(g.whoseTurn(), pos) != null &&
					g.getField().getCard(g.whoseTurn(), pos).getID() == CardNames.Templum)
					|| (pos > 1 && g.getField().getCard(g.whoseTurn(), pos) != null &&
					g.getField().getCard(g.whoseTurn(), pos-2).getID() == CardNames.Templum)) {

				g.getController().showMessage("You have a Templum adjecent to the activated Forum.");
				
				if (g.getController().getBoolean("Do you wish to use your remaining dice and " +
						"add its face value to your haul?")) {

					setUseTemplum(true);

				}

			}

		} else {

			setError("You don't have enough dice to activate this card");

		}
	
	}
	
	
	public boolean isValid() {
	
		return (forumDie != 0);
	
	}
	
}
