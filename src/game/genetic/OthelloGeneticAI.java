package game.genetic;

import game.OthelloAction;
import game.OthelloGame;
import game.OthelloSkipAction;
import game.OthelloState;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import aima.core.search.framework.GoalTest;
import aima.core.search.local.FitnessFunction;
import aima.core.search.local.GeneticAlgorithm;
import aima.core.search.local.Individual;

public class OthelloGeneticAI {
	public OthelloAction makeDecision(OthelloGame game, OthelloState state) {
		Set<OthelloAction> actions = state.getAllUnMarkedPositions();
		/*
		 * Use the FitnessFunction, GoalTest and functions bellow that you have
		 * implemented to use genetic algorithm. this function is called each
		 * time the player's turn comes. Therefore, it should return a single
		 * action for the current turn each time it is called.
		 */
		return null;

	}

	private OthelloAction getTheAction(List<OthelloAction> representation,
			OthelloGame game, OthelloState curentState) {
		/*
		 * Select the best fit action as player current turn's action
		 */
		return null;
	}

	private Set<Individual<OthelloAction>> createInitialPopulation(int size,
			int length, OthelloState currentState, OthelloGame game) {
		/*
		 * Implement this function to create the initial Population for the
		 * genetic algorithm. Pay attention that the meaningful the initial
		 * population be the closer you'll become to the answer. By being
		 * meaningful I mean having less possible answers in chromosomes.
		 */
		return null;

	}
}

class OthelloIndividual extends Individual<OthelloAction> {

	public OthelloIndividual(List<OthelloAction> representation) {
		super(representation);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		String tmp = "[ ";
		for (int i = 0; i < getRepresentation().size(); i++) {
			if (i != 0)
				tmp += ",";
			tmp += getRepresentation().get(i);
		}
		tmp += " ]";
		return tmp;
	}

}