package game;

import java.util.List;


public class OthelloTest {
	void test1(){
		OthelloGame game= new OthelloGame();
		OthelloState state=game.getInitialState();
		List<OthelloAction> actions = game.getActions(state);
		state=game.getResult(state, actions.get(0));
		
	}
	public static void main(String[] args) {
		OthelloTest test= new OthelloTest();
		test.test1();
	}

}
