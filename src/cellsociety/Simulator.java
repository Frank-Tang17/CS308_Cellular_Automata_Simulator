package cellsociety;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
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
  private Configuration simulationLoaded;

  private static final int upperLeftX = 75;
  private static final int upperLeftY = 30;

  private Color[] cellFillColors = {Color.WHITE, Color.BLACK, Color.BLACK};
  private Color[] cellStrokeColors = {Color.GREY, Color.GREY, Color.GREY};
  private Rectangle[][] polygonGrid;

  private Timeline animation = new Timeline();

  private Grid mainGrid;
  private Grid updateGrid;

  public Simulator(String selectedSimulation) {
    simulationLoaded = new Configuration(selectedSimulation);
    mainGrid = new Grid(simulationLoaded);
    updateGrid = new Grid(simulationLoaded);
    polygonGrid = initializeRectangleGrid(mainGrid.getWidth(), mainGrid.getHeight());
  }

  public void runSimulation(Group grid){
    KeyFrame frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> {
      step();
    });
    animation.setCycleCount(Timeline.INDEFINITE);
    animation.getKeyFrames().add(frame);
    animation.play();
    displayGrid(mainGrid, grid);

  }
  private void step() {
    updateGrid.updateGrid(mainGrid);
    mainGrid.copyGrid(updateGrid);
    updateGridAppearance(mainGrid);
    frameCounter++;
    checkSimulationForward();
  }

  /**
   * Update the fill and the stroke color of the rectangle based on current state
   */
  public void updateGridAppearance(Grid displayedGrid) {
    for(int i = 0; i < displayedGrid.getHeight(); i++){
      for(int j = 0; j < displayedGrid.getWidth(); j++){
        updateCellAppearance(i, j, displayedGrid.getCell(i, j));
      }
    }
  }

  public void updateCellAppearance(int row, int col, Cell currentCell){
    polygonGrid[col][row].setFill(cellFillColors[currentCell.getCurrentState()]);
    polygonGrid[col][row].setStroke(cellStrokeColors[currentCell.getCurrentState()]);
  }

  public void displayGrid(Grid displayedGrid, Group rootGrid) {
    for(int i = 0; i < displayedGrid.getHeight(); i++){
      for(int j = 0; j < displayedGrid.getWidth(); j++){
        Rectangle newCell = makeInitialCellShape(i, j, displayedGrid.getCell(i, j));
        polygonGrid[j][i] = newCell;
        rootGrid.getChildren().add(newCell);
      }
    }
  }

  public Rectangle makeInitialCellShape(int row, int col, Cell currentCell){
    Rectangle myShape = new Rectangle(col * 20 + upperLeftX, row * 20 + upperLeftY, 20, 20);
    myShape.setStrokeType(cellStrokeType);
    myShape.setStrokeWidth(cellStrokeProportion * 20);
    myShape.setFill(cellFillColors[currentCell.getCurrentState()]);
    myShape.setStroke(cellStrokeColors[currentCell.getCurrentState()]);
    return myShape;
  }

  public Rectangle[][] initializeRectangleGrid(int width, int height){
    return new Rectangle[width][height];
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
