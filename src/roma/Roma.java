package roma;

public class Roma {
	
	public static void main(String[] args) {
		IController controller = new ControllerConsole();
		Game g = new Game(controller);
		controller.setGame(g);
		controller.runGame();
	}
}