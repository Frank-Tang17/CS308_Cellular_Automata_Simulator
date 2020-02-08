package cellsociety;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;

public class PolygonGrid {

  private static final int upperLeftX = 75;
  private static final int upperLeftY = 50;

  private Color[] cellFillColors = {Color.WHITE, Color.GREEN, Color.BLUE};
  private Color[] cellStrokeColors = {Color.GREY, Color.GREY, Color.GREY};
  private static final StrokeType cellStrokeType = StrokeType.CENTERED; // INSIDE, OUTSIDE, or CENTERED
  private static final double cellStrokeProportion = 0.1;

  private Rectangle[][] polygonGrid;

  public PolygonGrid(Grid gridToDisplay) {
    polygonGrid = new Rectangle[gridToDisplay.getWidth()][gridToDisplay.getHeight()];
  }

//  public Rectangle[][] initializeRectangleGrid(int width, int height) {
//    return new Rectangle[width][height];
//  }

  /**
   * Update the fill and the stroke color of the rectangle based on current state
   */
  public void updateGridAppearance(Grid displayedGrid) {
    for (int i = 0; i < displayedGrid.getHeight(); i++) {
      for (int j = 0; j < displayedGrid.getWidth(); j++) {
        updateCellAppearance(i, j, displayedGrid.getCell(i, j));
      }
    }
  }

  public void updateCellAppearance(int height, int width, Cell currentCell) {
    polygonGrid[width][height].setFill(cellFillColors[currentCell.getCurrentState()]);
    polygonGrid[width][height].setStroke(cellStrokeColors[currentCell.getCurrentState()]);
  }

  public void displayGrid(Grid displayedGrid, Group rootGrid) {
    for (int i = 0; i < displayedGrid.getHeight(); i++) {
      for (int j = 0; j < displayedGrid.getWidth(); j++) {
        Rectangle newCell = makeInitialCellShape(i, j, displayedGrid.getCell(i, j));
        polygonGrid[j][i] = newCell;
        rootGrid.getChildren().add(newCell);
      }
    }
  }

  public Rectangle makeInitialCellShape(int row, int col, Cell currentCell) {
    Rectangle myShape = new Rectangle(col * 20 + upperLeftX, row * 20 + upperLeftY, 20, 20);
    myShape.setStrokeType(cellStrokeType);
    myShape.setStrokeWidth(cellStrokeProportion * 20);
    myShape.setFill(cellFillColors[currentCell.getCurrentState()]);
    myShape.setStroke(cellStrokeColors[currentCell.getCurrentState()]);
    return myShape;
  }


}
