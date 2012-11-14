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

public class GameControlPanel extends JPanel{
	private static final long serialVersionUID = 1L;
	private ArrayList<Player> players = new ArrayList<Player>();
	private int whichPlayer=0;
	private JButton nextPlayer, makeAccusation;
	private TurnIndicator ti;
	private prListner prList = new prListner();
	private Board board;
	private Random rand= new Random();
	public class Die extends JPanel {
		private static final long serialVersionUID = 1L;
		
		private JTextField die;
		public Die(){
			setLayout(new FlowLayout(FlowLayout.LEFT));
			JLabel label = new JLabel("Roll");
			die = new JTextField();
			die.setEditable(false);
			add(label);
			add(die);
			setBorder(new TitledBorder(new EtchedBorder(), "Die"));
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
			guess.setEditable(false);
			//add(guess);
			JPanel panel = new JPanel();
			panel.setLayout(new GridLayout(2,1));
			panel.add(label);
			panel.add(guess);
			add(panel);
			setBorder(new TitledBorder(new EtchedBorder(), "Guess"));
		}
	}
	
	public class GuessResult extends JPanel {
		private static final long serialVersionUID = 1L;
		
		private JTextField guessResult;
		public GuessResult(){
			setLayout(new FlowLayout(FlowLayout.LEFT));
			JLabel label = new JLabel("Response");
			guessResult = new JTextField();
			add(label);
			guessResult.setEditable(false);
			add(guessResult);
			setBorder(new TitledBorder(new EtchedBorder(), "Guess Result"));
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
	
	public GameControlPanel(Board bord) {
		
		// init components
		Die die = new Die();
		this.board = bord;
		Guess guess = new Guess();
		GuessResult guessResult = new GuessResult();
		JPanel panel = new JPanel();
		ti = new TurnIndicator();
		nextPlayer = new JButton("Next Player");
		makeAccusation = new JButton("Make Accusation");
		players = (ArrayList<Player>) board.getAllPlayers().clone();
		// set layout and add components
		panel.setLayout(new GridLayout(2,3));
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
		public void actionPerformed(ActionEvent e){
			if(e.getSource() == nextPlayer){
				ti.setText(players.get(whichPlayer).name);
				rand.setSeed(rand.nextLong());
				int roll = rand.nextInt(6)+1;
				if(whichPlayer !=0){
					System.out.println("yo dawg");
					board.makeMove((ComputerPlayer) players.get(whichPlayer), roll);
				}
				whichPlayer++;
				if(whichPlayer >5)
					whichPlayer =0;
			}
		}
	}
}