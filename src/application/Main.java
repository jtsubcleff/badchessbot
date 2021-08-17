
/*
package application;
	
import java.io.IOException;
import java.net.URL;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;




public class Main extends Application {
	 private GraphicsContext gc;
	 private Scene scene;
	 @Override
	    public void start(Stage stage) {
	        try {
	            final URL r = getClass().getResource("chessboard.fxml");
	            if (r == null) {
	                System.err.println("No FXML resource found.");
	                try {
	                    stop();
	                } catch (final Exception e) {
	                }
	            }
	            Pane node = FXMLLoader.load(r);
	            node.setOnMouseClicked(e -> toggleSel(e.getX(), e.getY()));
	            scene = new Scene(node);
	            stage.setTitle("FXML demo");
	            stage.setResizable(true);
	            stage.setScene(scene);
	            stage.sizeToScene();
	            stage.show();
	            

	        } catch (final IOException ioe) {
	            System.err.println("Can't load FXML file.");
	            ioe.printStackTrace();
	            try {
	                stop();
	            } catch (final Exception e) {
	            }
	        }
	    }
	 private void toggleSel(double x, double y) {
			System.out.println(x);
	}
	private void refresh() {
		
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
*/
package application;


import java.util.Arrays;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;
 
public class Main extends Application {
	
	ChessBoard cb;
	Board b;
	boolean isSelected = false;
	int[] selSquare;
	private Timeline fpsLock;
	Engine engine;
    public static void main(String[] args) {
        launch(args);
    }
    
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Hello World!");
        Button btn = new Button();
        Canvas c = new Canvas(300,300);
     // Get the graphics context of the canvas
        GraphicsContext gc = c.getGraphicsContext2D();
         
        // Draw a Text
        gc.strokeText("Hello Canvas", 150, 100);
        btn.setText("Say 'Hello World'");
        btn.setOnAction(new EventHandler<ActionEvent>() {
 
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Hello World!");
            }
        });
        fpsLock = new Timeline();
        fpsLock.setCycleCount(Animation.INDEFINITE);
        fpsLock.setRate(30);
        fpsLock.getKeyFrames().add(new KeyFrame(Duration.seconds(1), e -> gameLoop(selSquare)));
        fpsLock.play();
        StackPane root = new StackPane();
        b = new Board(600, 600);
        b.setOnMouseClicked(e -> click(e.getX(), e.getY()));
        cb = new ChessBoard(true);
        engine = new Engine();
        b.refresh(cb.board, selSquare);
        root.getChildren().add(b);
        primaryStage.setScene(new Scene(root, 600, 600));
        primaryStage.show();
    }
    private void gameLoop(int[] s) {
    	if (cb.isMate) {
    		System.out.println("GAME OVER!");
    		System.exit(0);
    	} else {
    		b.refresh(cb.board, s);
    		//System.out.println(engine.matCount(cb.board));
    	}
    }
    private void click(double x, double y) {
    	
		if (cb.toMove == "black") {
			int d = 3;
			if (cb.moveCount >= 15)
				d = 4;
			cb.board = cb.calc(cb.board,d , "black");
			cb.toMove = "white";
		}
    	int[] selCoords = b.convertCoords(x,y);
		if (isSelected) {
			cb.attemptMove(selSquare, selCoords);
			selSquare = null;
		} else {
			selSquare = selCoords;
		}
		isSelected = !isSelected;
		b.refresh(cb.board, selSquare);
    }	
}