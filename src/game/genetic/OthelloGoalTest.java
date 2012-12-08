package game.genetic;

import game.OthelloAction;
import aima.core.search.framework.GoalTest;
import aima.core.search.local.Individual;

public class OthelloGoalTest implements GoalTest {
	private final OthelloFitness fitness;

	public OthelloGoalTest(OthelloFitness fitness) {
		this.fitness = fitness;
	}

	@Override
	public boolean isGoalState(Object arg0) {
		if (arg0 instanceof Individual<?>) {

			if (fitness.getValue((Individual<OthelloAction>) arg0) == fitness
					.getCuttentState().width
					* fitness.getCuttentState().width
					* fitness.getCuttentState().width) {
				return true;
			}
		}
		return false;
	}
}
