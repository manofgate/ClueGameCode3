package clueGame;

import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import clueGame.Card.CardType;

public class PlayerDisplay extends JPanel {
	public class People extends JPanel{
		private JTextArea cardPeep1;
		public People(){
			cardPeep1 = new JTextArea();
			cardPeep1.setEditable(false);
			add(cardPeep1);
			setBorder(new TitledBorder(new EtchedBorder(), "People"));
		}
		public void addTExt(String name){
			cardPeep1.append(name+ "\n");
		}
	}
	public class Rooms extends JPanel{
		private JTextArea cardRoom;
		public Rooms(){
			cardRoom = new JTextArea();
			add(cardRoom);
			cardRoom.setEditable(false);
			setBorder(new TitledBorder(new EtchedBorder(), "Rooms"));
		}
		public void addTExt(String name){
			cardRoom.append(name);
			cardRoom.append("\n");
		}
	}
	public class Weapons extends JPanel{
		private JTextArea cardWeap;
		public Weapons(){
			cardWeap= new JTextArea();
			add(cardWeap);
			cardWeap.setEditable(false);
			setBorder(new TitledBorder(new EtchedBorder(), "Weapons"));
		}
		public void addTExt(String name){
			cardWeap.append(name+ "\n");
		}
	}
	public PlayerDisplay(Player pl){
		JLabel label = new JLabel("My cards");
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(4,1));
		panel.setPreferredSize(new Dimension(100, 400));
		add(label);
		panel.add(label);
		People p = new People();
		Rooms r = new Rooms();
		Weapons w = new Weapons();
		for(Card c: pl.getCards()){
			if(c.type == CardType.PERSON){
				p.addTExt(c.name);	
			}
			if(c.type == CardType.ROOM){
				r.addTExt(c.name);
			}
			if(c.type == CardType.WEAPON){
				w.addTExt(c.name);
			
			}
		}
		panel.add(p);
		panel.add(r);
		panel.add(w);
		add(panel);
	}
}

