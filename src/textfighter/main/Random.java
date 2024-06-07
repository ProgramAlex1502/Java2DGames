package textfighter.main;

public class Random {
	
	private Random() {
		
	}
	
	public static int RInt(int max) {
		java.util.Random rand = new java.util.Random();
		
		return (rand.nextInt(max) + 1);
	}
	
	public static int RInt(int min, int max) {
		java.util.Random rand = new java.util.Random();
		
		return (rand.nextInt((max - min) + 1) + min);
	}

}
