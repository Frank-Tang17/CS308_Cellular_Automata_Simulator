package cellsociety.view;

import cellsociety.configuration.Configuration;
import cellsociety.model.Grid;
import javafx.scene.shape.Polygon;

public class HexagonGrid extends ShapeGrid {

  private Double[] pointArray;
  private Polygon hexagon;
  private double hexagonalUpperLeftX;
  private double hexagonalUpperLeftY;

  public HexagonGrid(Grid gridToDisplay, Configuration simulationConfiguration) {
    super(gridToDisplay, simulationConfiguration);
  }

  @Override
  public Polygon makeInitialShape(double row, double col, double sideLength) {
    if (row % 2 == 1) {
      col = col + 0.5;
      row = row + row / 2;
    } else if (row != 0 && row % 2 == 0) {
      row = row + row / 2;
    }
    row = row + 0.5;
    pointArray = new Double[]{
        getUpperLeftX() + col * sideLength, getUpperLeftY() + row * sideLength,
        getUpperLeftX() + (col + 0.5) * sideLength, getUpperLeftY() + (row - 0.5) * sideLength,
        getUpperLeftX() + (col + 1) * sideLength, getUpperLeftY() + row * sideLength,
        getUpperLeftX() + (col + 1) * sideLength, getUpperLeftY() + (row + 1) * sideLength,
        getUpperLeftX() + (col + 0.5) * sideLength, getUpperLeftY() + (row + 1.5) * sideLength,
        getUpperLeftX() + col * sideLength, getUpperLeftY() + (row + 1) * sideLength
    };
    hexagon = new Polygon();
    hexagon.getPoints().addAll(pointArray);
    return hexagon;
  }

}
