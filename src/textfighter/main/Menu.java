package textfighter.main;

import static java.util.Arrays.asList;

public class Menu {
	
	public void load() {
		while (true) {
			GameUtils.showPopup(Constants.WELCOME_HEADER,
					Constants.SUB_HEADER, 
					asList("To get started, Type in a number below", "and press enter."), 
					asList("Start Game", "About Game"));
			
			switch (Ui.getValidInt()) {
			case 1:
				Ui.guiEnabled = false;
				new Game().start();
				
				if (User.getPlayerDefault() > 0 && Game.hadGameStarted()) {
					Saves.save(false);
				}
				break;
			case 2:
				About.view(false);
				break;
			case 3: 
				return;
			default:
				break;
			}
		}
	}

}
