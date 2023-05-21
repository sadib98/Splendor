package fr.uge.splendor.game;


import java.io.IOException;
import java.util.Scanner;

public class getUserInput {
	
	public int getUserInputInt() throws IOException {
		Scanner scan = new Scanner(System.in);
		if(scan.hasNextInt()) {
			return scan.nextInt();
		}
		return -1;
	}
	
	public String getUserInputString() throws IOException {
		Scanner scan = new Scanner(System.in);
		return scan.nextLine();
	}

}