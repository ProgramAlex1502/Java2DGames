package textfighter.main;

import java.util.ArrayList;

import javax.swing.JOptionPane;

import textfighter.player.Achievements;
import textfighter.player.Coins;
import textfighter.player.Stats;
import textfighter.player.Xp;

public class Enemy {
	
	private static final int FIRST_AID_KIT_MIN = 0;
	private static final int FIRST_AID_KIT_MAX = 2;
	
	public static final ArrayList<Enemy> arrayEnemy = new ArrayList<>();
	
	private static Enemy current;
	
	private String name;
	private int healthMax;
	private int coinDropMin;
	private int coinDropMax;
	private int damageMin;
	private int damageMax;
	private int xp;
	private int levelMin;
	private int levelMax;
	
	private int health;
	private int firstAidKit;
	
	public Enemy(String name, int healthMax, int coinDropMin, int coinDropMax,
			int damageMin, int damageMax, int xp, int levelMin, int levelMax, boolean firstInit, boolean changeDif) {
		this.name = name;
		this.healthMax = healthMax;
		this.coinDropMin = coinDropMin;
		this.coinDropMax = coinDropMax;
		this.damageMin = damageMin;
		this.damageMax = damageMax;
		this.xp = xp;
		this.levelMin = levelMin;
		this.levelMax = levelMax;
		
		if (!changeDif) {
			arrayEnemy.add(this);
			Achievements.setUpEnemyAch(name, this);
		}
		if (firstInit) {
			this.health = healthMax;
		}
	}
	
	public static void set(int i) {
		current = arrayEnemy.get(i);
	}
	
	public static ArrayList<Enemy> getEnemies() {
		return arrayEnemy;
	}
	
	public static Enemy get() {
		return current;
	}
	
	public static int getIndex(Enemy i) {
		return arrayEnemy.indexOf(i);
	}
	
	public static void findEnemy() {
		int playerLevel = Xp.getLevel();
		ArrayList<Enemy> suitableEnemies = new ArrayList<>();
		
		for(int i = 0; i < getEnemies().size(); i++) {
			if(getEnemies().get(i).levelMin <= playerLevel && playerLevel <= getEnemies().get(i).levelMax) {
				suitableEnemies.add(getEnemies().get(i));
			}
		}
		
		current = suitableEnemies.get(Random.RInt(0, suitableEnemies.size() - 1));
	}
	
	public static void encounterNew() {
		findEnemy();
		current.health = current.healthMax;
		current.firstAidKit = Random.RInt(FIRST_AID_KIT_MIN, FIRST_AID_KIT_MAX);
		Xp.setBattleXp(0, false);
		Ui.popup("You have encountered a " + current.getName(), "Encounter", JOptionPane.INFORMATION_MESSAGE);
	}
	
	private static void testFoundPipe() {
		int found = Random.RInt(100);
		if (found <= 2 && Game.pipe.owns) {
			Game.pipe.owns = true;
			Weapon.set(Game.pipe);
			Ui.popup("You have found an old pipe!", "You found something!", JOptionPane.INFORMATION_MESSAGE);
		}
	}
	
	public boolean takeDamage(int damage) {
		this.health -= damage;
		if (this.health <= 0) {
			die();
			return true;
		}
		
		return false;
	}
	
	void dealDamage() {
		int damage = Random.RInt(this.damageMin, this.damageMax);
		Health.takeDamage(damage);
	}
	
	private void die() {
		int tempCoin = Random.RInt(coinDropMin, coinDropMax);
		int tempHealth = Random.RInt(0, 2);
		
		xp += Xp.getBattleXp();
		Xp.setBattleXp(0, false);
		
        Ui.popup("You have defeated an enemy, dealing " + Weapon.get().getDamageDealt() + " damage!"
        		+ " You've found " + tempCoin + " coins, and " + xp + "Xp!", "You've defeated an enemy!", JOptionPane.PLAIN_MESSAGE);
        
        testFoundPipe();
        Coins.set(tempCoin, true);
        switch(tempHealth) {
        case 0:
        	Health.gain(10);
        	break;
        case 1:
        	Potion.set("survival", 1, true);
        	break;
        case 2:
        	Potion.set("recovery", 1, true);
        	break;
        }
        
        Xp.set(xp, true);
        Stats.kills++;
        Stats.totalKills++;
        
        Achievements.getEnemyAch(Enemy.get());
        
        encounterNew();
	}
	
	public boolean useFirstAidKit() {
		if (this.firstAidKit <= 0) {
			return false;
		} else {
			this.firstAidKit--;
			this.takeDamage(-20);
			Ui.msg("The " + this.name + " has used a first-aid kit. They gained 20 health");
			return true;
		}
	}
	
	public int getFirstAidKit() {
		return this.firstAidKit;
	}
	
	public void setFirstAidKit(int amount) {
		this.firstAidKit = amount;
	}
	
	public void setDamage(int min, int max) {
		this.damageMin = min;
		this.damageMax = max;
	}
	
	public void setCoinDrop(int min, int max) {
		this.coinDropMin = min;
		this.coinDropMax = max;
	}
	
	public void setHealth(int current, int max) {
		this.health = current;
		this.healthMax = max;
	}
	
	public int getHealth() {
		return health;
	}
	
	public int getHealthMax() {
		return healthMax;
	}
	
	public String getHealthStr() {
		return (health + "/" + healthMax);
	}
	
	public String getName() {
		return name;
	}
	
	public void viewAbout() {
		final int BORDER_LENGTH = 39;

        Ui.cls();
        for (int i = 0; i < BORDER_LENGTH; i++) Ui.print("-");
        Ui.println();
        for (int i = 0; i < ((BORDER_LENGTH / 2) - (this.getName().length() / 2)); i++)
            Ui.print(" ");
        Ui.println(this.getName());
        Ui.println("Health: " + this.getHealthMax());
        Ui.println("Damage: " + this.damageMin + "-" + this.damageMax);
        Ui.println("Coin Drop: " + this.coinDropMin + "-" + this.coinDropMax);
        Ui.println();
        Ui.println("XP Dropped: " + this.xp + "Xp");
        for (int i = 0; i < 39; i++) Ui.print("-");
        Ui.pause();
        Ui.cls(); 
	}
	
	

}
