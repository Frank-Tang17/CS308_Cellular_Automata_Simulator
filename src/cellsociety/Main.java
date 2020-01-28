package cellsociety;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * Feel free to completely change this code or delete it entirely. 
 */
public class Main extends SimulationLoader {
    private static final int FRAMES_PER_SECOND = 1;
    private static final int MILLISECOND_DELAY = 1000/FRAMES_PER_SECOND;
    private static final double SECOND_DELAY = 1.0/FRAMES_PER_SECOND;
    public static boolean runSimulation = true;

    private static double simulationRate = 1;

    private static Rectangle display = new Rectangle(0,gameStatusDisplayHeight, SIZE,400);
    private static int framingTest = 0;

    public static void test() {
        KeyFrame frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> {step(SECOND_DELAY);});
        Timeline animation = new Timeline();
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.getKeyFrames().add(frame);
        animation.play();
        SimulationLoader.root.getChildren().add(display);
    }
    private static void step (double elapsedTime) {
        if(runSimulation) {
            if (framingTest % 2 == 0) {
                display.setFill(Color.RED);
            } else {
                display.setFill(Color.BLUE);
            }
            framingTest++;

        }
    }

    public static void pauseResume(){
        runSimulation = !runSimulation;
    }
//    public static void speedUpSimulation(){
//        animation.setRate();
//    }
//    public static void slowDownSimulation(){
//        runSimulation = !runSimulation;
//    }

}
