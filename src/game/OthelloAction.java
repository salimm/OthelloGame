package game;

import aima.core.util.datastructure.XYLocation;

public class OthelloAction extends XYLocation {

	private boolean isSkip = false;

	public OthelloAction(int x, int y) {
		super(x, y);
	}

	public boolean isSkip() {
		return isSkip;
	}

	public void setSkip(boolean isSkip) {
		this.isSkip = isSkip;
	}

	@Override
	public String toString() {
		if (this instanceof OthelloSkipAction)
			return "OthelloSkipAction";
		return "OthelloAction(" + getXCoOrdinate() + "," + getYCoOrdinate()
				+ ")";
	}

}
