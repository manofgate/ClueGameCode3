package clueGame;

public class RoomCell extends BoardCell {

	public enum DoorDirection { UP, DOWN, LEFT, RIGHT, NONE }
	
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
				default :
					return DoorDirection.NONE;
			}
		}
	}
	
	public boolean isRoom() { return true; }

	public DoorDirection getDoorDirection() { return doorDirection; }

	public char getInitial() { return initial; }
	
	public boolean isDoorway() {
		if(this.doorDirection != DoorDirection.NONE){
			return true;
		}
		return false;
	}
	
}
