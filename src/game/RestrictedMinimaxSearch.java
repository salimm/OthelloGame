package game;

import aima.core.search.adversarial.AdversarialSearch;
import aima.core.search.adversarial.Game;
import aima.core.search.adversarial.MinimaxSearch;
import aima.core.search.framework.Metrics;

public class RestrictedMinimaxSearch<STATE, ACTION, PLAYER> implements
		AdversarialSearch<STATE, ACTION> {

	private Game<STATE, ACTION, PLAYER> game;
	private int expandedNodes;
	private int maxDepth = 10;

	/** Creates a new search object for a given game. */
	public static <STATE, ACTION, PLAYER> RestrictedMinimaxSearch<STATE, ACTION, PLAYER> createFor(
			Game<STATE, ACTION, PLAYER> game) {
		return new RestrictedMinimaxSearch<STATE, ACTION, PLAYER>(game);
	}

	public RestrictedMinimaxSearch(Game<STATE, ACTION, PLAYER> game) {
		this.game = game;

	}

	public void setMaxDepth(int depth) {
		this.maxDepth = depth;
	}

	public int getMaxDepth() {
		return maxDepth;
	}

	@Override
	public ACTION makeDecision(STATE state) {
		/*
		 * You need to apply some changes to this function. However for help you
		 * can use the implemented version in MinMaxSearch class in aima.
		 */
		return null;
	}

	public double maxValue(STATE state, PLAYER player, int depth) {
		/*
		 * You need to apply some changes to this function. However for help you
		 * can use the implemented version in MinMaxSearch class in aima.
		 */
		return -1;
	}

	public double minValue(STATE state, PLAYER player, int depth) {
		/*
		 * You need to apply some changes to this function. However for help you
		 * can use the implemented version in MinMaxSearch class in aima.
		 */
		return -1;
	}

	@Override
	public Metrics getMetrics() {
		Metrics result = new Metrics();
		result.set("expandedNodes", expandedNodes);
		return result;
	}
}
