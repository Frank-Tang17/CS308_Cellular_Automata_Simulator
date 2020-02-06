package cellsociety;

import java.util.ArrayList;
import java.util.Collections;
import javafx.scene.paint.Color;

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
  public SegregationCell(int row, int col, double size, int startingState) {
    super(row, col, size, startingState);
    neighborColIndex = new int[]{0, 1, 1, 1, 0, -1, -1, -1}; // Define sets of coordinates for neighbors
    neighborRowIndex = new int[]{-1, -1, 0, 1, 1, 1, 0, -1}; // Define sets of coordinates for neighbors
    cellFillColors = new Color[]{Color.WHITE, Color.BLUE, Color.RED};
    cellStrokeColors = new Color[]{Color.BLACK, Color.BLACK, Color.BLACK};
    justSwitched = false;
    //justOccupiedToBeTwo = false;
    //justOccupiedToBeOne = false;
    updateRectangle();
  }

  @Override
  public void update(Grid theOldGrid, Grid theNewGrid) {
    ArrayList<Integer> neighborStatesAsList = new ArrayList<>(this.getNeighborStates(theOldGrid));

    int numberOfSimilarNeighbors = Collections.frequency(neighborStatesAsList, myState);
    double proportionOfSimilarNeighbors = (double)numberOfSimilarNeighbors / ((double)neighborStatesAsList.size() - (double) Collections.frequency(neighborStatesAsList, emptyState));
    //int i = 0;
    boolean notSwitchedYet = true;
    if (proportionOfSimilarNeighbors < threshold && myState != emptyState) {
      for (int i = 0; i < theOldGrid.getHeight(); i++) {
        for (int j = 0; j < theOldGrid.getWidth(); j++) {
          Cell currentCell = theOldGrid.getCell(i, j);
          if (currentCell.myState == emptyState && !currentCell.justSwitched && notSwitchedYet) {
            theNewGrid.getCell(currentCell.myRow, currentCell.myCol).myState = myState;
            myState = emptyState;
            currentCell.justSwitched = true;
            justSwitched = true;
            notSwitchedYet = false;
          }

        }
      }
    } /*else if (myState == emptyState) {
      if (theOldGrid.getGrid()[myRow][myCol].justOccupiedToBeOne) {
        myState = typeAState;
        justOccupiedToBeOne = false;
        this.updateRectangle();
      } else if (theOldGrid.getGrid()[myRow][myCol].justOccupiedToBeTwo) {
        myState = typeBState;
        justOccupiedToBeTwo = false;
        this.updateRectangle();
      }
    }*/

  }
}
