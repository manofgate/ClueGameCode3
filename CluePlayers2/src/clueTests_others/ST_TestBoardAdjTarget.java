package clueTests_others;

import org.junit.Test;

import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Set;

import org.junit.Assert;
import org.junit.BeforeClass;

import clueGame.BadConfigFormatException;
import clueGame.Board;
import clueGame.BoardCell;

public class ST_TestBoardAdjTarget {
	private static Board board;
	
	@BeforeClass
	public static void setUp() throws FileNotFoundException, BadConfigFormatException {
		board = new Board();
	}

	// Ensure that player does not move around within room
	@Test
	public void testAdjacenciesEdge()
	{
		LinkedList<Integer> testList;
		
		// Test a corner
		testList = board.getAdjList(board.calcIndex(0, 0));
		Assert.assertEquals(0, testList.size());
		// Test one that has walkway underneath
		testList = board.getAdjList(board.calcIndex(4, 0));
		Assert.assertEquals(0, testList.size());
		// Test one that has walkway above
		testList = board.getAdjList(board.calcIndex(15, 20));
		Assert.assertEquals(0, testList.size());
		// Test one that is in middle of room
		testList = board.getAdjList(board.calcIndex(18, 11));
		Assert.assertEquals(0, testList.size());
		// Test one beside a door
		testList = board.getAdjList(board.calcIndex(14, 12));
		Assert.assertEquals(0, testList.size());
		// Test one in a corner of room
		testList = board.getAdjList(board.calcIndex(5, 20));
		Assert.assertEquals(0, testList.size());	
	}

	// Ensure that the adjacency list from a doorway is only the
	// walkway. NOTE: This test could be merged with door 
	// direction test. 
	@Test
	public void testAdjacencyDoorway()
	{
		// TEST DOORWAY RIGHT 
		LinkedList<Integer> testList = board.getAdjList(board.calcIndex(6, 9));
		Assert.assertEquals(1, testList.size());
		Assert.assertTrue(testList.contains(board.calcIndex(7, 9)));
		// TEST DOORWAY LEFT
		testList = board.getAdjList(board.calcIndex(4, 7));
		Assert.assertEquals(1, testList.size());
		Assert.assertTrue(testList.contains(board.calcIndex(4, 6)));
		//TEST DOORWAY DOWN
		testList = board.getAdjList(board.calcIndex(6, 15));
		Assert.assertEquals(1, testList.size());
		Assert.assertTrue(testList.contains(board.calcIndex(7, 15)));
		//TEST DOORWAY UP
		testList = board.getAdjList(board.calcIndex(10, 16));
		Assert.assertEquals(1, testList.size());
		Assert.assertTrue(testList.contains(board.calcIndex(9, 16)));
		
	}

	// Test a variety of walkway scenarios
	@Test
	public void testAdjacencyWalkways()
	{
		
		// Test on top edge of board, just one walkway piece
		LinkedList<Integer> testList = board.getAdjList(board.calcIndex(0, 4));
		Assert.assertTrue(testList.contains(5));
		Assert.assertEquals(1, testList.size());
		
		// Test on right edge of board, three walkway pieces
		testList = board.getAdjList(board.calcIndex(8, 20));
		Assert.assertTrue(testList.contains(board.calcIndex(7, 20)));
		Assert.assertTrue(testList.contains(board.calcIndex(9, 20)));
		Assert.assertTrue(testList.contains(board.calcIndex(8, 19)));
		Assert.assertEquals(3, testList.size());

		// Test between two rooms, walkways right and left
		testList = board.getAdjList(board.calcIndex(12, 1));
		Assert.assertTrue(testList.contains(board.calcIndex(12, 0)));
		Assert.assertTrue(testList.contains(board.calcIndex(12, 2)));
		Assert.assertEquals(2, testList.size());

		// Test surrounded by 4 walkways
		testList = board.getAdjList(board.calcIndex(16,7));
		Assert.assertTrue(testList.contains(board.calcIndex(16, 8)));
		Assert.assertTrue(testList.contains(board.calcIndex(16, 6)));
		Assert.assertTrue(testList.contains(board.calcIndex(15, 7)));
		Assert.assertTrue(testList.contains(board.calcIndex(17, 7)));
		Assert.assertEquals(4, testList.size());
		
		// Test on bottom edge of board, next to 1 room piece		
		testList = board.getAdjList(board.calcIndex(24, 6));
		Assert.assertTrue(testList.contains(board.calcIndex(24, 7)));
		Assert.assertTrue(testList.contains(board.calcIndex(23, 6)));
		Assert.assertEquals(2, testList.size());
		
		// Test on ridge edge of board, next to 1 room piece
		testList = board.getAdjList(board.calcIndex(19, 20));
		Assert.assertTrue(testList.contains(board.calcIndex(18, 20)));
		Assert.assertTrue(testList.contains(board.calcIndex(19, 19)));
		Assert.assertEquals(2, testList.size());		
	}

	// Test adjacency at entrance to rooms
	@Test
	public void testAdjacencyRoom()
	{
		
		// Test beside a door direction RIGHT
		LinkedList<Integer> testList = board.getAdjList(board.calcIndex(16, 6));
		Assert.assertTrue(testList.contains(board.calcIndex(16, 7)));
		Assert.assertTrue(testList.contains(board.calcIndex(15, 6)));
		Assert.assertTrue(testList.contains(board.calcIndex(17, 6)));
		Assert.assertTrue(testList.contains(board.calcIndex(16, 5)));
		Assert.assertEquals(4, testList.size());
		
		// Test beside a door direction DOWN
		testList = board.getAdjList(board.calcIndex(12, 2));
		Assert.assertTrue(testList.contains(board.calcIndex(12, 1)));
		Assert.assertTrue(testList.contains(board.calcIndex(12, 3)));
		Assert.assertTrue(testList.contains(board.calcIndex(11, 2)));
		Assert.assertEquals(3, testList.size());
		
		// Test beside a door direction LEFT
		testList = board.getAdjList(board.calcIndex(4, 6));
		Assert.assertTrue(testList.contains(board.calcIndex(5, 6)));
		Assert.assertTrue(testList.contains(board.calcIndex(3, 6)));
		Assert.assertTrue(testList.contains(board.calcIndex(4, 7)));
		Assert.assertTrue(testList.contains(board.calcIndex(4, 5)));
		Assert.assertEquals(4, testList.size());
		
		// Test beside a door direction UP
		testList = board.getAdjList(board.calcIndex(9, 16));
		Assert.assertTrue(testList.contains(board.calcIndex(8, 16)));
		Assert.assertTrue(testList.contains(board.calcIndex(10, 16)));
		Assert.assertTrue(testList.contains(board.calcIndex(9, 17)));
		Assert.assertTrue(testList.contains(board.calcIndex(9, 15)));
		Assert.assertEquals(4, testList.size());
		
	}
	
	// Tests of just walkways, 4 steps
	@Test
	public void testTargetsFourSteps() {
		
		board.calcTargets(board.calcIndex(21, 7), 4);
		
		Set<BoardCell> targets = board.getTargets();
		
		Assert.assertEquals(10, targets.size());
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(17, 7))));
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(19, 7))));
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(18, 6))));
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(20, 6))));
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(20, 5))));
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(24, 6))));
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(23, 7))));
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(19, 5))));
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(22, 6))));
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(19, 8))));

	}
	
}