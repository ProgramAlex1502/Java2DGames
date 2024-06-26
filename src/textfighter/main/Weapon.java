package textfighter.main;

import java.util.ArrayList;
import java.util.Collections;

import javax.swing.JOptionPane;

import textfighter.player.Achievements;
import textfighter.player.Coins;
import textfighter.player.Health;
import textfighter.player.Stats;
import textfighter.player.Xp;

public class Weapon implements Comparable<Weapon>{

	public static final ArrayList<Weapon> arrayWeapon = new ArrayList<>();
	
	public static int BULLET_DAMAGE;
	public static int BULLET_CRITICAL_MULTIPLIER;
	public static double BULLET_CRITICAL_CHANCE;
	
	public static Weapon starting;
	private static Weapon current = null;
	public int price;
	public int level;
	public boolean melee;
	public boolean owns;
	private int damageMin;
	private int damageMax;
	private int damageDealt;
	private double chanceOfMissing;
	private double critChanceMultiplier;
	private int critDamMultiplierMin;
	private int critDamMultiplierMax;
	private String name;
	private boolean buyable;
	
	private int ammo;
	private int ammoUsed;
	private int ammoPrice;
	private int ammoIncludedWithPurchase;
	
	public Weapon(String name, int ammoUsed, int ammoIncludedWithPurchase, boolean buyable, int price,
			int ammoPrice, int level, double chanceOfMissing, double critChanceMultiplier, int critDamMultiplierMin, int critDamMultiplierMax, boolean firstInit, boolean changeDif) {
		this.name = name;
		this.ammoUsed = ammoUsed;
		this.ammoIncludedWithPurchase = ammoIncludedWithPurchase;
		this.buyable = buyable;
		this.price = price;
		this.ammoPrice = ammoPrice;
		this.level = level;
		this.chanceOfMissing = chanceOfMissing;
		this.critChanceMultiplier = critChanceMultiplier;
		this.critDamMultiplierMin = critDamMultiplierMin;
		this.critDamMultiplierMax = critDamMultiplierMax;
		this.melee = false;
		
		if(!changeDif) {
			arrayWeapon.add(this);
		}
		
		if(firstInit) {
			this.owns = false;
		}
		
		Collections.sort(arrayWeapon);
	}
	
	public Weapon(String name, boolean startingWeapon, boolean buyable, int price, int level,
			int damageMin, int damageMax, boolean firstInit, boolean changeDif) {
		this.name = name;
		this.buyable = buyable;
		this.price = price;
		this.level = level;
		this.damageMin = damageMin;
		this.damageMax = damageMax;
		this.melee = true;
		
		if (!changeDif) {
			if (startingWeapon) {
				this.owns = true;
				current = this;
				starting = this;
			} else {
				this.owns = false;
			}
		}
	}
	
	public static ArrayList<Weapon> getWeapons() {
		return arrayWeapon;
	}
	
	public static Weapon get() {
		return current;
	}
	
	static int getIndex(Weapon i) {
		return arrayWeapon.indexOf(i);
	}
	
	public static void set(Weapon x) {
		current = x;
	}
	
	public static void set(int i) {
		current = arrayWeapon.get(i);
	}
	
	public static void choose() {
		while (true) {
			Ui.cls();
			Ui.println("----------------------------");
			Ui.println("Equip new weapon");
			Ui.println();
			Ui.println("Ammo: " + current.getAmmo());
			Ui.println("Equipped weapon: " + current.getName());
			Ui.println("----------------------------");
			
			int j = 0;
			int[] offset = new int[getWeapons().size()];
			
			for (int i = 0; i < getWeapons().size(); i++) {
				if (getWeapons().get(i).owns()) {
					Ui.println((j + 1) + ") " + getWeapons().get(i).getName());
					offset[j] = i - j;
					j++;
				}
			}
			
			Ui.println((j + 1) + ") Back");
			
			while(true) {
				int menuItem = Ui.getValidInt();
				
				try {
					if(menuItem == (j + 1) || menuItem > j) return;
					
					menuItem--;
					menuItem = menuItem + offset[menuItem];
					
					if(!getWeapons().get(menuItem).owns) {
						Ui.msg("You do not own this weapon!");
						return;
					}
					
					current = getWeapons().get(menuItem);
					Ui.msg("You have equipped a " + getWeapons().get(menuItem).getName());
					return;
				} catch (Exception e) {
					Ui.println();
					Ui.println(menuItem + " is not an option");
				}
			}
		}
	}
	
	private static void noAmmo() {
		Ui.popup("You've run out of ammo!", "Warning", JOptionPane.WARNING_MESSAGE);
		Weapon.current = Weapon.starting;
	}
	
	public static void displayAmmo() {
		if (!(Weapon.get().melee)) {
			Ui.println("     Ammo: " + Weapon.get().getAmmo());
		}
	}
	
	public String getName() {
		return name;
	}
	
	public boolean owns() {
		return owns;
	}
	
	public void setAmmo(int amount, boolean add) {
		if (this.melee) return;
		if (add) {
			this.ammo += amount;
		} else {
			this.ammo = amount;
		}
	}
	
	public int getAmmo() {
		return this.ammo;
	}
	
	public int getDamageDealt() {
		return this.damageDealt;
	}
	
	public void dealDam() {
		if (this.melee) {
			damageDealt = Random.RInt(this.damageMin, this.damageMax);
		} else {
			if(getAmmo() >= this.ammoUsed) {
				for (int i = 1; i <= this.ammoUsed; i++) {
					if (Random.RInt(100) > this.chanceOfMissing) {
						damageDealt += BULLET_DAMAGE;
						Stats.bulletsThatHit++;
					}
					
					setAmmo(-1, true);
					Stats.bulletsFired += 1;
				}
				criticalHit();
				bulletCriticalHit();
			} else {
				noAmmo();
				damageDealt = 0;
			}
		}
		
		Stats.totalDamageDealt += damageDealt;
		Xp.setBattleXp(damageDealt, true);
		
		if(!Enemy.get().takeDamage(damageDealt)) {
			Ui.cls();
			Ui.println("----------------------------------------------------");
			Ui.println("You have attacked a " + Enemy.get().getName() + "!");
			Ui.println("You dealt " + damageDealt + " damage with a " + this.name);
			Ui.println("----------------------------------------------------");
			Ui.println("Your health: " + Health.getStr());
			Ui.println("Enemy health: " + Enemy.get().getHealthStr());
			Ui.println("----------------------------------------------------");
			Ui.pause();
			
			if(Enemy.get().getHealth() <= Enemy.get().getHealthMax() / 3) {
				Enemy.get().useFirstAidKit();
			}
		}
		damageDealt = 0;
	}
	
	private void criticalHit() {
		if (wasCriticalHit()) {
			int critMultiplier = Random.RInt(this.critDamMultiplierMin, this.critDamMultiplierMax);
			
			damageDealt *= critMultiplier;
			
			Ui.cls();
			Ui.println("----------------------------------------------------");
			Ui.println("Critical Hit!");
			Ui.println("You dealt " + critMultiplier + "x normal damage.");
			Ui.println("----------------------------------------------------");
			Ui.pause();
		}
	}
	
	private void bulletCriticalHit() {
		if(bulletWasCriticalHit()) {
			damageDealt *= Weapon.BULLET_CRITICAL_MULTIPLIER;
			
			Ui.cls();
			Ui.println("----------------------------------------------------");
			Ui.println("Critical Bullet Hit!");
			Ui.println("Your bullets dealt " + Weapon.BULLET_CRITICAL_MULTIPLIER + "x normal damage.");
			Ui.println("----------------------------------------------------");
			Ui.pause();
		}
	}
	
	private boolean wasCriticalHit() {
		return Random.RInt((int) (100 / this.critChanceMultiplier)) == 1;
	}
	
	private boolean bulletWasCriticalHit() {
		return Random.RInt((int) (100 / Weapon.BULLET_CRITICAL_CHANCE)) == 1;
	}
	
	public void viewAbout() {
		final int BORDER_LENGTH = 39;
		
		Ui.cls();
		for(int i = 0; i < BORDER_LENGTH; i++) Ui.print("-");
		Ui.println();
		for(int i = 0; i < ((BORDER_LENGTH / 2) - (this.getName().length() / 2)); i++) Ui.print(" ");
		Ui.println(this.getName());
		Ui.println("Price: " + this.price + " coins");
		Ui.println("Chance of missing: " + this.chanceOfMissing + "%");
		Ui.println("Ammo Used: " + this.ammoUsed);
		Ui.println("Damage: " + this.getDamage());
		Ui.println("Chance of critical hit: " + this.critChanceMultiplier + "%");
		Ui.println("Critical hit damage multiplier: " + this.critDamMultiplierMin + "-" + this.critDamMultiplierMax + "x");
		if (!this.melee) {
			Ui.println("Chance of critical hit, bullet: " + Weapon.BULLET_CRITICAL_CHANCE + "%");
			Ui.println("Bullet Critical of critical hit, bullet: " + Weapon.BULLET_CRITICAL_MULTIPLIER + "x");
		}
		for (int i = 0; i < BORDER_LENGTH; i++) Ui.print("-");
		Ui.pause();
		Ui.cls();
	}
	
	private String getDamage() {
		if (this.melee) {
			return (this.damageMin + " - " + this.damageMax);
		} else {
			if (this.chanceOfMissing == 0) {
				return String.valueOf((BULLET_DAMAGE * this.ammoUsed));
			} else {
				return ("0 - " + String.valueOf((BULLET_DAMAGE * this.ammoUsed)));
			}
		}
	}
	
	public boolean isBuyable() {
		return this.buyable;
	}
	
	public void buy() {
		if (!isBuyable()) {
			Ui.msg("Sorry, this item is no longer in stock.");
			return;
		}
		if(this.owns()) {
			Ui.msg("You already own this weapon.");
			return;
		}
		if (level > Xp.getLevel()) {
			Ui.msg("You are not a high enough level to buy this item.");
			return;
		}
		if (price > Coins.get()) {
			Ui.msg("You do not have enough coins to buy this item.");
			return;
		}
		
		Achievements.boughtItem = true;
		Coins.set(-price, true);
		Stats.coinsSpentOnWeapons += price;
		this.owns = true;
		current = this;
		Ui.println("You have bought a " + this.getName() + " for " + this.price + " coins.");
		Ui.println("Coins: " + Coins.get());
		Ui.pause();
		
		ammo += this.ammoIncludedWithPurchase;
	}
	
	public void buyAmmo() {
		Ui.cls();
		
		if (Xp.getLevel() < this.level) {
			Ui.println("You are not a high enough level. You need to be at least level " + this.level + ".");
			Ui.pause();
			return;
		}
		
		Ui.println("How much ammo would you like to buy?");
		Ui.println("1 ammo cost " + this.ammoPrice + " coins.");
		Ui.println("You have " + Coins.get() + " coins.");
		int ammoToBuy = Ui.getValidInt();
		int cost = ammoToBuy * ammoPrice;
		
		if (Coins.get() < (cost)) {
			Ui.println("You don't have enough coins. You need " + (cost - Coins.get()) + " more coins.");
			Ui.pause();
			return;
		}
		
		this.ammo += ammoToBuy;
		Coins.set(-cost, true);
		Stats.coinsSpentOnWeapons += cost;
		
		Ui.println("You have bought " + ammoToBuy + " ammo");
		Ui.pause();
	}
	
	public int getAmmoPrice() {
		return this.ammoPrice;
	}
	
	@Override
	public int compareTo(Weapon w) {
		return Integer.compare(this.level, w.level);
	}
	
}
