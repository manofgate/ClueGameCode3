package clueGame;

import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class GameControlPanel extends JPanel {
	public class Die extends JPanel{
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
	public class Guess extends JPanel{
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
	public class GuessResult extends JPanel{
		private JTextField guessResult;
		public GuessResult(){
			JLabel label = new JLabel("Guess result");
			guessResult = new JTextField();
			add(label);
			guessResult.setEditable(false);
			add(guessResult);
			setBorder(new TitledBorder(new EtchedBorder(), "Guess Result"));
		}
	}
	public GameControlPanel(){
		Die die = new Die();
		Guess guess = new Guess();
		GuessResult guessResult = new GuessResult();
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(2,3));
		panel.add(die);
		panel.add(guess);
		panel.add(guessResult);
		add(panel);
	}
}
