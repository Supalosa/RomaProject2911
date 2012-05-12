package roma;
import java.util.*;

import cards.*;
import enums.*;



public class CardTypes {
	
	
	private Map<CardNames, String> cardMap;
	
	public CardTypes () {
		cardMap = new HashMap<CardNames, String>();
		cardMap.put(CardNames.Sicarius, "cards.CardSicarius");
		cardMap.put(CardNames.Architectus, "cards.CardArchitectus");
		cardMap.put(CardNames.Consiliarius, "cards.CardConsiliarius");
		cardMap.put(CardNames.Legat, "cards.CardLegat");
		cardMap.put(CardNames.Gladiator, "cards.CardGladiator");
		cardMap.put(CardNames.Mercator, "cards.CardMercator");
		cardMap.put(CardNames.Consul, "cards.CardConsul");
		cardMap.put(CardNames.Legionarius, "cards.CardLegionarius");
		cardMap.put(CardNames.Nero, "cards.CardNero");
		cardMap.put(CardNames.Praetorianus, "cards.CardPraetorianus");
		cardMap.put(CardNames.Scaenicus, "cards.CardScaenicus");
		cardMap.put(CardNames.Haruspex, "cards.CardHaruspex");
		cardMap.put(CardNames.Senator, "cards.CardSenator");
		cardMap.put(CardNames.Velites, "cards.CardVelites");
		cardMap.put(CardNames.Essedum, "cards.CardEssedum");
		cardMap.put(CardNames.Tribunus_Plebis, "cards.CardTribunusPlebis");
		cardMap.put(CardNames.Centurio, "cards.CardCenturio");
		cardMap.put(CardNames.Aesculapinum, "cards.CardAesculapinum");
		cardMap.put(CardNames.Basilica, "cards.CardBasilica");
		cardMap.put(CardNames.Machina, "cards.CardMachina");
		cardMap.put(CardNames.Forum, "cards.CardForum");
		cardMap.put(CardNames.Mercatus, "cards.CardMercatus");
		cardMap.put(CardNames.Onager, "cards.CardOnager");
		cardMap.put(CardNames.Templum, "cards.CardTemplum");
		cardMap.put(CardNames.Turris, "cards.CardTurris");
		cardMap.put(CardNames.Kat, "cards.CardKat");
		cardMap.put(CardNames.GrimReaper, "cards.CardGrimReaper");
		cardMap.put(CardNames.TelephoneBox, "cards.CardTelephoneBox");
		

	}
	
	public void InitialiseCards(Pile d) {
		addCards(d, CardNames.Sicarius, 20);
		
		
		// Sicarius x1
		addCards(d, CardNames.Sicarius, 1);
		// Architectus x2
		addCards(d, CardNames.Architectus, 2);
		// Consiliarius x2
		addCards(d, CardNames.Consiliarius, 2);
		// Legat x2
		addCards(d, CardNames.Legat, 2);
		// Gladiator x2
		addCards(d, CardNames.Gladiator, 2);
		// Mercator x1
		addCards(d, CardNames.Mercator, 1);
		// Consul x2
		addCards(d, CardNames.Consul, 2);
		// Legionarius x3
		addCards(d, CardNames.Legionarius, 3);
		// Nero x1
		addCards(d, CardNames.Nero, 1);
		// Praetorianus x2
		addCards(d, CardNames.Praetorianus, 2);
		// Scaenicus x2
		addCards(d, CardNames.Scaenicus, 2);
		// Haruspex x2
		addCards(d, CardNames.Haruspex, 2);
		// Senator x2
		addCards(d, CardNames.Senator, 2);
		// Velites x2
		addCards(d, CardNames.Velites, 2);
		// Essedum x2
		addCards(d, CardNames.Essedum, 2);
		// Tribunis Plebis x2
		addCards(d, CardNames.Tribunus_Plebis, 2);
		// Centurio x2
		addCards(d, CardNames.Centurio, 2);
		//Aesculapinum x2
		addCards(d, CardNames.Aesculapinum, 2);
		//Basilica x2
		addCards(d, CardNames.Basilica, 2);
		//Forum x6
		addCards(d, CardNames.Forum, 6);
		//Machina x2
		addCards(d, CardNames.Machina, 2);
		//Mercatus x2
		addCards(d, CardNames.Mercatus, 2);
		//Onager x2
		addCards(d, CardNames.Onager, 2);
		//Templum x2
		addCards(d, CardNames.Templum, 2);
		//Turris x2
		addCards(d, CardNames.Turris, 2);
		
	}
	
	private void addCards(Pile d, CardNames t, int count) {
		for(int i = 0; i < count; i++) {
			Card c = getCard(t);
			d.addCard(c); // potential issue because of hooked events
		}
	}
	
	public Card getCard(CardNames c) {
		String cardClassName = cardMap.get(c);
		Card cardInstance = null;
		try {
			cardInstance = (Card)Class.forName(cardClassName).newInstance();
		} catch (NullPointerException e) {
			System.err.println ("CardTypes: No class with name " + cardClassName + " (recieved " + c + ")");
			e.printStackTrace();
		} catch (InstantiationException e) {
			System.err.println ("CardTypes: Could not instantiate class " + cardClassName);
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			System.err.println ("CardTypes: Illegal Access Exception creating " + cardClassName);
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			System.err.println ("CardTypes: Could not find class " + cardClassName);
			e.printStackTrace();
		}

		return cardInstance;
		
	}
	
	public Card getCardFromString(String cardName) {
		CardNames c = null;
		for (CardNames card : CardNames.values()) {
			if (card.toString().compareTo(cardName) == 0) {
				c = card;
			}
		}
		
		if (c == null) {
			System.err.println ("CardNames::getCardFromString: no card " + cardName);
		}

		return getCard (c);
		
	}
	
}
