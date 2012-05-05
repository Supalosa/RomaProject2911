package roma;

import framework.interfaces.AcceptanceInterface;
import framework.interfaces.GameState;
import framework.interfaces.MoveMaker;

public class AcceptanceAdapter implements AcceptanceInterface {
	GameAcceptanceAdapter game;
	@Override
	public MoveMaker getMover(GameState state) {
		return game.getMoveMaker();
	}

	@Override
	public GameState getInitialState() {
		game = new GameAcceptanceAdapter();
		return game;
	}

}
