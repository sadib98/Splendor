package fr.uge.splendor.nobles;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import fr.uge.splendor.token.Token;
import fr.uge.splendor.token.Tokens;

/**
 * 
 * @author AHAMMAD Sadib - DAKKAK Inès
 *
 */
public class NobleInventory {
	private ArrayList<Noble> allNobles;
	
	public NobleInventory() {
		allNobles = new ArrayList<>();
	}
	
	public List<Noble> getNobles(){
		return allNobles;
	}
	
	private Noble createNoble(String[] values) {
		var bonus = new Tokens();
		var prestige = Integer.parseInt(values[0]);
		var name = values[1];
		bonus.add(Token.WHITE, Integer.parseInt(values[2]));
		bonus.add(Token.BLUE, Integer.parseInt(values[3]));
		bonus.add(Token.GREEN, Integer.parseInt(values[4]));
		bonus.add(Token.RED, Integer.parseInt(values[5]));
		bonus.add(Token.BLACK, Integer.parseInt(values[6]));
		return new Noble(prestige, name, bonus);
	}
	
	
	public void getNoblesFromFile(Path path) {
		try(var reader = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
			String line;
			while((line = reader.readLine()) != null) {
				var noble = createNoble(line.split(","));
				allNobles.add(noble);
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
	 * Picks some random Noble from list by nbPlayer and returns the selectedNobles.
	 * @param nbPlayer
	 * @return
	 */
	public List<Noble> pickRandomNoble(int nbPlayer) {
		var revealedNobles = new ArrayList<Noble>();
		Collections.shuffle(allNobles);
		for(var i = 0; i < nbPlayer+1; i++) {
			revealedNobles.add(allNobles.get(i));
		}
		return revealedNobles;
	}
	
	@Override
	public String toString() {
		var str = new StringBuffer();
		
		allNobles.forEach(noble -> str.append(noble.toString()).append("\n"));
		
		return str.toString();
	}
}

