package clueGame;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.JOptionPane;

public class HumanGuess extends JDialog {
	private static final long serialVersionUID = 1L;

	Board board;
	
	ArrayList<String> people = new ArrayList<String>();
	ArrayList<String> rooms = new ArrayList<String>();
	ArrayList<String> weapons = new ArrayList<String>();
	
	private JComboBox roomsCombo, peopleCombo, weaponsCombo;
	private JButton submit, cancel;
	
	private ButtonListener buttonListener = new ButtonListener();
	private SuggestionButtonListener suggestionButtonListener = new SuggestionButtonListener();
	
	public HumanGuess(Board board) {
		this.board = board;
		
		// init
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setTitle("Make a Guess");
		setSize(250, 300);
		
		// load up arrays
		ArrayList<Card> deck = board.getDeck();
		for (Card card : deck) {
			if (card.type == Card.CardType.PERSON) people.add(card.name);
			else if (card.type == Card.CardType.ROOM) rooms.add(card.name);
			else if (card.type == Card.CardType.WEAPON) weapons.add(card.name);
		}
		
		// create combos
		roomsCombo = makeComboUsing(rooms);
		peopleCombo = makeComboUsing(people);
		weaponsCombo = makeComboUsing(weapons);
		
		// buttons
		submit = new JButton("Submit");
		cancel = new JButton("Cancel");
		
		submit.addActionListener(buttonListener);
		cancel.addActionListener(buttonListener);
		
		// set layout and add components
		setLayout(new GridLayout(4, 2));
		add(new JLabel("Rooms"));
		add(roomsCombo);
		add(new JLabel("People"));
		add(peopleCombo);
		add(new JLabel("Weapons"));
		add(weaponsCombo);
		add(submit);
		add(cancel);
		
		// setVisible
		setVisible(true);
	}
	
	// suggestion panel
	public HumanGuess(Board board, String roomName) {
		this.board = board;
		
		// init
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setTitle("Make a Guess");
		setSize(250, 300);
		
		// load up arrays
		ArrayList<Card> deck = board.getDeck();
		for (Card card : deck) {
			if (card.type == Card.CardType.PERSON) people.add(card.name);
			else if (card.type == Card.CardType.ROOM) rooms.add(card.name);
			else if (card.type == Card.CardType.WEAPON) weapons.add(card.name);
		}
		
		// create combos
		roomsCombo = makeComboUsing(rooms);
		peopleCombo = makeComboUsing(people);
		weaponsCombo = makeComboUsing(weapons);
		
		// buttons
		submit = new JButton("Submit");
		cancel = new JButton("Cancel");
		
		submit.addActionListener(suggestionButtonListener);
		cancel.addActionListener(suggestionButtonListener);
		
		JComboBox combo = new JComboBox();
		combo.addItem(roomName);
		roomsCombo = combo;
		roomsCombo.setEnabled(false);
		
		// set layout and add components
		setLayout(new GridLayout(4, 2));
		add(new JLabel("Rooms"));
		add(roomsCombo);
		add(new JLabel("People"));
		add(peopleCombo);
		add(new JLabel("Weapons"));
		add(weaponsCombo);
		add(submit);
		add(cancel);
		
		// setVisible
		setVisible(true);
	}

	private JComboBox makeComboUsing(ArrayList<String> list) {
		JComboBox combo = new JComboBox(); 
		for (String string : list) {
			combo.addItem(string);
		}
		return combo; 
	}
	
	private class ButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == submit) {
				setVisible(false);
				GameControlPanel.makeAccusation.setEnabled(false);
				GameControlPanel.nextPlayer.setEnabled(true);
				board.targetsSet = true;
				board.playerHasMoved = true;
				board.repaintInitialBoard();
				
				// check solution
				Card person = null;
				Card room = null;
				Card weapon = null;
				
				String roomSelected = roomsCombo.getSelectedItem().toString();
				String personSelected = peopleCombo.getSelectedItem().toString();
				String weaponSelected = weaponsCombo.getSelectedItem().toString();
				
				for (Card someCard : board.getDeck()) {
					if (someCard.name.equalsIgnoreCase(roomSelected)) room = someCard;
					else if (someCard.name.equalsIgnoreCase(personSelected)) person = someCard;
					else if (someCard.name.equalsIgnoreCase(weaponSelected)) weapon = someCard;
				}
				
				boolean winner = board.checkAccusation(person, room, weapon);
				
				if (winner) {
					GameControlPanel.nextPlayer.setEnabled(false);
					JOptionPane.showMessageDialog(null, "You win!", "Accusation correct!", 0);
				}
				else {
					JOptionPane.showMessageDialog(null, "Sorry, not correct!", "Accusation not correct!", 0);
				}
			}
			else if (e.getSource() == cancel) {
				setVisible(false);
			}
			
		}
		
	}
	
	private class SuggestionButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == submit) {
				setVisible(false);
				GameControlPanel.makeAccusation.setEnabled(false);
				GameControlPanel.nextPlayer.setEnabled(true);
				board.targetsSet = true;
				board.playerHasMoved = true;
				board.repaintInitialBoard();
				
				// check solution
				/*
				Card person = null;
				Card room = null;
				Card weapon = null;
				*/
				
				String roomSelected = roomsCombo.getSelectedItem().toString();
				String personSelected = peopleCombo.getSelectedItem().toString();
				String weaponSelected = weaponsCombo.getSelectedItem().toString();
				
				/*
				for (Card someCard : board.getDeck()) {
					if (someCard.name.equalsIgnoreCase(roomSelected)) room = someCard;
					else if (someCard.name.equalsIgnoreCase(personSelected)) person = someCard;
					else if (someCard.name.equalsIgnoreCase(weaponSelected)) weapon = someCard;
				}
				*/
				
				ClueGame.gcp.guess.setGuess(roomSelected + " " + personSelected + " " + weaponSelected);
				Card disproveCard = board.disproveSuggestion(0, personSelected, roomSelected, weaponSelected);;
				ClueGame.gcp.guessResult.setResult(disproveCard.name);
			}
			else if (e.getSource() == cancel) {
				setVisible(false);
			}
			
		}
		
	}

}
