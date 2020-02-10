package cellsociety.view;

import cellsociety.configuration.Configuration;
import cellsociety.model.Cell;
import cellsociety.model.Grid;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.StrokeType;

/**
 * Abstract parent class for making the shapes representing the grid of the simulation
 *
 * @author Frank Tang
 */
public abstract class ShapeGrid {

  private static final int upperLeftX = 75;
  private static final int upperLeftY = 70;

  private Color[] cellFillColors;
  private Color[] cellStrokeColors;
  private static final StrokeType cellStrokeType = StrokeType.CENTERED; // INSIDE, OUTSIDE, or CENTERED
  private static final double cellStrokeProportion = 0.1;

  private Polygon[][] polygonGrid;
  private double shapeSideLength;

  /**
   * Parent class to make ShapeGrid object
   */
  public ShapeGrid(Grid gridToDisplay, Configuration simulationConfiguration) {
    polygonGrid = new Polygon[gridToDisplay.getWidth()][gridToDisplay.getHeight()];
    makeFillColorArray(simulationConfiguration.getColors(), simulationConfiguration.getSColors());
    shapeSideLength = determineCellSize(gridToDisplay.getHeight(), gridToDisplay.getWidth(),
        gridToDisplay);
  }

  /**
   * Abstract method for making a Polygon shape -- inherited by other subclasses to make different
   * shapes
   */
  public abstract Polygon makeInitialShape(double row, double col, double sideLength);

  /**
   * Method to fill in the fill and stroke color arrays from the configuration file.
   */
  private void makeFillColorArray(String[] fillColorsFromConfig, String[] strokeColorsFromConfig) {
    cellFillColors = new Color[fillColorsFromConfig.length];
    cellStrokeColors = new Color[fillColorsFromConfig.length];
    for (int i = 0; i < cellFillColors.length; i++) {
      cellFillColors[i] = Color.web(fillColorsFromConfig[i]);
      cellStrokeColors[i] = Color.web(strokeColorsFromConfig[i]);
    }
  }

  /**
   * Update the fill and the stroke color of the rectangle based on current state
   */
  public void updateGridAppearance(Grid displayedGrid) {
    for (int i = 0; i < displayedGrid.getHeight(); i++) {
      for (int j = 0; j < displayedGrid.getWidth(); j++) {
        updateCellAppearance(j, i, displayedGrid.getCell(i, j));
      }
    }
  }

  /**
   * Method to update a Polygon in the polygonGrid
   */
  public void updateCellAppearance(int row, int col, Cell currentCell) {
    polygonGrid[row][col].setFill(cellFillColors[currentCell.getCurrentState()]);
    polygonGrid[row][col].setStroke(cellStrokeColors[currentCell.getCurrentState()]);
  }

  /**
   * Method to display the ShapeGrid object in the simulation window
   */
  public void displayGrid(Grid displayedGrid, Group rootGrid) {
    for (int i = 0; i < displayedGrid.getHeight(); i++) {
      for (int j = 0; j < displayedGrid.getWidth(); j++) {
        Polygon newCell = makeInitialShape(i, j, shapeSideLength);
        fillShapeWithColor(displayedGrid.getCell(i, j), newCell);
        polygonGrid[j][i] = newCell;
        rootGrid.getChildren().add(newCell);
      }
    }
  }

  /**
   * Method that takes an existing Polygon and colors it based on the corresponding cell in the Grid
   * object
   */
  public void fillShapeWithColor(Cell currentCell, Polygon currentShape) {
    currentShape.setStrokeType(cellStrokeType);
    currentShape.setStrokeWidth(cellStrokeProportion * 20);
    currentShape.setFill(cellFillColors[currentCell.getCurrentState()]);
    currentShape.setStroke(cellStrokeColors[currentCell.getCurrentState()]);
  }

  /**
   * Method to get a polygon at a point in the ShapeGrid
   */
  public Polygon getPolygon(int row, int col) {
    return polygonGrid[row][col];
  }

  /**
   * Method that determines the appropriate size of the cell so that they are always uniformly sized
   * and not stretched
   */
  private double determineCellSize(int numRows, int numCols, Grid gridToDisplay) {
    double maxWidth = gridToDisplay.getSimulationScreenWidth() / numCols;
    double maxHeight = gridToDisplay.getSimulationScreenHeight() / numRows;

    return Math.min(maxWidth, maxHeight);
  }

  /**
   * Method that gets the starting x-position of the graph in the window
   */
  public double getUpperLeftX() {
    return upperLeftX;
  }

  /**
   * Method that gets the starting y-position of the graph in the window
   */
  public double getUpperLeftY() {
    return upperLeftY;
  }

}
