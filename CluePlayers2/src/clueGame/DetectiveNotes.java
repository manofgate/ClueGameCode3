package clueGame;

import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.border.*;

public class DetectiveNotes extends JDialog {
	private static final long serialVersionUID = 1L;

	Board board;
	
	ArrayList<String> people = new ArrayList<String>();
	ArrayList<String> rooms = new ArrayList<String>();
	ArrayList<String> weapons = new ArrayList<String>();
	
	public DetectiveNotes(Board board) {
		this.board = board;
		
		// init
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setTitle("Detective Notes");
		setSize(400, 650);
		
		// load up arrays
		ArrayList<Card> deck = board.getDeck();
		for (Card card : deck) {
			if (card.type == Card.CardType.PERSON) people.add(card.name);
			else if (card.type == Card.CardType.ROOM) rooms.add(card.name);
			else if (card.type == Card.CardType.WEAPON) weapons.add(card.name);
		}
		
		// set layout and add components
		setLayout(new GridLayout(3, 2));
		add(new PeoplePanel());
		add(new PersonGuessPanel());
		add(new RoomsPanel());
		add(new RoomGuessPanel());
		add(new WeaponsPanel());
		add(new WeaponGuessPanel());

	}
	
	class PeoplePanel extends JPanel {
		private static final long serialVersionUID = 1L;
		PeoplePanel() {
			// set layout and add components
//			setLayout(new GridLayout(3, 2));
			for (String person : people) {
				add(new JCheckBox(person));
			}
			// set border
			setBorder(new TitledBorder(new EtchedBorder(), "People"));
		}
	}
	
	class PersonGuessPanel extends JPanel {
		private static final long serialVersionUID = 1L;
		PersonGuessPanel() {
			add(createComboBoxUsing(people));
			// set border
			setBorder(new TitledBorder(new EtchedBorder(), "Person Guess"));
		}
	}

	class RoomsPanel extends JPanel {
		private static final long serialVersionUID = 1L;
		RoomsPanel() {
			// set layout and add components
//			setLayout(new GridLayout(5, 2));

			for (String room : rooms) {
				add(new JCheckBox(room));
			}
			
			// set border
			setBorder(new TitledBorder(new EtchedBorder(), "Rooms"));		
		}
	}
	
	class RoomGuessPanel extends JPanel {
		private static final long serialVersionUID = 1L;
		RoomGuessPanel() {
			add(createComboBoxUsing(rooms));
			// set border
			setBorder(new TitledBorder(new EtchedBorder(), "Room Guess"));
		}
	}
	
	class WeaponsPanel extends JPanel {
		private static final long serialVersionUID = 1L;
		WeaponsPanel() {
			
			// set layout and add components
//			setLayout(new GridLayout(4, 2));
			for (String weapon : weapons) {
				add(new JCheckBox(weapon));
			}
			// set border
			setBorder(new TitledBorder(new EtchedBorder(), "Weapons"));		
					
		}
	}
	
	class WeaponGuessPanel extends JPanel {
		private static final long serialVersionUID = 1L;

		WeaponGuessPanel() {
			add(createComboBoxUsing(weapons));
			// set border
			setBorder(new TitledBorder(new EtchedBorder(), "Weapon Guess"));		

		}
	}
	
	JComboBox createComboBoxUsing(ArrayList<String> strings) {
		JComboBox combo = new JComboBox();
		
		for (String string : strings) {
			combo.addItem(string);
		}
		
		return combo;
	}
	
}
