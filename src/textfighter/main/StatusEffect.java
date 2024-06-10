package textfighter.main;

public class StatusEffect {
	
	public enum type {
		HEALTH, STRENGTH, STAMINA, ACCURACY, LUCK;
		
		public String toString() {
			String effectString = super.toString();
			return effectString.substring(0, 1) + effectString.substring(1).toLowerCase();
		}
	}

}
