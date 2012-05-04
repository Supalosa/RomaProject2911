package roma;

public class Roma {
	
	public static void main(String[] args) {
		Controller controller = new ControllerConsole();
		Game g = new Game(controller);
		controller.runGame(g);
		//UnitTests u = new UnitTests();
		//u.TestCardCollection();
		//u.TestGame();
	}
}