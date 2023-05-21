package fr.uge.splendor.game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

import java.util.ArrayList;

import fr.umlv.zen5.KeyboardKey;

public class Menu {
	private float width;
	private float height;
	private int selected;
	private ArrayList<String> items = new ArrayList<>();
	private ArrayList<Coordinates> itemCoord = new ArrayList<>();
	
	public Menu(float width, float height) {
		this.width = width;
		this.height = height;
		initItems();
	}
	
	public int getSelected() {
		return selected;
	}
	
	public void initItems() {
		var y = height / 5;
		for(var i = 0; i < 3; i++) {
			var coord = new Coordinates(width / 3 + 100, y);
			itemCoord.add(coord);
			y += 60;
		}
		items.add("2 PLAYERS");
		items.add("3 PLAYERS");
		items.add("4 PLAYERS");
	}
	
	public void changeSelection(KeyboardKey key) {
		switch(key) {
			case UP:	if(selected > 0) {
							selected -= 1;
					 	}
					 	break;
			case DOWN: 	if(selected < 2) {
							selected += 1;
						}
						break;
			default:	break;
		}
	}
	
	public void draw(Graphics2D graphics) {
		
		graphics.setColor(Color.BLACK);
		graphics.drawString("Use ARROWS -> navigate, SPACE -> SELECT, Q -> QUIT", 400, 200);
		
		for(var i = 0; i < 3; i++) {
			var elem = itemCoord.get(i);
			graphics.setColor(Color.BLACK);
			graphics.fill(new Rectangle2D.Float(elem.x(), elem.y(), 200, 50));
			
			graphics.setColor(Color.WHITE);
			graphics.drawString(items.get(i), elem.x() + 75, elem.y() + 30);
			
			if(i == selected) {
				graphics.setColor(Color.RED);
				graphics.fill(new Rectangle2D.Float(elem.x() - 15, elem.y(), 10, 50));
				graphics.fill(new Rectangle2D.Float(elem.x() + 205, elem.y(), 10, 50));
			}
		}
	}
}