package fr.uge.splendor.game;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import fr.uge.splendor.card.Card;

public class GridDisplay {
	private float width;
	private float height;
	private HashMap<Integer, ArrayList<Coordinates>> itemCoord = new HashMap<>();
	
	public GridDisplay(float width, float height) {
		this.width = width;
		this.height = height;
		initCoords();
	}
	
	public void initCoords() {
		var x = 20;
		var tmpX = x;
		var y = 180;
		
		for(var level = 1; level < 4; level++) {
			var tmp = new ArrayList<Coordinates>();
			for(var i = 0; i < 4; i++) {
				var coord = new Coordinates(x, y);
				tmp.add(coord);
				x += 220;
			}
			itemCoord.put(level, tmp);
			y += 105;
			x = tmpX;
		}
	}
	
	public void draw(Graphics2D graphics, Map<Integer, ArrayList<Card>> cards) {
		for(var level = 1; level < 4; level++) {
			for(var i = 0; i < cards.get(level).size(); i++) {
				var elem = itemCoord.get(level).get(i);
				
				graphics.setColor(Color.GRAY);
				graphics.fill(new Rectangle2D.Float(elem.x(), elem.y(), 215, 100));
				
				graphics.setColor(Color.WHITE);
				graphics.drawString("level: "+cards.get(level).get(i).level(), elem.x()+10, elem.y() + 20);
				graphics.drawString(""+cards.get(level).get(i).prestige(), elem.x()+10, elem.y() + 40);
				graphics.drawString("Bonus: "+cards.get(level).get(i).bonus().toString(), elem.x()+10, elem.y() + 60);
				graphics.drawString(""+cards.get(level).get(i).price().toString(), elem.x()+10, elem.y() + 80);
			}
		}
	}
}