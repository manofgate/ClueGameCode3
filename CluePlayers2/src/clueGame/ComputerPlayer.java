package clueGame;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

import clueGame.Card.CardType;

public class ComputerPlayer extends Player {
	private boolean noDisprove;
	private ArrayList<Card> sugAcusation = new ArrayList<Card>();
	
	public ComputerPlayer() {
		super("Captain Cadaver", "black", 0);
		noDisprove = false;
	}
	
	public ComputerPlayer(String name, String color, int startingIndexedLocation) {
		super(name, color, startingIndexedLocation);
		noDisprove = false;
	}
	
	@Override
	public boolean isComputer() { return true; }

	public BoardCell lastRoom;

	public BoardCell pickLocation(HashSet<BoardCell> targets) {
		Random numGenerator = new Random();
		int targetChoice;
		BoardCell[] targetArray;
		// if in range of room, go to room
		// unless been in that room recently
		// otherwise, pick a totally random location out of the list
		
		ArrayList<String> seenRooms = new ArrayList<String>();
		for (Card someCard : Board.getCardsSeen()) { 
			if (someCard.type == CardType.ROOM) {
				seenRooms.add(someCard.name);
			}
		}
		
		for (BoardCell target : targets) {
			if (target.isDoorway()) {
				RoomCell roomCell = (RoomCell) target;
				boolean haveSeen = false;
				for (String roomName : seenRooms) {
					if (roomName.equalsIgnoreCase(Board.getRooms().get(roomCell.getInitial()))) {
						haveSeen = true;
						break;
					}
				}
				if(!roomCell.equals(lastRoom) && !haveSeen)
					return target;				
			}
		}
		
		targets.remove(lastRoom);
		
		targetChoice = numGenerator.nextInt(targets.size());
		targetArray = targets.toArray(new BoardCell[targets.size()]);
		
		return targetArray[targetChoice];
	}

	public void setNoDisprove(boolean noDisprove) {
		this.noDisprove = noDisprove;
	}

	public boolean isNoDisprove() {
		return noDisprove;
	}
	public ArrayList<Card> getSugAcusation() {
		return sugAcusation;
	}

	public void setSugAcusation(ArrayList<Card> sugAcusation) {
		this.sugAcusation = sugAcusation;
	}

}
