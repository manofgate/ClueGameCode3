package clueGame;

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class GameControlPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	
	public class Die extends JPanel {
		private static final long serialVersionUID = 1L;
		
		private JTextField die;
		public Die(){
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
			JLabel label = new JLabel("Guess");
			guess = new JTextField();
			add(label);
			guess.setEditable(false);
			add(guess);
			setBorder(new TitledBorder(new EtchedBorder(), "Guess"));
		}
	}
	
	public class GuessResult extends JPanel {
		private static final long serialVersionUID = 1L;
		
		private JTextField guessResult;
		public GuessResult(){
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
	
	}
	
	public GameControlPanel() {
		
		// init components
		Die die = new Die();
		Guess guess = new Guess();
		GuessResult guessResult = new GuessResult();
		JPanel panel = new JPanel();
		TurnIndicator ti = new TurnIndicator();
		
		// set layout and add components
		panel.setLayout(new GridLayout(2,3));
		panel.add(ti);
		panel.add(new JButton("Next Player"));
		panel.add(new JButton("Make Accusation"));
		panel.add(die);
		panel.add(guess);
		panel.add(guessResult);
		
		add(panel);
		
	}
	
}
