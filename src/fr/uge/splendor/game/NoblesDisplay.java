package fr.uge.splendor.game;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

import fr.uge.splendor.nobles.Noble;

public class NoblesDisplay {
	private float width;
	private float height;
	private ArrayList<Coordinates> itemCoord = new ArrayList<>();
	
	public NoblesDisplay(float width, float height) {
		this.width = width;
		this.height = height;
	}
	
	public void initItems(int total) {
		var x = 20;
		var y = 50;
		
		for(var i = 0; i < total; i++) {
			var coord = new Coordinates(x, y);
			itemCoord.add(coord);
			x += 175;
		}
	}
	
	public void draw(Graphics2D graphics, List<Noble> nobles) {
		for(var i = 0; i < nobles.size(); i++) {
			var elem = itemCoord.get(i);
			graphics.setColor(Color.DARK_GRAY);
			graphics.fill(new Rectangle2D.Float(elem.x(), elem.y(), 170, 100));
			
			graphics.setColor(Color.WHITE);
			graphics.drawString(""+nobles.get(i).prestige(), elem.x()+10, elem.y() + 20);
			graphics.drawString(""+nobles.get(i).name(), elem.x()+10, elem.y() + 50);
			graphics.drawString(nobles.get(i).bonus().toString(), elem.x()+10, elem.y() + 90);
		}
	}
}
