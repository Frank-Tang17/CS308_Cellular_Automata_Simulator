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
  private static final int MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
  private static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;
  private static boolean runSimulation = true;
  public static int simulationGridSize = 450;
  public static StrokeType cellStrokeType = StrokeType.INSIDE;
  public static int cellStrokeProportion = 2;

  private static double simulationRate = 1;
  private static Timeline animation = new Timeline();

  private static Grid mainGrid;
  private static Grid updateGrid;


  public static void test() {
    KeyFrame frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> {
      step(SECOND_DELAY);
    });
    animation.setCycleCount(Timeline.INDEFINITE);
    animation.getKeyFrames().add(frame);
    animation.play();
    mainGrid = new Grid(15,15);
    updateGrid = new Grid(15,15);
    mainGrid.gridVisualization();

  }

  private static void step(double elapsedTime) {
    if (runSimulation) {
      mainGrid.updateGrid(updateGrid);
      mainGrid.copyGrid(updateGrid);
    }
  }

    public static void pauseResume () {
      runSimulation = !runSimulation;
    }

    public static void speedUpSimulation () {
      simulationRate *= 2;
      animation.setRate(simulationRate);
    }

    public static void slowDownSimulation () {
      simulationRate /= 2;
      animation.setRate(simulationRate);
    }

  }
