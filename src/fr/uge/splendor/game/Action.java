package fr.uge.splendor.game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

import fr.umlv.zen5.KeyboardKey;

public class Action {
	private float width;
	private PossibleActions action;
	private int selected;
	private int actionSelection;
	
	public Action(float width, float height) {
		this.width	= width;
		action = PossibleActions.noAction;
	}
	
	public PossibleActions getAction() {
		return action;
	}
	
	private PossibleActions intToEnum(int nb) {
		return switch(nb) {
		case 0 ->	{yield PossibleActions.noAction;}
		case 1 -> 	{yield PossibleActions.takeTwoTokens;}
		case 2 -> 	{yield PossibleActions.takeTripleSingleTokens;}
		case 3 -> 	{yield PossibleActions.reserveCardGrid;}
		case 4 -> 	{yield PossibleActions.reserveCardInventory;}
		case 5 -> 	{yield PossibleActions.buyCardGrid;}
		case 6 -> 	{yield PossibleActions.buyCardReserved;}
		default -> 	{yield null;}
		};
	}
	
	private void changeAction(KeyboardKey key) {
		switch(key) {
			case UP:	if(actionSelection > 1) {
							actionSelection -= 1;
							action = intToEnum(actionSelection);
						}
						break;
			case DOWN:	if(actionSelection < 6) {
							actionSelection += 1;
							action = intToEnum(actionSelection);
						}
						break;
			case SPACE:	selected = actionSelection;
						break;
			default:	break;
		}
	}
	
	public void change(KeyboardKey key) {
		switch(selected) {
			case 0:		changeAction(key);
						break;
			
			default:	break;
		}
	}
	
	private void drawSelection(Graphics2D graphics) {
		switch(action) {
			case takeTwoTokens:				graphics.draw(new Rectangle2D.Float(width-130, 40, 120, 12));
											break;
			case takeTripleSingleTokens:	graphics.draw(new Rectangle2D.Float(width-130, 55, 120, 12));
											break;
			case reserveCardGrid: 			graphics.draw(new Rectangle2D.Float(width-130, 70, 120, 12));
											break;
			case reserveCardInventory: 		graphics.draw(new Rectangle2D.Float(width-130, 85, 120, 12));
											break;
			case buyCardGrid:	 			graphics.draw(new Rectangle2D.Float(width-130, 100, 120, 12));
											break;
			case buyCardReserved: 			graphics.draw(new Rectangle2D.Float(width-130, 115, 120, 12));
											break;
			default:			break;
		}
	}
	
	public void drawInstruction(Graphics2D graphics) {
		graphics.setColor(Color.GRAY);
		graphics.draw(new Rectangle2D.Float(width-130, 0, 120, 200));
		graphics.drawString("USE ARROW", width-120, 15);
		graphics.drawString("SPACE -> select", width-120, 30);
		graphics.drawString("two tokens", width-120, 50);
		graphics.drawString("triple token", width-120, 65);
		graphics.drawString("reserve grid", width-120, 80);
		graphics.drawString("reserve invent", width-120, 95);
		graphics.drawString("buy grid", width-120, 110);
		graphics.drawString("buy reserve", width-120, 125);
		graphics.drawString("Q -> QUIT", width-120, 140);
		drawSelection(graphics);
	}
}
