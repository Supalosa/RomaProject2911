package adapters;

import enums.*;

/**
 * Converts our internal representation of card names -> Acceptance tester names
 * @author Supalosa
 *
 */
public enum CardNameAdapter {
	Sicarius 		("Sicarius", 		CardNames.Sicarius),
	Architectus 	("Architectus", 	CardNames.Architectus),
	Consiliarius 	("Consiliarius", 	CardNames.Consiliarius),
	Legat 			("Legat", 			CardNames.Legat),
	Gladiator 		("Gladiator", 		CardNames.Gladiator),
	Mercator 		("Mercator", 		CardNames.Mercator),
	Consul 			("Consul", 			CardNames.Consul),
	Legionarius 	("Legionarius", 	CardNames.Legionarius),
	Nero			("Nero", 			CardNames.Nero),
	Praetorianus 	("Praetorianus",	CardNames.Praetorianus),
	Scaenicus 		("Scaenicus", 		CardNames.Scaenicus),
	Haruspex 		("Haruspex", 		CardNames.Haruspex),
	Senator 		("Senator", 		CardNames.Senator),
	Velites 		("Velites", 		CardNames.Velites),
	Essedum 		("Essedum", 		CardNames.Essedum),
	Tribunus_Plebis ("Tribunus Plebis", CardNames.Tribunus_Plebis, "Tribunusplebis"),
	Centurio 		("Centurio", 		CardNames.Centurio),
	Aesculapinum 	("Aesculapinum", 	CardNames.Aesculapinum),
	Basilica 		("Basilica", 		CardNames.Basilica),
	Machina 		("Machina", 		CardNames.Machina),
	Forum 			("Forum", 			CardNames.Forum),
	Mercatus 		("Mercatus", 		CardNames.Mercatus),
	Onager 			("Onager", 			CardNames.Onager),
	Templum 		("Templum", 		CardNames.Templum),
	Turris 			("Turris", 			CardNames.Turris),
	Kat 			("Kat", 			CardNames.Kat),
	GrimReaper		("Grim Reaper", 	CardNames.GrimReaper, "Grimreaper"),
	TelephoneBox	("Telephone Box",	CardNames.TelephoneBox, "Telephonebox");

	// The name in Roma format (the actual card name
	private String romaName;
	
	// Enum entry
	private CardNames romaEnum;
	
	// The name in Acceptance Test format
	private String acceptanceName;
	

	// Mapping
	private CardNameAdapter (String romaName, CardNames romaEnum, String acceptanceName) {
		this.romaName = romaName;
		this.romaEnum = romaEnum;
		this.acceptanceName = acceptanceName;
	}
	
	// Constructor when there is no difference
	private CardNameAdapter (String name, CardNames romaEnum) {
		this.romaName = name;
		this.romaEnum = romaEnum;
		this.acceptanceName = name;
	}


	public String getAcceptanceName() {
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
		//System.out.println ("CardNameAdapter::getRomaAdapter (" + acceptanceName + ")");
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
		
}
