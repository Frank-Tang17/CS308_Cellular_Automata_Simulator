package cellsociety.cells;

import cellsociety.Cell;
import cellsociety.Grid;
import java.util.ArrayList;

public class PercolationCell extends Cell {
  private static final int blockedState = 0;
  private static final int openState = 1;
  private static final int waterState = 2;

  /**
   * Constructor for master class Cell object
   *
   * @param row           the cells row in the grid
   * @param col           the cells col in the grid
   * @param size          the width and height of cell
   * @param startingState the starting state of the cell
   */
  public PercolationCell(int row, int col, int startingState, int[] neighborRowIndexes,
      int[] neighborColIndexes) {
    super(row, col, startingState, neighborRowIndexes, neighborColIndexes);
//    cellFillColors = new Color[]{Color.BLACK, Color.WHITE, Color.LIGHTBLUE};
//    cellStrokeColors = new Color[]{Color.GREY, Color.GREY, Color.GREY};
//    updateRectangle();
  }

  @Override
  public void update(Grid theOldGrid, Grid theNewGrid) {
    ArrayList<Integer> neighborStatesAsList = new ArrayList<>(this.getNeighborStates(theOldGrid));

    if (theOldGrid.getCell(getRowAndCol()[0], getRowAndCol()[1]).getCurrentState() == openState && neighborStatesAsList.contains(waterState)) {
      setCellState(waterState);
    }
  }
}
