package clueGame;

import java.awt.Color;
import java.awt.Graphics;
import java.lang.reflect.Field;
import java.util.ArrayList;

public abstract class Player {
	
	private static final int length = 23;
	
//	instance variables
	public String name;
	public Color color;
	public ArrayList<Card> cards;
	public int indexedLocation;
	
	public Player(String name, String color, int startingIndexedLocation) {
		this.name = name;
		this.color = convertColor(color);
		indexedLocation = startingIndexedLocation;
		cards = new ArrayList<Card>();
	}
	public Color convertColor(String strColor) {
		Color color;
		try {
			Field field = Class.forName("java.awt.Color").getField(strColor.trim());
			color = (Color) field.get(null);
		}
		catch (Exception e) {
			color = null;
		}
		return color;
	}
	
	public boolean isHuman() { return false; }
	public boolean isComputer() { return false; }
	
//	disprove suggestion
	public Card disproveSuggestion(String cardName) {
		for (Card someCard : cards) {
			if (someCard.name.equalsIgnoreCase(cardName)) {
				return someCard;
			}
		}
		return new Card();
	}
	
	// Helpers for test purposes
	public void giveCard(Card card) {
		//Add card to players card array
		if(!cards.contains(card)) cards.add(card);
	}
	public boolean hasCard(Card card) {
		if (cards.contains(card)) return true;
		else return false;	
	}
	
	public ArrayList<Card> getCards() {
		return cards;
	}
	public void draw(Graphics g, Board board) {
		int[] columnAndRow = board.calcColumnAndRow(indexedLocation);
		g.setColor(color);
		g.fillOval(columnAndRow[0] * length + 1, columnAndRow[1] * length + 1, length - 2, length - 2);
		g.setColor(Color.BLACK);
		g.drawOval(columnAndRow[0] * length + 1, columnAndRow[1] * length + 1, length - 2, length - 2);
	}
	
}
