package testers;

import java.util.ArrayList;
import java.util.List;

public class RomaTests {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		System.out.println ("Testing our Roma...");
		
		List<ITestSuite> tests = new ArrayList<ITestSuite>();
		
		// Check assertions enabled
		try {
			assert (false);
			System.err.println ("Warning: assertions not enabled. Please run with -ea.");
			System.err.println ("Roma Tests terminated.");
			System.exit(1);
		} catch (AssertionError e) {
			// assertions on! do nothing.
		}
		
		/**
		 * Add tests here!
		 */
		tests.add(new InitTester());
		tests.add(new TurnTester());
		tests.add(new PlayCardActionTester());
		
		
		
		/**
		 * End test list
		 */
		for (ITestSuite test : tests) {
			System.out.println ("Testing " + test.getTestName () + ": ");
			test.run ();
			System.out.println ();
			System.out.println ();
		}
		
		System.out.println ("All tests passed! You are AWESOME!");
		
	}

}
