package clueTests;

import static org.junit.Assert.*;

import java.awt.Color;
import java.util.HashSet;
import java.util.Set;

import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.Board;
import clueGame.Card;
import clueGame.Card.CardType;
import clueGame.Player;

public class GameSetupTests {

	private static Board board;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception { board = new Board(); }
	
//	test to confirm loading the player files is correct
	@Test
	public void testLoadingPeople() {
//		check all players!
//		check human player Miss Scarlet
		Player somePlayer = board.getPlayer(0);
		assertTrue(somePlayer.isHuman());
		assertFalse(somePlayer.isComputer());
		assertTrue(somePlayer.name.equals("Miss Scarlet"));
		assertTrue(somePlayer.color.equals(Color.RED));
		assertTrue(somePlayer.indexedLocation == board.calcIndex(13, 22));
		
//		check computer player Mr. Green
		somePlayer = board.getPlayer(1);
		assertTrue(somePlayer.isComputer());
		assertFalse(somePlayer.isHuman());
		assertTrue(somePlayer.name.equals("Mr. Green"));
		assertTrue(somePlayer.color.equals(Color.GREEN));
		assertTrue(somePlayer.indexedLocation == board.calcIndex(21, 6));
		
//		check computer player Mrs. Peacock
		somePlayer = board.getPlayer(2);
		assertTrue(somePlayer.isComputer());
		assertFalse(somePlayer.isHuman());
		assertTrue(somePlayer.name.equals("Mrs. Peacock"));
		assertTrue(somePlayer.color.equals(Color.BLUE));
		assertTrue(somePlayer.indexedLocation == board.calcIndex(0, 4));
		
//		check computer player Colonel Mustard
		somePlayer = board.getPlayer(3);
		assertTrue(somePlayer.isComputer());
		assertFalse(somePlayer.isHuman());
		assertTrue(somePlayer.name.equals("Colonel Mustard"));
		assertTrue(somePlayer.color.equals(Color.YELLOW));
		assertTrue(somePlayer.indexedLocation == board.calcIndex(21, 15));
		
//		check computer player Mrs. White
		somePlayer = board.getPlayer(4);
		assertTrue(somePlayer.isComputer());
		assertFalse(somePlayer.isHuman());
		assertTrue(somePlayer.name.equals("Mrs. White"));
		assertTrue(somePlayer.color.equals(Color.WHITE));
		assertTrue(somePlayer.indexedLocation == board.calcIndex(13, 0));
		
//		check computer player Professor Plum
		somePlayer = board.getPlayer(5);
		assertTrue(somePlayer.isComputer());
		assertFalse(somePlayer.isHuman());
		assertTrue(somePlayer.name.equals("Professor Plum"));
		assertTrue(somePlayer.color.equals(Color.MAGENTA));
		assertTrue(somePlayer.indexedLocation == board.calcIndex(0, 19));
	}
	
//	test to confirm loading the cards are correct
	@Test
	public void testLoadingCards() {
		assertTrue(board.deck.size() == 23);
		int cardsOfTypePerson = 0;
		int cardsOfTypeWeapon = 0;
		int cardsOfTypeRoom = 0;
		
		for (Card someCard : board.deck) {
			if (someCard.type == CardType.PERSON) ++cardsOfTypePerson;
			else if (someCard.type == CardType.WEAPON) ++cardsOfTypeWeapon;
			else if (someCard.type == CardType.ROOM) ++cardsOfTypeRoom;
		}
		
		assertTrue(cardsOfTypePerson == 6);
		assertTrue(cardsOfTypeWeapon == 8);
		assertTrue(cardsOfTypeRoom == 9);
		
		Boolean testBool = false;
		// check for one room
		for (Card someCard : board.deck) {
			if (someCard.name.equalsIgnoreCase("Conservatory")) {
				testBool = true; break;
			}
		}
		assertTrue(testBool);
		
		testBool = false;
		// check for one weapon
		for (Card someCard : board.deck) {
			if (someCard.name.equalsIgnoreCase("Gossip")) {
				testBool = true; break;
			}
		}
		assertTrue(testBool);
		
		testBool = false;
		// check for one person
		for (Card someCard : board.deck) {
			if (someCard.name.equalsIgnoreCase("Miss Scarlet")) {
				testBool = true; break;
			}
		}
		assertTrue(testBool);
	}
	
//	test to confirm dealing the cards work
	@Test
	public void testDealingCards() {
		// ensures all cards are dealt
		assertTrue(board.dealDeck.isEmpty());
		
		// ensures all players have roughly the same amount of cards
		// with 23 cards, each player will have 3 cards, except two people will have 4
		for (Player somePlayer : board.allPlayers) {
			assertTrue(somePlayer.cards.size() >= 3);
		}
		
		// ensure solution has exactly 3 cards,
		// one of each type
		assertTrue(board.solution.size() == 3);
		boolean solutionHasPerson = false;
		boolean solutionHasRoom = false;
		boolean solutionHasWeapon = false;
		for (Card someCard : board.solution) {
			if (someCard.type == CardType.PERSON) solutionHasPerson = true;
			else if (someCard.type == CardType.ROOM) solutionHasRoom = true;
			else if (someCard.type == CardType.WEAPON) solutionHasWeapon = true; 
		}
		assertTrue(solutionHasPerson);
		assertTrue(solutionHasRoom);
		assertTrue(solutionHasWeapon);

		// one card is not given to two different players
		// for each player, add all cards to a hashset
		// also add all solutions set
		// duplicates cannot be added to a hashset
		// so the hashset size should be 23
		Set<Card> cardSetTest = new HashSet<Card>(); 
		for (Player somePlayer : board.allPlayers) {
			cardSetTest.addAll(somePlayer.cards);
		}
		cardSetTest.addAll(board.solution);
		assertTrue(cardSetTest.size() == 23);
		
	}

}
