package clueGame;

import java.awt.Color;
import java.awt.Graphics;

public class WalkwayCell extends BoardCell {
	private static final int length= 23;
	public WalkwayCell(int row, int column) {
		this.row = row;
		this.column = column;
	}
	
	public boolean isWalkway() { return true; }
	@Override
	public void draw(Graphics g, Board board){
		g.setColor(Color.yellow);
		g.fillRect(column*length, row*length, length, length);
		g.setColor(Color.black);
		g.drawRect(column*length, row*length, length, length);
	}
}
