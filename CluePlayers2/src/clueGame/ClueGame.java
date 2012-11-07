package clueGame;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class ClueGame extends JFrame {
	private static final long serialVersionUID = 1L;

	public ClueGame() {
		// init
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Clue Game");
		setSize(700, 700);
		
		// menu
		JMenuBar menu = new JMenuBar();
		setJMenuBar(menu);
		menu.add(createFileMenu());
		
		// components
		Board board = new Board();
		add(board, BorderLayout.CENTER);
		
		// set visible
		setVisible(true);
		
	}
	
	// file menu, for nothing but teh [EXIT]
	private JMenu createFileMenu() {
	  JMenu menu = new JMenu("File"); 
	  menu.add(createFileExitItem());
	  return menu;
	}
	private JMenuItem createFileExitItem() {
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
	
	// main, wooters
	public static void main(String[] args) {
		System.out.println("Hello world!!\n");
		
		@SuppressWarnings("unused")
		ClueGame futureHazard = new ClueGame();
	
		System.out.println("\nGoodbye world..");
	}
	
}
