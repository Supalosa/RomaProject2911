package adapters;

import framework.interfaces.AcceptanceInterface;
import framework.interfaces.GameState;
import framework.interfaces.MoveMaker;

public class AcceptanceAdapter implements AcceptanceInterface {
	
	GameAdapter game;
	MoveMaker mover;
	
	@Override
	public MoveMaker getMover(GameState state) {
	
		mover = new MoveMakerAdapter(game, game.getController());
		return mover;
	
	}

	@Override
	public GameState getInitialState() {
	
		game = new GameAdapter();
		return game;
	
	}

}
