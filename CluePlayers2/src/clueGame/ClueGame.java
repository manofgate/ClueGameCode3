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
	
	DetectiveNotes detectiveNotes;

	public ClueGame() {
		// init
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Clue Game");
		setSize(700, 720);
		
		// components
		Board board = new Board();
		add(board, BorderLayout.CENTER);
		PlayerDisplay pDisplay = new PlayerDisplay(board.getAllPlayers().get(board.findHuman()));
		add(pDisplay, BorderLayout.EAST);
		GameControlPanel  gcp = new GameControlPanel();
		add(gcp, BorderLayout.SOUTH);
		
		// menu
		JMenuBar menu = new JMenuBar();
		setJMenuBar(menu);
		menu.add(createFileMenu());
		
		detectiveNotes = new DetectiveNotes(board);

		// set visible
		setVisible(true);

	}

	// file menu, for nothing but teh [EXIT]
	private JMenu createFileMenu() {
		JMenu menu = new JMenu("File"); 
		menu.add(createDetectiveNotes());
		menu.add(createFileExitItem());
		return menu;
	}
	private JMenuItem createDetectiveNotes() {
		JMenuItem item = new JMenuItem("Show Detective Notes");
		class MenuItemListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				detectiveNotes.setVisible(true);
			}
		}
		item.addActionListener(new MenuItemListener());
		return item;
	}
	private JMenuItem createFileExitItem() {
		JMenuItem item = new JMenuItem("Exit");
		class MenuItemListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
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
