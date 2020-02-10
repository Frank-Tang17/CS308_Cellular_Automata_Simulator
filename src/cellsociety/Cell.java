package cellsociety;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.swing.text.html.ImageView;


/**
 * Cell acts as a node of the grid for the simulation
 */
public abstract class Cell {

  protected int myRow, myCol;
  protected int myState;

  protected int[] neighborColIndex;
  protected int[] neighborRowIndex;
  protected int[] neighborEvenRowIndex;
  protected int[] neighborOddRowIndex;

  private boolean torodial = false;
  private boolean hexagon = false;

  public boolean justSwitched;



  /**
   * Constructor for master class Cell object
   * @param row           the cells row in the grid
   * @param col           the cells col in the grid
   * @param size          the width and height of cell
   * @param startingState the starting state of the cell
   * @param neighborRowIndexes
   * @param neighborColIndexes
   */
  public Cell(int row, int col, double size, int startingState, int[] neighborRowIndexes,
      int[] neighborColIndexes) {
    myState = startingState;
    myRow = row;
    myCol = col;
    neighborColIndex = neighborColIndexes;
    if (!hexagon) {
      neighborEvenRowIndex = neighborRowIndexes;
      neighborOddRowIndex = neighborRowIndexes;
    } else {
      neighborEvenRowIndex = neighborRowIndexes;
      neighborOddRowIndex = neighborRowIndexes;
    }
  }

  /**
   * Getter method for row and column
   *
   * @return array of Cell row and col in that order
   */
  public int[] getRowAndCol() {
    return new int[]{myRow, myCol};
  }

  /**
   * Returns the states of each neighboring cell
   *
   * @param theGrid current Grid to update state based on
   * @return array of neighboring states
   */
  public List<Integer> getNeighborStates(Grid theGrid) {
    ArrayList<Integer> neighborStates = new ArrayList();
    for (int i = 0; i < neighborColIndex.length; i++) {
      if (theGrid.isValidIndex(myRow + neighborRowIndex[i], myCol + neighborColIndex[i])) {
        neighborStates.add(
            theGrid.getCell((myRow + neighborRowIndex[i]), (myCol + neighborColIndex[i]))
                .getCurrentState());
      } else if (torodial) {
        int[] newIndexes = getTorodialIndex(theGrid, i);
        if (theGrid.isValidIndex(newIndexes[0], newIndexes[1])) {
          neighborStates.add(
              theGrid.getCell(newIndexes[0], newIndexes[1]).getCurrentState());
        }
      }

    }
    return neighborStates;
  }

  private int[] getTorodialIndex(Grid theGrid, int i) {
    int newNeighborRowIndex = myRow + neighborRowIndex[i];
    int newNeighborColIndex = myCol + neighborColIndex[i];
    if (myRow + neighborRowIndex[i] > theGrid.getHeight()) {
      newNeighborRowIndex = neighborRowIndex[i] - 1;
    } else if (myRow + neighborRowIndex[i] < theGrid.getHeight()) {
      newNeighborRowIndex = theGrid.getHeight() + neighborRowIndex[i];
    }

    if (myCol + neighborColIndex[i] > theGrid.getWidth()) {
      newNeighborColIndex = neighborColIndex[i] - 1;
    } else if (myCol + neighborColIndex[i] < theGrid.getWidth()) {
      newNeighborColIndex = theGrid.getWidth() + neighborColIndex[i];
    }
    return new int[] {newNeighborRowIndex, newNeighborColIndex};
  }


  public void setCellState(int newState) {
    this.myState = newState;
  }

  /**
   * Cell type dependent method that changes the current state of the cell
   *
   * @param theOldGrid current Grid to update state based on
   * @param theNewGrid
   */
  public abstract void update(Grid theOldGrid, Grid theNewGrid);

  /**
   * Getter method for the current state of the Cell
   *
   * @return the current state
   */
  public int getCurrentState() {
    return myState;
  }

}

