package clueGame;

public class Card {
	
	// enum type for Card, null is used for disproveSuggestion
	public enum CardType { PERSON, WEAPON, ROOM, NULL };
	
	// instance variables
	public String name;
	public CardType type;
	
	public Card() {
		this.name = "No suggestion.";
		this.type = CardType.NULL;
	}
	
	// constructor
	public Card(String name, CardType someCardType) {
		this.name = name;
		this.type = someCardType;
	}

}
