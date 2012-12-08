package game;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToolBar;

import aima.core.search.adversarial.AdversarialSearch;
import aima.core.search.adversarial.AlphaBetaSearch;
import aima.core.search.adversarial.IterativeDeepeningAlphaBetaSearch;
import aima.core.search.adversarial.MinimaxSearch;
import aima.core.search.framework.Metrics;

/**
 * Simple graphical Tic-tac-toe game application. It demonstrates the Minimax
 * algorithm for move selection as well as alpha-beta pruning.
 * 
 * @author Ruediger Lunde
 */
public class OthelloApp {

	/** Used for integration into the universal demo application. */
	public JFrame constructApplicationFrame() {
		JFrame frame = new JFrame();
		JPanel panel = new OttelloPanel();
		frame.add(panel, BorderLayout.CENTER);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		return frame;
	}

	/** Application starter. */
	public static void main(String[] args) {
		JFrame frame = new OthelloApp().constructApplicationFrame();
		frame.setSize(700, 700);
		frame.setVisible(true);
	}

	/** Simple panel to control the game. */
	private static class OttelloPanel extends JPanel implements ActionListener {
		private static final long serialVersionUID = 1L;
		JComboBox strategyCombo;
		JButton clearButton;
		JButton proposeButton;
		JButton[] squares;
		JLabel statusBar;

		OthelloGame game;
		OthelloState currState;
		Metrics searchMetrics;

		/** Standard constructor. */
		OttelloPanel() {
			this.setLayout(new BorderLayout());
			JToolBar tbar = new JToolBar();
			tbar.setFloatable(false);
			strategyCombo = new JComboBox(new String[] { "ResrictedMinimax",
					"AlphaBeta" + "" });
			strategyCombo.setSelectedIndex(0);
			tbar.add(strategyCombo);
			tbar.add(Box.createHorizontalGlue());
			clearButton = new JButton("Clear");
			clearButton.addActionListener(this);
			tbar.add(clearButton);
			proposeButton = new JButton("Propose Move");
			proposeButton.addActionListener(this);
			tbar.add(proposeButton);

			add(tbar, BorderLayout.NORTH);
			JPanel spanel = new JPanel();
			spanel.setLayout(new GridLayout(OthelloState.width,
					OthelloState.width));
			add(spanel, BorderLayout.CENTER);
			squares = new JButton[OthelloState.width * OthelloState.width];
			Font f = new java.awt.Font(Font.SANS_SERIF, Font.PLAIN, 32);
			for (int i = 0; i < OthelloState.width * OthelloState.width; i++) {
				JButton square = new JButton("");
				square.setFont(f);
				square.setBackground(Color.GREEN.darker().darker());
				square.addActionListener(this);
				squares[i] = square;
				squares[i].setOpaque(true);
				spanel.add(square);
			}
			statusBar = new JLabel(" ");
			statusBar.setBorder(BorderFactory.createEtchedBorder());
			add(statusBar, BorderLayout.SOUTH);

			game = new OthelloGame();
			actionPerformed(null);
		}

		/** Handles all button events and updates the view. */
		@Override
		public void actionPerformed(ActionEvent ae) {
			searchMetrics = null;
			if (ae == null || ae.getSource() == clearButton)
				currState = game.getInitialState();
			else if (!game.isTerminal(currState)) {
				if (ae.getSource() == proposeButton)
					proposeMove();
				else {
					for (int i = 0; i < OthelloState.width * OthelloState.width; i++)
						if (ae.getSource() == squares[i])
							currState = game.getResult(currState,
									new OthelloAction(i % OthelloState.width, i
											/ OthelloState.width));
				}
			}
			for (int i = 0; i < OthelloState.width * OthelloState.width; i++) {
				String val = currState.getValue(i % OthelloState.width, i
						/ OthelloState.width);
				if (val == OthelloState.EMPTY)
					val = "";
				if (val.equals(OthelloState.WHITE))
					squares[i].setBackground(Color.white);
				if (val.equals(OthelloState.BLACK))
					squares[i].setBackground(Color.BLACK);
				if (!val.equals(""))
					squares[i].setText(val.charAt(0) + "");

			}
			updateStatus();
		}

		/** Uses adversarial search for selecting the next action. */
		private void proposeMove() {
			AdversarialSearch<OthelloState, OthelloAction> search;
			OthelloAction action;
			switch (strategyCombo.getSelectedIndex()) {
			case 0:
				search = RestrictedMinimaxSearch.createFor(game);
				((RestrictedMinimaxSearch) search).setMaxDepth(1);
				break;
			case 1:
				search = AlphaBetaSearch.createFor(game);
				break;
			case 2:
				search = IterativeDeepeningAlphaBetaSearch.createFor(game, 0.0,
						1.0, 1000);
				break;
			default:
				search = IterativeDeepeningAlphaBetaSearch.createFor(game, 0.0,
						1.0, 1000);
				((IterativeDeepeningAlphaBetaSearch<?, ?, ?>) search)
						.setLogEnabled(true);
			}
			action = search.makeDecision(currState);
			searchMetrics = search.getMetrics();
			currState = game.getResult(currState, action);
		}

		/** Updates the status bar. */
		private void updateStatus() {
			String statusText;
			if (game.isTerminal(currState))
				if (game.getUtility(currState, OthelloState.WHITE) == 1)
					statusText = "White has won :-)";
				else if (game.getUtility(currState, OthelloState.BLACK) == 1)
					statusText = "Black has won :-)";
				else
					statusText = "No winner...";
			else
				statusText = "Next move: " + game.getPlayer(currState);
			if (searchMetrics != null)
				statusText += "    " + searchMetrics;
			statusBar.setText(statusText);
		}
	}
}
