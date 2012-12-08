package game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * A state class for Ottelo Game
 * 
 * @author Salim Malakouti
 * 
 */
public class OthelloState implements Cloneable {
	public static final String BLACK = "BLACK";
	public static final String WHITE = "WHITE";
	public static final String EMPTY = "(---)";
	public static final int width = 6;

	private String[] board;
	private boolean changables[];

	private int changes = 0;

	private String playerToMove = WHITE;
	private double utility = -1; // 1: win for WHITE, 0: win for BLACK, 0.5:
									// draw

	public OthelloState() {
		init();
	}

	private void init() {
		changables = new boolean[width * width];
		board = new String[width * width];
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < width; j++) {
				board[getAbsPosition(i, j)] = EMPTY;
				if (i == width / 2 - 1 && j == width / 2 - 1)
					board[getAbsPosition(i, j)] = BLACK;
				if (i == width / 2 && j == width / 2 - 1)
					board[getAbsPosition(i, j)] = WHITE;
				if (i == width / 2 - 1 && j == width / 2)
					board[getAbsPosition(i, j)] = WHITE;
				if (i == width / 2 && j == width / 2)
					board[getAbsPosition(i, j)] = BLACK;
			}
		}
		// for (int i = 0; i < width; i++) {
		// board[getAbsPosition(i,0 )] = BLACK;
		// }
		// for (int i = 0; i < width; i++) {
		// board[getAbsPosition(i,1 )] = WHITE;
		// }
		// for (int i = 0; i < width; i++) {
		// board[getAbsPosition(i,2 )] = WHITE;
		// }
	}

	public String getPlayerToMove() {
		return playerToMove;
	}

	public boolean isEmpty(int col, int row) {
		return board[getAbsPosition(col, row)] == EMPTY;
	}

	public String getValue(int col, int row) {
		return board[getAbsPosition(col, row)];
	}

	public double getUtility() {
		return utility;
	}

	public void mark(OthelloAction action) {
		if (action instanceof OthelloSkipAction) {
			playerToMove = (playerToMove == WHITE ? BLACK : WHITE);
			return;
		}
		if (action == null) {
			playerToMove = (playerToMove == WHITE ? BLACK : WHITE);
			return;
		}
		mark(action.getXCoOrdinate(), action.getYCoOrdinate());
	}

	public void mark(int col, int row) {
		if (utility == -1 && getValue(col, row) == EMPTY
				&& ifChanges(col, row, getPlayerToMove(), true)) {
			board[getAbsPosition(col, row)] = playerToMove;
			applyChanges();
			analyzeUtility();
			playerToMove = (playerToMove == WHITE ? BLACK : WHITE);
		}
	}

	private void applyChanges() {
		for (int i = 0; i < changables.length; i++) {
			if (changables[i]) {
				board[i] = playerToMove;
			}
		}
	}

	private void analyzeUtility() {
		int whites = whites();
		int blacks = blacks();
		if (getUnMarkedPositions(BLACK).size() == 0
				&& getUnMarkedPositions(WHITE).size() == 0
				|| getNumberOfMarkedPositions() == width * width) {
			if (whites > blacks) {
				utility = 1;
			} else if (whites < blacks) {
				utility = 0;
			} else if (whites == blacks) {
				utility = 0.5;
			}
		}
	}

	public int whites() {
		int num = 0;
		for (String b : board) {
			if (b.equals(WHITE))
				num++;
		}
		return num;
	}

	public int blacks() {
		int num = 0;
		for (String b : board) {
			if (b.equals(BLACK))
				num++;
		}
		return num;
	}

	public boolean ifChanges(int col, int row, String player, boolean setFlags) {
		clearChangables();
		changes = howManyInColumnChangable(col, row, player, setFlags)
				+ howManyInRowChangable(row, player, col, setFlags)
				+ howManyInDiagonalsChangable(col, row, player, setFlags) + 1;
		return (changes > 1);
	}

	private int howManyInRowChangable(int row, String player, int column,
			boolean setFlags) {
		int first = -1;
		boolean newEffect = false;
		boolean oppositeFound = false;
		int total = 0;
		for (int col = 0; col < width; col++) {
			if (oppositeFound
					&& ((getValue(col, row).equals(player) && newEffect) || (column == col))) {
				total += col - first - 1;
				setChangables(first, row, col, row, setFlags);
				first = col;
				if (column == col)
					newEffect = true;
				else
					newEffect = false;
				oppositeFound = false;

			} else if ((getValue(col, row).equals(player) || (column == col))) {
				first = col;
				if (column == col)
					newEffect = true;
				else
					newEffect = false;
			} else if (getValue(col, row).equals(EMPTY)) {
				first = -1;
				oppositeFound = false;
			} else if (first != -1) {
				oppositeFound = true;
			}
		}
		return total;
	}

	private int howManyInColumnChangable(int column, int irow, String player,
			boolean setFlags) {
		int first = -1;
		boolean newEffect = false;

		boolean oppositeFound = false;
		int total = 0;
		for (int row = 0; row < width; row++) {
			if (oppositeFound
					&& ((getValue(column, row).equals(player) && newEffect) || (row == irow))) {
				total += row - first - 1;
				setChangables(column, first, column, row, setFlags);
				first = row;
				if (row == irow)
					newEffect = true;
				else
					newEffect = false;
				oppositeFound = false;

			} else if ((getValue(column, row).equals(player) || (row == irow))) {
				first = row;
				if (row == irow)
					newEffect = true;
				else
					newEffect = false;
			} else if (getValue(column, row).equals(EMPTY)) {
				first = -1;
				oppositeFound = false;
			} else if (first != -1) {
				oppositeFound = true;
			}
		}
		return total;
	}

	private int howManyInDiagonalsChangable(int col, int row, String player,
			boolean setFlags) {
		int first = -1;
		boolean oppositeFound = false;
		int total = 0;
		int dif = col - row;
		boolean newEffect = false;
		int c = 0;
		for (int r = 0; r < width; r++) {
			c = r + dif;
			if (c >= width || c < 0)
				continue;
			if (oppositeFound
					&& ((getValue(c, r).equals(player) && newEffect) || (row == r && c == col))) {
				total += r - first - 1;
				setChangables(first + dif, first, c, r, setFlags);
				first = r;
				if (row == r && c == col)
					newEffect = true;
				else
					newEffect = false;
				oppositeFound = false;
			} else if ((getValue(c, r).equals(player) || (row == r && c == col))) {
				first = r;
				if (row == r && c == col)
					newEffect = true;
				else
					newEffect = false;
			} else if (getValue(c, r).equals(EMPTY)) {
				first = -1;
				oppositeFound = false;
			} else if (first != -1) {
				oppositeFound = true;
			}
		}
		first = -1;
		oppositeFound = false;
		newEffect = false;
		c = 0;
		dif = (width - 1 - col) - row;
		for (int r = 0; r < width; r++) {
			c = (width - 1) - dif - r;
			if (c >= width || c < 0)
				continue;
			if (oppositeFound
					&& ((getValue(c, r).equals(player) && newEffect) || (row == r && c == col))) {
				total += r - first - 1;
				setChangables(width - 1 - first - dif, first, c, r, setFlags);
				first = r;
				if (row == r && c == col)
					newEffect = true;
				else
					newEffect = false;
				oppositeFound = false;
			} else if ((getValue(c, r).equals(player) || (row == r && c == col))) {
				first = r;
				if (row == r && c == col)
					newEffect = true;
				else
					newEffect = false;
			} else if (getValue(c, r).equals(EMPTY)) {
				first = -1;
				oppositeFound = false;
			} else if (first != -1) {
				oppositeFound = true;
			}
		}
		return total;
	}

	public void setChangables(int c1, int r1, int c2, int r2, boolean setFlags) {
		if (!setFlags)
			return;
		int r = Math.min(r1, r2);
		int rm = Math.max(r1, r2);

		int c = Math.min(c1, c2);
		int cm = Math.max(c1, c2);
		int total = 0;

		if (r == rm) {
			for (int i = c; i < cm; i++) {
				changables[getAbsPosition(i, r)] = true;
				total++;
			}
		} else if (c == cm) {
			for (int i = r; i < rm; i++) {
				changables[getAbsPosition(c, i)] = true;
				total++;
			}
		} else {
			if (c2 < c1) {
				int dif = (width - 1 - c1) - r1;
				for (int i = r1; i < r2; i++) {
					c = width - 1 - dif - i;
					changables[getAbsPosition(c, i)] = true;
					total++;
				}
			} else {
				int dif = c1 - r1;
				for (int i = r1; i < r2; i++) {
					c = i + dif;
					changables[getAbsPosition(c, i)] = true;
					total++;
				}
			}
		}
		// System.out.println("changed: " + total);

	}

	public int getNumberOfMarkedPositions() {
		int retVal = 0;
		for (int col = 0; col < width; col++) {
			for (int row = 0; row < width; row++) {
				if (!(isEmpty(col, row))) {
					retVal++;
				}
			}
		}
		return retVal;
	}

	public Set<OthelloAction> getAllUnMarkedPositions() {
		Set<OthelloAction> result = new HashSet<OthelloAction>();
		for (int col = 0; col < width; col++) {
			for (int row = 0; row < width; row++) {
				if (isEmpty(col, row)) {
					result.add(new OthelloAction(col, row));
				}
			}
		}
		return result;
	}

	public List<OthelloAction> getUnMarkedPositions(String player) {
		List<OthelloAction> result = new ArrayList<OthelloAction>();
		boolean hasEmpty = false;
		for (int col = 0; col < width; col++) {
			for (int row = 0; row < width; row++) {
				if (isEmpty(col, row)) {
					hasEmpty = true;
					if (ifChanges(col, row, player, false)) {
						result.add(new OthelloAction(col, row));
					}
				}
			}
		}
		if (result.isEmpty() && !hasEmpty)
			result.add(new OthelloSkipAction());
		return result;
	}

	public void clearChangables() {
		for (int i = 0; i < OthelloState.width * OthelloState.width; i++) {
			changables[i] = false;
		}
	}

	@Override
	public OthelloState clone() {
		OthelloState copy = null;
		try {
			copy = (OthelloState) super.clone();
			copy.board = Arrays.copyOf(board, board.length);
			copy.changables = Arrays.copyOf(changables, changables.length);
			copy.playerToMove = playerToMove;
			copy.utility = utility;
			copy.changes = changes;
		} catch (CloneNotSupportedException e) {
			e.printStackTrace(); // should never happen...
		}
		return copy;
	}

	@Override
	public boolean equals(Object anObj) {
		if (anObj != null && anObj.getClass() == getClass()) {
			OthelloState anotherState = (OthelloState) anObj;
			for (int i = 0; i < OthelloState.width * OthelloState.width; i++) {
				if (board[i] != anotherState.board[i]) {
					return false;
				}
			}
			return true;
		}
		return false;
	}

	@Override
	public int hashCode() {
		// Need to ensure equal objects have equivalent hashcodes (Issue 77).
		return toString().hashCode();
	}

	@Override
	public String toString() {
		StringBuilder strBuilder = new StringBuilder();
		for (int row = 0; row < width; row++) {
			for (int col = 0; col < width; col++) {
				strBuilder.append(getValue(col, row) + " ");
			}
			strBuilder.append("\n");
		}
		return strBuilder.toString();
	}

	//
	// PRIVATE METHODS
	//

	private int getAbsPosition(int col, int row) {
		return row * width + col;
	}

	public String getWinner() {
		analyzeUtility();
		if (utility == 1) {
			return WHITE;
		} else if (utility == 0) {
			return BLACK;
		} else
			return "DRAW";
	}
}
