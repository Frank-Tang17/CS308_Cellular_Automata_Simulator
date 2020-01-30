package cellsociety;

import java.util.Arrays;
import java.util.List;
import javafx.scene.paint.Color;

public class PercolationCell extends Cell {


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
    List neighborStatesAsList = Arrays.asList(getNeighborStates(theOldGrid));

    if (theOldGrid.getGrid()[myRow][myCol].myState == 0 && neighborStatesAsList.contains(2)) {
      myState = 2;
    }
  }
}
