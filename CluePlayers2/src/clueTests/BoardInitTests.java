package clueTests;

import static org.junit.Assert.*;

import java.util.Map;

import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.Board;
import clueGame.BoardCell;
import clueGame.RoomCell;

public class BoardInitTests {
	
	private static Board board;
	// these numbers are specific to my config files
	public static final int NUM_ROOMS = 11;
	public static final int NUM_ROWS = 22;
	public static final int NUM_COLUMNS = 23;
	public static final int NUM_ROOM_CELLS = NUM_ROWS * NUM_COLUMNS;
	public static final int NUM_DOORWAYS = 17;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception { board = new Board(); }

	// these tests are specific to my config files
	@Test
	public void roomConfigFileLoadedCorrectly() {
		@SuppressWarnings("static-access")
		Map<Character, String> rooms = board.getRooms();
		assertEquals(NUM_ROOMS, rooms.size());
		assertEquals("Conservatory", 	rooms.get('C'));
		assertEquals("Kitchen", 		rooms.get('K'));
		assertEquals("Ballroom", 		rooms.get('B'));
		assertEquals("Billiard Room", 	rooms.get('R'));
		assertEquals("Library", 		rooms.get('L'));
		assertEquals("Study",			rooms.get('S'));
		assertEquals("Dining Room", 	rooms.get('D'));
		assertEquals("Lounge",			rooms.get('O'));
		assertEquals("Hall", 			rooms.get('H'));
		assertEquals("Closet", 			rooms.get('X'));
		assertEquals("Walkway", 		rooms.get('W'));
	}
	
	@Test
	public void boardDimensionsAreCorrect() {
		assertEquals(NUM_ROWS, 		board.getNumRows());
		assertEquals(NUM_COLUMNS, 	board.getNumColumns());
	}
	
	@Test
	public void doorDirectionsLoadedCorrectly() {
		RoomCell room;
		// begin RIGHT
		room = board.getRoomCellAt(4, 3);
		assertTrue(room.isDoorway());
		assertEquals(RoomCell.DoorDirection.RIGHT, room.getDoorDirection());
		// begin LEFT
		room = board.getRoomCellAt(9, 17);
		assertTrue(room.isDoorway());
		assertEquals(RoomCell.DoorDirection.LEFT, room.getDoorDirection());
		// being UP
		room = board.getRoomCellAt(14, 11);
		assertTrue(room.isDoorway());
		assertEquals(RoomCell.DoorDirection.UP, room.getDoorDirection());
		// being DOWN
		room = board.getRoomCellAt(5, 15);
		assertTrue(room.isDoorway());
		assertEquals(RoomCell.DoorDirection.DOWN, room.getDoorDirection());
		// testing roomCell but not doorway
		room = board.getRoomCellAt(10, 10);
		assertFalse(room.isDoorway());
		// testing walkway
		BoardCell cell = board.getCellAt(board.calcIndex(6, 8));
		assertFalse(cell.isDoorway());
	}
	
	@Test
	public void correctNumberOfDoorways() {
		int doorways = 0;
		int totalCells = board.getNumRows() * board.getNumColumns();
		assertEquals(totalCells, NUM_ROOM_CELLS);
		for (int i = 0; i < totalCells; ++i) {
			BoardCell cell = board.getCellAt(i);
			if (cell.isDoorway()) ++doorways;
		}
		assertEquals(NUM_DOORWAYS, doorways);
	}
	
	@Test
	public void indexIsCalculatingCorrectly() {
		// top left, top right, bottom left, bottom right 
		assertEquals(0, board.calcIndex(0, 0));
		assertEquals(NUM_COLUMNS - 1, board.calcIndex(0, NUM_COLUMNS - 1));
		assertEquals(483, board.calcIndex(NUM_ROWS - 1, 0));
		assertEquals(505, board.calcIndex(NUM_ROWS - 1, NUM_COLUMNS - 1));
		// 2 points from CR test
		assertEquals(24, board.calcIndex(1, 1));
		assertEquals(66, board.calcIndex(2, 20));
		// 2 points
		assertEquals(167, board.calcIndex(7, 6));
		assertEquals(339, board.calcIndex(14, 17));
	}

	@Test
	public void roomInitialsLoadedCorrectly() {
		assertEquals('C', board.getRoomCellAt(0, 0).getInitial());
		assertEquals('K', board.getRoomCellAt(19, 1).getInitial());
		assertEquals('B', board.getRoomCellAt(9, 3).getInitial());
		assertEquals('R', board.getRoomCellAt(2, 10).getInitial());
		assertEquals('L', board.getRoomCellAt(2, 15).getInitial());
		assertEquals('S', board.getRoomCellAt(3, 21).getInitial());
		assertEquals('D', board.getRoomCellAt(18, 11).getInitial());
		assertEquals('O', board.getRoomCellAt(18, 20).getInitial());
		assertEquals('H', board.getRoomCellAt(10, 20).getInitial());	
	}
	
}
