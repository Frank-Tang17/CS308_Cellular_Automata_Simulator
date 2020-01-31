package cellsociety;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javafx.scene.paint.Color;

public class PercolationCell extends Cell {
  private int blockedState = 0;
  private int openState = 1;
  private int waterState = 2;

  /**
   * Constructor for master class Cell object
   *
   * @param row           the cells row in the grid
   * @param col           the cells col in the grid
   * @param size          the width and height of cell
   * @param startingState the starting state of the cell
   */
  public PercolationCell(int row, int col, double size, int startingState) {
    super(row, col, size, startingState);
    neighborColIndex = new int[]{0, 1, 1, 1, 0, -1, -1, -1}; // Define sets of coordinates for neighbors
    neighborRowIndex = new int[]{-1, -1, 0, 1, 1, 1, 0, -1}; // Define sets of coordinates for neighbors
    cellFillColors = new Color[]{Color.BLACK, Color.WHITE, Color.LIGHTBLUE};
    cellStrokeColors = new Color[]{Color.GREY, Color.GREY, Color.GREY};
    updateRectangle();
  }

  @Override
  public void update(Grid theOldGrid) {
    ArrayList<Integer> neighborStatesAsList = this.getNeighborStates(theOldGrid);

    if (theOldGrid.getCell(myRow, myCol).myState == openState && neighborStatesAsList.contains(waterState)) {
      myState = waterState;
    }
  }
}
