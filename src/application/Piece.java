package application;

import application.ChessBoard.Pieces;

public class Piece {
	String color;
	Pieces type;
	int val;
	public Piece(String color, Pieces type) {
		this.color = color;
		this.type = type;
		int mult = color == "white" ? 1 : -1;
		switch (type) {
		case PAWN:
			val = 1;
			break;
		case KNIGHT:
			val = 3;
			break;
		case BISHOP:
			val = 3;
			break;
		case ROOK:
			val = 5;
			break;
		case KING:
			val = 2000;
			break;
		case QUEEN:
			val = 9;
			break;
		}
		val *= mult;
	}
	
	public String toString() {
		return color + " " + type.toString();
	}

	public boolean equals(Object o) {
		if (o instanceof Piece) {
			Piece p = (Piece) o;
			return color == p.color && type == p.type;
		}
		return false;
	}
}
