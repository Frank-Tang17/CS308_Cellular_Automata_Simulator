package cellsociety.view;

import cellsociety.configuration.Configuration;
import cellsociety.model.Grid;
import cellsociety.view.gridshapes.HexagonGrid;
import cellsociety.view.gridshapes.RectangleGrid;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.util.Duration;


/**
 * Class that acts as a simulation object -- in charge of running the simulation in the window
 *
 * @author Frank Tang
 */
public class Simulator {

  private static final int FRAMES_PER_SECOND = 1;
  private static final int MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
  private boolean runSimulation = true;
  private double simulationRate;
  private final double stepForwardRate = 10;
  private int frameCounter = 0;
  private int forwardFrameCounter;
  private int framesToStepForward = 1;
  private int newState = 0;

  private Configuration simulationConfiguration;
  private ShapeGrid shapeGrid;
  private SimulationGraph simulationGraph;

  private Timeline animation = new Timeline();

  private Grid mainGrid;
  private Grid updateGrid;
  private Scene scene;

  /**
   * Makes a new Simulator object that will run a simulation based on a configuration file Sets up
   * the Grid objects and makes a ShapeGrid object based on certain values in the Configuration and
   * values from the Grid objects.
   */
  public Simulator(Configuration passedConfiguration, String selectedSimulation,
      Scene userInterfaceScene, String languageSelected) {
    scene = userInterfaceScene;
    scene.setOnKeyPressed(e -> handleKeyInput(e.getCode()));
    scene.setOnMouseClicked(e -> handleMouseInput(e.getX(), e.getY()));
    simulationConfiguration = passedConfiguration;
    mainGrid = new Grid(simulationConfiguration);
    updateGrid = new Grid(simulationConfiguration);
    if (simulationConfiguration.isHexagonal()) {
      shapeGrid = new HexagonGrid(mainGrid, simulationConfiguration);
    } else {
      shapeGrid = new RectangleGrid(mainGrid, simulationConfiguration);
    }
    simulationGraph = new SimulationGraph(selectedSimulation, languageSelected);
  }

  /**
   * Method to start the simulation by setting up a Timeline and displaying the grid
   */
  public void runSimulation(Group grid) {
    KeyFrame frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> {
      step();
    });
    animation.setCycleCount(Timeline.INDEFINITE);
    animation.getKeyFrames().add(frame);
    animation.play();
    shapeGrid.displayGrid(mainGrid, grid);
  }

  /**
   * Method that will process the simulation rules as each frame of the simulation occurs
   */
  private void step() {
    if (runSimulation) {
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

  /**
   * Method to toggle whether the simulation is running or not
   */
  public void invertRunSimulationStatus() {
    runSimulation = !runSimulation;
  }

  /**
   * Method to pause and resume the simulation
   */
  public void pauseResume() {
    if (runSimulation) {
      animation.pause();
    } else {
      animation.play();
    }
    invertRunSimulationStatus();
  }

  /**
   * Method that pauses the simulation and allows the user to step through a simulation frame by
   * frame
   */
  public void stepForward() {
    forwardFrameCounter = frameCounter + framesToStepForward;
    animation.setRate(stepForwardRate);
    pauseResume();
  }

  /**
   * Method that is used to check if the simulation needs to go frame by frame, and will pause the
   * simulation on the correct frame.
   */
  public void checkSimulationForward() {
    if (frameCounter == forwardFrameCounter) {
      pauseResume();
      animation.setRate(simulationRate);
    }

  }

  /**
   * Method that sets the rate of the simulation
   */
  public void setSimulationRate(double simulationSliderRate) {
    simulationRate = simulationSliderRate;
    animation.setRate(simulationRate);
  }

  /**
   * Method that checks if a cell in the grid has been clicked. Used to dynamically change the state
   * of the cell.
   */
  public void dynamicCellStateChange(Grid displayedGrid, double x, double y) {
    for (int i = 0; i < displayedGrid.getHeight(); i++) {
      for (int j = 0; j < displayedGrid.getWidth(); j++) {
        if (shapeGrid.getPolygon(j, i).getBoundsInLocal().contains(x, y)) {
          displayedGrid.getCell(i, j).setCellState(newState);
          shapeGrid.updateCellAppearance(j, i, displayedGrid.getCell(i, j));
        }
      }
    }
  }

  /**
   * Method to allow the user to choose what state to set a cell to be when clicked
   */
  private void handleKeyInput(KeyCode code) {
    if (code == KeyCode.DIGIT1) {
      newState = 0;
    } else if (code == KeyCode.DIGIT2) {
      newState = 1;
    } else if (code == KeyCode.DIGIT3) {
      newState = 2;
    }
  }

  /**
   * Method to check where the mouse has clicked and will run the cell state change method
   */
  private void handleMouseInput(double x, double y) {
    dynamicCellStateChange(updateGrid, x, y);
  }
}
