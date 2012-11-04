package clueGame;

public class BoardCell {

	public int row;
	public int column;
	
	public BoardCell() { }
	
	public BoardCell(int row, int col) {
		this.row = row;
		this.column = col;
	}
	
	public boolean isWalkway() {
		if(!(this.isRoom())){
			return true;
		}
		return false;
	}
	
	public boolean isRoom() { return false; }
	
	public boolean isDoorway() {
		if (this.isRoom()) {
			return true;
		}
		return false;
	}
	
}
