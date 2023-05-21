package fr.uge.splendor.game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Map;

import fr.uge.splendor.token.Token;

public class TokensDisplay {
	private float width;
	private float height;
	private ArrayList<Coordinates> itemCoord = new ArrayList<>();
	private ArrayList<Token> selectedTokens;
	
	public TokensDisplay(float width, float height) {
		this.width = width;
		this.height = height;
		selectedTokens = new ArrayList<>();
	}
	
	public void initCoord(int total) {
		var x = 20;
		var y = 520;
		
		for(var i = 0; i < total; i++) {
			var coord = new Coordinates(x, y);
			itemCoord.add(coord);
			x += 55;
		}
	}
	
	
	private Integer getIndexWithClick(float x, float y) {
		for(var i = 0; i < itemCoord.size(); i++) {
			var x1 = itemCoord.get(i).x();
			var y1 = itemCoord.get(i).y();
			var x2 = x1 + 50;
			var y2 = y1 + 50;
			if(x1 <= x && x <= x2 &&
			   y1 <= y && y <= y2) {
				return i;
			}
		}
		return null;
	}
	
	public void selectToken(float x, float y, PossibleActions action, Map<Token, Integer> gridTokens) {
//		var index = getIndexWithClick(x, y);
//		if(index != null) {
//			switch(action) {
//			case takeTwoTokens -> {
//				if(selectedTokens.isEmpty()) {
//					selectedTokens.add(gridTokens.get(index));
//					selectedTokens.add(gridTokens.get(index));
//				}
//			}
//			
//			case takeTripleSingleTokens -> {
//				if(selectedTokens.size() < 3) {
//					if(!selectedTokens.contains(gridTokens.get(index))) {
//						selectedTokens.add(gridTokens.get(index));
//					}
//				}
//			}
//			
//			default -> {return;}
//			}
//		}
	}
	
	private Color getTokenColor(Token token) {
		return switch(token) {
		case YELLOW -> {yield Color.YELLOW;}
		case GREEN -> {yield Color.GREEN;}
		case BLUE -> {yield Color.BLUE;}
		case RED -> {yield Color.RED;}
		case WHITE -> {yield Color.WHITE;}
		case BLACK -> {yield Color.BLACK;}
		default -> {yield null;}
		};
	}
	
	public void draw(Graphics2D graphics, Map<Token, Integer> tokens) {
		var i = 0;
		for(var token: tokens.entrySet()) {
			var color = token.getKey();
			var value = token.getValue();
			
			var elem = itemCoord.get(i);
			
			graphics.setColor(getTokenColor(color));
			graphics.fill(new Ellipse2D.Float(elem.x(), elem.y(), 50, 50));
			
			graphics.setColor(Color.BLACK);
			graphics.drawString(""+value, elem.x()+20, elem.y() - 5);
			if(tokens.containsKey(color)) {
				graphics.setColor(Color.RED);
				graphics.draw(new Rectangle2D.Float(elem.x(), elem.y(), 50, 50));
			}
			i++;
		}
	}
}