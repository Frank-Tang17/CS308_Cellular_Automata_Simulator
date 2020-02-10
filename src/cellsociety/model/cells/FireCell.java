package cellsociety.model.cells;

import cellsociety.model.Cell;
import cellsociety.model.Grid;
import java.util.ArrayList;

/**
 * Fire Cell class
 */
public class FireCell extends Cell {

  private double prob = .55;
  private static final int EMPTY_STATE = 0;
  private static final int TREE_STATE = 1;
  private static final int BURNING_STATE = 2;


  /**
   * Constructor for FireCell class Cell object
   *
   * @param row                the cells row in the grid
   * @param col                the cells col in the grid
   * @param startingState      the starting state of the cell
   * @param neighborRowIndexes the int array that holds the row locations for the neighborhood
   * @param neighborColIndexes the int array that holds the col locations for the neighborhood
   */
  public FireCell(int row, int col, int startingState, int[] neighborRowIndexes,
      int[] neighborColIndexes) {
    super(row, col, startingState, neighborRowIndexes, neighborColIndexes);
  }

  /**
   * Updates the FireCell depending on its own set of rules
   *
   * @param theOldGrid current Grid to update state based on
   * @param theNewGrid the grid that is being updated
   */
  @Override
  public void update(Grid theOldGrid, Grid theNewGrid) {
    ArrayList<Integer> neighborStatesAsList = new ArrayList<>(this.getNeighborStates(theOldGrid));

    double rand = (Math.random() * 100);
    double compProb = 100 * prob;

    if (theOldGrid.getCell(getRowAndCol()[0], getRowAndCol()[1]).getCurrentState()
        == BURNING_STATE) {
      setCellState(EMPTY_STATE);
    } else if (neighborStatesAsList.contains(BURNING_STATE)
        && theOldGrid.getCell(getRowAndCol()[0], getRowAndCol()[1]).getCurrentState() == TREE_STATE
        && (rand <= compProb)) {
      setCellState(BURNING_STATE);
    }
  }

  /**
   * Setter method for probability of catching fire
   *
   * @param newProb
   */
  public void setProb(double newProb) {
    prob = newProb;
  }
}
