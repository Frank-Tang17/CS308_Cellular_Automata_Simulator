package cellsociety;

import java.awt.*;
import java.util.*;
import java.util.List;
import javafx.scene.paint.Color;

public class GameOfLifeCell extends Cell {
  private static int deadState = 0;
  private static int aliveState = 1;
  private static int cellsNeededForLife = 2;
  private static int upperAliveCellBound = 3;
  private static int lowerAliveCellBound = 2;

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
    ArrayList<Integer> neighborStatesAsList = this.getNeighborStates(theOldGrid);

    int numNeighborAlive = Collections.frequency(neighborStatesAsList, aliveState);

    if (theOldGrid.getCell(myRow, myCol).getCurrentState() == 0 && (numNeighborAlive == 3 || numNeighborAlive == 2)) {
      this.myState = 1;
    } else if (theOldGrid.getCell(myRow, myCol).getCurrentState() == 1 && (numNeighborAlive < 2 || numNeighborAlive > 3)) {
      this.myState = 0;
    }
  }
}




