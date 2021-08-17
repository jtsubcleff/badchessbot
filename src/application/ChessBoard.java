package application;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

public class ChessBoard {
	
	Piece[][] board = new Piece[8][8];
	ArrayList<Move> history = new ArrayList<>();
	String toMove = "white";
	boolean isMate = false;
	final boolean isWhite;
	int moveCount;
	public ChessBoard(boolean isWhite) {
		this.isWhite = isWhite;
		String bottomColor = isWhite ? "white" : "black";
		String topColor = isWhite ? "black" : "white";
		for (int j = 0; j < 8; j++) {
			board[1][j] = new Piece(bottomColor, Pieces.PAWN);
			board[6][j] = new Piece(topColor, Pieces.PAWN);
		}		
		board[0][0] = new Piece(bottomColor, Pieces.ROOK);
		board[0][7] = new Piece(bottomColor,Pieces.ROOK);
		board[7][0] = new Piece(topColor, Pieces.ROOK);
		board[7][7] = new Piece(topColor, Pieces.ROOK);
		
		board[0][1] = new Piece(bottomColor, Pieces.KNIGHT);
		board[0][6] = new Piece(bottomColor, Pieces.KNIGHT);
		board[7][1] = new Piece(topColor, Pieces.KNIGHT);
		board[7][6] = new Piece(topColor, Pieces.KNIGHT);
		board[0][2] = new Piece(bottomColor, Pieces.BISHOP);
		board[0][5] = new Piece(bottomColor,Pieces.BISHOP);
		board[7][2] = new Piece(topColor,Pieces.BISHOP);
		board[7][5] = new Piece(topColor,Pieces.BISHOP);
		
		board[0][3] = isWhite ? new Piece(bottomColor, Pieces.QUEEN) : new Piece(bottomColor, Pieces.KING);
		board[0][4] = !isWhite ? new Piece(bottomColor, Pieces.QUEEN) : new Piece(bottomColor, Pieces.KING);
		board[7][3] = isWhite ? new Piece(topColor, Pieces.QUEEN) : new Piece(topColor, Pieces.KING);
		board[7][4] = !isWhite ? new Piece(topColor, Pieces.QUEEN) : new Piece(topColor, Pieces.KING);
	}
	public enum Pieces {
		
		PAWN("pawn"), KING("king"), QUEEN("queen"), ROOK("rook"), BISHOP("bishop"), KNIGHT("knight");
		
		private String strRep;
		
		private Pieces(String strRep) {
			this.strRep = strRep;
		}
		public String toString() {
			return strRep;
		}
	}
	public Piece[][] move(Piece[][] b, Move m, int x1, int y1, String toMove) {
		if (b[y1][ x1] != null &&  b[y1][ x1].color == toMove && isValid(b[y1][ x1],x1,y1, m.c, m.r)) {
			if (b[y1][ x1].type == Pieces.KING && ((b[y1][ x1].color == "white" && y1== 0 && m.r== 0) || (b[y1][ x1].color == "black" && y1== 7 && m.r== 7) )) {
				if (m.c == 2) {
					b[m.r][3] = b[m.r][0];
					b[m.r][0] = null;
				} else if (m.c == 6) {
					
					b[m.r][5] = b[m.r][7];
					b[m.r][7] = null;
				}
			}

			b[m.r][m.c] = b[y1][x1];
			b[y1][x1] = null;

		}
		return b;
	}


	public void attemptMove(int[] start, int[] end) {
	
		Piece[][] backup = shallowCopy(board);
		if (board[start[1]][ start[0]] != null &&  board[start[1]][ start[0]].color == toMove && isValid(board[start[1]][ start[0]],start[0],start[1], end[0], end[1])) {
			if (board[start[1]][ start[0]].type == Pieces.KING && ((board[start[1]][ start[0]].color == "white" && start[1]== 0 && end [1]== 0) || (board[start[1]][ start[0]].color == "black" && start[1]== 7 && end [1]== 7) )) {
				if (end[0] == 2) {
					board[end[1]][3] = board[end[1]][0];
					board[end[1]][0] = null;
				} else if (end[0] == 6) {
					
					board[end[1]][5] = board[end[1]][7];
					board[end[1]][7] = null;
				}
			}
			if (board[start[1]][start[0]].type == Pieces.PAWN && board[end[1]][end[0]] == null && end[0] != start[0]) {

				
				board[start[1]][end[0]] = null;
			}
			board[end[1]][end[0]] = board[start[1]][start[0]];
			board[start[1]][start[0]] = null;
			if (inCheck(toMove, board)) {

				board = backup;
				return;
			}

			history.add(new Move(board[end[1]][end[0]], end[0], end[1]));
			if (toMove ==  "white")
				moveCount++;
			toMove = toMove == "white" ? "black" : "white";
		}
		System.out.println(moveCount);
		isMate = isCheckmate(board,toMove);
		
	}
	public boolean isValid(Piece p, int x1, int y1, int x2, int y2) {
		ArrayList<Move> validMoves = validMoves(board, p, x1, y1);
		//System.out.println(validMoves.toString());
		return validMoves.contains(new Move(p, x2, y2));
		
		
	}
	public ArrayList<Move> validMoves(Piece[][] board, Piece p, int x1, int y1) {
		ArrayList<Move> valid = new ArrayList<>();
		switch (p.type) {
		case PAWN:
			int ydif = p.color == "white" ? 1 : -1;
			int startRank = p.color == "white" ? 1 : 6;

			if (get(board, x1, y1 + ydif) == null) {
				valid.add(new Move(p, x1, y1+ydif));
				if (y1 == startRank && (get(board, x1, y1 + (ydif*2)) == null)) {
					valid.add(new Move(p, x1, y1+(ydif*2)));
				}
			}
			int[] a = {x1 - 1, x1 + 1};
			for (int x : a) {
				
				if (get(board, x, y1 + ydif) != null && get(board, x, y1 + ydif).color != p.color) {
					valid.add(new Move(p, x, y1+ydif));
				}
			}
			
			Move mostRecent = history.size() == 0 ? null : history.get(history.size()-1);
			//System.out.println(mostRecent);
			if (mostRecent != null && mostRecent.r == y1 && Math.abs(mostRecent.c - x1) == 1 && mostRecent.p.color != p.color) {
				for (Move m : history) {
					if (m.c == mostRecent.c + ydif && m.r == mostRecent.r && m.p.color == mostRecent.p.color) {

						return valid;
					}
				}

				valid.add(new Move(p, mostRecent.c, mostRecent.r + ydif ));
			}
			return valid;
		case KING:
			int[] dirs = {-1, 0, 1};
			for (int i : dirs) {
				for (int j : dirs) {
					if (i != 0 || j != 0) {
						if (get(board, x1 + i, y1 + j) == null || get(board, x1 + i, y1 + j).color != p.color) {
							try {
								valid.add(new Move(p, x1+i, y1+j));
							} catch (ArrayIndexOutOfBoundsException e) {
								
							}
						}
					}
				}
			}
			if (((y1 == 0) || (y1  == 7) && x1 == 4)) {
				//short castle
				boolean shortCastle = true;
				boolean longCastle = true;
				for (Move m: history) {
					if (m.p == p || (m.p.color == p.color && m.p.type == Pieces.ROOK && m.c == 7 && m.r == y1)) {
						shortCastle = false;
					} else if (m.p == p || (m.p.color == p.color && m.p.type == Pieces.ROOK && m.c == 0 && m.r == y1)) {
						longCastle = false;
					}
				}
				if (shortCastle && board[y1][5] == null && board[y1][6] == null && board[y1][7] != null && board[y1][7].color == p.color && board[y1][7].type == Pieces.ROOK) {
					
					valid.add(new Move(p, 6, y1));
				}
				if (longCastle && board[y1][3] == null && board[y1][2] == null && board[y1][1] == null && board[y1][0] != null && board[y1][0].color == p.color && board[y1][0].type == Pieces.ROOK) {

					valid.add(new Move(p, 2, y1));
				}
				}
			return valid;
		case QUEEN:
			int[] dirs1 = {-1, 0, 1};
			for (int i : dirs1) {
				for (int j : dirs1) {
					if (i != 0 || j != 0) {
						continuousPieces(board, valid, p, x1, y1, i, j);
					}
				}
			}
			return valid;
		case KNIGHT:
			int[] ndirs = {-1, -2, 1, 2};
			for (int i: ndirs) {
				for (int j: ndirs) {
					if(Math.abs(i) != Math.abs(j)) {
						if (get(board, x1 + i, y1 + j) == null || get(board, x1 + i, y1 + j).color != p.color) {
							try {
								valid.add(new Move(p, x1+i, y1+j));
							} catch (ArrayIndexOutOfBoundsException e) {
								
							}
						}
					}
				}
			}
		return valid;
		case BISHOP:
			int[] bdirs = {1, -1};
			for (int i : bdirs) { 
				for (int j: bdirs) {
					continuousPieces(board, valid, p, x1, y1, i, j);
				}
			}
			return valid;
			
		case ROOK:
			int[] rdirs = {1, 0, -1};
			for (int i: rdirs) {
				for (int j : rdirs) {
					if(i != 0 && j == 0 || i == 0 && j != 0) {
						continuousPieces(board, valid, p, x1,y1, i, j);
					}
				}
			}
			
			return valid;
		}
		
		
		
		return valid;
	}
	
	public void continuousPieces(Piece[][] board, ArrayList<Move> valid, Piece p, int x1, int y1, int i, int j) {
		int x = x1;
		int y = y1;
		while (x >= 0 && x < 8 && y >= 0 && y < 8) {
			x += i;
			y += j;
			try {
				if (get(board, x,y) == null) {
					valid.add(new Move(p, x,y));
				} else {
					if (get(board, x,y).color != p.color) {
						valid.add(new Move(p,x,y));
					}
					break;
				}
			} catch (ArrayIndexOutOfBoundsException e) {}
		}
	}
	public Piece get(Piece[][] board, int x, int y) {
		try {
			return board[y][x];
		} catch (ArrayIndexOutOfBoundsException e) {
			return null;
		}
		
	}
	public boolean inCheck(String color, Piece[][] board) {
		int kX =  -1;
		int kY =  -1;
		ArrayList<Move> opponentMoves = new ArrayList<>();
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (board[j][i] != null && board[j][i].color != color) {
					opponentMoves.addAll(validMoves(board, board[j][i], i, j));
				}
				if (board[j][i] != null && board[j][i].equals(new Piece(color, Pieces.KING))) {
					kX = i;
					kY = j;
				}
			}
		}
		
		for (Move m: opponentMoves) {
			if (m.c == kX && m.r == kY) {
				return true;
			}
		}
		return false;
	}
	
	public Piece[][] shallowCopy(Piece[][] board) {
		Piece[][] newBoard = new Piece[8][8];
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (board[i][j] == null) {
					newBoard[i][j] = null;
				}else {
					newBoard[i][j] = new Piece(board[i][j].color, board[i][j].type);
				}
			}
		}
		return newBoard;
	}
	
	public boolean isCheckmate(Piece[][] board, String toMove) {
		String color = toMove;
		ArrayList<Move> moves = new ArrayList<>();
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (get(board, i, j) != null && get(board, i, j).color == color) {
					moves = validMoves(board, get(board, i,j), i, j);
				}
				for (Move m: moves) {
					Piece[][] temp = shallowCopy(board);
					temp = move(temp, m, i, j,toMove);
					
					
					if (!inCheck(color, temp)) {
						
						return false;
					}
				}
			}
		}
		return true;
	}
	public int matCount(Piece[][] board) {
		int matdiff = 0;
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j ++) {
				if (board[i][j] != null) {
					matdiff += board[i][j].val;
				}
			}
		}
		return matdiff;
	}
	
	public Piece[][] calc(Piece[][] board, int depth, String toMove) {
		System.out.println("-");
		boolean posIsGood = toMove == "white";
		ArrayList<TreeNode> boards = new ArrayList<>();
		TreeNode root = new TreeNode(board,null, true, null);
		boards.add(root);
		boolean samecol = false;
		for (int i = 0; i < depth; i++) {
			ArrayList<TreeNode> temp = new ArrayList<>();
			for (TreeNode t : boards) {
				if (isCheckmate(t.board, toMove)) {
					t.isMate = true;
					continue;
				}
				for (Pair p :allMoves(t.board,toMove)) {
					if(p.m.toString().equals("white bishop to a6")) {
						System.out.println("--");
						//printboard(t.board);
						System.out.println("--");
						//printboard(p.board);
						System.out.println("----");
					}
					t.children.add(new TreeNode(p.board,t,samecol,p.m));
				}
				temp.addAll(t.children);
			}
			boards = temp;
			toMove = toMove == "white" ? "black" : "white";
			samecol = !samecol;
		}
		HashSet<TreeNode> parents = new HashSet<>();
		System.out.println("***");
		for (TreeNode t : boards) {
			//System.out.println(t.path);
			if (t.path.toString().equals("white bishop to a6")) {
				//printboard(t.board);
			}
			t.score = matCount(t.board);
			//System.out.println(matCount(t.board));
			parents.add(t.parent);
		}
		System.out.println("***");
		while (!parents.contains(null)) {
			HashSet<TreeNode> temp = new HashSet<>();
			for (TreeNode p : parents) {
				if (!p.sameColor) {
					int m = Integer.MIN_VALUE;
					for (TreeNode c : p.children) {
						System.out.print(c.score + " " + c.path + ", ");
						if (c.score > m)
							m = c.score;
					}
					p.score = m;
				} else {
					int m = Integer.MAX_VALUE;
					for (TreeNode c : p.children) {
						System.out.print(c.score + " " + c.path + ", ");
						if (c.score < m)
							m = c.score;
					}
					p.score = m;
				}
				System.out.println(p.sameColor + " ");
				System.out.println(p.path);
				System.out.println(p.score);
				temp.add(p.parent);
			}
			parents = temp;
		}
		int m = posIsGood ? Integer.MIN_VALUE : Integer.MAX_VALUE;
		Piece[][] bb = null;
		for (TreeNode c : root.children) {
			//System.out.println(c.score);
			if (posIsGood && c.score > m) {
				m = c.score;
				bb = c.board;
			} else if (!posIsGood && c.score < m) {
				m = c.score;
				bb = c.board;
			}
		}
		//System.out.println("M" + m);
		return bb;
	}
	
	public HashSet<Pair> allMoves(Piece[][] board, String toMove) {
		HashSet<Pair> outcomes = new HashSet<>();
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j ++) {
				if (board[j][i] != null && board[j][i].color == toMove) {
					ArrayList<Move> vMoves = validMoves(board, board[j][i], i,j);
					for (Move m : vMoves) {
						Piece[][] temp = shallowCopy(board);
						temp = move(temp, m, i, j, toMove);
						if (!inCheck(toMove,temp)) 
							outcomes.add((new Pair(m,temp)));
					}
				}
			}
		}
		return outcomes;
		
	}
	public void printboard(Piece[][] board) {
		for (int j = 7; j >= 0; j--) {
			for (int i = 0; i < 8; i++) {
				if (board[j][i] == null) {
					System.out.print("  ");
					continue;
				}
				String c = board[j][i].color == "white" ? "W" : "B";
				switch (board[j][i].type) {
				case PAWN:
					System.out.print(c+"P");
					break;
				case KNIGHT:
					System.out.print(c+"N");
					break;
				
				case BISHOP:
					System.out.print(c+"B");
					break;
				case KING:
					System.out.print(c+"K");
					break;
				case QUEEN:
					System.out.print(c+"Q");
					break;
				case ROOK:
					System.out.print(c+"R");
					break;
				}
			}
			System.out.println();
		}

	}
	
}

