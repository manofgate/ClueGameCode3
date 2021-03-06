package clueGame;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import clueGame.Card.CardType;

public class Board extends JPanel {
	private static final long serialVersionUID = 1L;
	
////////////////////////////////
//  configuration files
//
	
//	private static final String boardConfigFile = "config/others/ST_ClueBoardConfig.csv";
//	private static final String boardLegendFile = "config/others/ST_ClueLegend.txt";
//	private static final String boardConfigFile = "config/others/CR_ClueLayout.csv";
//	private static final String boardLegendFile = "config/others/CR_ClueLegend.txt";
	private static final String boardConfigFile = "config/ClueLayout.csv";
	private static final String boardLegendFile = "config/ClueLegend.txt";
//	CluePlayers
	private static final String boardPlayersFile = "config/CluePlayers.txt";
	private static final String boardCardsFile = "config/ClueCards.txt";
	
//
////////////////////////////////
	
////////////////////////////////
//  declaration of variables
//
	
	ArrayList<BoardCell> cells = new ArrayList<BoardCell>();
	static Map<Character,String> rooms = new HashMap<Character,String>();	
	
	private Map<Integer, LinkedList<Integer>>adjList = new HashMap<Integer, LinkedList<Integer>>();
	private HashSet<BoardCell> targets;
	
	private	int numRows;
	private	int numColumns;
	int index;
	
	private boolean[] visited;
	
//
////////////////////////////////
	
////////////////////////////////
//  ClueGameBoard part 1
//  constructor with initial setup shenanigans
//
	
	public Board() {
		loadConfigFiles();
		calcAdjacencies();
		// added mouse listener
		addMouseListener(new MouseHandler());
	}

	public void loadConfigFiles() {
		loadConfigLegend();
		loadConfigBoard();
		loadCluePlayerConfigFiles();
	}
	
	public void loadConfigLegend() {
		try {
			FileReader reader = new FileReader(boardLegendFile);
			Scanner in = new Scanner(reader);
			while (in.hasNext()) {
				String input = in.nextLine();
				String[] tokens = input.split(",");
				if (tokens.length != 2) {
					throw new BadConfigFormatException("Error with legend file.");
				}
				Character key = new Character(tokens[0].charAt((0)));
				rooms.put(key, tokens[1].trim());
			}
		} catch (BadConfigFormatException e) {
			System.out.println(e);
		} catch (FileNotFoundException e) {
			System.out.println(e);
		}
	}
	
	public void loadConfigBoard() {
		try {
			FileReader reader = new FileReader(boardConfigFile);
			Scanner in = new Scanner(reader);
			while (in.hasNext()) {
				String input = in.nextLine();
				String[] tokens = input.split(",");
				if (input.length() < 1) {
					throw new BadConfigFormatException("Error with board config file.");
				}
				for (int i = 0; i < tokens.length; i++) {
					if (tokens[i].equalsIgnoreCase("W")) {
						cells.add(new WalkwayCell(numRows, i));
					} else {
						cells.add(new RoomCell(numRows, i, tokens[i]));
					}
				}
				numRows++;
			}
			numColumns = (cells.size() / numRows);
		} catch (FileNotFoundException e) {
			System.out.println(e);
		} catch (BadConfigFormatException e) {
			System.out.println(e);
		} catch (Exception e){
			e.printStackTrace();
		}
	}
	
	public void calcAdjacencies() {
		for (int x = 0; x < numRows; x++) {
			for (int y = 0; y < numColumns; y++) {
				LinkedList<Integer> list = new LinkedList<Integer>();
				
				int index = calcIndex(x, y);
				if(getCellAt(index).isWalkway()){
					if (x > 0) {
						int attempt = calcIndex(x-1, y);
						if (validMove(index, attempt)){
							list.add(attempt);
						}
					}
					if (y > 0){
						int attempt = calcIndex(x, y-1);
						if (validMove(index, attempt)){
							list.add(attempt);
						}
					}
					if (x != numRows-1) {
						int attempt = calcIndex(x+1, y);
						if (validMove(index, attempt)){
							list.add(attempt);
						}
					}
					if (y != numColumns-1) {
						int attempt = calcIndex(x, y+1);
						if (validMove(index, attempt)){
							list.add(attempt);
						}
					}
				}
				
				else if(getCellAt(index).isRoom()){
					RoomCell r = getRoomCellAt(x, y);
					
					if(r.isDoorway()){
						switch (r.doorDirection){
						case DOWN:
							if(validMove(index, calcIndex(x+1, y))){
								list.add(calcIndex(x+1, y));
							}
							break;
						case UP:
							if(validMove(index,calcIndex(x-1, y))){
								list.add(calcIndex(x-1, y));
							}
							break;
						case LEFT:
							if(validMove(index, calcIndex(x, y-1))){
								list.add(calcIndex(x, y-1));
							}
							break;
						case RIGHT:
							if(validMove(index,calcIndex(x, y+1))){
								list.add(calcIndex(x, y+1));
							}
							break;
						default:
							
						}
					}
				}
						
				adjList.put(index, list);
			}
		}
	}
//	helper method for calcAdjancencies
	public boolean validMove(int current, int attempt){
		if (getCellAt(attempt).isWalkway()){
			return true;
		}
		else if(getCellAt(attempt).isRoom()){
			RoomCell r = getRoomCellAt(attempt);
			
			if(r.isDoorway()){
				switch (r.doorDirection){
				case DOWN:
					if (current == calcIndex(r.row+1, r.column)){
						return true;
					}
					break;
				case UP:
					if (current == calcIndex(r.row-1, r.column)){
						return true;
					}
					break;
				case LEFT:
					if (current == calcIndex(r.row, r.column-1)){
						return true;
					}
					break;
				case RIGHT:
					if (current == calcIndex(r.row, r.column+1)){
						return true;
					}
					break;
				default:
					
				}
			}
		}
	
		return false;
	}
	
//
////////////////////////////////
	
////////////////////////////////
//	ClueGameBoard part i
//	calcIndex instance method and getters
//	
	
//	instance method calcIndex
	public int calcIndex(int row, int column) { return ((numColumns)*row) + column; }
//	get get get your getters
	public RoomCell getRoomCellAt(int row, int column) {
		index = calcIndex(row, column);
		if ((cells.get(index)).isRoom()) {
			return (RoomCell) cells.get(index);
		} 
		
		return null;
	}
	
	public RoomCell getRoomCellAt(int index) {
		if ((cells.get(index)).isRoom()) {
			return (RoomCell) cells.get(index);
		}
		
		return null;
	}

	public BoardCell getCellAt(int index) { return cells.get(index); }

	static public Map<Character, String> getRooms() { return rooms; }

	public int getNumRows() { return numRows; }

	public int getNumColumns() { return numColumns; }
	
//	
////////////////////////////////
	
////////////////////////////////
//	ClueGameBoard part ii	
//	calcTargets and getters
//	
	
	public void calcTargets(int index, int steps) {
		visited = new boolean[getNumColumns()*getNumRows()];
		targets = new HashSet<BoardCell>();
		
		LinkedList<Integer> path = new LinkedList<Integer>();
		
		visited[index] = true;
		
		visitTargets(adjList.get(index), path, steps);
	}
//	helper method for calcTargets, recursive
	public void visitTargets(LinkedList<Integer> adjacents, LinkedList<Integer> path, int steps) {
		
		LinkedList<Integer> adjacentsClone = new LinkedList<Integer>();
		adjacentsClone.addAll(adjacents);
		
 		for (Iterator<Integer> itr = adjacentsClone.iterator(); itr.hasNext();) {
			
			int current = itr.next();
			
			if (getCellAt(current).isRoom()) {
				targets.add(getCellAt(current));
			}
			
			else {
			
				path.addLast(current);
				visited[current] = true;
				
				if (path.size() == steps) { targets.add(getCellAt(current)); }
				else {
					LinkedList<Integer>	list = new LinkedList<Integer>();
					list.addAll(adjList.get(current));
					
					for (Iterator<Integer> itr2 = list.iterator(); itr2.hasNext();){
						int node = itr2.next();
						
						if(visited[node]) {
							itr2.remove();
						}
					}
					
					if (list.size() > 0) {
						visitTargets(list, path, steps);
					}
				}
				
				visited[current] = false;
				
				path.removeLast();
				
			}
			
 		}
 		
	}
//	getters
	public HashSet<BoardCell> getTargets() { return targets; }
	
	public LinkedList<Integer> getAdjList(int cell) { return adjList.get(cell); }

//	
////////////////////////////////
	
////////////////////////////////
//	CluePlayers
//	
	
//	variables
	private ArrayList<Player> allPlayers = new ArrayList<Player>();
	public ArrayList<Player> getAllPlayers() { return allPlayers; }

	private ArrayList<Card> deck = new ArrayList<Card>();
	private ArrayList<Card> dealDeck = new ArrayList<Card>();
	public ArrayList<Card> getDealDeck() { return dealDeck; }
	public ArrayList<Card> getDeck() { return deck; }

	static private ArrayList<Card> cardsSeen = new ArrayList<Card>();
	private ArrayList<Card> solution = new ArrayList<Card>();
	public ArrayList<Card> getSolution() { return solution; }
	static public ArrayList<Card> getCardsSeen() { return cardsSeen; }
	
//	variables to hold list of cards, list of computer 
//	players, one human player, and an indicator of whose turn it is
	
//	i say we have one ArrayList of Players, instantiate in order of file
	
////////////////////////////////
//	GameSetup section
//		

	public void loadCluePlayerConfigFiles() {
		// create players
		try {
			loadCluePlayers();
			// generate cards
			loadClueCards();
			// deal cards
			dealClueCards();
		} catch (FileNotFoundException e) {
			System.out.println("File was not found!");
		} catch (BadConfigFormatException e) {
			System.out.println(e.getMessage());
		}
		
	}
	
	public void loadCluePlayers() throws FileNotFoundException, BadConfigFormatException {
		FileReader reader = new FileReader(boardPlayersFile);
		Scanner in = new Scanner(reader);
		while (in.hasNext()) {
			String input = in.nextLine();
			String[] tokens = input.split(",");
			if (tokens.length != 4) { throw new BadConfigFormatException("Unexpected notation in players file."); }
			
			if (tokens[0].equalsIgnoreCase("human")) { allPlayers.add(new HumanPlayer(tokens[1], tokens[2], Integer.parseInt(tokens[3]))); }
			else if (tokens[0].equalsIgnoreCase("computer")) { allPlayers.add(new ComputerPlayer(tokens[1], tokens[2], Integer.parseInt(tokens[3]))); }
			else { throw new BadConfigFormatException("Unexpected notation in players file."); }
		}
	}
	
	public void loadClueCards() throws FileNotFoundException, BadConfigFormatException {
		FileReader reader = new FileReader(boardCardsFile);
		Scanner in = new Scanner(reader);
		while (in.hasNext()) {
			String input = in.nextLine();
			String[] tokens = input.split(",");
			if (tokens.length != 2) { throw new BadConfigFormatException("Unexpected notation in cards file."); }
			
			if (tokens[0].equalsIgnoreCase("person")) { deck.add(new Card(tokens[1], CardType.PERSON)); }
			else if (tokens[0].equalsIgnoreCase("room")) { deck.add(new Card(tokens[1], CardType.ROOM)); }
			else if (tokens[0].equalsIgnoreCase("weapon")) { deck.add(new Card(tokens[1], CardType.WEAPON)); }
			else { throw new BadConfigFormatException("Unexpected notation in cards file."); }
		}
	}
	
	public void dealClueCards() {
		
		Random hazard = new Random();
		int playerIndex = 0;
		Card someCard;

		dealDeck.addAll(deck);
		hazard.setSeed(hazard.nextLong());
		// create solution set
		while (true) {
			someCard = dealDeck.get(hazard.nextInt(dealDeck.size()));
			if (someCard.type == CardType.PERSON){
				dealDeck.remove(someCard);
				solution.add(someCard);
				break;
			}
		}
		while (true) {
			someCard = dealDeck.get(hazard.nextInt(dealDeck.size()));
			if (someCard.type == CardType.ROOM){
				dealDeck.remove(someCard);
				solution.add(someCard);
				break;
			}
		}
		while (true) {
			someCard = dealDeck.get(hazard.nextInt(dealDeck.size()));
			if (someCard.type == CardType.WEAPON){
				dealDeck.remove(someCard);
				solution.add(someCard);
				break;
			}
		}
		
		// deal out rest of cards
		while (!dealDeck.isEmpty()) {
			someCard = dealDeck.get(hazard.nextInt(dealDeck.size()));
			dealDeck.remove(someCard);
			allPlayers.get(playerIndex).cards.add(someCard);
			
			++playerIndex;
			if (playerIndex == allPlayers.size()) playerIndex = 0;
		}
	}

//	
////////////////////////////////

////////////////////////////////
//	 GameAction section
//	
	
	// return true if accusation is true, false otherwise
	public boolean checkAccusation(Card person, Card room, Card weapon){
		ArrayList<Card> accusation = new ArrayList<Card>();
		accusation.add(person);
		accusation.add(room);
		accusation.add(weapon);
		
		if (solution.containsAll(accusation)) return true;
		else return false;
	}
	
	// returns a card from a player or null card if no players have any of suggested cards
	public Card disproveSuggestion(int currentPlayer, String person, String room, String weapon) {
		Card someCard;
		ArrayList<Player> playersToCheck = new ArrayList<Player>();
		playersToCheck.addAll(allPlayers);
		playersToCheck.remove(currentPlayer);
		Collections.shuffle(playersToCheck);
		
		for (Player somePlayer : playersToCheck) {
			someCard = somePlayer.disproveSuggestion(person);
			if (someCard.type != CardType.NULL) return someCard;
			someCard = somePlayer.disproveSuggestion(room);
			if (someCard.type != CardType.NULL) return someCard;
			someCard = somePlayer.disproveSuggestion(weapon);
			if (someCard.type != CardType.NULL) return someCard;
		}
		
		return new Card();
	}
	
	// computer players making a suggestion
	public Card makeSuggestion(int indexOfComputerPlayer) {
		Card someCard;
		Card personCard;
		Card roomCard = null;
		Card weaponCard;
		Random hazard = new Random();
		
		ArrayList<Card> haveNotSeen = new ArrayList<Card>();
		haveNotSeen.addAll(deck);
		haveNotSeen.removeAll(allPlayers.get(indexOfComputerPlayer).cards);
		haveNotSeen.removeAll(cardsSeen);
		
		// select a person
		while (true) {
			someCard = haveNotSeen.get(hazard.nextInt(haveNotSeen.size()));
			if (someCard.type == CardType.PERSON) {
				personCard = someCard;
				break;
			}
		}
		
		// use room that computer player is currently in
		ComputerPlayer computerPlayer = (ComputerPlayer) allPlayers.get(indexOfComputerPlayer);
		RoomCell roomCell = (RoomCell) getCellAt(computerPlayer.indexedLocation);
		String roomName = getRooms().get(roomCell.initial);
		for (Card someOtherCard : deck) {
			if (someOtherCard.name.equalsIgnoreCase(roomName)) {
				roomCard = someOtherCard;
				break;
			}
		}
		
		// select a weapon
		while (true) {
			someCard = haveNotSeen.get(hazard.nextInt(haveNotSeen.size()));
			if (someCard.type == CardType.WEAPON){
				weaponCard = someCard;
				break;
			}
		} 
		
		// move selected person to the room cell
		for (Player player : allPlayers) {
			if (player.name.equalsIgnoreCase(personCard.name)) {
				player.indexedLocation = calcIndex(roomCell.row, roomCell.column);
				repaint();
				break;
			}
		}
		
		// ending stuff
		suggestion.add(personCard);
		suggestion.add(roomCard);
		suggestion.add(weaponCard);
		someCard = disproveSuggestion(indexOfComputerPlayer, personCard.name, roomCard.name, weaponCard.name);
		cardsSeen.add(someCard);
		return someCard;
	}
	
	private ArrayList<Card> suggestion = new ArrayList<Card>();
	public ArrayList<Card> getSuggestion() { return suggestion; }
	
	public int findHuman() {
		for (Player p : allPlayers) {
			if (p.isHuman())
				return allPlayers.indexOf(p);
		}
		return (Integer) null;
		
	}

//	
////////////////////////////////

////////////////////////////////
//	clue board GUI
	
	public int[] calcColumnAndRow(int index) {
		
		int[] columnAndRow = new int[2];
		
		int column = index % numColumns;
		int row = index / numColumns;
		
		columnAndRow[0] = column;
		columnAndRow[1] = row;
		
		return columnAndRow;
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		for (BoardCell cell : cells) {
			cell.draw(g, this);
		}
		for (Player player : allPlayers) {
			player.draw(g, this);
		}
	}
	

////////////////////////////////
//	Clue Play!
	
	// some variables
	public Card disprovedCard = new Card();
	public boolean targetsSet = false;
	public boolean playerHasMoved = false;
	
	public static final int drawLength = 26;
	
	// given a computer player and a dice roll
	// make the move or make an accusation
	@SuppressWarnings({ "unchecked", "static-access" }) // <-- :(
	public Boolean makeMove(ComputerPlayer playerd, int step) {
		suggestion.clear();
		if (!playerd.isNoDisprove()) {
			// this part makes them move!!
			calcTargets(playerd.indexedLocation, step);
			BoardCell picked = playerd.pickLocation(targets);
			playerd.setIndexedLocation(calcIndex(picked.row, picked.column));
			repaint();
			
			// if computer player is in room, make a suggestion
			Boolean inRoom = false;
			if (picked.isRoom()) {
				inRoom = true;
				disprovedCard = makeSuggestion(allPlayers.indexOf(playerd));
				if (disprovedCard.type == CardType.NULL) {
					playerd.setNoDisprove(true);
					playerd.setSugAcusation((ArrayList<Card>) suggestion.clone());
				}
			}
			return inRoom;
		}
		
		// computers want to accuse someone, and either fails or succeeds.
		else {
			playerd.setNoDisprove(false);  
			if (checkAccusation(playerd.getSugAcusation().get(0), playerd.getSugAcusation().get(1), playerd.getSugAcusation().get(2))) {
				JOptionPane.showMessageDialog(this, 
						playerd.name + ", accused " + 
						playerd.getSugAcusation().get(0).name + " " + 
						playerd.getSugAcusation().get(1).name + " " + 
						playerd.getSugAcusation().get(2).name + 
						" is correct and won!", "Accusation correct!", JOptionPane.INFORMATION_MESSAGE);
				ClueGame.gcp.nextPlayer.setEnabled(false);
			}
			else {
				JOptionPane.showMessageDialog(this, (playerd.name + ", accused " + playerd.getSugAcusation().get(0).name + " " + playerd.getSugAcusation().get(1).name +" " +playerd.getSugAcusation().get(2).name + " is NOT correct!"), "Accusation not correct!", JOptionPane.INFORMATION_MESSAGE);
			}
		}
		return null;
	}

	// repaint using human player targets
	public void repaintBoardWithHumanTargets(int roll) {
		HumanPlayer humanPlayer = (HumanPlayer) getAllPlayers().get(0);
		calcTargets(humanPlayer.indexedLocation, roll);
		for (BoardCell cell : getTargets()) {
			cell.active = true;
		}
		targetsSet = true;
		repaint();
	}
	
	public void repaintInitialBoard() {
		for (BoardCell cell : cells) {
			cell.active = false;
		}
		repaint();
	}
	
	// board mouse handler, makes the players move 
	// if it is their time to move
	private class MouseHandler implements MouseListener {
		@SuppressWarnings("static-access")
		@Override
		public void mousePressed(MouseEvent e) {
			if (targetsSet) {
				int column = e.getX() / drawLength;
				int row = e.getY() / drawLength;
				if (column > numColumns || row > numRows) return;
				BoardCell cell = getCellAt(calcIndex(row, column));
				if (cell.active) {
					allPlayers.get(0).indexedLocation = calcIndex(row,column);
					playerHasMoved = true;
					ClueGame.gcp.makeAccusation.setEnabled(false);
					ClueGame.gcp.nextPlayer.setEnabled(true);
					repaint();
					if (ClueGame.humanGuess != null) ClueGame.humanGuess.setVisible(false);
					//
					//	START HERE
					//
					if (cell.isRoom()) {
						RoomCell roomCell = (RoomCell) cell;
						ClueGame.humanGuess = new HumanGuess(ClueGame.board, ClueGame.board.getRooms().get(roomCell.initial));
					}
				}
			}
		}
		public void mouseClicked(MouseEvent arg0) { }
		public void mouseEntered(MouseEvent arg0) { }
		public void mouseExited(MouseEvent arg0) { }
		public void mouseReleased(MouseEvent arg0) { }
	}
	

	
//	
////////////////////////////////
//		  END OF FILE		  //	
////////////////////////////////
	
}
