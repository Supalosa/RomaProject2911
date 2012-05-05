package roma;

import java.util.List;

import actions.*;
import framework.cards.Card;
import framework.interfaces.MoveMaker;
import framework.interfaces.activators.CardActivator;

/**
 * based on functioned called from MoveMaker, sends a Controller input to the Game.
 * @author Supalosa
 *
 */
public class MoveMakerAdapter implements MoveMaker, Controller {

	boolean endTurn;
	Game g;
	
	public MoveMakerAdapter() {
		endTurn = false;
	}
	
	@Override
	public CardActivator chooseCardToActivate(int disc)
			throws UnsupportedOperationException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void activateCardsDisc(int diceToUse, Card chosen)
			throws UnsupportedOperationException {
		// TODO Auto-generated method stub

	}

	@Override
	public void activateMoneyDisc(int diceToUse)
			throws UnsupportedOperationException {
		// TODO Auto-generated method stub

	}

	@Override
	public CardActivator activateBribeDisc(int diceToUse)
			throws UnsupportedOperationException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void endTurn() throws UnsupportedOperationException {
		g.nextTurn();
		
	}

	@Override
	public void placeCard(Card toPlace, int discToPlaceOn)
			throws UnsupportedOperationException {
		// TODO Auto-generated method stub

	}

	public void setGame(Game g) {
		this.g = g;
	}
	
	@Override
	public void runGame() {

	}

	@Override
	public String getString(String message) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getInt(String message) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public cards.Card getCard(Player p, String message) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public cards.Card getCard(List<cards.Card> cardList, String message) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getCard(cards.Card[][] field, String message) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean getWait(String message) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean getBoolean(String message) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void showMessage(String s) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void showScreen(Player p) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void showDiceRolls() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void showHand(Player p) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void showField() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public PlayerAction getAction(Player p) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void showCard(cards.Card c) {
		// TODO Auto-generated method stub
		
	}

}
