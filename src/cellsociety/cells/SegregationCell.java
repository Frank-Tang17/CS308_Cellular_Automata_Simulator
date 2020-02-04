package cellsociety.cells;

import cellsociety.Cell;
import cellsociety.Grid;
import java.util.ArrayList;
import java.util.Collections;
import javafx.scene.paint.Color;

public class SegregationCell extends Cell {
  private final double threshold;
  private final int emptyState = 0;
  private final int typeAState = 1;
  private final int typeBState = 2;


  /**
   * Constructor for master class Cell object
   *  @param row           the cells row in the grid
   * @param col           the cells col in the grid
   * @param size          the width and height of cell
   * @param startingState the starting state of the cell
   */
  public SegregationCell(int row, int col, double size, int startingState) {
    super(row, col, size, startingState);
    threshold = .5;
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
    ArrayList<Integer> neighborStatesAsList = this.getNeighborStates(theOldGrid);

    int numberOfSimilarNeighbors = Collections.frequency(neighborStatesAsList, myState);
    double proportionOfSimilarNeighbors = (double)numberOfSimilarNeighbors / ((double)neighborStatesAsList.size() - (double) Collections.frequency(neighborStatesAsList, emptyState));
    //int i = 0;
    boolean notSwitchedYet = true;
    if (proportionOfSimilarNeighbors < threshold && myState != emptyState) {
      //System.out.println("Row: " + myRow + ", Col: " + myCol);
      for (Cell[] row : theOldGrid.getGrid()) {
        for (Cell currentCell : row) {
          if (currentCell.myState == emptyState && !currentCell.justSwitched && notSwitchedYet) {
            //System.out.println("Row: " + myRow + ", Col: " + myCol);
            theNewGrid.getGrid()[currentCell.myRow][currentCell.myCol].myState = myState;
            myState = emptyState;
            currentCell.justSwitched = true;
            justSwitched = true;
            notSwitchedYet = false;

            /*
            if (myState == typeAState) {
              currentCell.justOccupiedToBeOne = true;
              currentCell
            } else if (myState == typeBState) {
              currentCell.justOccupiedToBeTwo = true;
            }
            currentCell.justSwitched = true;
            myState = emptyState;
            */


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
