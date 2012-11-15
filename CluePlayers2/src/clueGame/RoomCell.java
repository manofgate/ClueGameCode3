package clueGame;

import java.awt.Color;
import java.awt.Graphics;

public class RoomCell extends BoardCell {

	public enum DoorDirection { UP, DOWN, LEFT, RIGHT, NONE, NAME }

	DoorDirection doorDirection;
	char initial;
	
	public RoomCell(int row, int column, String token) {
		this.row = row;
		this.column = column;
		this.initial = token.charAt(0);
		this.doorDirection = findDoorDirection(token);
	}

	public DoorDirection findDoorDirection(String direction) {
		if (direction.length() == 1) {
			return DoorDirection.NONE;
		} 
		else {
			switch (direction.charAt(1)){
				case 'U':
					return DoorDirection.UP;
				case 'D':
					return DoorDirection.DOWN;
				case 'L':
					return DoorDirection.LEFT;
				case 'R':
					return DoorDirection.RIGHT;
				case 'N':
					return DoorDirection.NAME;
				default :
					return DoorDirection.NONE;
			}
		}
	}

	public boolean isRoom() { return true; }

	public DoorDirection getDoorDirection() { return doorDirection; }

	public char getInitial() { return initial; }

	public boolean isDoorway() {
		if (this.doorDirection != DoorDirection.NONE) {
			return true;
		}
		return false;
	}
	
	@Override
	public void draw(Graphics g, Board board){
		g.setColor(Color.gray);
		g.fillRect(column*length, row*length, length, length);
		if(this.isDoorway()){
			g.setColor(Color.BLUE);
			if(this.doorDirection == DoorDirection.UP)
				g.fillRect(column*length, row*length, length, 4);
			if(this.doorDirection == DoorDirection.DOWN)
				g.fillRect(column*length, row*length+length-4, length, 4);
			if(this.doorDirection == DoorDirection.LEFT)
				g.fillRect(column*length, row*length, 4, length);
			if(this.doorDirection == DoorDirection.RIGHT)
				g.fillRect(column*length+length-4, row*length, 4, length);
			if(this.doorDirection == DoorDirection.NAME)
				g.drawString(board.rooms.get(initial), (int) (column*length-.5*length), row*length - 5);
		}
		
		if (active) super.drawHighlighted(g, board);

	}
}