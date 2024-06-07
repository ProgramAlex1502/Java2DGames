package textfighter.item;

import java.util.ArrayList;

import textfighter.main.Handle;
import textfighter.main.NPC;
import textfighter.main.Ui;
import textfighter.player.Coins;
import textfighter.player.Xp;

public class Armour {
	
	private static ArrayList<Armour> armours = new ArrayList<>(3);
	private String name;
	private int price;
	private int damResist;
	private int level;
	private boolean owns;
	private boolean equipped;
	
	public Armour(String name, int price, int damResist, int level) {
		this.name = name;
		this.price = price;
		this.damResist = damResist;
		this.level = level;
		armours.add(this);
	}
	
	public static Armour getEquipped() {
		for (Armour i : armours) {
			if (i.isEquipped()) return i;
		}
		Handle.error("Error - No armour equipped");
		return null;
	}
	
	public static ArrayList<Armour> getArmours() {
		return armours;
	}
	
	public static int get() {
		return armours.indexOf(getEquipped());
	}
	
	public static void set(int i) {
		armours.get(i).equipped = true;
	}
	
	public static void choose() {
		while (true) {
			Ui.cls();
			Ui.println("----------------------------");
			Ui.println("Equip new armor");
			Ui.println();
			Ui.println("Eqquiped: " + getEquipped().toString());
			Ui.println("----------------------------");
			
			int j = 0;
			int[] offset = new int[getArmours().size()];
			
			for(int i = 0; i < getArmours().size(); i++) {
				if(getArmours().get(i).isOwns()) {
					Ui.println((j + 1) + ") " + getArmours().get(i).getName());
					offset[j] = i - j;
					j++;
				}
			}
			
			while (true) {
				int menuItem = Ui.getValidInt();
				
				try {
					menuItem--;
					menuItem = menuItem + offset[menuItem];
					
					Armour.getArmours().get(menuItem).equip();
					return;
				} catch (Exception e) {
					Ui.println();
					Ui.println((menuItem + 1) + " is not an option");
				}
			}
		}
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		if (name.equals("")) return;
		this.name = name;
	}
	
	public int getPrice() {
		return this.price;
	}
	
	public void setPrice(int price) {
		this.price = price;
	}
	
	public int getDamResist() {
		return this.damResist;
	}
	
	public void setDamResist(int damResist) {
		this.damResist = damResist;
	}
	
	public int getLevel() {
		return this.level;
	}
	
	public void setLevel(int level) {
		this.level = level;
	}
	
	public boolean isOwns() {
		return this.owns;
	}
	
	public void setOwns(boolean owns) {
		this.owns = owns;
	}
	
	public boolean isEquipped() {
		return this.equipped;
	}
	
	public void equip() {
		if (!(this.owns)) {
			Ui.msg("You do not own this.");
			return;
		}
		
		this.equipped = true;
		getEquipped().unequip();
		this.equipped = true;
		Ui.msg("You have equipped " + this.toString());
	}
	
	public void equipSilent() {
		if (!(this.owns)) {
			return;
		}
		
		this.equipped = true;
		getEquipped().unequip();
		this.equipped = true;
	}
	
	public void unequip() {
		this.equipped = false;
	}
	
	public String toString() {
		if (this.getName().equals("None")) return "No armour";
		return this.getName() + " armour";
	}
	
	public boolean buy() {
		if (Xp.getLevel() < this.getLevel()) {
			Ui.println("You have to be at least level " + this.getLevel() + " to buy this!");
			Ui.pause();
			return false;
		} else if (this.isOwns()) {
			Ui.println("You already own this.");
			Ui.pause();
			return false;
		} else if (this.getPrice() <= Coins.get()) {
			Coins.set(-this.price, true);
			setOwns(true);
			equipSilent();
			NPC.gratitude("Armour", "purchase");
			Ui.pause();
			return true;
		} else {
			Ui.println("You do not have enough coins.");
			Ui.pause();
			return false;
		}
	}
	
	public void viewAbout() {
		final int BORDER_LENGTH = 39;
		
		Ui.cls();
		
		for (int i = 0; i < BORDER_LENGTH; i++) Ui.print("-");
		Ui.println();
		for (int i = 0; i < ((BORDER_LENGTH / 2) - (this.getName().length() / 2)); i++) Ui.print(" ");
		
		Ui.println(this.toString());
		Ui.println("Price: " + this.price + " coins");
		Ui.println("Damage Resistance(%): " + this.damResist + "%");
		Ui.println("Level needed: " + this.level);
		for (int i = 0; i < BORDER_LENGTH; i++) Ui.print("-");
		Ui.pause();
		Ui.cls();
	}
	
}
