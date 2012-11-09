package clueGame;

import java.awt.Graphics;

public abstract class BoardCell {
	protected static final int length = 23;
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
	
	public abstract void draw(Graphics g, Board b);

	
}
