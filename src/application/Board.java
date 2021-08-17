package application;

import com.sun.prism.paint.Color;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Paint;


public class Board extends Canvas{
	private GraphicsContext gc;
	private final Image WPAWN = new Image(getClass().getResourceAsStream("Chess_plt60.png"));
    private final Image WKING = new Image(getClass().getResourceAsStream("Chess_klt60.png"));
    private final Image WQUEEN  = new Image(getClass().getResourceAsStream("Chess_qlt60.png"));
    private final Image WKNIGHT = new Image(getClass().getResourceAsStream("Chess_nlt60.png"));
    private final Image WBISHOP = new Image(getClass().getResourceAsStream("Chess_blt60.png"));
    private final Image WROOK = new Image(getClass().getResourceAsStream("Chess_rlt60.png"));
    private final Image BPAWN = new Image(getClass().getResourceAsStream("Chess_pdt60.png"));
    private final Image BKING = new Image(getClass().getResourceAsStream("Chess_kdt60.png"));
    private final Image BQUEEN  = new Image(getClass().getResourceAsStream("Chess_qdt60.png"));
    private final Image BKNIGHT = new Image(getClass().getResourceAsStream("Chess_ndt60.png"));
    private final Image BBISHOP = new Image(getClass().getResourceAsStream("Chess_bdt60.png"));
    private final Image BROOK = new Image(getClass().getResourceAsStream("Chess_rdt60.png"));
    private final Image[] WHITE_PIECES = {WPAWN, WKING, WQUEEN, WKNIGHT, WBISHOP, WROOK};
    private final Image[] BLACK_PIECES = {BPAWN, BKING, BQUEEN, BKNIGHT, BBISHOP, BROOK};
    double sideLength;
    
	
	
	public Board(double width, double height) {
		super(width, height);
		gc = this.getGraphicsContext2D();
        gc.beginPath();
        gc.setLineWidth(1.5);
        sideLength = this.getHeight() / 8;
	}
	
	public void refresh(Piece[][] board, int[] sel) {
		gc.clearRect(0, 0, this.getWidth(), this.getHeight());
		for (int i =7; i > -1; i--) {
			for (int j = 0; j < 8; j++) {
				double[] y = {i * sideLength, (i+1) * sideLength, (i+1) * sideLength, (i) * sideLength};
				double[] x = {j * sideLength, (j) * sideLength, (j+1) * sideLength, (j+1) * sideLength};
				
				if (i % 2 ==  j % 2) {
					gc.setFill(Paint.valueOf("white"));
				} else {
					gc.setFill(Paint.valueOf("green"));
				}	
				if (sel != null && (7-i) == sel[1] && (j) == sel[0]) {
					gc.setFill(Paint.valueOf("yellow"));
				}
				gc.fillPolygon(x, y, 4);
				gc.strokePolygon(x, y, 4);
				if (board[7-i][j] != null) {
					Image[] color = board[7-i][j].color == "white" ? WHITE_PIECES : BLACK_PIECES;
					switch (board[7-i][j].type) {
					case PAWN:
						gc.drawImage(color[0], j * sideLength, i * sideLength, sideLength, sideLength);
						break;
					case KING:
						gc.drawImage(color[1], j * sideLength, i * sideLength, sideLength, sideLength);
						break;
					case QUEEN:
						gc.drawImage(color[2], j * sideLength, i * sideLength, sideLength, sideLength);
						break;
					case KNIGHT:
						gc.drawImage(color[3], j * sideLength, i * sideLength, sideLength, sideLength);
						break;
					case BISHOP:
						gc.drawImage(color[4], j * sideLength, i * sideLength, sideLength, sideLength);
						break;
					case ROOK:
						gc.drawImage(color[5], j * sideLength, i * sideLength, sideLength, sideLength);
						break;
					
					}
				}
			}
		}
	}

	
	public int[] convertCoords(double x, double y) {
		int newX = (int) Math.floor(x / sideLength);
		int newY = 7 - (int) Math.floor(y / sideLength);
		int[] a = new int[2];
		a[0] = newX;
		a[1] = newY;
		return a;
		
	}
	

}
