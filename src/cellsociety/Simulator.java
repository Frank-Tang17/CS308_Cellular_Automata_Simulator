package cellsociety;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.stage.Stage;
import javafx.util.Duration;


/**
 * Feel free to completely change this code or delete it entirely.
 */
public class Simulator {

  private static final int FRAMES_PER_SECOND = 1;
  private static final int MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
  private boolean runSimulation = true;
  private double simulationRate = 1;
  private final double maxSimulationRate = 16;
  private final double minSimulationRate = 0.0625;
  private int frameCounter = 0;
  private int forwardFrameCounter;
  private int framesToStepForward = 1;
  private Configuration simulationLoaded;
  private PolygonGrid shapeGrid;
  private SimulationGraph simulationGraph;



  private Timeline animation = new Timeline();

  private Grid mainGrid;
  private Grid updateGrid;


  public Simulator(String selectedSimulation) {
    simulationLoaded = new Configuration(selectedSimulation);
    mainGrid = new Grid(simulationLoaded);
    updateGrid = new Grid(simulationLoaded);
    shapeGrid = new PolygonGrid(mainGrid);
    simulationGraph = new SimulationGraph();

  }


  public void runSimulation(Group grid){
    KeyFrame frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> {
      step();
    });
    animation.setCycleCount(Timeline.INDEFINITE);
    animation.getKeyFrames().add(frame);
    animation.play();
    shapeGrid.displayGrid(mainGrid, grid);

  }
  private void step() {
    updateGrid.updateGrid(mainGrid);
    mainGrid.copyGrid(updateGrid);
    shapeGrid.updateGridAppearance(mainGrid);
    mainGrid.updateStateTotal();
    simulationGraph.updateGraph(frameCounter, mainGrid.getNumberofCellState0(), mainGrid.getNumberofCellState1(), mainGrid.getNumberofCellState2());
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
