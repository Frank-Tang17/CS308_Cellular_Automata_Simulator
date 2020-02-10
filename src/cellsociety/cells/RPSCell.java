package cellsociety.cells;

import cellsociety.Cell;
import cellsociety.Grid;
import java.util.ArrayList;
import java.util.Collections;

public class RPSCell extends Cell {
  private int threshold = 3;
  private static final int rockState = 0;
  private static final int paperState = 1;
  private static final int scissorState = 2;
  /**
   * Constructor for master class Cell object
   *
   * @param row                the cells row in the grid
   * @param col                the cells col in the grid
   * @param startingState      the starting state of the cell
   * @param neighborRowIndexes
   * @param neighborColIndexes
   */
  public RPSCell(int row, int col, int startingState, int[] neighborRowIndexes,
      int[] neighborColIndexes) {
    super(row, col, startingState, neighborRowIndexes, neighborColIndexes);
  }

  @Override
  public void update(Grid theOldGrid, Grid theNewGrid) {
    ArrayList<Integer> neighborStatesAsList = new ArrayList<>(this.getNeighborStates(theOldGrid));
    int numberOfSimilarNeighbors = 0;
    int newState = 0;

    if (getCurrentState() == rockState) {
      numberOfSimilarNeighbors = Collections.frequency(neighborStatesAsList, paperState);
      newState = paperState;
    } else if (getCurrentState() == paperState) {
      numberOfSimilarNeighbors = Collections.frequency(neighborStatesAsList, scissorState);
      newState = scissorState;
    } else if (getCurrentState() == scissorState) {
      numberOfSimilarNeighbors = Collections.frequency(neighborStatesAsList, rockState);
      newState = rockState;
    }

    if (numberOfSimilarNeighbors > threshold) {
      setCellState(newState);
    }

  }

  public void setThreshold(double newThreshold) {
    threshold = (int) newThreshold;
  }
}
