package textfighter.main;

public class User {
	
	private static String playerName = "Player";
	
	private static int playerDefault = -1;
	
	public static String name() {
		return playerName;
	}
	
	public static int getPlayerDefault() {
		return playerDefault;
	}
	
	public static void setName(String name) {
		playerName = name;
		playerDefault = 1;
	}
	
	public static void promptNameSelection() {
		Ui.cls();
		Ui.println("Please enter your username.");
		String name = Ui.getValidString();
		
		name = name.trim();
		if (name.equals("")) {
			Ui.println("Name cannot be blank");
			promptNameSelection();
		}
		
		playerName = name;
		playerDefault = 1;
	}

}
