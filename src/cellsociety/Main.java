package cellsociety;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.util.Duration;

/**
 * Feel free to completely change this code or delete it entirely.
 */
public class Main extends SimulationLoader {
  private static final int FRAMES_PER_SECOND = 1;
  private static final int MILLISECOND_DELAY = 1000/FRAMES_PER_SECOND;
  private static final double SECOND_DELAY = 1.0/FRAMES_PER_SECOND;
  public static final StrokeType cellStrokeType = StrokeType.CENTERED; // INSIDE, OUTSIDE, or CENTERED
  public static final double cellStrokeProportion = 0.05;
  private static boolean runSimulation = true;
  private static int simulationGridSize = 450;

  private static double simulationRate = 1;
  private static Timeline animation = new Timeline();

  private static Rectangle display = new Rectangle((SIZE - simulationGridSize)/2, gameStatusDisplayHeight, simulationGridSize,simulationGridSize);
  private static int framingTest = 0;

  public static void test() {
    KeyFrame frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> {step(SECOND_DELAY);});
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
  public static void speedUpSimulation(){
    simulationRate *= 2;
    animation.setRate(simulationRate);
  }
  public static void slowDownSimulation(){
    simulationRate /= 2;
    animation.setRate(simulationRate);
  }

}
