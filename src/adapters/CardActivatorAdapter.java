package adapters;

import cards.*;
import roma.*;
import adapters.activators.*;
import enums.*;

public class CardActivatorAdapter {

	
	/**
	 * Get the Activator Adapter which corresponds to the card 'card'.
	 * @param card The card to be activated
	 * @param disc
	 * @param game
	 * @param activatedCard
	 * @return The Activator Adapter.
	 */
	public static GenericAdapterActivator getActivator(CardNames card, int disc, Game game, Card activatedCard) {
		
		GenericAdapterActivator activator = null;
		
		if (card == CardNames.Tribunus_Plebis) {
			
			activator = new TribunusPlebisAdapterActivator(disc, game, activatedCard);
			
		} else if (card == CardNames.Legat){
			
			activator = new LegatAdapterActivator(disc, game, activatedCard);
			
		} else if (card == CardNames.Sicarius){
			
			activator = new SicariusAdapterActivator(disc, game, activatedCard);
			
		} else if (card == CardNames.Aesculapinum) {
			
			activator = new AesculapinumAdapterActivator(disc, game, activatedCard);
			
		} else if (card == CardNames.Consul) {
			
			activator = new ConsulAdapterActivator(disc, game, activatedCard);
			
		} else if (card == CardNames.Senator) {
			
			activator = new SenatorAdapterActivator(disc, game, activatedCard);
			
		} else if (card == CardNames.Forum) {
			
			activator = new ForumAdapterActivator(disc, game, activatedCard);
			
		} else if (card == CardNames.Consiliarius) {
			
			activator = new ConsiliariusAdapterActivator(disc, game, activatedCard);
			
		} else if (card == CardNames.Essedum) {
			
			activator = new EssedumAdapterActivator(disc, game, activatedCard);
			
		} else if (card == CardNames.Centurio) {
			
			activator = new CenturioAdapterActivator(disc, game, activatedCard);
			
		} else if (card == CardNames.Legionarius) {
			
			activator = new LegionariusAdapterActivator(disc, game, activatedCard);
			
		} else if (card == CardNames.Mercator) {
			
			activator = new MercatorAdapterActivator(disc, game, activatedCard);
			
		} else if (card == CardNames.Onager) {
			
			activator = new OnagerAdapterActivator(disc, game, activatedCard);
			
		} else if (card == CardNames.Mercatus) {
			
			activator = new MercatusAdapterActivator(disc, game, activatedCard);
			
		} else if (card == CardNames.Machina) {
			
			activator = new MachinaAdapterActivator(disc, game, activatedCard);
			
		} else if (card == CardNames.Gladiator) {
			
			activator = new GladiatorAdapterActivator(disc, game, activatedCard);
			
		} else if (card == CardNames.Nero) {
			
			activator = new NeroAdapterActivator(disc, game, activatedCard);
			
		} else if (card == CardNames.Scaenicus) {
			
			activator = new ScaenicusAdapterActivator(disc, game, activatedCard);
			
		} else if (card == CardNames.Velites) {
			
			activator = new VelitesAdapterActivator(disc, game, activatedCard);
			
		} else if (card == CardNames.Praetorianus) {
			
			activator = new PraetorianusAdapterActivator(disc, game, activatedCard);
			
		} else if (card == CardNames.Architectus) {
			
			activator = new ArchitectusAdapterActivator(disc, game, activatedCard);
			
		} else if (card == CardNames.Haruspex) {
			
			activator = new HaruspexAdapterActivator(disc, game, activatedCard);
			
		} else if (card == CardNames.TelephoneBox) {
			
			activator = new TelephoneBoxAdapterActivator(disc, game, activatedCard);
			
		}
		
		return activator;
	
	}
	
}
