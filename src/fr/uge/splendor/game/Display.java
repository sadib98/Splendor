package fr.uge.splendor.game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

import fr.umlv.zen5.ApplicationContext;
import fr.umlv.zen5.KeyboardKey;

public class Display {
	private float width;
	private float height;
	private int startGame;
	private Menu menu;
	private NoblesDisplay nobles;
	private GridDisplay grids;
	private TokensDisplay tokens;
	private PlayerDisplay player;
	private Action action;
	private Game game;
	
	public Display(float width, float height) {
		if(width <= 0 || height <= 0) {
			throw new IllegalArgumentException("width <= 0 || height <= 0");
		}
		this.width	= width;
		this.height = height;
		menu 	= new Menu(width, height);
		game	= new Game();
		nobles	= new NoblesDisplay(width, height);
		grids	= new GridDisplay(width, height);
		tokens	= new TokensDisplay(width, height);
		player	= new PlayerDisplay(width, height);
		action	= new Action(width, height);
	}
	
	private void initialize() {
		game.init(selected()+2);
		nobles.initItems(game.getTotalNobles());
		tokens.initCoord(game.getTray().getTokens().totalTokens());
	}
	
	private int selected() {
		return menu.getSelected();
	}
	
	
	public void change(KeyboardKey key) {
		switch(startGame) {
				case 0 -> 	{	menu.changeSelection(key);
								if(key == KeyboardKey.SPACE) {
									startGame = 1;
									initialize();
								}
							}
					
			case 1 -> 	{	action.change(key);
//							var gridTokens = game.getTray().getTokens().getTokens();
//							tokens.selectToken(x, y, action.getAction(), gridTokens);
						}
					
			default -> {return;}
		}
	}
	
	private void refresh(Graphics2D graphics) {
		graphics.setColor(Color.LIGHT_GRAY);
		graphics.fill(new Rectangle2D.Float(0, 0, width, height));
	}
	
	private void drawElements(Graphics2D graphics) {
		nobles.draw(graphics, game.getTray().getNobles());
		grids.draw(graphics, game.getTray().getGrid());
		tokens.draw(graphics, game.getTray().getTokens().getTokens());
		player.draw(graphics, game.getActualPlayer(), game.getNowPlaying());
		action.drawInstruction(graphics);
	}
	
	public void draw(ApplicationContext context) {
		context.renderFrame(graphics -> {
			refresh(graphics);
			switch(startGame) {
				case 0:	menu.draw(graphics);
						break;
				case 1: refresh(graphics); 
						drawElements(graphics);
						break;
				default: break;
			}
		});
		
	}
}
