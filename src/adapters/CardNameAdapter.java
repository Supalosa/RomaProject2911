package adapters;

import enums.*;
import framework.cards.*;

/**
 * Converts our internal representation of card names -> Acceptance tester names
 * @author Supalosa
 *
 */
public enum CardNameAdapter {
	
	Sicarius 		("Sicarius", 		CardNames.Sicarius, Card.SICARIUS),
	Architectus 	("Architectus", 	CardNames.Architectus, Card.ARCHITECTUS),
	Consiliarius 	("Consiliarius", 	CardNames.Consiliarius, Card.CONSILIARIUS),
	Legat 			("Legat", 			CardNames.Legat, Card.LEGAT),
	Gladiator 		("Gladiator", 		CardNames.Gladiator, Card.GLADIATOR),
	Mercator 		("Mercator", 		CardNames.Mercator, Card.MERCATOR),
	Consul 			("Consul", 			CardNames.Consul, Card.CONSUL),
	Legionarius 	("Legionarius", 	CardNames.Legionarius, Card.LEGIONARIUS),
	Nero			("Nero", 			CardNames.Nero, Card.NERO),
	Praetorianus 	("Praetorianus",	CardNames.Praetorianus, Card.PRAETORIANUS),
	Scaenicus 		("Scaenicus", 		CardNames.Scaenicus, Card.SCAENICUS),
	Haruspex 		("Haruspex", 		CardNames.Haruspex, Card.HARUSPEX),
	Senator 		("Senator", 		CardNames.Senator, Card.SENATOR),
	Velites 		("Velites", 		CardNames.Velites, Card.VELITES),
	Essedum 		("Essedum", 		CardNames.Essedum, Card.ESSEDUM),
	Tribunus_Plebis ("Tribunus Plebis", CardNames.Tribunus_Plebis, Card.TRIBUNUSPLEBIS),
	Centurio 		("Centurio", 		CardNames.Centurio, Card.CENTURIO),
	Aesculapinum 	("Aesculapinum", 	CardNames.Aesculapinum, Card.AESCULAPINUM),
	Basilica 		("Basilica", 		CardNames.Basilica, Card.BASILICA),
	Machina 		("Machina", 		CardNames.Machina, Card.MACHINA),
	Forum 			("Forum", 			CardNames.Forum, Card.FORUM),
	Mercatus 		("Mercatus", 		CardNames.Mercatus, Card.MERCATUS),
	Onager 			("Onager", 			CardNames.Onager, Card.ONAGER),
	Templum 		("Templum", 		CardNames.Templum, Card.TEMPLUM),
	Turris 			("Turris", 			CardNames.Turris, Card.TURRIS),
	Kat 			("Kat", 			CardNames.Kat, Card.KAT),
	GrimReaper		("Grim Reaper", 	CardNames.GrimReaper, Card.GRIMREAPER),
	TelephoneBox	("Telephone Box",	CardNames.TelephoneBox, Card.TELEPHONEBOX);

	// The name in Roma format (the actual card name
	private String romaName;
	
	// Enum entry
	private CardNames romaEnum;
	
	// The name in Acceptance Test format
	private Card acceptanceName;
	

	// Mapping
	private CardNameAdapter (String romaName, CardNames romaEnum, Card acceptanceName) {
		
		this.romaName = romaName;
		this.romaEnum = romaEnum;
		this.acceptanceName = acceptanceName;
	
	}
	
	// Constructor when there is no difference
	private CardNameAdapter (String name, CardNames romaEnum) {
		
		this.romaName = name;
		this.romaEnum = romaEnum;
		this.acceptanceName = Card.valueOf(name.toUpperCase());
	
	}


	public String getAcceptanceName() {
	
		return this.acceptanceName.toString();
	
	}
	
	public Card getAcceptanceCard() {
	
		return this.acceptanceName;
	
	}
	
	public String getRomaName() {
	
		return this.romaName;
	
	}
	
	public CardNames getRomaEnum() {
	
		return this.romaEnum;

	}
	
	public static CardNameAdapter getRomaAdapter (String acceptanceName) {
		
		CardNameAdapter adapter = null;
		
		for (CardNameAdapter tempAdapter : CardNameAdapter.values()) {
		
			if (tempAdapter.getAcceptanceName().equals(acceptanceName)) {
			
				adapter = tempAdapter;
			
			}
		
		}
		
		if (adapter == null) {
		
			System.err.println ("CardNameAdapter::getRomaAdapter: Could not find acceptance card " + acceptanceName);
		
		}
		
		return adapter;
	
	}
	
	public static CardNameAdapter getRomaAdapter (Card acceptanceCard) {
	
		return getRomaAdapter(acceptanceCard.toString());
	
	}
	
	public static CardNameAdapter getAcceptanceAdapter (String romaName) {
	
		CardNameAdapter adapter = null;
		
		for (CardNameAdapter tempAdapter : CardNameAdapter.values()) {
		
			if (tempAdapter.getRomaName().equals(romaName)) {
			
				adapter = tempAdapter;
			
			}
		
		}
		
		if (adapter == null) {
		
			System.err.println ("CardNameAdapter::getAcceptanceAdapter: Could not find roma card " + romaName);
		
		}
		
		return adapter;
	
	}
	
	public static String getRomaName(String acceptanceName) {
	
		return getRomaAdapter(acceptanceName).getRomaName();
	
	}
	
	public static String getAcceptanceName(String romaName) {
	
		return getAcceptanceAdapter(romaName).getAcceptanceName();
	
	}
	
	public static Card getAcceptanceCard(String romaName) {
	
		return getAcceptanceAdapter(romaName).getAcceptanceCard();
	
	}
		
}
