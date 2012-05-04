package roma;

import java.util.*;

import cards.*;

import actions.PlayerAction;


public interface Controller {
	
	public void runGame(Game g);
	
	public String getString(String message);
	
	public int getInt(String message);
	
	public Card getCard(Player p, String message);	
	
	public Card getCard(List<Card> cardList, String message);
	
	public int getCard(Card[][] field, String message);
	
	public boolean getWait (String message);
	
	public boolean getBoolean (String message);
	
	public void showMessage(String s);
	
	public void showScreen(Player p);
	
	public void showDiceRolls();
	
	public void showHand(Player p);
	
	public void showField(Game g);
	
	public PlayerAction getAction (Player p);

	public void showCard(Card c);
	
}
