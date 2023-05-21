package fr.uge.splendor.player;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import fr.uge.splendor.card.Card;

public class ReservedCards {
	private ArrayList<Card> cards;
	
	public ReservedCards() {
		cards = new ArrayList<>();
	}
	
	public List<Card> getCards(){
		return cards;
	}
	
	public int total() {
		return cards.size();
	}
	
	public void add(Card card) {
		Objects.requireNonNull(card);
		if(total() < 3 && cards.contains(card) == false) {
			cards.add(card);
		}
	}
	
	public Card remove(int index) {
		if(index < 0 || index > 2) {
			throw new IllegalArgumentException("index < 0 || index > 2");
		}
		return cards.remove(index);
	}
	
	@Override
	public String toString() {
		var str = new StringBuilder();
		cards.forEach(elem -> str.append(elem.toString()).append("\n"));
		return str.toString();
	}

}