package fr.uge.splendor.game;

import java.io.IOException;

import fr.uge.splendor.token.Token;

public class Terminal {
	private Game game;
	private PossibleActions action;
	private getUserInput input;
	
	public Terminal() {
		game = new Game();
		action = PossibleActions.noAction;
		input = new getUserInput();
	}
	
	public void initialize() throws IOException {
		int inputNbPlayer;
		do {
			System.out.print("Enter the amount of player between 2 and 4:");
			inputNbPlayer = input.getUserInputInt();
		}while(inputNbPlayer < 2 || inputNbPlayer > 4);
		game.init(inputNbPlayer);
	}
	
	private void getAction() throws IOException {
		System.out.println("Possible actions: \n"
				 + "[1: Take 2 same tokens]\n"
				 + "[2: Take 3 diffent tokens]\n"
				 + "[3: Reserve a card from grid]\n"
				 + "[4: Reserve a card from Inventory]\n"
				 + "[5: Buy a card from grid]"
		 		 + "[6: Buy a card from reserved]");
		System.out.print("your action number: ");
		var enter = input.getUserInputInt();
		switch(enter) {
			case 1 -> {action = PossibleActions.takeTwoTokens;}
			case 2 -> {action = PossibleActions.takeTripleSingleTokens;}
			case 3 -> {action = PossibleActions.reserveCardGrid;}
			case 4 -> {action = PossibleActions.reserveCardInventory;}
			case 5 -> {action = PossibleActions.buyCardGrid;}
			case 6 -> {action = PossibleActions.buyCardReserved;}
			default -> {return;}
		}
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
	
	private void takeSameColor() throws IOException {
		Token token = null;
		do{
			System.out.println(game.getTray().getTokens().toString());
			System.out.println("Enter token name: ");
			String enter = input.getUserInputString();
			token = getToken(enter);
		}while(token == null);
		game.takeSameColorToken(token);
	}
	
	private void takeDifferentColor() throws IOException {
		Token token1, token2,  token3;
		String enter;
		do{
			System.out.println(game.getTray().getTokens().toString());
			System.out.println("Enter token 1: ");
			enter = input.getUserInputString();
			token1 = getToken(enter);
			System.out.println("Enter token 2: ");
			enter = input.getUserInputString();
			token2 = getToken(enter);
			System.out.println("Enter token 3: ");
			enter = input.getUserInputString();
			token3 = getToken(enter);
		}while(token1 == null || token2 == null || token3 == null || 
			   token1 == token2 || token1 == token3 || token2 == token3);
		game.takeDifferentToken(token1, token2, token3);
	}
	
	private void reserveCard() throws IOException {
		int level;
		int cardIndex;
		do {
			System.out.println("Enter card level[1-3]: ");
			level = input.getUserInputInt();
			System.out.println("Enter which card[1-4]: ");
			cardIndex = input.getUserInputInt();
		}while(level < 1 || cardIndex < 1 || level > 3 || cardIndex > 4);
		game.reserveCardFromGrid(level, cardIndex);
	}
	
	private void buyCard() throws IOException {
		int level;
		int cardIndex;
		do {
			System.out.println("Enter card level[1-3]: ");
			level = input.getUserInputInt();
			System.out.println("Enter which card[1-4]: ");
			cardIndex = input.getUserInputInt();
		}while(level < 1 || cardIndex < 1 || level > 3 || cardIndex > 4);
	}
	
	private void changePlayer() {
		game.changeNowPlaying();
	}
	
	private void executeAction() throws IOException {
		switch(action) {
		case takeTwoTokens 			-> {takeSameColor();}
		case takeTripleSingleTokens -> {takeDifferentColor();}
		case reserveCardGrid 		-> {reserveCard();}
		case reserveCardInventory 	-> {}
		case buyCardGrid 			-> {buyCard();}
		case buyCardReserved 		-> {}
		default 					-> {return;}
		}
	}
	
	public void run() throws IOException {
		do {
			System.out.println(game.toString());
			getAction();
			executeAction();
			changePlayer();
		}while(game.isWin() == null);
	}
	
	@Override
	public String toString() {
		return game.toString();
	}

}
