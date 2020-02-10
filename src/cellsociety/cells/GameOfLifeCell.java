package cellsociety.cells;

import cellsociety.Cell;
import cellsociety.Grid;
import java.util.*;

/**
 * GameOfLife Cell class
 */
public class GameOfLifeCell extends Cell {
  private static final int deadState = 0;
  private static final int aliveState = 1;
  private static final int cellsNeededForLife = 3;
  private static final int upperAliveCellBound = 3;
  private static final int lowerAliveCellBound = 2;

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

    int numNeighborAlive = Collections.frequency(neighborStatesAsList, aliveState);

    if (theOldGrid.getCell(getRowAndCol()[0], getRowAndCol()[1]).getCurrentState() == deadState && numNeighborAlive == cellsNeededForLife) {
      setCellState(aliveState);
    } else if (theOldGrid.getCell(getRowAndCol()[0], getRowAndCol()[1]).getCurrentState() == aliveState && (numNeighborAlive < lowerAliveCellBound || numNeighborAlive > upperAliveCellBound)) {
      setCellState(deadState);
    }
  }
}




