package cellsociety.model.cells;

import cellsociety.model.Cell;
import cellsociety.model.Grid;
import java.util.ArrayList;

/**
 * Percolation Cell class
 */
public class PercolationCell extends Cell {

  private static final int BLOCKED_STATE = 0;
  private static final int OPEN_STATE = 1;
  private static final int WATER_STATE = 2;

  /**
   * Constructor for PercolationCell class Cell object
   *
   * @param row                the cells row in the grid
   * @param col                the cells col in the grid
   * @param startingState      the starting state of the cell
   * @param neighborRowIndexes the int array that holds the row locations for the neighborhood
   * @param neighborColIndexes the int array that holds the col locations for the neighborhood
   */
  public PercolationCell(int row, int col, int startingState, int[] neighborRowIndexes,
      int[] neighborColIndexes) {
    super(row, col, startingState, neighborRowIndexes, neighborColIndexes);
  }

  /**
   * Updates the PercolationCell depending on its own set of rules
   *
   * @param theOldGrid current Grid to update state based on
   * @param theNewGrid the grid that is being updated
   */
  @Override
  public void update(Grid theOldGrid, Grid theNewGrid) {
    ArrayList<Integer> neighborStatesAsList = new ArrayList<>(this.getNeighborStates(theOldGrid));

    if (theOldGrid.getCell(getRowAndCol()[0], getRowAndCol()[1]).getCurrentState() == OPEN_STATE
        && neighborStatesAsList.contains(
        WATER_STATE)) {
      setCellState(WATER_STATE);
    }
  }
}
