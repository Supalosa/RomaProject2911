package roma;

import java.util.*;

import cards.*;

import actions.IPlayerAction;


public interface IController {
	
	public void setGame (Game g);
	
	public void runGame();
	
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
	
	public void showField();
	
	public IPlayerAction getAction (Player p);

	public void showCard(Card c);
	
}
