package cellsociety.cells;

import cellsociety.Cell;
import cellsociety.Grid;
import java.util.*;

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
  public GameOfLifeCell(int row, int col, double size, int startingState, int[] neighborRowIndexes,
      int[] neighborColIndexes) {
    super(row, col, size, startingState, neighborRowIndexes, neighborColIndexes);
  }

  /**
   * Implements rules for simulation and updates the appearance of the cell
   *
   * @param theOldGrid current Grid to update state based on
   * @param theNewGrid
   */
  @Override

  public void update(Grid theOldGrid, Grid theNewGrid) {
    ArrayList<Integer> neighborStatesAsList = new ArrayList<>(this.getNeighborStates(theOldGrid));

    int numNeighborAlive = Collections.frequency(neighborStatesAsList, aliveState);

    if (theOldGrid.getCell(getRowAndCol()[0], getRowAndCol()[1]).getCurrentState() == deadState && numNeighborAlive == cellsNeededForLife) {
      setCellState(aliveState);
    } else if (theOldGrid.getCell(getRowAndCol()[0], getRowAndCol()[1]).getCurrentState() == aliveState && (numNeighborAlive < lowerAliveCellBound || numNeighborAlive > upperAliveCellBound)) {
      setCellState(deadState);
    }
  }
}




