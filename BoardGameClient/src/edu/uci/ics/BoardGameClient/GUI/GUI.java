package edu.uci.ics.BoardGameClient.GUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import edu.uci.ics.BoardGameClient.Action.Action;
import edu.uci.ics.BoardGameClient.Board.Board;

public class GUI {

	private Action action;
	private Board board;
	private JFrame frame = new JFrame("Board Game Client");
	private JTextArea textConsole = new JTextArea();
	private JLayeredPane panelMain;
	private BoardGUI boardGUI;

	public GUI(Action action) {
		this.action = action;

		setupWindow();
	}
	
	public void setBoard(Board board) {
		this.board = board;
	}

	private void setupWindow() {
		
		textConsole.setEditable(false);
		textConsole.setRows(4);
		textConsole.append("Welcome to the Board Game Client! \n");
		
		Login login = new Login(action, textConsole);

		panelMain = new JLayeredPane();
		panelMain.setLayout(new BorderLayout());
		
		JPanel loginPanel = login.getPanel();
		panelMain.add(loginPanel, BorderLayout.NORTH, 0);
		panelMain.setLayer(loginPanel, 1);
		
		panelMain.add(textConsole, BorderLayout.SOUTH, 1);
		panelMain.setLayer(textConsole, -1);

		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent event) {
				closeFrame();
			}
		});

		frame.setPreferredSize(new Dimension(600, 600));
		frame.setSize(600, 600);
		
		frame.add(panelMain);
		
		frame.pack();
		frame.setVisible(true);

	}

	private void closeFrame() {
		frame.setVisible(false);
		frame.dispose();
		action.shutdown();
	}
	
	public void setToLogin() {
		panelMain.remove(0);
		
		Login login = new Login(action, textConsole);
		
		JPanel loginPanel = login.getPanel();
		panelMain.add(loginPanel, BorderLayout.NORTH, 0);
		panelMain.setLayer(loginPanel, 1);
		
		panelMain.revalidate();
		frame.repaint();
	}
	
	public void setToPickGame() {
		panelMain.remove(0);
		
		PickGame pickGame = new PickGame(action, textConsole);
		
		JPanel pickGamePanel = pickGame.getPanel();
		panelMain.add(pickGamePanel, BorderLayout.NORTH, 0);
		panelMain.setLayer(pickGamePanel, 1);
		
		panelMain.revalidate();
		frame.repaint();
	}
	
	public void setToBoard() {
		panelMain.remove(0);
		
		boardGUI = new BoardGUI(action, board, frame, textConsole);
		
		JPanel boardPanel = boardGUI.getPanel();
		panelMain.add(boardPanel, BorderLayout.NORTH, 0);
		panelMain.setLayer(boardPanel, 1);
		
		panelMain.revalidate();
		frame.repaint();
	}
	
	public void updateBoard() {
		boardGUI.update();
	}
	
	public void setText(String text) {
		textConsole.setText(text);
	}
	
	public void appendText(String text) {
		textConsole.append(text + " \n");
	}

}
