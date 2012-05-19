package cards;

import java.util.ArrayList;
import java.util.List;

import cards.activators.CardParams;
import cards.activators.ScaenicusParams;

import roma.*;
import enums.*;

public class CardScaenicus extends Card {

	@Override
	public CardNames getID() {
		return CardNames.Scaenicus;
	}

	@Override
	public int getCostToPlay() {
		return 8;
	}

	@Override
	public int getDiceToActivate() {
		return 1;
	}

	@Override
	public boolean isBuilding() {
		return false;
	}

	@Override
	public String getName() {
		return "Scaenicus";
	}

	@Override
	public String getDescription() {
		return "He performs no action of his own but can copy the action of any " +
				"of the player's own face-up character cards, and the next time round" +
				" that of another.";
	}

	@Override
	public int getDefense() {
		return 3;
	}


	@Override
	public CardParams getParams() {
		return new ScaenicusParams();
	}

	@Override
	public boolean performEffect(GameVisor g, int pos, CardParams a) {
		ScaenicusParams myParams = (ScaenicusParams)a;
		boolean performed = true;
		
		int posToCopy = myParams.getPositionToCopy();
		
		Card copiedCard = g.getField().getCard(g.whoseTurn(), posToCopy);
		
		/* Very hacky fix, but acceptance works weirdly.
		 * If we have been passed a CardActivator, then it has already been activated!
		 * So don't run it here.
		 */
		if (copiedCard != null && myParams.getCopiedParams() == null) {
			
			CardParams params = copiedCard.getParams();
			params.query(g, pos);
			
			copiedCard.performEffect(g, pos, params);

		}
		
		return performed;
	}

}
