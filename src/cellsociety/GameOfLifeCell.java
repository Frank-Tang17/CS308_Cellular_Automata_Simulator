package cellsociety;

import java.util.*;
import javafx.scene.paint.Color;

public class GameOfLifeCell extends Cell {
  private static final int deadState = 0;
  private static final int aliveState = 1;
  private static final int cellsNeededForLife = 3;
  private static final int upperAliveCellBound = 3;
  private static final int lowerAliveCellBound = 2;

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
   * @param theNewGrid
   */
  @Override

  public void update(Grid theOldGrid, Grid theNewGrid) {
    ArrayList<Integer> neighborStatesAsList = this.getNeighborStates(theOldGrid);

    int numNeighborAlive = Collections.frequency(neighborStatesAsList, aliveState);

    if (theOldGrid.getCell(myRow, myCol).getCurrentState() == deadState && numNeighborAlive == cellsNeededForLife) {
      this.myState = aliveState;
    } else if (theOldGrid.getCell(myRow, myCol).getCurrentState() == aliveState && (numNeighborAlive < lowerAliveCellBound || numNeighborAlive > upperAliveCellBound)) {
      this.myState = deadState;
    }
  }
}




