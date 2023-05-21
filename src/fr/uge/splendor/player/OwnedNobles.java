package fr.uge.splendor.player;

import java.util.ArrayList;
import java.util.Objects;

import fr.uge.splendor.nobles.Noble;

public class OwnedNobles {
	private ArrayList<Noble> nobles;
	
	public OwnedNobles() {
		nobles = new ArrayList<>();
	}
	
	public boolean add(Noble noble) {
		Objects.requireNonNull(noble);
		if(nobles.contains(noble) == false) {
			nobles.add(noble);
			return true;
		}
		return false;
	}
	
	@Override
	public String toString() {
		var str = new StringBuilder();
		nobles.forEach(elem -> str.append(elem.toString()).append("\n"));
		return str.toString();
	}

}

