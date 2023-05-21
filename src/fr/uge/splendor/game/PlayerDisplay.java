package fr.uge.splendor.game;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.util.List;

import fr.uge.splendor.card.Card;
import fr.uge.splendor.player.Player;

public class PlayerDisplay {
	private float width;
	private float height;
	
	public PlayerDisplay(float width, float height) {
		this.width = width;
		this.height = height;
	}
	
	private void drawReservedCards(Graphics2D graphics, List<Card> cards) {
		var x = 20;
		var y = 660;
		for(var elem: cards) {
			graphics.setColor(Color.GRAY);
			graphics.fill(new Rectangle2D.Float(x, y, 215, 100));
			
			graphics.setColor(Color.WHITE);
			graphics.drawString("level: "+elem.level(), x+10, y + 20);
			graphics.drawString(""+elem.prestige(), x+10, y + 40);
			graphics.drawString("Bonus: "+elem.bonus().toString(), x+10, y + 60);
			graphics.drawString(""+elem.price().toString(), x+10, y + 80);
			x += 220;
		}
	}
	
	public void draw(Graphics2D graphics, Player player, int nowPlayig) {
		graphics.setColor(Color.BLACK);
		graphics.drawString("Now playing: " + (nowPlayig + 1), 20, 600);
		graphics.drawString("Prestige: " + player.prestige(), 20, 620);
		graphics.drawString("Bonus: " + player.bonus().toString(), 20, 640);
		drawReservedCards(graphics, player.getReservedCards().getCards());
	}
}
