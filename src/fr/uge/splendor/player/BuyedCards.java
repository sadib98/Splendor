package fr.uge.splendor.player;

import java.util.ArrayList;
import java.util.Objects;

import fr.uge.splendor.card.Card;

public class BuyedCards {
	private ArrayList<Card> cards;
	
	public BuyedCards() {
		cards = new ArrayList<>();
	}
	
	public void add(Card card) {
		Objects.requireNonNull(card);
		cards.add(card);
	}
	
	@Override
	public String toString() {
		var str = new StringBuilder();
		cards.forEach(elem -> str.append(elem.toString()).append("\n"));
		return str.toString();
	}

}