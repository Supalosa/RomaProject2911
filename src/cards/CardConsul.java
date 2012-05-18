package cards;

import java.util.*;

import cards.activators.CardParams;
import cards.activators.ConsulParams;

import roma.Game;
import roma.GameVisor;
import enums.CardNames;

public class CardConsul extends Card {

	@Override
	public CardNames getID() {
		return CardNames.Consul;
	}

	@Override
	public int getCostToPlay() {
		return 3;
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
		return "Consul";
	}

	@Override
	public String getDescription() {
		return  "The score on an action die which has not yet been used can be " +
				"increased or decreased by 1 point.";
	}

	@Override
	public int getDefense() {
		return 3;
	}


	@Override
	public CardParams getParams() {
		return new ConsulParams();
	}

	@Override
	public boolean performEffect(GameVisor g, int pos, CardParams a) {
		
		ConsulParams myParams = (ConsulParams)a;
		if (myParams.isIncreaseDice()) {
			g.setDiceRoll(myParams.getDiceValue(), myParams.getDiceValue()+1);
		} else {
			g.setDiceRoll(myParams.getDiceValue(), myParams.getDiceValue()-1);
		}
		
		return true;
		
	}

}
