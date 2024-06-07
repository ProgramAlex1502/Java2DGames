package textfighter.main;

import java.util.Scanner;

public class Cheats {
	
	private static Scanner cheat = new Scanner(System.in);
	private static boolean enabled = false;
	private static boolean locked = false;
	
	//TODO: finish Cheats
	
	public static boolean enabled() {
		return enabled;
	}
	
	public static boolean locked() {
		return locked;
	}
	
	public static void enable() {
		enabled = true;
	}
	
	public static void lock() {
		locked = true;
	}

}
