package cellsociety.view;

import cellsociety.configuration.Configuration;
import cellsociety.model.Cell;
import cellsociety.model.Grid;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.StrokeType;


public abstract class ShapeGrid {

  private static final int upperLeftX = 75;
  private static final int upperLeftY = 70;

  private Color[] cellFillColors;
  private Color[] cellStrokeColors;
  private static final StrokeType cellStrokeType = StrokeType.CENTERED; // INSIDE, OUTSIDE, or CENTERED
  private static final double cellStrokeProportion = 0.1;

  private Polygon[][] polygonGrid;
  private double shapeSideLength;

  public ShapeGrid(Grid gridToDisplay, Configuration simulationConfiguration) {
    polygonGrid = new Polygon[gridToDisplay.getWidth()][gridToDisplay.getHeight()];
    makeFillColorArray(simulationConfiguration.getColors(), simulationConfiguration.getSColors());
    shapeSideLength = determineCellSize(gridToDisplay.getHeight(), gridToDisplay.getWidth(),
        gridToDisplay);
  }

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

  public void updateCellAppearance(int row, int col, Cell currentCell) {
    polygonGrid[row][col].setFill(cellFillColors[currentCell.getCurrentState()]);
    polygonGrid[row][col].setStroke(cellStrokeColors[currentCell.getCurrentState()]);
  }

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

  public abstract Polygon makeInitialShape(double row, double col, double sideLength);

  public void fillShapeWithColor(Cell currentCell, Polygon currentShape) {
//    Polygon myShape = new Polygon(col * 20 + upperLeftX, row * 20 + upperLeftY, 20, 20);
    currentShape.setStrokeType(cellStrokeType);
    currentShape.setStrokeWidth(cellStrokeProportion * 20);
    currentShape.setFill(cellFillColors[currentCell.getCurrentState()]);
    currentShape.setStroke(cellStrokeColors[currentCell.getCurrentState()]);
  }

  public Polygon getPolygon(int row, int col) {
    return polygonGrid[row][col];
  }

  private double determineCellSize(int numRows, int numCols, Grid gridToDisplay) {
    double maxWidth = gridToDisplay.getSimulationScreenWidth() / numCols;
    double maxHeight = gridToDisplay.getSimulationScreenHeight() / numRows;

    return Math.min(maxWidth, maxHeight);
  }

  public double getUpperLeftX() {
    return upperLeftX;
  }

  public double getUpperLeftY() {
    return upperLeftY;
  }

}
