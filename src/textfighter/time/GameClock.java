package textfighter.time;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class GameClock {
	
	private static LocalDateTime baseTime;
	private static LocalDateTime fastTime;
	private static long startTime;
	private static long endTime;
	private static long increasedTime;
	
	private static double increasePercent = 0.5;
	private static DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("E, MMM dd yyyy-HH:mm:ss");
	private static String gameDate;
	private static String gameTime;
	
	public GameClock() {
		
	}
	
	public static void startTimeClock() {
		startTime = System.currentTimeMillis();
		baseTime = LocalDateTime.now();
	}
	
	public static void endTimeCounter() {
		endTime = System.currentTimeMillis();
	}
	
	public static long timeConversion() {
		increasedTime = endTime - startTime;
		return (long) (increasedTime + (increasedTime * increasePercent));
	}
	
	public static void updateGameTime() {
		endTimeCounter();
		fastTime = baseTime.plus(Duration.ofMillis(timeConversion()));
		splitDateTime(fastTime);
	}
	
	public static void splitDateTime(LocalDateTime dateTime) {
		String[] dateTimeArray = dateTime.format(myFormatObj).split("-");
		gameDate = dateTimeArray[0];
		gameTime = dateTimeArray[1];
	}
	
	public LocalDateTime getBaseTime() {
		return GameClock.baseTime;
	}
	
	public void setBaseTime(LocalDateTime newBaseTime) {
		GameClock.baseTime = newBaseTime;
	}
	
	public LocalDateTime getFastTime() {
		return GameClock.fastTime;
	}
	
	public void setFastTime(LocalDateTime newFastTime) {
		GameClock.fastTime = newFastTime;
	}
	
	public long getStartTime() {
		return GameClock.startTime;
	}
	
	public void setStartTime(long newStartTime) {
		GameClock.startTime = newStartTime;
	}
	
	public long getEndTime() {
		return GameClock.endTime;
	}
	
	public void setEndTime(long newEndTime) {
		GameClock.endTime = newEndTime;
	}
	
	public long getIncreasedTime() {
		return GameClock.increasedTime;
	}
	
	public double getIncreasePercent() {
		return GameClock.increasePercent;
	}
	
	public DateTimeFormatter getDateTimeFormat() {
		return GameClock.myFormatObj;
	}
	
	public static void setGameDate(String date) {
		GameClock.gameDate = date;
	}
	
	public static String getGameDate() {
		return GameClock.gameDate;
	}
	
	public static void setGameTime(String time) {
		GameClock.gameTime = time;
	}
	
	public static String getGameTime() {
		return GameClock.gameTime;
	}

}
