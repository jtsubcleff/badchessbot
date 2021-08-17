package application;

import javafx.animation.Animation;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import java.net.URL;
import java.util.ResourceBundle;


public class Controller implements Initializable {
	
    @FXML
    private AnchorPane board;
    
    private Timeline time;
    private Timeline fpsLock;
    private boolean selected = false;
    private int[] selSquare;


    @Override
    public void initialize(final URL location, final ResourceBundle resources) {
    	
    	board.setOnMouseClicked(e -> toggleSel(e.getX(), e.getY()));
        selSquare = new int[2];

      
        time = new Timeline();
        time.setCycleCount(Timeline.INDEFINITE);
        //time.getKeyFrames().add(new KeyFrame(Duration.seconds(1), e -> advanceTime(1)));
        
        fpsLock = new Timeline();
        fpsLock.setCycleCount(Animation.INDEFINITE);
        fpsLock.setRate(30);
       
        fpsLock.play();


       
    }

    private void toggleSel(double x, double y) {
		System.out.println("hello");
	}

}