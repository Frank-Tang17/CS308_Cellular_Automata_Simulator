package cellsociety.view.gridshapes;

import cellsociety.configuration.Configuration;
import cellsociety.model.Grid;
import cellsociety.view.ShapeGrid;
import javafx.scene.shape.Polygon;

/**
 * Subclass to make a Rectangle Polygon
 *
 * @author Frank Tang
 */
public class RectangleGrid extends ShapeGrid {

  private Double[] pointArray;
  private Polygon rectangle;

  public RectangleGrid(Grid gridToDisplay, Configuration simulationConfiguration) {
    super(gridToDisplay, simulationConfiguration);
  }

  /**
   * Method to make make and place a rectangle polygon in the correct position
   *
   * @return Polygon in the shape of a Rectangle
   */
  @Override
  public Polygon makeInitialShape(double row, double col, double sideLength) {
    pointArray = new Double[]{
        getUpperLeftX() + col * sideLength, getUpperLeftY() + row * sideLength,
        getUpperLeftX() + (col + 1) * sideLength, getUpperLeftY() + row * sideLength,
        getUpperLeftX() + (col + 1) * sideLength, getUpperLeftY() + (row + 1) * sideLength,
        getUpperLeftX() + col * sideLength, getUpperLeftY() + (row + 1) * sideLength
    };
    rectangle = new Polygon();
    rectangle.getPoints().addAll(pointArray);
    return rectangle;
  }

}
