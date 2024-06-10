package textfighter.main;

import java.util.Scanner;

import textfighter.item.Armour;

public class Game {
	
	//TODO: Finish class Game
	
	private final static Scanner SCAN = new Scanner(System.in);
	
	private static boolean gameStarted = false;
	
	public static boolean hadGameStarted() {
		return gameStarted;
	}
	
	public static Enemy darkElf;
	public static Enemy ninja;
	public static Enemy giantSpider;
	public static Enemy zombie;
	public static Enemy goblin;
	public static Enemy ghost;
	public static Enemy barbarian;
	public static Enemy giantAnt;
	public static Enemy evilUnicorn;
	public static Enemy ogre;
	
	public static Weapon fists;
	public static Weapon baseballBat;
	public static Weapon knife;
	public static Weapon pipe;
	public static Weapon pistol;	
	public static Weapon smg;
	public static Weapon shotgun;
	public static Weapon rifle;
	public static Weapon sniper;
	
	public static Weapon chainsaw;
	
	public static Armour none = new Armour("None", 0, 0, 1);
	public static Armour basic = new Armour("Basic", 400, 15, 5);
	public static Armour advanced = new Armour("Advanced", 750, 30, 7);
	
	public static Food apple	   = new Food("Apple", 		   "A boring 'ol apple.", 				 StatusEffect.type.HEALTH, Food.type.FRUIT, 	 5);
	public static Food orange	   = new Food("Orange",		   "Sort of like an apple, but orange.", StatusEffect.type.HEALTH, Food.type.FRUIT, 	 5);
	public static Food dragonFruit = new Food("Dragon Fruit",  "Unfortunately, not a real dragon.",  StatusEffect.type.HEALTH, Food.type.FRUIT, 	 10);
	public static Food meat 	   = new Food("Chunk of meat", "Probably not rotten.", 				 StatusEffect.type.HEALTH, Food.type.MEAT_OTHER, 15);
	public static Food mushroom	   = new Food("Mushroom",	   "The good kind!",					 StatusEffect.type.HEALTH, Food.type.OTHER,		 5);
	public static Food fish		   = new Food("Fish",		   "Found in rivers and lakes.", 		 StatusEffect.type.HEALTH, Food.type.MEAT_FISH,  15);
	

}
