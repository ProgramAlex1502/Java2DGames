package textfighter.main;

import java.util.List;

public class GameUtils {
	
	public static void print(String input) {
		System.out.print(input);
	}
	
	public static void println(String input) {
		print(input + "\n");
	}
	
	public static void showPopup(String header, String subheader, List<?> message, List<?> inputs) {
		Ui.cls();
		println(center(Constants.DASH_DIVIDER));
		if(!header.isEmpty()) {
			println(center(header));
		}
		
		if(!subheader.isEmpty()) {
			println(center(subheader));
			println(center(Constants.STAR_DIVIDER));
		}
		println(center(Constants.EMPTY_SPACE_BOX));
		
		for(Object o : message) {
			println(center(String.valueOf(o)));
		}
		
		println(center(Constants.EMPTY_SPACE_BOX));
		
		for(int i = 0; i < inputs.size(); i++) {
			int input_num = i + 1;
			String input = input_num + "- " + inputs.get(i);
			println(leftAlign(input));
		}
		
		println(center(Constants.DASH_DIVIDER));
	}
	
	public static String center(String s) {
		return center(s, 45, ' ');
	}
	
	public static String leftAlign(String s) {
		return leftAlign(s, 45, ' ');
	}
	
	public static String center(String input, int size, char pad) {
		if (input ==  null || size <= input.length()) 
			return input;
		
		StringBuilder output = new StringBuilder(size);
		output.append("|");
		output.append(String.valueOf(pad).repeat((size - input.length()) / 2));
		output.append(input);
		
		while(output.length() < size) {
			output.append(pad);
		}
		
		output.append("|");
		
		return output.toString();
	}
	
	public static String leftAlign(String input, int size, char pad) {
		if (input == null || size <= input.length()) 
			return input;
		
		StringBuilder output = new StringBuilder(size);
		output.append("|");
		output.append(String.valueOf(pad).repeat(10));
		output.append(input);
		
		while (output.length() < size) {
			output.append(pad);
		}
		
		output.append("|");
		
		return output.toString();
		
	}

}
