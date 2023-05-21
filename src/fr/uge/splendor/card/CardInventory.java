package fr.uge.splendor.card;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import fr.uge.splendor.token.Token;
import fr.uge.splendor.token.Tokens;

/**
 * 
 * @author AHAMMAD Sadib - DAKKAK Inès
 *
 */
public class CardInventory {
	public static HashMap<Integer, ArrayList<Card>> allCards;
	
	public CardInventory() {
		allCards = new HashMap<>();
	}
	
	public Map<Integer, ArrayList<Card>> getAllCards(){
		return allCards;
	}
	
	private static Token getToken(String value) {
		return switch(value) {
		case "green" -> {yield Token.GREEN;}
		case "white" -> {yield Token.WHITE;}
		case "blue"  -> {yield Token.BLUE;}
		case "black" -> {yield Token.BLACK;}
		case "red"   -> {yield Token.RED;}
		default      -> {yield null;}
		};
	}
	/**
	 * Creates a new Card from string llist given.
	 * @param values
	 * @return a new Card.
	 */
	private Card createCard(String[] values) {
		var price = new Tokens();
		int level = Integer.parseInt(values[0]);
		int prestige = Integer.parseInt(values[2]);
		var bonus = getToken(values[1]);
		price.add(Token.WHITE, Integer.parseInt(values[3]));
		price.add(Token.BLUE, Integer.parseInt(values[4]));
		price.add(Token.GREEN, Integer.parseInt(values[5]));
		price.add(Token.RED, Integer.parseInt(values[6]));
		price.add(Token.BLACK, Integer.parseInt(values[7]));
		return new Card(level, prestige, bonus, price);
	}
	
	/**
	 * Read a file to create list of cards.
	 * @param path
	 */
	
	public void getCardsFromFile(Path path) {
		try(var reader = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
			String line;
			ArrayList<Card> tmp;
			while((line = reader.readLine()) != null) {
				var card = createCard(line.split(","));
				if(allCards.containsKey(card.level())) {
					tmp = allCards.get(card.level());
				}else {
					tmp = new ArrayList<>();
				}
				tmp.add(card);
				allCards.put(card.level(), tmp);
			}
		} catch (IOException e) {
			System.err.println(e.getMessage()); 
			System.exit(1);
		
		}catch (NumberFormatException e){
			System.err.println(e.getMessage()); 
			System.exit(1);
		}
	}
	
	/**
	 * Picks a random card from list and remove it.
	 * @param level
	 * @return
	 */
	public Card pickRandomCard(int level) {
		if(level < 1 || level > 3) {
			throw new IllegalArgumentException("level < 1 || level > 3");
		}
		if(allCards.get(level).isEmpty()) {
			return null;
		}
		var cardList = allCards.get(level);
		Collections.shuffle(cardList);
		var card = cardList.get(0);
		cardList.remove(0);
		allCards.put(level, cardList);
		return card;
	}
	
	
	@Override
	public String toString() {
		var str = new StringBuffer();
		
		allCards.forEach((level, list) -> {
			list.forEach(elem -> str.append(elem.toString()).append("\n") );
		});
		
		return str.toString();
	}
}

