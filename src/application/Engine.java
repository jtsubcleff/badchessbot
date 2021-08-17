package application;

import java.util.HashSet;

public class Engine {
	int matcount;
	
	public int matCount(Piece[][] board) {
		int matdiff = 0;
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j ++) {
				if (board[i][j] != null) {
					matdiff += board[i][j].val;
				}
			}
		}
		matcount = matdiff;
		return matdiff;
	}
	
	public void calc(Piece[][] board, int depth) {
		HashSet<Piece[][]> boards = new HashSet<>();
		boards.add(board);
		
	}
	

}
