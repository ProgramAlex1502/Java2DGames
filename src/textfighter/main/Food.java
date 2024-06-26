package textfighter.main;

import java.util.ArrayList;

import textfighter.player.Health;

public class Food {
	
	public static final ArrayList<Food> arrayFood = new ArrayList<>();
	public static int totalEaten;
	private String name;
	private String desc;
	private int quantity = 1;
	private StatusEffect.type statusEffect;
	private int effectLevel;
	private type foodType;
	
	private boolean viewedAbout;
	
	public Food(String name, String desc, StatusEffect.type statusEffect, type foodType, int effectLevel) {
		this.name = name;
		this.desc = desc;
		this.statusEffect = statusEffect;
		this.foodType = foodType;
		this.effectLevel = effectLevel;
		arrayFood.add(this);
	}
	
	public static void list() {
		while(true) {
			Ui.cls();
			
			int j = 0;
			int[] offset = new int[getFoods().size()];
			
			for (int i = 0; i < getFoods().size(); j++) {
				if (getFoods().get(i).quantity > 0) {
					Ui.println((j + 1) + ") " + getFoods().get(i).getName() + "(" + getFoods().get(i).quantity + ")");
					offset[j] = i - j;
					j++;
				}
			}
			
			Ui.println((j + 1) + ") Back");
			
			while (true) {
				int menuItem = Ui.getValidInt();
				
				try {
					if (menuItem == (j + 1) || menuItem > j)
						return;
					
					menuItem--;
					menuItem = menuItem + offset[menuItem];
					
					if (getFoods().get(menuItem).getStatusEffect() == StatusEffect.type.HEALTH && Health.get() == Health.getOutOf()) {
						Ui.msg("Your health is already full. No need to eat this!");
						return;
					}
					
					if (getFoods().get(menuItem).quantity > 0) {
						Food.getFoods().get(menuItem).eat();
					}
					return;
				} catch (Exception e) {
					Ui.println();
					Ui.println(menuItem + " is not an option.");
				}
			}
		}
	}
	
	public static ArrayList<Food> getFoods() {
		return arrayFood;
	}
	
	public String getName() {
		return this.name;
	}
	
	public int getEffectLevel() {
		return effectLevel;
	}
	
	public StatusEffect.type getStatusEffect() {
		return statusEffect;
	}
	
	public int getQuantity() {
		return quantity;
	}
	
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	public void eat() {
		Ui.cls();
		
		Ui.println("You have ate a " + getName());
		Ui.println("You've gained " + effectLevel + " " + statusEffect.toString() + " points.");
		this.quantity--;
		totalEaten++;
		Ui.pause();
		
		switch(this.statusEffect) {
		case HEALTH:
			Health.gain(this.effectLevel);
			break;
		case STRENGTH:
			break;
		case STAMINA:
			break;
		case ACCURACY:
			break;
		case LUCK:
			break;
		}
	}
	
	public void viewAbout() {
		final int BORDER_LENGTH = 39;

        Ui.cls();
        for (int i = 0; i < BORDER_LENGTH; i++) Ui.print("-");//draw line
        Ui.println();
        for (int i = 0; i < ((BORDER_LENGTH / 2) - (this.getName().length() / 2)); i++)
            Ui.print(" ");
        Ui.println(this.getName());
        Ui.println(this.desc);
        Ui.println("Category: " + this.foodType.toString());
        Ui.println("Status effect type: " + this.statusEffect.toString());
        Ui.println("Status effect level: " + this.getEffectLevel());
        for (int i = 0; i < BORDER_LENGTH; i++) Ui.print("-");
        Ui.pause();
        Ui.cls();
        
        this.setViewed(true);
	}
	
	public boolean viewedAbout() {
		return this.viewedAbout;
	}
	
	public void setViewed(boolean v) {
		this.viewedAbout = v;
	}
	
	public enum type {
		MEAT_FISH,
		MEAT_OTHER,
		FRUIT,
		OTHER;
		
		public String toString() {
			String effectString = super.toString();
			effectString = effectString.replace("_", " ");
			effectString = effectString.replace("MEAT", "");
			effectString = effectString.trim();
			return effectString.substring(0, 1) + effectString.substring(1).toLowerCase();
		}
	}

}
