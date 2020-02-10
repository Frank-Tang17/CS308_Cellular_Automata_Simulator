package cellsociety.model.cells;

import cellsociety.model.Cell;
import cellsociety.model.Grid;
import java.util.ArrayList;
import java.util.Collections;

/**
 * RPSCell Cell class
 */
public class RPSCell extends Cell {

  private int threshold = 3;
  private static final int ROCK_STATE = 0;
  private static final int PAPER_STATE = 1;
  private static final int SCISSOR_STATE = 2;

  /**
   * Constructor for RPSCell class Cell object
   *
   * @param row                the cells row in the grid
   * @param col                the cells col in the grid
   * @param startingState      the starting state of the cell
   * @param neighborRowIndexes the int array that holds the row locations for the neighborhood
   * @param neighborColIndexes the int array that holds the col locations for the neighborhood
   */
  public RPSCell(int row, int col, int startingState, int[] neighborRowIndexes,
      int[] neighborColIndexes) {
    super(row, col, startingState, neighborRowIndexes, neighborColIndexes);
  }

  /**
   * Updates the RPSCell depending on its own set of rules
   *
   * @param theOldGrid current Grid to update state based on
   * @param theNewGrid the grid that is being updated
   */
  @Override
  public void update(Grid theOldGrid, Grid theNewGrid) {
    ArrayList<Integer> neighborStatesAsList = new ArrayList<>(this.getNeighborStates(theOldGrid));
    int numberOfSimilarNeighbors = 0;
    int newState = 0;

    if (getCurrentState() == ROCK_STATE) {
      numberOfSimilarNeighbors = Collections.frequency(neighborStatesAsList, PAPER_STATE);
      newState = PAPER_STATE;
    } else if (getCurrentState() == PAPER_STATE) {
      numberOfSimilarNeighbors = Collections.frequency(neighborStatesAsList, SCISSOR_STATE);
      newState = SCISSOR_STATE;
    } else if (getCurrentState() == SCISSOR_STATE) {
      numberOfSimilarNeighbors = Collections.frequency(neighborStatesAsList, ROCK_STATE);
      newState = ROCK_STATE;
    }

    if (numberOfSimilarNeighbors > threshold) {
      setCellState(newState);
    }

  }

  /**
   * Setter method for threshold value
   *
   * @param newThreshold
   */
  public void setThreshold(double newThreshold) {
    threshold = (int) newThreshold;
  }
}
