package textfighter.main;

import java.util.ArrayList;

import textfighter.item.Armour;
import textfighter.player.Xp;

public class Quest {
	
	private static final ArrayList<Quest> QuestList = new ArrayList<>();
	
	private final ArrayList<Armour> rewardArmor = new ArrayList<>();
	private final ArrayList<Weapon> rewardWeapon = new ArrayList<>();
	
	private String host;
	
	private int coinRewardMin;
	private int coinRewardMax;
	private int xpRewardMin;
	private int xpRewardMax;
	private int healthRewardMin;
	private int healthRewardMax;
	
	private int minLevelReq;
	private boolean completed;
	private boolean available;
	
	public Quest(String host, int coinMin, int coinMax, int xpMin, int xpMax,
			int healthMin, int healthMax, int minLevel, boolean complete, boolean avail) {
		this.host = host;
		this.coinRewardMin = coinMin;
		this.coinRewardMax = coinMax;
		this.xpRewardMin = xpMin;
		this.xpRewardMax = xpMax;
		this.healthRewardMin = healthMin;
		this.healthRewardMax = healthMax;
		this.minLevelReq = minLevel;
		this.completed = complete;
		this.available = avail;
		QuestList.add(this);
	}
	
	public static boolean checkQuestsForNPC(String npcName) {
		boolean check = false;
		int i = 0;
		
		do {
			if (QuestList.get(i).host.equalsIgnoreCase(npcName)) {
				if (QuestList.get(i).getMinLevelReq() <= Xp.getLevel()) {
					if(QuestList.get(i).getAvailable())
						check = !QuestList.get(i).getComplete();
				}
			}
			
			i++;
		} while ((i < QuestList.size()) || (check));
		
		return check;
	}
	
	public int getMinLevelReq() {
		return minLevelReq;
	}
	
	public boolean getComplete() {
		return completed;
	}
	
	public boolean getAvailable() {
		return available;
	}
	

}
