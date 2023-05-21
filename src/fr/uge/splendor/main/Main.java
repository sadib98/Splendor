package fr.uge.splendor.main;

import java.awt.Color;
import java.io.IOException;

import fr.uge.splendor.game.Display;
import fr.uge.splendor.game.Terminal;
import fr.uge.splendor.game.getUserInput;
import fr.umlv.zen5.Application;
import fr.umlv.zen5.Event.Action;
import fr.umlv.zen5.KeyboardKey;
import fr.umlv.zen5.ScreenInfo;

/**
 * 
 * @author AHAMMAD Sadib - DAKKAK Inès
 *
 */
public class Main {
	/**
	 * 
	 * @param args argument
	 * @throws IOException raised
	 */
	public static void main(String[] args) throws IOException {
		var input = new getUserInput();
		int choice;
		do {
			System.out.println("1: Graphics mode(Not working perfectly), 2: Terminal mode(Works better)");
			System.out.println("Your choice: ");
			choice = input.getUserInputInt();
		}while(choice < 1 || choice > 2);
		
		if(choice == 1) {
				Application.run(Color.BLACK, context -> {
				ScreenInfo screenInfo = context.getScreenInfo();
				float width = screenInfo.getWidth();
				float height = screenInfo.getHeight();
				var display = new Display(width, height);
				
				while(true) {
					
					display.draw(context);
					
					while(true) {
						var event = context.pollOrWaitEvent(10);
						if(event == null) {
							continue;
						}
						var action = event.getAction();
						KeyboardKey key = null;
						if(action == Action.KEY_PRESSED) {
							key = event.getKey();
							if(key == KeyboardKey.Q) {
								System.out.println("Exit !");
								context.exit(0);
								return;
							}
							display.change(key);
						}
						System.out.println(event);
						
						display.draw(context);
					}
				}
			});
		}else if(choice == 2) {
			var terminal = new Terminal();
			terminal.initialize();
			terminal.run();
		}
		
		
	}

}

