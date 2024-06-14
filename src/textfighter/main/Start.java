package textfighter.main;

public class Start {
	
	public static void main(String[] args) {
		if (args.length != 0 && args[0].equalsIgnoreCase("nogui")) Ui.guiEnabled = false;
		Ui.println("Loading..");
		
		if (Version.get().contains("DEV")) 
			Debug.enable();
		
		Menu menu = new Menu();
		menu.load();
		
		Ui.cls();
	}

}
