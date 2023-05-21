package fr.uge.splendor.tray;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import fr.uge.splendor.card.Card;
import fr.uge.splendor.card.CardInventory;
import fr.uge.splendor.nobles.Noble;
import fr.uge.splendor.nobles.NobleInventory;
import fr.uge.splendor.token.Token;
import fr.uge.splendor.token.Tokens;

public class Tray {
	private Tokens tokens;
	private ArrayList<Noble> nobles;
	private HashMap<Integer, ArrayList<Card>> grid;
	
	public Tray() {
		tokens = new Tokens();
		nobles = new ArrayList<>();
		grid = new HashMap<>();
	}
	
	public Tokens getTokens() {
		return tokens;
	}
	
	public List<Noble> getNobles(){
		return nobles;
	}
	
	public Map<Integer, ArrayList<Card>> getGrid(){
		return grid;
	}
	
	public void initTokens(int nbPlayer) {
		if(nbPlayer < 2 || nbPlayer > 4) {
			throw new IllegalArgumentException("nbPlayer < 2 || nbPlayer > 4");
		}
		int total = 0;
		switch(nbPlayer) {
		case 2: total = 4; break;
		case 3: total = 5; break;
		case 4: total = 7; break;
		default: break;
		}
		var toAdd = Map.of(Token.GREEN, total, Token.BLUE, total, 
					Token.RED, total, Token.WHITE, total, Token.BLACK, total, Token.YELLOW, 5);
		tokens.addTokens(toAdd);
	}
	
	public void initNobles(int nbPlayer) {
		if(nbPlayer < 2 || nbPlayer > 4) {
			throw new IllegalArgumentException("nbPlayer < 2 || nbPlayer > 4");
		}
		var allNobles = new NobleInventory();
		allNobles.getNoblesFromFile(Path.of("nobles-list.csv"));
		var picked = allNobles.pickRandomNoble(nbPlayer);
		picked.forEach(elem -> nobles.add(elem));
	}
	
	public void initGrid(CardInventory allCards) {
		Objects.requireNonNull(allCards);
		for(var i = 1; i < 4; i++) {
			var tmp = new ArrayList<Card>();
			for(var j = 0; j < 4; j++) {
				tmp.add(allCards.getAllCards().get(i).get(j));
			}
			grid.put(i, tmp);
		}
	}
	
	public void addToken(Token token, int value) {
		Objects.requireNonNull(token);
		tokens.add(token, value);
	}
	
	public boolean removeToken(Token token) {
		Objects.requireNonNull(token);
		if(tokens.contains(token) == false) {
			return false;
		}
		tokens.update(token, -1);
		return true;
	}
	
	public void addNoble(Noble noble) {
		Objects.requireNonNull(noble);
		if(!nobles.contains(noble)) {
			nobles.add(noble);
		}
	}
	
	public boolean removeNoble(Noble noble) {
		Objects.requireNonNull(noble);
		if(nobles.isEmpty() || nobles == null) {
			return false;
		}
		nobles.removeIf(elem -> elem.equals(noble));
		return true;
	}
	
	public void addCard(Card card) {
		Objects.requireNonNull(card);
		var level = card.level();
		if(grid.get(level).size() < 4) {
			grid.get(level).add(card);
		}
	}
	
	public boolean removeCard(Card card) {
		Objects.requireNonNull(card);
		var level = card.level();
		if(grid.get(level).size() > 4) {
			grid.get(level).removeIf(elem -> elem.equals(card));
			return true;
		}
		return false;
	}
	
	@Override
	public String toString() {
		var str = new StringBuffer();
		str.append("Nobles:\n");
		str.append(nobles.toString()).append("\n");
		str.append("GridDisplay:\n");
		grid.forEach((k, v) -> {
			v.forEach(elem -> str.append(elem.toString()).append("\n"));
		});
		str.append("Tokens:\n").append(tokens.toString()).append("\n");
		return str.toString();
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
