package cellsociety.cells;

import cellsociety.Cell;
import cellsociety.Grid;
import java.util.ArrayList;

public class FireCell extends Cell {
  private double prob = .55;
  private static final int emptyState = 0;
  private static final int treeState = 1;
  private static final int burningState = 2;


  /**
   * Constructor for master class Cell object
   *
   * @param row           the cells row in the grid
   * @param col           the cells col in the grid
   * @param startingState the starting state of the cell
   */
  public FireCell(int row, int col, int startingState, int[] neighborRowIndexes,
      int[] neighborColIndexes) {
    super(row, col, startingState, neighborRowIndexes, neighborColIndexes);
  }

  @Override
  public void update(Grid theOldGrid, Grid theNewGrid) {
    ArrayList<Integer> neighborStatesAsList = new ArrayList<>(this.getNeighborStates(theOldGrid));

    double rand = (Math.random() * 100);
    double compProb = 100 * prob;

    if (theOldGrid.getCell(getRowAndCol()[0], getRowAndCol()[1]).getCurrentState() == burningState) {
      setCellState(emptyState);
    } else if (neighborStatesAsList.contains(burningState) && theOldGrid.getCell(getRowAndCol()[0], getRowAndCol()[1]).getCurrentState() == treeState && (rand <= compProb)) {
      setCellState(burningState);
    }
  }

  public void setProb(double newProb) {
    prob = newProb;
  }
}
