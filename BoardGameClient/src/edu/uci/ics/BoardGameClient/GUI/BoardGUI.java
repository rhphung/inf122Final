package edu.uci.ics.BoardGameClient.GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import edu.uci.ics.BoardGameClient.Action.Action;
import edu.uci.ics.BoardGameClient.Action.GameObjectDisplayer;
import edu.uci.ics.BoardGameClient.Action.MoveSender;
import edu.uci.ics.BoardGameClient.Board.Board;
import edu.uci.ics.BoardGameClient.Board.Tile;

public class BoardGUI {

	private Action action;
	private Board board;
	private JFrame frame;
	private JTextArea textConsole;
	private JPanel paneBoard;

	public BoardGUI(Action action, Board board, JFrame frame, JTextArea textConsole) {
		this.action = action;
		this.board = board;
		this.frame = frame;
		this.textConsole = textConsole;

		setupPanel();
	}

	public JPanel getPanel() {
		return paneBoard;
	}

	private void setupPanel() {
		paneBoard = new JPanel();
	}

	// Not sure if this will repaint the new game board correctly. Will need to test.
	public void update() {
		paneBoard.removeAll();

		paneBoard.setLayout(new GridLayout(board.getHeight(), board.getWidth()));

		for (int i = 0; i < board.getHeight(); i++) {
			for (int j = 0; j < board.getWidth(); j++) {
				BoardPanel p = new BoardPanel(action, board.getTile(i, j), i, j, board.getGameType(), this);
				p.setLayout(new GridBagLayout());
				
				if(board.getTile(i, j).isSelected()) {
					p.setBackground(Color.YELLOW);
				}
				
				paneBoard.add(p);
			}
		}

		paneBoard.revalidate();
		frame.repaint();
	}

	class BoardPanel extends JPanel {
		private static final long serialVersionUID = 0001;

		public BoardPanel(final Action action, Tile tile, final int row, final int col, final int gameType, final BoardGUI boardGui) {

			Color bgColor = new Color(255, 255, 255);
			int textAreaSize = 100;

			int panelWidth = frame.getWidth()/board.getWidth();
			int panelHeight = (frame.getHeight()-textAreaSize)/board.getHeight();
			
			setPreferredSize(new Dimension(panelWidth, panelHeight));

			this.setBackground(bgColor);
			this.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));

			if (tile.getGameObjects().size() > 0) // If true, we want to display what's in this tile
			{
				for (int i = 0; i < tile.getGameObjects().size(); i++) {
					int objectType = tile.getGameObjects().get(i).getObjectType();
					this.add(GameObjectDisplayer.displayGameObject(objectType));
				}

			}

			this.addMouseListener(new MouseAdapter() {
				public void mousePressed(MouseEvent e) {
					if(action.areFurtherMovesAllowed())
					{
						MoveSender.sendMessage(action, gameType, row, col);
						
						boardGui.update();
					}
				}
			});
		}
	}
	
}
