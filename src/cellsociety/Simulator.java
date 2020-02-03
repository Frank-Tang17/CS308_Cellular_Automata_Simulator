package cellsociety;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.shape.StrokeType;
import javafx.util.Duration;

/**
 * Feel free to completely change this code or delete it entirely.
 */
public class Simulator {

  private static final int FRAMES_PER_SECOND = 1;
  private static final int MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
  private static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;
  public static final StrokeType cellStrokeType = StrokeType.CENTERED; // INSIDE, OUTSIDE, or CENTERED
  public static final double cellStrokeProportion = 0.1;
  private boolean runSimulation = true;
  private double simulationRate = 1;
  private Timeline animation = new Timeline();

  private Grid mainGrid;
  private Grid updateGrid;

  public Simulator(String selectedSimulation) {
    mainGrid = new Grid(50, 50);
    updateGrid = new Grid(50, 50);
  }

  public void test(Group grid){
    KeyFrame frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> {
      step();
    });
    animation.setCycleCount(Timeline.INDEFINITE);
    animation.getKeyFrames().add(frame);
    animation.play();

    mainGrid.gridVisualization(grid);

  }
  private void step() {
    if (runSimulation) {
      updateGrid.updateGrid(mainGrid);
      mainGrid.copyGrid(updateGrid);
    }
  }



  public void pauseResume() {
    runSimulation = !runSimulation;
  }

  public boolean getSimulationStatus() {
    return runSimulation;
  }

  public void speedUpSimulation() {
    simulationRate *= 2;
    animation.setRate(simulationRate);
  }

  public void slowDownSimulation() {
    simulationRate /= 2;
    animation.setRate(simulationRate);
  }

}
