package game;

import game.genetic.OthelloGeneticAI;
import aima.core.search.adversarial.AdversarialSearch;
import aima.core.search.adversarial.AlphaBetaSearch;
import aima.core.search.adversarial.MinimaxSearch;

public class OthelloDemo {
	private static void startRMinimaxDemo() {
		/*
		 * Implement this function similar to startMinimaxDemo but use
		 * RestrictedMinMax instead of MinMax
		 */
	}

	private static void startMinimaxDemo() {
		System.out.println("MIN MAX Ottello\n");
		OthelloGame game = new OthelloGame();
		OthelloState currState = game.getInitialState();
		AdversarialSearch<OthelloState, OthelloAction> search = MinimaxSearch
				.createFor(game);
		while (!(game.isTerminal(currState))) {
			System.out.println(game.getPlayer(currState) + "  playing ... ");
			OthelloAction action = search.makeDecision(currState);
			currState = game.getResult(currState, action);
			System.out.println(currState);
		}
		System.out.println("MIN MAX DEMO done");
	}

	private static void startAlphaBetaDemo() {
		/*
		 * Implement this function similar to startMinimaxDemo but use AlphaBeta
		 * instead of MinMax
		 */
	}

	private static void startMinMaxAlphaBetaDemo() {
		/*
		 * Implement this function in a way that player one (WHITE) is played by
		 * Restricted Minmax and player two by Alpha Beta. AlphaBeta
		 * implementation is similar to minamx.
		 */
	}

	private static void startMinMaxGeneticDemo() {
		/*
		 * Implement this function in a way that player one (WHITE) is played by
		 * Restricted Minmax and player two by Genetic Algorithm that you have
		 * implemented.
		 */
	}

	public static void main(String[] args) {
		System.out.println("Ottello DEMO");
		System.out.println("");
		// startRMinimaxDemo();
		// startMinimaxDemo();
		// startAlphaBetaDemo();
		// startMinMaxAlphaBetaDemo();
		startMinMaxGeneticDemo();
	}
}
