package game;

import java.util.List;

import aima.core.search.adversarial.Game;

public class OthelloGame implements Game<OthelloState, OthelloAction, String> {
	/**
	 * An initial state instance of Ottello Game
	 */
	OthelloState initialState = new OthelloState();

	@Override
	public List<OthelloAction> getActions(OthelloState arg0) {
		return arg0.getUnMarkedPositions(arg0.getPlayerToMove());
	}

	@Override
	public OthelloState getInitialState() {
		return initialState;
	}

	@Override
	public String getPlayer(OthelloState arg0) {
		return arg0.getPlayerToMove();
	}

	@Override
	public String[] getPlayers() {
		return new String[] { OthelloState.WHITE, OthelloState.BLACK };
	}

	@Override
	public OthelloState getResult(OthelloState state, OthelloAction action) {
		OthelloState result = state.clone();
		result.mark(action);
		return result;
	}

	@Override
	public double getUtility(OthelloState state, String player) {
		double result = state.getUtility();
		if (result != -1) {
			if (player == OthelloState.BLACK)
				result = 1 - result;
		} else {
			// throw new IllegalArgumentException("State is not terminal.");
		}
		return result;
	}

	@Override
	public boolean isTerminal(OthelloState state) {
		return state.getUtility() != -1;
	}

}
