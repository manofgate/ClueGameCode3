package clueGame;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class GameControlPanel extends JPanel {
	
	private static final long serialVersionUID = 1L;
	private ArrayList<Player> players = new ArrayList<Player>();
	private int whichPlayer = 0;
	public JButton nextPlayer, makeAccusation;
	private TurnIndicator ti;
	private prListner prList = new prListner();
	private Board board;
	private Random rand= new Random();
	private Guess guess; 
	private Die die;
	private GuessResult guessResult;
	public class Die extends JPanel {
		private static final long serialVersionUID = 1L;
		
		private JTextField die;
		public Die(){
			setLayout(new FlowLayout(FlowLayout.LEFT));
			JLabel label = new JLabel("Roll");
			die = new JTextField();
			die.setPreferredSize(new Dimension(30, 30));
			die.setEditable(false);
			
			add(label);
			add(die);
			setBorder(new TitledBorder(new EtchedBorder(), "Die"));
		}
		public void setRoll(int roll){
			die.setText(" "+roll);
		}
	}
	
	public class Guess extends JPanel {
		private static final long serialVersionUID = 1L;
		
		private JTextField guess;
		public Guess(){
			setLayout(new FlowLayout(FlowLayout.LEFT));
			JLabel label = new JLabel("Guess");
			guess = new JTextField();
			//add(label);
			guess.setPreferredSize(new Dimension(200, 20));
			guess.setEditable(false);
			//add(guess);
			JPanel panel = new JPanel();
			panel.setLayout(new GridLayout(2,1));
			panel.add(label);
			panel.add(guess);
			panel.setSize(getMaximumSize());
			add(panel);
			setBorder(new TitledBorder(new EtchedBorder(), "Guess"));
		}
		public void setGuess(String guessed){
			guess.setText(guessed);
		}
	}
	
	public class GuessResult extends JPanel {
		private static final long serialVersionUID = 1L;
		
		private JTextField guessResult;
		public GuessResult(){
			setLayout(new FlowLayout(FlowLayout.LEFT));
			JLabel label = new JLabel("Response");
			guessResult = new JTextField();
			guessResult.setPreferredSize(new Dimension(100, 20));
			add(label);
			guessResult.setEditable(false);
			add(guessResult);
			setBorder(new TitledBorder(new EtchedBorder(), "Guess Result"));
		}
		public void setResult(String message){
			guessResult.setText(message);
		}
	}
	
	public class TurnIndicator extends JPanel {
		private static final long serialVersionUID = 1L;
		
		private JTextField turn;
		public TurnIndicator() {
			// init components
			JLabel label = new JLabel("Whose turn?");
			turn = new JTextField();
			
			// set layout and add components
			setLayout(new GridLayout(2, 1));
			add(label);
			add(turn);
			
		}
		public void setText(String message){
			turn.setText(message);
		}
	
	}
	
	public GameControlPanel(Board board) {
		
		// init components
		die = new Die();
		this.board = board;
		guess = new Guess();
		guessResult = new GuessResult();
		JPanel panel = new JPanel();
		ti = new TurnIndicator();
		nextPlayer = new JButton("Next Player");
		makeAccusation = new JButton("Make Accusation");
		makeAccusation.setEnabled(false);
		players = (ArrayList<Player>) board.getAllPlayers().clone();
		
		// set layout and add components
		panel.setLayout(new GridLayout(2,3));
		
		// oh thats where this thing was hiding... o.O
		nextPlayer.addActionListener(prList);
		
		panel.setPreferredSize(new Dimension(650, 136));
		panel.add(ti);
		panel.add(nextPlayer);
		panel.add(makeAccusation);
		panel.add(die);
		panel.add(guess);
		panel.add(guessResult);
		add(panel);
		
	}
	
	public class prListner implements ActionListener{
		@Override
		// Next Player Button listener!!! <-- <-- 
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == nextPlayer) {
				if (board.targetsSet && !board.playerHasMoved) return;
				
				// clear Guesses and Results
				guess.setGuess("");
				guessResult.setResult("");
				
				// whichPlayer is global int, init to 0
				ti.setText(players.get(whichPlayer).name);
				// .ah... double layered random
				rand.setSeed(rand.nextLong());
				
				// create a die roll (1-6) and set display
				int roll = rand.nextInt(6)+1;
				die.setRoll(roll);
				
				//
				//  START HERE
				//
				
	
				// if whichPlayer = 0, do human stuff
				if (whichPlayer == 0 && !board.targetsSet) {
					// draw targets avaiable for gui
					board.repaintBoardWithHumanTargets(roll);
					board.playerHasMoved = false;
					makeAccusation.setEnabled(true);
					nextPlayer.setEnabled(false);
					return;
				}
				else if (whichPlayer == 0 && board.targetsSet && board.playerHasMoved) {
					// repaint empty board
					board.targetsSet = false;
					board.playerHasMoved = false;
					board.repaintInitialBoard();
					
					// go to next player
					whichPlayer++;
					roll = rand.nextInt(6)+1;
					die.setRoll(roll);
				}
				// 0 is human player, i believe
				// so if not human, execute computer code
				if (whichPlayer != 0) {
					//if(!((ComputerPlayer) players.get(whichPlayer)).isNoDisprove()){
						Boolean roomy = board.makeMove((ComputerPlayer) players.get(whichPlayer), roll);
						if (roomy) {
							guess.setGuess(board.getSuggestion().get(0).name + " " + board.getSuggestion().get(1).name + " " + board.getSuggestion().get(2).name);
							guessResult.setResult(board.disprovedCard.name);
						}
				}
				// exit executing Player code
				
				// increment turn
				whichPlayer++;
				if (whichPlayer > 5)
					whichPlayer = 0;
			}
		}
	}
}