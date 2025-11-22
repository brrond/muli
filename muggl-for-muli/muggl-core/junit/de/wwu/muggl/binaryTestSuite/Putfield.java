package de.wwu.muggl.binaryTestSuite;

/**
 * Testing the putfield instruction on an objectref/staticref
 * 
 * @author Max Schulze
 *
 */
public class Putfield {
	static Object testobj;

	char[] testobj2;
	static int primitive;
	static boolean prim2;
	public static String METHOD_testPutStaticNull = "testPutStaticNull";

	static boolean testPutStaticNull() {
		testobj = null;
		return testobj == null;
	}

	public static String METHOD_testPutfieldNull = "testPutfieldNull";

	static boolean testPutfieldNull() {
		Putfield neu = new Putfield();
		neu.testPutfieldInst(null);

		return neu.testobj2 == null;
	}

	public static String METHOD_testPutStaticBoolean = "testPutStaticBoolean";

	static boolean testPutStaticBoolean(boolean whatToStore) {
		prim2 = whatToStore;
		return prim2 == false;
	}

	public static String METHOD_testPutStaticInt = "testPutStaticInt";

	static boolean testPutStaticInt(int whatToStore) {
		primitive = whatToStore;
		return primitive > 2;
	}

	private void testPutfieldInst(char[] arg1) {
		this.testobj2 = arg1;
	}

	public static void main(String[] args) {
		System.out.println(testPutStaticNull());
		System.out.println(testPutfieldNull());
	}
}
