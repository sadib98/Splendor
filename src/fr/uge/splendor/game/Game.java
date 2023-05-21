package fr.uge.splendor.game;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Objects;

import fr.uge.splendor.card.CardInventory;
import fr.uge.splendor.player.Player;
import fr.uge.splendor.token.Token;
import fr.uge.splendor.tray.Tray;

public class Game {
	private Tray tray;
	private CardInventory cards;
	private ArrayList<Player> players;
	private int nowPlaying;
	
	public Game() {
		tray = new Tray();
		cards = new CardInventory();
		players = new ArrayList<>();
	}
	
	public void init(int nbPlayer) {
		if(nbPlayer < 2 || nbPlayer > 4) {
			throw new IllegalArgumentException("nbPlayer < 2 || nbPlayer > 4");
		}
		tray.initTokens(nbPlayer);
		tray.initNobles(nbPlayer);
		cards.getCardsFromFile(Path.of("cards-list.csv"));
		tray.initGrid(cards);
		for(var i = 0; i < nbPlayer; i++) {
			players.add(new Player());
		}
	}
	
	public Tray getTray() {
		return tray;
	}
	
	public int getTotalNobles() {
		return tray.getNobles().size();
	}
	
	public Player getActualPlayer() {
		return players.get(nowPlaying);
	}
	
	public int getNowPlaying() {
		return nowPlaying;
	}
	
	public void changeNowPlaying() {
		if(nowPlaying < players.size()-1) {
			nowPlaying++;
		}else {
			nowPlaying = 0;
		}
	}
	
	public Player isWin() {
		for(var elem: players) {
			if(elem.prestige() == 16) {
				return elem;
			}
		}
		return null;
	}
	
	public void takeSameColorToken(Token token) {
		Objects.requireNonNull(token);
		var tokens = tray.getTokens().getTokens();
		if(tokens.containsKey(token) && tokens.get(token) > 2) {
			tray.removeToken(token);
			tray.removeToken(token);
			players.get(nowPlaying).addToken(token, 2);
		}
		
	}
	
	public void takeDifferentToken(Token token1, Token token2, Token token3) {
		Objects.requireNonNull(token1);
		Objects.requireNonNull(token2);
		Objects.requireNonNull(token3);
		var tokens = tray.getTokens().getTokens();
		if(tokens.containsKey(token1) && tokens.get(token1) > 1 &&
		   tokens.containsKey(token2) && tokens.get(token2) > 1 &&
		   tokens.containsKey(token3) && tokens.get(token3) > 1) {
			tray.removeToken(token1);
			tray.removeToken(token2);
			tray.removeToken(token3);
			players.get(nowPlaying).addToken(token1, 1);
			players.get(nowPlaying).addToken(token2, 1);
			players.get(nowPlaying).addToken(token3, 1);
		}
	}
	
	public void reserveCardFromGrid(int level, int index) {
		var card = tray.getGrid().get(level).get(index-1);
		var newCard = cards.pickRandomCard(level);
		tray.removeCard(card);
		players.get(nowPlaying).addReservedCard(card);
		tray.addCard(newCard);
	}
	
	public void buyCardFromGrid(int level, int index) {
		var card = tray.getGrid().get(level).get(index-1);
		var newCard = cards.pickRandomCard(level);
		if(players.get(nowPlaying).addBuyedCard(card)) {
			tray.removeCard(card);
			tray.addCard(newCard);
		}
	}
	
	@Override
	public String toString() {
		var str = new StringBuffer();
		str.append(tray.toString());
		str.append(players.get(nowPlaying).toString());
		return str.toString();
	}
	
	public static void main(String[] args) {
		var game = new Game();
		game.init(2);
		
		System.out.println(game.toString());
	}

}
