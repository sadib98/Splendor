package fr.uge.splendor.player;

import java.util.Map;
import java.util.Objects;

import fr.uge.splendor.card.Card;
import fr.uge.splendor.nobles.Noble;
import fr.uge.splendor.token.Token;
import fr.uge.splendor.token.Tokens;

/**
 * 
 * @author AHAMMAD Sadib - DAKKAK Inès
 *
 */
public class Player{
	private int prestige;
	private Tokens tokens;
	private Tokens bonus;
	private BuyedCards buyedCards; 
	private ReservedCards reservedCards;
	private OwnedNobles nobles;
	
	public Player() {
		tokens = new Tokens();
		bonus = new Tokens();
		buyedCards = new BuyedCards();
		reservedCards = new ReservedCards();
		nobles = new OwnedNobles();
	}
	
	public int prestige() {
		return prestige;
	}
	public Tokens tokens() {
		return tokens;
	}
	
	public Tokens bonus() {
		return bonus;
	}
	
	public ReservedCards getReservedCards() {
		return reservedCards;
	}
	
	public void addToken(Token token, int value) {
		Objects.requireNonNull(token);
		if(value < 1)
			throw new IllegalArgumentException("tokenValue < 1");
		tokens.add(token, value);
	}
	
	public void addBonus(Token token) {
		Objects.requireNonNull(token);
		bonus.add(token, 1);
	}
	
	public void addYellow() {
		tokens.add(Token.YELLOW, 1);
	}
	/**
	 * Add a noble to a list and increases prestige.
	 * @param noble
	 */
	public void addNoble(Noble noble) {
		Objects.requireNonNull(noble);
		if(nobles.add(noble))
			prestige += noble.prestige();
	}
	
	public boolean applyYellowToken(Tokens price) {
		Objects.requireNonNull(price);
		if(tokens.contains(Token.YELLOW) == false) {
			return false;
		}else {
			var totalYellow = tokens.getTokens().get(Token.YELLOW);
			var newPrice = price.applyYellow(totalYellow);
			var checkPrice = newPrice.reduceTokens(tokens);
			if(checkPrice.totalTokens() != 0) {
				return false;
			}else {
				// Reduce player Yellows
				var reduceYellow = price.totalTokens() - newPrice.totalTokens();
				tokens.update(Token.YELLOW, -reduceYellow);
			}
		}
		return true;
	}
	
	/**
	 * Add card to the list and add bonus of this card.
	 * @param card
	 */
	public boolean addBuyedCard(Card card) {
		Objects.requireNonNull(card);
		var price = card.price().copy();
		var afterBonus = price.reduceTokens(bonus);
		var checkPrice = afterBonus.reduceTokens(tokens);
		if(checkPrice.totalTokens() == 0) {
			tokens.reduceTokens(afterBonus);
			buyedCards.add(card);
		}else {
			if(applyYellowToken(checkPrice)) {
				tokens.reduceTokens(checkPrice);
				buyedCards.add(card);
			}else {
				return false;
			}
		}
		return true;
	}
	
	public boolean canReserveCard() {
		if(reservedCards.total() < 3)
			return true;
		return false;
	}
	
	/**
	 * Add card to the list.
	 * @param card
	 */
	public void addReservedCard(Card card) {
		Objects.requireNonNull(card);
		if(canReserveCard())
			reservedCards.add(card);;
	}
	
	public Card removeFromReservedCards(int index) {
		if(index < 0 || index > 2) {
			throw new IllegalArgumentException("index < 0 || index > 2");
		}
		return reservedCards.remove(index);
	}
	
	
	@Override
	public String toString() {
		var playerStr = new StringBuffer();
		
		playerStr.append("Prestige: ").append(prestige).append("\n")
		.append("Tokens: ").append(tokens.toString()).append("\n")
		.append("Bonus: ").append(bonus.toString()).append("\n")
		.append("Cards: ").append(buyedCards.toString()).append("\n")
		.append("Reserved: ").append(reservedCards.toString()).append("\n");
		
		return playerStr.toString();
	}
	
	public static void main(String[] args) {
		var cardPrice = new Tokens();
		cardPrice.addTokens(Map.of(Token.BLACK, 2, Token.BLUE, 3, Token.RED, 1));
		var card = new Card(1, 2, Token.BLACK, cardPrice);
		
		System.out.println("Card: " + card.toString() + "\n");
		
		
		System.out.println("Player : Checking...");
		var player = new Player();
		player.addToken(Token.BLACK, 1);
		player.addToken(Token.BLUE, 2);
		
		player.addBonus(Token.RED);
		
		player.addYellow();
		player.addYellow();
		
		player.addBuyedCard(card);
		
		System.out.println(player.toString());
	}
}
