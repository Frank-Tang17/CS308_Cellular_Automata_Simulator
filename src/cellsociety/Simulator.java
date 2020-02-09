package cellsociety;

import java.security.Key;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.util.Duration;


/**
 * Feel free to completely change this code or delete it entirely.
 */
public class Simulator {

  private static final int FRAMES_PER_SECOND = 1;
  private static final int MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
  private boolean runSimulation = true;
  private double simulationRate;
  private final double stepForwardRate = 10;
//  private final double minSimulationRate = 1;
  private int frameCounter = 0;
  private int forwardFrameCounter;
  private int framesToStepForward = 1;
  private int newState = 0;

  private Configuration simulationLoaded;
  private PolygonGrid shapeGrid;
  private SimulationGraph simulationGraph;

  private Timeline animation = new Timeline();

  private Grid mainGrid;
  private Grid updateGrid;
  private Scene scene;


  public Simulator(String selectedSimulation, Scene userInterfaceScene, String languageSelected) {
    scene = userInterfaceScene;
    scene.setOnKeyPressed(e -> handleKeyInput(e.getCode()));
    scene.setOnMouseClicked(e -> handleMouseInput(e.getX(), e.getY()));
    simulationLoaded = new Configuration(selectedSimulation);
    mainGrid = new Grid(simulationLoaded);
    updateGrid = new Grid(simulationLoaded);
    shapeGrid = new PolygonGrid(mainGrid);
    simulationGraph = new SimulationGraph(selectedSimulation, languageSelected);
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
    if(runSimulation) {
      updateGrid.updateGrid(mainGrid);
      mainGrid.copyGrid(updateGrid);

      shapeGrid.updateGridAppearance(mainGrid);

      mainGrid.updateStateTotal();
      simulationGraph.updateGraph(frameCounter, mainGrid.getNumberofCellState0(),
          mainGrid.getNumberofCellState1(), mainGrid.getNumberofCellState2());

      frameCounter++;
      checkSimulationForward();
    }
  }

  public void invertRunSimulationStatus(){
    runSimulation = !runSimulation;
  }

  public void pauseResume() {
    if(runSimulation){
      animation.pause();
    }
    else{
      animation.play();
    }
    invertRunSimulationStatus();
  }

  public void stepForward(){
    forwardFrameCounter = frameCounter + framesToStepForward;
    animation.setRate(stepForwardRate);
    pauseResume();
  }

  public void checkSimulationForward(){
    if(frameCounter == forwardFrameCounter){
      pauseResume();
      animation.setRate(simulationRate);
    }

  }

  public void setSimulationRate(double simulationSliderRate){
    simulationRate = simulationSliderRate;
    animation.setRate(simulationRate);
  }

//  public void speedUpSimulation() {
//    if (simulationRate <= maxSimulationRate) {
//      simulationRate *= 2;
//      animation.setRate(simulationRate);
//    }
//  }
//
//  public void slowDownSimulation() {
//    if (simulationRate >= minSimulationRate) {
//      simulationRate /= 2;
//      animation.setRate(simulationRate);
//    }
//  }

  public void checkMouseClick(Grid displayedGrid, double x, double y){
    for (int i = 0; i < displayedGrid.getHeight(); i++) {
      for (int j = 0; j < displayedGrid.getWidth(); j++) {
        if(shapeGrid.getRectangle(j, i).getBoundsInLocal().contains(x, y)){
          displayedGrid.getCell(i, j).setCellState(newState);
          shapeGrid.updateCellAppearance(j, i, displayedGrid.getCell(i, j));
        }
      }
    }
  }

  private void handleKeyInput (KeyCode code) {
    if(code == KeyCode.DIGIT0){
      newState = 0;
    }
    else if(code == KeyCode.DIGIT1){
      newState = 1;
    }
    else if(code == KeyCode.DIGIT2){
      newState = 2;
    }
  }

  private void handleMouseInput (double x, double y) {
    checkMouseClick(updateGrid, x, y);
  }
}
