package textfighter.player;

import java.util.ArrayList;

import javax.swing.JOptionPane;

import textfighter.main.Enemy;
import textfighter.main.Ui;

public class Achievements {
	
	public static final ArrayList<Boolean> arrayKilled = new ArrayList<>();
	private static final ArrayList<Enemy> arrayEnemy = new ArrayList<>();
	
	public static boolean boughtItem = false;
	
	private Achievements() {}
	
	public static void setUpEnemyAch(String name, Enemy type) {
		arrayKilled.add(false);
		arrayEnemy.add(type);
	}
	
	public static void check() {
		//TODO: Complete the method
	}
	
	public static void getEnemyAch(Enemy x) {
		if (!arrayKilled.get(arrayEnemy.indexOf(x))) {
			get("Goodbye, " + x.getName() + "!");
			arrayKilled.set(arrayEnemy.indexOf(x), true);
		}
	}
	
	private static void get(String ach) {
		Ui.popup("You've got an achievement! \n\n" + ach, "Achievement", JOptionPane.INFORMATION_MESSAGE);
		Xp.set(100, true);
	}

}
