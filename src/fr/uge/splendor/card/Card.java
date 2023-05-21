package fr.uge.splendor.card;

import java.util.Objects;

import fr.uge.splendor.token.Token;
import fr.uge.splendor.token.Tokens;

/**
 * 
 * @author AHAMMAD Sadib - DAKKAK Inès
 *
 */
public record Card(int level, int prestige, Token bonus, Tokens price) {
	/**
	 * An object with several parameters that allow to play the game.
	 * @param level, card level.
	 * @param prestige, an int type that represents the value of prestige it will bring.
	 * @param bonus, Token type, it's a token that will be used to get a reduction for future purchase of card.
	 * @param price, Token type, it's the price that you need to have to have this card.
	 */
	public Card{
		if(level < 1 || level > 4)
			throw new IllegalArgumentException("level < 1 || level > 4");
		if(prestige < 0)
			throw new IllegalArgumentException("prestige < 0");
		Objects.requireNonNull(bonus);
		Objects.requireNonNull(price);
	}

	@Override
	public boolean equals(Object o) {
		Objects.requireNonNull(o);
		return o instanceof Card card
				&& level == card.level
				&& prestige == card.prestige
				&& bonus == card.bonus
				&& price.equals(card.price);
	}
	
	@Override
	public String toString() {
		var cardStr = new StringBuffer();
		
		cardStr.append("[ Level: ").append(level)
			   .append(", Prestige: ").append(prestige)
			   .append(", Bonus: ").append(bonus)
			   .append(", (Price: ").append(price.toString())
			   .append(") ]");
		
		return cardStr.toString();
	}
	
	public static void main(String[] args) {
		
	}
}