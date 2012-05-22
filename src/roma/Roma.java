package roma;

public class Roma {
	
	public static void main(String[] args) {
		
		System.out.println("   Welcome to");
		System.out.println("----------------------------------------------");
		System.out.println("    _/_/_/      _/_/    _/      _/    _/_/    ");
		System.out.println("   _/    _/  _/    _/  _/_/  _/_/  _/    _/   ");
		System.out.println("  _/_/_/    _/    _/  _/  _/  _/  _/_/_/_/    ");
		System.out.println(" _/    _/  _/    _/  _/      _/  _/    _/     ");
		System.out.println("_/    _/    _/_/    _/      _/  _/    _/      ");
		System.out.println("----------------------------------------------");
		System.out.println("The brain-melting, finger-tapping text-based Card Game from Randal Grant and Peter Xu!");
		System.out.println("----------------------------------------------");
		
		IController controller = new ControllerConsole();
		Game g = new Game(controller);
		controller.setGame(g);
		controller.runGame();
	
	}

}