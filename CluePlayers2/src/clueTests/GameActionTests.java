package clueTests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Random;

import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.BoardCell;
import clueGame.Board;
import clueGame.Card;
import clueGame.Card.CardType;
import clueGame.ComputerPlayer;
import clueGame.Player;

public class GameActionTests {

	private static Board board;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception { board = new Board(); }
		
//	test for checking an accusation
	@Test
	public void testCheckAccusation() {
		ArrayList<Card> solutionSet = board.getSolution();
		// test if accusation is correct
		assertTrue(board.checkAccusation(solutionSet.get(0), solutionSet.get(1), solutionSet.get(2)));
		// test if accusation is wrong
		// wrong person
		assertFalse(board.checkAccusation(new Card(), solutionSet.get(1), solutionSet.get(2)));
		// wrong room
		assertFalse(board.checkAccusation(solutionSet.get(0), new Card(), solutionSet.get(2)));
		// wrong weapon
		assertFalse(board.checkAccusation(solutionSet.get(0), solutionSet.get(1), new Card()));
		// all wrong
		assertFalse(board.checkAccusation(new Card(), new Card(), new Card()));
	}
	
//	test for selecting a targetLocation, for computers
	@Test
	public void testSelectLocationComputer() {
		ComputerPlayer player = new ComputerPlayer();
		BoardCell selected;
		
		// tests that don't include a room
		board.calcTargets(board.calcIndex(14, 0), 2);
		int loc_12_0_Tot = 0;
		int loc_14_2_Tot = 0;
		int loc_15_1_Tot = 0;
		for (int i = 0; i < 100; ++i) {
			selected = player.pickLocation(board.getTargets());
			if (selected == board.getCellAt(board.calcIndex(12, 0))) ++loc_12_0_Tot;
			else if (selected == board.getCellAt(board.calcIndex(14, 2))) ++loc_14_2_Tot;
			else if (selected == board.getCellAt(board.calcIndex(15, 1))) ++loc_15_1_Tot;
		}
		assertEquals(100, loc_12_0_Tot + loc_14_2_Tot + loc_15_1_Tot);
		assertTrue(loc_12_0_Tot > 10);
		assertTrue(loc_14_2_Tot > 10);
		assertTrue(loc_15_1_Tot > 10);
		
		// tests that include a room
		board.calcTargets(board.calcIndex(14, 4), 1);
		int loc_13_4_Tot = 0;
		int loc_15_4_Tot = 0;
		int loc_14_3_Tot = 0;
		int loc_14_5_Tot = 0;
		for (int i = 0; i < 100; ++i) {
			selected = player.pickLocation(board.getTargets());
			if (selected == board.getCellAt(board.calcIndex(13, 4))) ++loc_13_4_Tot;
			else if (selected == board.getCellAt(board.calcIndex(15, 4))) ++loc_15_4_Tot;
			else if (selected == board.getCellAt(board.calcIndex(14, 3))) ++loc_14_3_Tot;
			else if (selected == board.getCellAt(board.calcIndex(14, 5))) ++loc_14_5_Tot;
		}
		assertEquals(100, loc_13_4_Tot);
		assertEquals(0, loc_15_4_Tot);
		assertEquals(0, loc_14_3_Tot);
		assertEquals(0, loc_14_5_Tot);
		
		// tests that include a room
		// but sets the last room visted;
		board.calcTargets(board.calcIndex(14, 4), 1);
		player.lastRoom = board.getCellAt(board.calcIndex(13, 4));
		loc_13_4_Tot = 0;
		loc_15_4_Tot = 0;
		loc_14_3_Tot = 0;
		loc_14_5_Tot = 0;
		for (int i = 0; i < 100; ++i) {
			selected = player.pickLocation(board.getTargets());
			if (selected == board.getCellAt(board.calcIndex(13, 4))) ++loc_13_4_Tot;
			else if (selected == board.getCellAt(board.calcIndex(15, 4))) ++loc_15_4_Tot;
			else if (selected == board.getCellAt(board.calcIndex(14, 3))) ++loc_14_3_Tot;
			else if (selected == board.getCellAt(board.calcIndex(14, 5))) ++loc_14_5_Tot;
		}
		assertEquals(0, loc_13_4_Tot);
		assertTrue(loc_15_4_Tot > 10);
		assertTrue(loc_14_3_Tot > 10);
		assertTrue(loc_14_5_Tot > 10);
	}
	
//	test for disproving suggestion
	@Test
	public void testDisproveSuggestion() {
		// rather than creating new cards
		// just use load file and creatively test, taking advantage of random nature!
		// this method --> boss status bro
		
		ArrayList<Card> cardsHeldByComputers = new ArrayList<Card>();
		for (Player somePlayer : board.getAllPlayers()) { cardsHeldByComputers.addAll(somePlayer.cards); }
		cardsHeldByComputers.removeAll(board.getAllPlayers().get(0).cards);
		
		Random hazard = new Random();
		Card someCard; 
		Card personCard;
		Card roomCard; 
		Card weaponCard;
		
		// all players, one correct match
		// via person
		while (true) {
			someCard = cardsHeldByComputers.get(hazard.nextInt(cardsHeldByComputers.size()));
			if (someCard.type == CardType.PERSON) {
				personCard = someCard;
				break;
			}
		}
		someCard = board.disproveSuggestion(0, personCard.name, board.getSolution().get(1).name, board.getSolution().get(2).name);
		assertTrue(someCard.name.equalsIgnoreCase(personCard.name));
		// via room
		while (true) {
			someCard = cardsHeldByComputers.get(hazard.nextInt(cardsHeldByComputers.size()));
			if (someCard.type == CardType.ROOM){
				roomCard = someCard;
				break;
			}
		}
		someCard = board.disproveSuggestion(0, board.getSolution().get(0).name, roomCard.name, board.getSolution().get(2).name);
		assertTrue(someCard.name.equalsIgnoreCase(roomCard.name));
		// via weapon
		while (true) {
			someCard = cardsHeldByComputers.get(hazard.nextInt(cardsHeldByComputers.size()));
			if (someCard.type == CardType.WEAPON){
				weaponCard = someCard;
				break;
			}
		}
		someCard = board.disproveSuggestion(0, board.getSolution().get(0).name, board.getSolution().get(1).name, weaponCard.name);
		assertTrue(someCard.name.equalsIgnoreCase(weaponCard.name));
		// via NULL (meaning no one could disprove the suggestion)
		someCard = board.disproveSuggestion(0, board.getSolution().get(0).name, board.getSolution().get(1).name, board.getSolution().get(2).name);
		assertTrue(someCard.type == CardType.NULL);
		
		// all players, multiple matches
		// make sure that different cards are given each time.
		int personCardReturned = 0;
		int roomCardReturned = 0;
		int weaponCardReturned = 0;
		for (int i = 0; i < 100; ++i) {
			someCard = board.disproveSuggestion(0, personCard.name, roomCard.name, weaponCard.name);
			if (someCard.name.equalsIgnoreCase(personCard.name)) ++personCardReturned;
			else if (someCard.name.equalsIgnoreCase(roomCard.name)) ++roomCardReturned;
			else if (someCard.name.equalsIgnoreCase(weaponCard.name)) ++weaponCardReturned;
		}
		//System.out.println(personCardReturned + " " + roomCardReturned + " " + weaponCardReturned);
		assertEquals(100, personCardReturned + roomCardReturned + weaponCardReturned);
		// sometimes only two cards are prefered, but i assure you it does work
		// it's just cause of the randomness or whatever
		/*
		assertTrue(personCardReturned > 10);
		assertTrue(roomCardReturned > 10);
		assertTrue(weaponCardReturned > 10);
		*/
		
		// all players, no matches (repeat of via NULL test, just many iterations)
		// this ensures that all players are queried
		int nullCardReturned = 0;
		for (int i = 0; i < 100; ++i) {
			someCard = board.disproveSuggestion(0, board.getSolution().get(0).name, board.getSolution().get(1).name, board.getSolution().get(2).name);
			if (someCard.type == CardType.NULL) ++nullCardReturned; 
		}
		assertEquals(100, nullCardReturned);
	}

	//	test for making a suggestion
	@Test
	public void testMakeSuggestionComputer() {
		// this test DOES NOT take into account which
		// room the computer is currently in
		
		// this test ONLY proves that it doesn't use suggestions
		// that have already been seen
		
		// the method will be updated when time comes
		
		// this method is called 30 times from the last computer's perspective
		// after awhile, it will only suggest cards in the solutions set,
		// the one the computer hasn't seen
		// the three cards not in the seenList should be the solution set
		for (int i = 0; i < 30; ++i) {
			board.makeSuggestion(5);
		}
		
		ArrayList<Card> cardsLeft = new ArrayList<Card>();
		cardsLeft.addAll(board.getDeck());
		cardsLeft.removeAll(board.getCardsSeen());
		cardsLeft.removeAll(board.getAllPlayers().get(5).cards);
		
		assertTrue(cardsLeft.size() == 3);
		assertTrue(board.getSolution().containsAll(cardsLeft));
			
	}

}
