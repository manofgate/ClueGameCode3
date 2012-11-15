package clueGame;

import java.awt.Color;
import java.awt.Graphics;

public class WalkwayCell extends BoardCell {
	public WalkwayCell(int row, int column) {
		this.row = row;
		this.column = column;
	}

	public boolean isWalkway() { return true; }
	@Override
	public void draw(Graphics g, Board board){
		g.setColor(Color.yellow);
		g.fillRect(column*Board.drawLength, row*Board.drawLength, Board.drawLength, Board.drawLength);
		g.setColor(Color.black);
		g.drawRect(column*Board.drawLength, row*Board.drawLength, Board.drawLength, Board.drawLength);
		
		if (active) super.drawHighlighted(g, board);
	}
	
	
}