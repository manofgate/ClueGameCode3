package clueTests;

import static org.junit.Assert.*;

import java.util.LinkedList;
import java.util.Set;

import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.Board;
import clueGame.BoardCell;

public class BoardAdjTargetTests {
	
	private static Board board;
	
	// these tests are specific to my, Peter HK Choi, config files
	@BeforeClass
	public static void setUpBeforeClass() throws Exception { board = new Board(); }

	@Test
	public void orangeAdjWalkwayTests() {
		// first one
		LinkedList<Integer> testList = board.getAdjList(board.calcIndex(0, 4));
		assertEquals(1, testList.size());
		assertTrue(testList.contains(board.calcIndex(0, 5)));
		// second one
		testList = board.getAdjList(board.calcIndex(6, 18));
		assertEquals(3, testList.size());
		assertTrue(testList.contains(board.calcIndex(6, 17)));
		assertTrue(testList.contains(board.calcIndex(6, 19)));
		assertTrue(testList.contains(board.calcIndex(5, 18)));
		// third one
		testList = board.getAdjList(board.calcIndex(15, 7));
		assertEquals(4, testList.size());
		assertTrue(testList.contains(board.calcIndex(14, 7)));
		assertTrue(testList.contains(board.calcIndex(16, 7)));
		assertTrue(testList.contains(board.calcIndex(15, 6)));
		assertTrue(testList.contains(board.calcIndex(15, 8)));
	}
	
	@Test
	public void greenAdjEdgeOfBoardTests() {
		// first one
		LinkedList<Integer> testList = board.getAdjList(board.calcIndex(6, 0));
		assertEquals(3, testList.size());
		assertTrue(testList.contains(board.calcIndex(5, 0)));
		assertTrue(testList.contains(board.calcIndex(7, 0)));
		assertTrue(testList.contains(board.calcIndex(6, 1)));
		// second one
		testList = board.getAdjList(board.calcIndex(0, 18));
		assertEquals(2, testList.size());
		assertTrue(testList.contains(board.calcIndex(0, 19)));
		assertTrue(testList.contains(board.calcIndex(1, 18)));
		// third one
		testList = board.getAdjList(board.calcIndex(21, 6));
		assertEquals(2, testList.size());
		assertTrue(testList.contains(board.calcIndex(21, 7)));
		assertTrue(testList.contains(board.calcIndex(20, 6)));
		// fourth one
		testList = board.getAdjList(board.calcIndex(13, 22));
		assertEquals(2, testList.size());
		assertTrue(testList.contains(board.calcIndex(14, 22)));
		assertTrue(testList.contains(board.calcIndex(13, 21)));
		
	}

	@Test
	public void purpleAdjEdgeOfRoomTests() {
		// first one
		LinkedList<Integer> testList = board.getAdjList(board.calcIndex(19, 7));
		assertEquals(3, testList.size());
		assertTrue(testList.contains(board.calcIndex(20, 7)));
		assertTrue(testList.contains(board.calcIndex(18, 7)));
		assertTrue(testList.contains(board.calcIndex(19, 6)));
		// second one
		testList = board.getAdjList(board.calcIndex(12, 11));
		assertEquals(3, testList.size());
		assertTrue(testList.contains(board.calcIndex(12, 10)));
		assertTrue(testList.contains(board.calcIndex(12, 12)));
		assertTrue(testList.contains(board.calcIndex(13, 11)));
	}
	
	@Test
	public void yellowNextToDoorwayTests() { 
		// first one
		LinkedList<Integer> testList = board.getAdjList(board.calcIndex(5, 8));
		assertEquals(4, testList.size());
		assertTrue(testList.contains(board.calcIndex(5, 9)));
		assertTrue(testList.contains(board.calcIndex(5, 7)));
		assertTrue(testList.contains(board.calcIndex(6, 8)));
		assertTrue(testList.contains(board.calcIndex(4, 8)));
		// second one
		testList = board.getAdjList(board.calcIndex(15, 7));
		assertEquals(4, testList.size());
		assertTrue(testList.contains(board.calcIndex(14, 7)));
		assertTrue(testList.contains(board.calcIndex(16, 7)));
		assertTrue(testList.contains(board.calcIndex(15, 6)));
		assertTrue(testList.contains(board.calcIndex(15, 8)));
	}
	
	@Test 
	public void redTargetTestsSetOne() {
		// first set of target tests use two spots with 1~4 rolls
		// one step
		board.calcTargets(board.calcIndex(14, 0), 1);
		Set<BoardCell> targets = board.getTargets();
		assertEquals(3, targets.size());
		assertTrue(targets.contains(board.getCellAt(board.calcIndex(13, 0))));
		assertTrue(targets.contains(board.getCellAt(board.calcIndex(15, 0))));
		assertTrue(targets.contains(board.getCellAt(board.calcIndex(14, 1))));

		board.calcTargets(board.calcIndex(21, 16), 1);
		targets = board.getTargets();
		assertEquals(3, targets.size());
		assertTrue(targets.contains(board.getCellAt(board.calcIndex(21, 15))));
		assertTrue(targets.contains(board.getCellAt(board.calcIndex(21, 17))));
		assertTrue(targets.contains(board.getCellAt(board.calcIndex(20, 16))));
		
		// two steps
		board.calcTargets(board.calcIndex(14, 0), 2);
		targets = board.getTargets();
		assertEquals(3, targets.size());
		assertTrue(targets.contains(board.getCellAt(board.calcIndex(12, 0))));
		assertTrue(targets.contains(board.getCellAt(board.calcIndex(14, 2))));
		assertTrue(targets.contains(board.getCellAt(board.calcIndex(15, 1))));
		
		board.calcTargets(board.calcIndex(21, 16), 2);
		targets = board.getTargets();
		assertEquals(3, targets.size());
		assertTrue(targets.contains(board.getCellAt(board.calcIndex(20, 15))));
		assertTrue(targets.contains(board.getCellAt(board.calcIndex(20, 17))));
		assertTrue(targets.contains(board.getCellAt(board.calcIndex(19, 16))));
			
		// three steps
		board.calcTargets(board.calcIndex(14, 0), 3);
		targets = board.getTargets();
		assertEquals(4, targets.size());
		assertTrue(targets.contains(board.getCellAt(board.calcIndex(14, 3))));
		assertTrue(targets.contains(board.getCellAt(board.calcIndex(14, 1))));
		assertTrue(targets.contains(board.getCellAt(board.calcIndex(15, 2))));
		assertTrue(targets.contains(board.getCellAt(board.calcIndex(15, 0))));

		board.calcTargets(board.calcIndex(21, 16), 3);
		targets = board.getTargets();
		assertEquals(6, targets.size());
		assertTrue(targets.contains(board.getCellAt(board.calcIndex(18, 16))));
		assertTrue(targets.contains(board.getCellAt(board.calcIndex(20, 16))));
		assertTrue(targets.contains(board.getCellAt(board.calcIndex(19, 15))));
		assertTrue(targets.contains(board.getCellAt(board.calcIndex(19, 17))));
		assertTrue(targets.contains(board.getCellAt(board.calcIndex(21, 15))));
		assertTrue(targets.contains(board.getCellAt(board.calcIndex(21, 17))));
		
		// four steps
		board.calcTargets(board.calcIndex(14, 0), 4);
		targets = board.getTargets();
		assertEquals(4, targets.size());
		assertTrue(targets.contains(board.getCellAt(board.calcIndex(14, 4))));
		assertTrue(targets.contains(board.getCellAt(board.calcIndex(14, 2))));
		assertTrue(targets.contains(board.getCellAt(board.calcIndex(15, 3))));
		assertTrue(targets.contains(board.getCellAt(board.calcIndex(15, 1))));

		board.calcTargets(board.calcIndex(21, 16), 4);
		targets = board.getTargets();
		assertEquals(6, targets.size());
		assertTrue(targets.contains(board.getCellAt(board.calcIndex(17, 16))));
		assertTrue(targets.contains(board.getCellAt(board.calcIndex(18, 15))));
		assertTrue(targets.contains(board.getCellAt(board.calcIndex(18, 17))));
		assertTrue(targets.contains(board.getCellAt(board.calcIndex(19, 16))));
		assertTrue(targets.contains(board.getCellAt(board.calcIndex(20, 15))));
		assertTrue(targets.contains(board.getCellAt(board.calcIndex(20, 17))));
		
	}
	
	@Test
	public void redTargetTestsSetTwo() {
		// cell one
		board.calcTargets(board.calcIndex(4, 12), 5);
		Set<BoardCell> targets = board.getTargets();
		assertEquals(11, targets.size());
		assertTrue(targets.contains(board.getCellAt(board.calcIndex(0, 11))));
		assertTrue(targets.contains(board.getCellAt(board.calcIndex(2, 13))));
		assertTrue(targets.contains(board.getCellAt(board.calcIndex(5, 8))));
		assertTrue(targets.contains(board.getCellAt(board.calcIndex(5, 10))));
		assertTrue(targets.contains(board.getCellAt(board.calcIndex(6, 9))));
		assertTrue(targets.contains(board.getCellAt(board.calcIndex(6, 11))));
		assertTrue(targets.contains(board.getCellAt(board.calcIndex(6, 13))));
		assertTrue(targets.contains(board.getCellAt(board.calcIndex(6, 15))));
		assertTrue(targets.contains(board.getCellAt(board.calcIndex(7, 10))));
		assertTrue(targets.contains(board.getCellAt(board.calcIndex(7, 12))));
		assertTrue(targets.contains(board.getCellAt(board.calcIndex(7, 14))));

		// cell two
		board.calcTargets(board.calcIndex(9, 17), 1);
		targets = board.getTargets();
		assertEquals(1, targets.size());
		assertTrue(targets.contains(board.getCellAt(board.calcIndex(9, 16))));

		// cell three
		board.calcTargets(board.calcIndex(10, 8), 5);
		targets = board.getTargets();
		assertEquals(18, targets.size());
		assertTrue(targets.contains(board.getCellAt(board.calcIndex(5, 8))));
		assertTrue(targets.contains(board.getCellAt(board.calcIndex(6, 7))));
		assertTrue(targets.contains(board.getCellAt(board.calcIndex(6, 9))));
		assertTrue(targets.contains(board.getCellAt(board.calcIndex(7, 8))));
		assertTrue(targets.contains(board.getCellAt(board.calcIndex(7, 10))));
		assertTrue(targets.contains(board.getCellAt(board.calcIndex(8, 6))));
		assertTrue(targets.contains(board.getCellAt(board.calcIndex(8, 7))));
		assertTrue(targets.contains(board.getCellAt(board.calcIndex(9, 8))));
		assertTrue(targets.contains(board.getCellAt(board.calcIndex(10, 7))));
		assertTrue(targets.contains(board.getCellAt(board.calcIndex(11, 8))));
		assertTrue(targets.contains(board.getCellAt(board.calcIndex(12, 6))));
		assertTrue(targets.contains(board.getCellAt(board.calcIndex(12, 7))));
		assertTrue(targets.contains(board.getCellAt(board.calcIndex(12, 9))));
		assertTrue(targets.contains(board.getCellAt(board.calcIndex(12, 11))));
		assertTrue(targets.contains(board.getCellAt(board.calcIndex(13, 8))));
		assertTrue(targets.contains(board.getCellAt(board.calcIndex(13, 10))));
		assertTrue(targets.contains(board.getCellAt(board.calcIndex(14, 7))));
		assertTrue(targets.contains(board.getCellAt(board.calcIndex(15, 8))));

		
		// cell four
		board.calcTargets(board.calcIndex(12, 6), 2);
		targets = board.getTargets();
		assertEquals(3, targets.size());
		assertTrue(targets.contains(board.getCellAt(board.calcIndex(12, 8))));
		assertTrue(targets.contains(board.getCellAt(board.calcIndex(11, 7))));
		assertTrue(targets.contains(board.getCellAt(board.calcIndex(13, 7))));
		
	}
	
}
