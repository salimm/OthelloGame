package game.genetic;

import game.OthelloAction;
import game.OthelloGame;
import game.OthelloState;
import aima.core.search.local.FitnessFunction;
import aima.core.search.local.Individual;

public class OthelloFitness implements FitnessFunction<OthelloAction> {
	private final OthelloState cuttentState;
	private final OthelloGame game;
	private final String player;

	public OthelloFitness(OthelloState cuttentState, OthelloGame game,
			String player) {
		this.cuttentState = cuttentState;
		this.game = game;
		this.player = player;
	}

	@Override
	public double getValue(Individual<OthelloAction> ind) {
		/*
		 * (STUDENT) Implement a getCalue function for the Othello Fitness
		 * function. Waning: see Aima's Genetic Source code to make sure wether
		 * the value of it is seeking a Global Maximum ro Minimum
		 */
		return 0;

	}

	public OthelloState getCuttentState() {
		return cuttentState;
	}

}
