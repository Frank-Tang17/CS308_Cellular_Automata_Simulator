package cellsociety.cells;

import cellsociety.Cell;
import cellsociety.Configuration;
import cellsociety.Grid;
import java.util.ArrayList;
import java.util.Collections;

public class SegregationCell extends Cell {
  private static final double threshold = 0.5;
  private static final int emptyState = 0;
  private static final int typeAState = 1;
  private static final int typeBState = 2;


  /**
   * Constructor for master class Cell object
   *  @param row           the cells row in the grid
   * @param col           the cells col in the grid
   * @param size          the width and height of cell
   * @param startingState the starting state of the cell
   */

  public SegregationCell(int row, int col, double size, int startingState, int[] neighborRowIndexes,
      int[] neighborColIndexes) {
    super(row, col, size, startingState, neighborRowIndexes, neighborColIndexes);
    justSwitched = false;

  }

  @Override
  public void update(Grid theOldGrid, Grid theNewGrid) {
    ArrayList<Integer> neighborStatesAsList = new ArrayList<>(this.getNeighborStates(theOldGrid));

    int numberOfSimilarNeighbors = Collections.frequency(neighborStatesAsList, getCurrentState());
    double proportionOfSimilarNeighbors = (double)numberOfSimilarNeighbors / ((double)neighborStatesAsList.size() - (double) Collections.frequency(neighborStatesAsList, emptyState));

    boolean notSwitchedYet = true;
    if (proportionOfSimilarNeighbors < threshold && getCurrentState() != emptyState) {
      for (int i = 0; i < theOldGrid.getHeight(); i++) {
        for (int j = 0; j < theOldGrid.getWidth(); j++) {
          Cell currentCell = theOldGrid.getCell(i, j);
          if (currentCell.getCurrentState() == emptyState && !currentCell.justSwitched && notSwitchedYet) {
            theNewGrid.getCell(currentCell.getRowAndCol()[0], currentCell.getRowAndCol()[1]).setCellState(getCurrentState());
            setCellState(emptyState);
            currentCell.justSwitched = true;
            justSwitched = true;
            notSwitchedYet = false;
          }

        }
      }
    }
  }

}