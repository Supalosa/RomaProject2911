package roma;
import java.util.Random;


public class RandomGenerator {

	Random generator;
	
	public RandomGenerator() {
		
		generator = new Random();
		
	}
	
	public int randomInt (int low, int high) {
		
		return low + generator.nextInt(high - low + 1);
	
	}
	
}
