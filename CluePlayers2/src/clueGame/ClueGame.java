package clueGame;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class ClueGame extends JFrame {
	public ClueGame(){
		setSize(700, 700);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Clue Game");
		JMenuBar menu = new JMenuBar();
		menu.add(createFileMenu());
		add(menu, BorderLayout.NORTH);
		Board board = new Board();
		add(board, BorderLayout.CENTER);
	}
	private JMenu createFileMenu()
	{
	  JMenu menu = new JMenu("File"); 
	  menu.add(createFileExitItem());
	  return menu;
	}
	private JMenuItem createFileExitItem()
	{
	  JMenuItem item = new JMenuItem("Exit");
	  class MenuItemListener implements ActionListener {
	    public void actionPerformed(ActionEvent e)
	    {
	       System.exit(0);
	    }
	  }
	  item.addActionListener(new MenuItemListener());
	  return item;
	}
	public static void main(String[] args) {
		System.out.println("Hello world!!\n");
		ClueGame cGameGui = new ClueGame();
		cGameGui.setVisible(true);
		//@SuppressWarnings("unused")
		//Board board = new Board();
		
//		System.out.println("Starting positions for players (given by index): ");
//		System.out.println("Miss Scarlet: " + board.calcIndex(13, 22));
//		System.out.println("Mr. Green: " + board.calcIndex(21, 6));
//		System.out.println("Mrs. Peacock: " + board.calcIndex(0, 4));
//		System.out.println("Colonel Mustard: " + board.calcIndex(21, 15));
//		System.out.println("Mrs. White: " + board.calcIndex(13, 0));
//		System.out.println("Professor Plum: " + board.calcIndex(0, 19));
	
		System.out.println("\nGoodbye world..");
	}
	
}
