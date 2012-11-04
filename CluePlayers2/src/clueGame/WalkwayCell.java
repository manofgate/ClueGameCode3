package clueGame;

public class WalkwayCell extends BoardCell {

	public WalkwayCell(int row, int column) {
		this.row = row;
		this.column = column;
	}
	
	public boolean isWalkway() { return true; }
	
}
