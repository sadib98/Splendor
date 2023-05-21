package fr.uge.splendor.nobles;

import java.util.Objects;

import fr.uge.splendor.token.Tokens;

/**
 * 
 * @author AHAMMAD Sadib - DAKKAK Inès
 *
 */
public record Noble(int prestige, String name, Tokens bonus) {
	/**
	 * An object with several parameters that allow to play the game.
	 * @param prestige
	 * @param name
	 * @param bonus
	 */
	public Noble{
		Objects.requireNonNull(name);
		Objects.requireNonNull(bonus);
		if(prestige < 0) {
			throw new IllegalArgumentException("prestige < 0");
		}
	}
	
	@Override
	public boolean equals(Object o) {
		var other = (Noble)o;
		if(name.equals(other.name) == false) {
			return false;
		}
		return true;
	}
	
	@Override
	public String toString() {
		var nobleStr = new StringBuffer();
		
		nobleStr.append("[ ").append(prestige).append(" ")
			   .append(name).append(" ")
			   .append(bonus.toString()).append(" ")
			   .append("]");
		
		return nobleStr.toString();
	}
}
