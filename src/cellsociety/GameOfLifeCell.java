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
   * @param size          the width and height of the cell
   * @param startingState the starting state of the cell
   */
  public GameOfLifeCell(int row, int col, double size, int startingState) {
    super(row, col, size, startingState);
    neighborColIndex = new int[]{0, 1, 0, -1}; // Define sets of coordinates for neighbors
    neighborRowIndex = new int[]{-1, 0, 1, 0}; // Define sets of coordinates for neighbors
    cellFillColors = new Color[]{Color.WHITE, Color.BLACK};
    cellStrokeColors = new Color[]{Color.GREY, Color.GREY};
    updateRectangle();
  }

  /**
   * Implements rules for simulation and updates the appearance of the cell
   *
   * @param theOldGrid current Grid to update state based on
   */
  @Override
  public void update(Grid theOldGrid) {
    List neighborStatesAsList = Arrays.asList(getNeighborStates(theOldGrid));

    int numNeighborAlive = Collections.frequency(neighborStatesAsList, 1);

    if (theOldGrid.getGrid()[myRow][myCol].myState == 0 && numNeighborAlive == 3) {
      myState = 1;
    } else if (theOldGrid.getGrid()[myRow][myCol].myState == 1 && (numNeighborAlive < 2 || numNeighborAlive > 3)) {
      myState = 0;
    }

    updateRectangle();
  }
}

