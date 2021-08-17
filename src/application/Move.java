package application;

public class Move {
	public final Piece p;
	public final int c;
	public final int r;
	public Move(Piece p, int c, int r) {
		if (c < 8 && c >= 0 && r < 8 && r>= 0 ) {
			this.p = p;
			this.r = r;
			this.c = c;
		} else {
			throw new ArrayIndexOutOfBoundsException();
		}
	}
	
	public String toString() {
		return p.toString() + " to " + (char)(c + 97) + "" + ((int)r+1);
	}
	
	public boolean equals(Object o) {
		if (o instanceof Move) {
			Move m = (Move) o;
			return p.equals(m.p) && c == m.c && r == m.r;
		}
		return false;
	}
	
}
