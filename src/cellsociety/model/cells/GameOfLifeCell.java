package cellsociety.model.cells;

import cellsociety.model.Cell;
import cellsociety.model.Grid;
import java.util.*;

/**
 * GameOfLife Cell class
 */
public class GameOfLifeCell extends Cell {

  private static final int DEAD_STATE = 0;
  private static final int ALIVE_STATE = 1;
  private static final int CELLS_NEEDED_FOR_LIFE = 3;
  private static final int UPPER_ALIVE_CELL_BOUND = 3;
  private static final int LOWER_ALIVE_CELL_BOUND = 2;

  /**
   * Constructor for GameOfLifeCell class Cell object
   *
   * @param row                the cells row in the grid
   * @param col                the cells col in the grid
   * @param startingState      the starting state of the cell
   * @param neighborRowIndexes the int array that holds the row locations for the neighborhood
   * @param neighborColIndexes the int array that holds the col locations for the neighborhood
   */
  public GameOfLifeCell(int row, int col, int startingState, int[] neighborRowIndexes,
      int[] neighborColIndexes) {
    super(row, col, startingState, neighborRowIndexes, neighborColIndexes);
  }

  /**
   * Updates the GameOfLifeCell depending on its own set of rules
   *
   * @param theOldGrid current Grid to update state based on
   * @param theNewGrid the grid that is being updated
   */
  @Override
  public void update(Grid theOldGrid, Grid theNewGrid) {
    ArrayList<Integer> neighborStatesAsList = new ArrayList<>(this.getNeighborStates(theOldGrid));

    int numNeighborAlive = Collections.frequency(neighborStatesAsList, ALIVE_STATE);

    if (theOldGrid.getCell(getRowAndCol()[0], getRowAndCol()[1]).getCurrentState() == DEAD_STATE
        && numNeighborAlive == CELLS_NEEDED_FOR_LIFE) {
      setCellState(ALIVE_STATE);
    } else if (
        theOldGrid.getCell(getRowAndCol()[0], getRowAndCol()[1]).getCurrentState() == ALIVE_STATE
            && (numNeighborAlive < LOWER_ALIVE_CELL_BOUND
            || numNeighborAlive > UPPER_ALIVE_CELL_BOUND)) {
      setCellState(DEAD_STATE);
    }
  }
}




