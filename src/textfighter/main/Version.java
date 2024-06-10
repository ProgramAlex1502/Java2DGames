package textfighter.main;

class Version {
	
	private static final String VERSION = "4.9DEV";
	private static final String STAGE = "Alpha";
	private static final String DESC = ""
			+ "Text-Fighter is a Text-Based\n"
            + "Fighter RPG game, completely\n"
            + "written in Java.\n\n"
            + "Text-Fighter is currently in Alpha stage\n"
            + "which means it's still in early development,\n"
            + "and will contain lots of bugs and missing features.";
	private static final String CHANGE_LOG = ""
			+ "(Not compatible with previous saves)\n\n"

            + "New Stuff:\n"
            + "- Added new casino game: Lottery\n"
            + "- Added new casino game: Blackjack\n"
            + "-\n"
            + "-\n"
            + "-\n\n"

            + "Bug Fixes:\n"
            + "-\n"
            + " ";
	
	private Version() {}
	
	public static String get() {
		return VERSION;
	}
	
	public static String getStage() {
		return STAGE;
	}
	
	public static String getFull() {
		return STAGE + " " + VERSION;
	}
	
	public static String getDesc() {
		return DESC;
	}
	
	public static String getChange() {
		return CHANGE_LOG;
	}
	
}
