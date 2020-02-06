package cellsociety;

import java.util.ArrayList;
import javafx.scene.paint.Color;

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
   * @param size          the width and height of the cell
   * @param startingState the starting state of the cell
   */
  public FireCell(int row, int col, double size, int startingState) {
    super(row, col, size, startingState);
    neighborColIndex = new int[]{0, 1, 0, -1}; // Define sets of coordinates for neighbors
    neighborRowIndex = new int[]{-1, 0, 1, 0}; // Define sets of coordinates for neighbors
    cellFillColors = new Color[]{Color.YELLOW, Color.GREEN, Color.FIREBRICK};
    cellStrokeColors = new Color[]{Color.BLACK, Color.BLACK, Color.BLACK};
    updateRectangle();
  }

  @Override
  public void update(Grid theOldGrid, Grid theNewGrid) {
    ArrayList<Integer> neighborStatesAsList = new ArrayList<>(this.getNeighborStates(theOldGrid));

    double rand = (Math.random() * 100);
    double compProb = 100 * prob;

    if (theOldGrid.getCell(myRow, myCol).myState == burningState) {
      myState = emptyState;
    } else if (neighborStatesAsList.contains(burningState) && theOldGrid.getCell(myRow, myCol).myState == treeState && (rand <= compProb)) {
      myState = burningState;
    }
  }
}
