package cellsociety;

import java.awt.*;
import java.util.*;
import java.util.List;
import javafx.scene.paint.Color;

public class GameOfLifeCell extends Cell {

  /**
   * Constructor for master class Cell object
   *
   * @param row           the cells row in the grid
   * @param col           the cells col in the grid
   * @param xCoor         the x-coordinate of the upper left hand corner of the cell
   * @param yCoor         the y-coordinate of the upper left hand corner of the cell
   * @param width         the width of the cell
   * @param height        the height of the cell
   * @param startingState the starting state of the cell
   */
  public GameOfLifeCell(int row, int col, int xCoor, int yCoor, int width, int height,
      int startingState) {
    super(row, col, xCoor, yCoor, width, height, startingState);
    neighborColIndex = new int[]{0, 1, 0, -1}; // Define sets of coordinates for neighbors
    neighborRowIndex = new int[]{-1, 0, 1, 0}; // Define sets of coordinates for neighbors
    cellFillColors = new Color[]{Color.WHITE,
        Color.BLACK}; // Colors should align will cell state number
    cellStrokeColors = new Color[]{Color.GREY,
        Color.GREY}; // Colors should align will cell state number
    updateRectangle();
  }

  /**
   * Implements rules for simulation and updates the appearance of the cell
   *
   * @param theGrid current Grid to update state based on
   */
  @Override
  public void update(Grid theGrid) {
    List neighborStatesAsList = Arrays.asList(getNeighborStates(theGrid));

    int numNeighborAlive = Collections.frequency(neighborStatesAsList, 1);

    if (this.myState == 0 && numNeighborAlive == 3) {
      myState = 1;
    } else if (this.myState == 1 && (numNeighborAlive < 2 || numNeighborAlive > 3)) {
      myState = 0;
    }

    updateRectangle();
  }
}

