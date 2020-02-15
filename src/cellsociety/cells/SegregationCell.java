package cellsociety.cells;

import cellsociety.Cell;
import cellsociety.Grid;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Segregation Cell class
 */
public class SegregationCell extends Cell {
  private double threshold = 0.5;
  private static final int EMPTY_STATE = 0;
  // typeAState = 1;
  // typeBState = 2;


  /**
   * Constructor for SegregationCell class Cell object
   *
   * @param row                the cells row in the grid
   * @param col                the cells col in the grid
   * @param startingState      the starting state of the cell
   * @param neighborRowIndexes the int array that holds the row locations for the neighborhood
   * @param neighborColIndexes the int array that holds the col locations for the neighborhood
   */
  public SegregationCell(int row, int col, int startingState, int[] neighborRowIndexes,
      int[] neighborColIndexes) {
    super(row, col, startingState, neighborRowIndexes, neighborColIndexes);
    justSwitched = false;

  }

  /**
   * Updates the SegregationCell depending on its own set of rules
   *
   * @param theOldGrid current Grid to update state based on
   * @param theNewGrid the grid that is being updated
   */
  @Override
  public void update(Grid theOldGrid, Grid theNewGrid) {
    ArrayList<Integer> neighborStatesAsList = new ArrayList<>(this.getNeighborStates(theOldGrid));

    int numberOfSimilarNeighbors = Collections.frequency(neighborStatesAsList, getCurrentState());
    double proportionOfSimilarNeighbors = (double)numberOfSimilarNeighbors / ((double)neighborStatesAsList.size() - (double) Collections.frequency(neighborStatesAsList,
        EMPTY_STATE));

    boolean notSwitchedYet = true;
    if (proportionOfSimilarNeighbors < threshold && getCurrentState() != EMPTY_STATE) {
      for (int i = 0; i < theOldGrid.getHeight(); i++) {
        for (int j = 0; j < theOldGrid.getWidth(); j++) {
          Cell currentCell = theOldGrid.getCell(i, j);
          if (currentCell.getCurrentState() == EMPTY_STATE && !currentCell.justSwitched && notSwitchedYet) {
            theNewGrid.getCell(currentCell.getRowAndCol()[0], currentCell.getRowAndCol()[1]).setCellState(getCurrentState());
            setCellState(EMPTY_STATE);
            currentCell.justSwitched = true;
            justSwitched = true;
            notSwitchedYet = false;
          }
        }
      }
    }
  }

  /**
   * Setter method for the threshold of neighbors
   * @param newThreshold the threshold to set it to
   */
  public void setThreshold(double newThreshold) {
    threshold = newThreshold;
  }

}