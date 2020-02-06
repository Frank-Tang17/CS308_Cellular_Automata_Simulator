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
  public static final StrokeType cellStrokeType = StrokeType.CENTERED; // INSIDE, OUTSIDE, or CENTERED
  public static final double cellStrokeProportion = 0.1;
  private boolean runSimulation = true;
  private double simulationRate = 1;
  private final double maxSimulationRate = 16;
  private final double minSimulationRate = 0.0625;
  private int frameCounter = 0;
  private int forwardFrameCounter;
  private int framesToStepForward = 1;

  private Timeline animation = new Timeline();

  private Grid mainGrid;
  private Grid updateGrid;

  public Simulator(String selectedSimulation) {
    mainGrid = new Grid(selectedSimulation);
    updateGrid = new Grid(selectedSimulation);
  }

  public void runSimulation(Group grid){
    KeyFrame frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> {
      step();
    });
    animation.setCycleCount(Timeline.INDEFINITE);
    animation.getKeyFrames().add(frame);
    animation.play();
    mainGrid.gridVisualization(grid);

  }
  private void step() {
    updateGrid.updateGrid(mainGrid);
    mainGrid.copyGrid(updateGrid);
    frameCounter++;
    checkSimulationForward();
  }



  public void pauseResume() {
    if(runSimulation){
      animation.pause();
    }
    else{
      animation.play();
    }
    runSimulation = !runSimulation;
  }

  public void stepForward(){
    forwardFrameCounter = frameCounter + framesToStepForward;
    pauseResume();
  }

  public void checkSimulationForward(){
    if(frameCounter == forwardFrameCounter){
      pauseResume();
    }
  }

  public int getFrameCounter(){
    return frameCounter;
  }

  public boolean getSimulationStatus() {
    return runSimulation;
  }

  public double getSimulationRate(){
    return simulationRate;
  }

  public void speedUpSimulation() {
    if (simulationRate <= maxSimulationRate) {
      simulationRate *= 2;
      animation.setRate(simulationRate);
    }
  }

  public void slowDownSimulation() {
    if (simulationRate >= minSimulationRate) {
      simulationRate /= 2;
      animation.setRate(simulationRate);
    }
  }
}
