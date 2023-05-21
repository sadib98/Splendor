package fr.uge.splendor.token;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Tokens {
	private HashMap<Token, Integer> tokens;
	private int totalTokens;
	
	
	public Tokens() {
		tokens = new HashMap<>();
	}
	
	public Map<Token, Integer> getTokens(){
		return tokens;
	}
	
	public int totalTokens() {
		return totalTokens;
	}
	
	public Tokens copy() {
		var newTokens = new Tokens();
		newTokens.addTokens(tokens);
		return newTokens;
	}
	
	public void add(Token token, int value) {
		Objects.requireNonNull(token);
		if(value > 0) {
			if(tokens.putIfAbsent(token, value) != null) {
				tokens.put(token, tokens.get(token) + value);
			}
			totalTokens += value;
		}
	}
	
	public void addTokens(Map<Token, Integer> tokensToAdd) {
		Objects.requireNonNull(tokensToAdd);
		tokensToAdd.forEach((key, value) -> add(key, value));
	}
	
	public void delete(Token toRemove) {
		Objects.requireNonNull(toRemove);
		if(tokens.containsKey(toRemove)) {
			totalTokens -= tokens.get(toRemove);
			tokens.remove(toRemove);
		}
	}
	
	public void update(Token tokenToRemove, int amount) {
		Objects.requireNonNull(tokenToRemove);
		if(tokens.containsKey(tokenToRemove)) {
			var newValue = tokens.get(tokenToRemove) + amount;
			if(newValue <= 0) {
				delete(tokenToRemove);
			}else {
				totalTokens += amount;
				tokens.put(tokenToRemove, tokens.get(tokenToRemove) + amount);
			}
		}
	}
	
	public Tokens reduceTokens(Tokens applyTokens) {
		Objects.requireNonNull(applyTokens);
		applyTokens.getTokens().forEach((key, value) -> update(key, -value));
		var newTokens = new Tokens();
		newTokens.addTokens(tokens);
		return newTokens;
	}
	
	public boolean isEmpty() {
		if(tokens.isEmpty()) {
			return true;
		}
		return false;
	}
	
	public boolean contains(Token token) {
		Objects.requireNonNull(token);
		if(tokens.containsKey(token)) {
			return true;
		}
		return false;
	}
	
	public Tokens applyYellow(int amount) {
		var temp = tokens;
		var iterator = temp.entrySet().iterator();
		while((amount != 0)  && (iterator.hasNext()) ) {
			var elem = iterator.next();
			var value = elem.getValue() - amount;
			if(value < 0) {
				iterator.remove();
				amount = amount - elem.getValue();
			}else if(value == 0) {
				iterator.remove();
				amount = 0;
			}else {
				temp.put(elem.getKey(), elem.getValue() - amount);
				amount = 0;
			}
		}
		var newTokens = new Tokens();
		newTokens.addTokens(temp);
		return newTokens;
	}
	
	@Override
	public boolean equals(Object o) {
		var other = (Tokens)o;
		for(var elem: tokens.entrySet()) {
			var value = elem.getValue();
			var key = elem.getKey();
			if(value != other.getTokens().get(key)) {
				return false;
			}
		}
		return true;
	}
	
	@Override
	public String toString() {
		var tokensStr = new StringBuffer();
		tokens.forEach((key, value) -> tokensStr.append(key).append(":").append(value).append(" "));
//		tokensStr.append(" Total: ").append(totalTokens);
		return tokensStr.toString();
	}
	
	
	public static void main(String[] args) {
//		var tokens = new Tokens();
//		
//		System.out.println(tokens.toString());
//		
//		
//		var newTokens = tokens.applyYellow(4);
//		System.out.println(newTokens.toString());
		
//		var price1 = new Tokens();
//		price1.add(Token.BLACK, 1);
//		price1.add(Token.BLUE, 3);
//		
//		var price2 = new Tokens();
//		price2.add(Token.BLACK, 2);
//		price2.add(Token.BLUE, 3);
//		
//		System.out.println(price1.equals(price2));
	}	
}
