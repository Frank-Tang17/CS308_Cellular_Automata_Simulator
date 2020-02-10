package cellsociety.model;

import java.util.ArrayList;
import java.util.List;


/**
 * Cell acts as a node of the grid for the simulation
 */
public abstract class Cell {

  private int myRow, myCol;
  private int myState;

  private int[] neighborColIndex;
  private int[] neighborRowIndex;
  private int[] neighborEvenColIndex;
  private int[] neighborOddColIndex;

  private boolean toroidal = true;
  private boolean hexagon = true;

  public boolean justSwitched;


  /**
   * Constructor for master class Cell object
   *
   * @param row                the cells row in the grid
   * @param col                the cells col in the grid
   * @param startingState      the starting state of the cell
   * @param neighborRowIndexes the int array that holds the row locations for the neighborhood
   * @param neighborColIndexes the int array that holds the col locations for the neighborhood
   */
  public Cell(int row, int col, int startingState, int[] neighborRowIndexes,
      int[] neighborColIndexes) {
    myState = startingState;
    myRow = row;
    myCol = col;
    neighborRowIndex = neighborRowIndexes;
    if (hexagon) {
      neighborEvenColIndex = new int[]{-1, -1, 0, 1, 0, -1};
      neighborOddColIndex = new int[]{-1, 0, 1, 1, 1, 0};
      neighborRowIndex = new int[]{0, -1, -1, 0, 1, 1};
    } else {
      neighborEvenColIndex = neighborColIndexes;
      neighborOddColIndex = neighborColIndexes;
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
    int[] neighborColIndexForMyRow = getNeighborColIndex();

    for (int i = 0; i < neighborColIndexForMyRow.length; i++) {
      if (theGrid.isValidIndex(myRow + neighborRowIndex[i], myCol + neighborColIndexForMyRow[i])) {
        neighborStates.add(
            theGrid.getCell((myRow + neighborRowIndex[i]), (myCol + neighborColIndexForMyRow[i]))
                .getCurrentState());
      } else if (toroidal) {
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
    int[] neighborColIndexForMyRow = getNeighborColIndex();
    int newNeighborRowIndex = myRow + neighborRowIndex[i];
    int newNeighborColIndex = myCol + neighborColIndexForMyRow[i];
    if (myRow + neighborRowIndex[i] > theGrid.getHeight()) {
      newNeighborRowIndex = neighborRowIndex[i] - 1;
    } else if (myRow + neighborRowIndex[i] < theGrid.getHeight()) {
      newNeighborRowIndex = theGrid.getHeight() + neighborRowIndex[i];
    }

    if (myCol + neighborColIndexForMyRow[i] > theGrid.getWidth()) {
      newNeighborColIndex = neighborColIndexForMyRow[i] - 1;
    } else if (myCol + neighborColIndexForMyRow[i] < theGrid.getWidth()) {
      newNeighborColIndex = theGrid.getWidth() + neighborColIndexForMyRow[i];
    }
    return new int[]{newNeighborRowIndex, newNeighborColIndex};
  }

  /**
   * Setter method for cell state
   *
   * @param newState
   */
  public void setCellState(int newState) {
    this.myState = newState;
  }

  /**
   * Cell type dependent method that changes the current state of the cell
   *
   * @param theOldGrid current Grid to update state based on
   * @param theNewGrid the grid that is being updated
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

  /**
   * Getter method for the column indexes for the neighborhood
   *
   * @return
   */
  public int[] getNeighborColIndex() {
    if (myRow % 2 == 0) {
      return neighborEvenColIndex;
    } else {
      return neighborOddColIndex;
    }
  }

  /**
   * Getter method for the row indexes for the neighborhood
   *
   * @return
   */
  public int[] getNeighborRowIndex() {
    return neighborRowIndex;
  }

  /**
   * Setter method for toroidal
   *
   * @param newStatus
   */
  public void setToroidal(boolean newStatus) {
    toroidal = newStatus;
  }

  /**
   * Setter method for hexagon
   *
   * @param newStatus
   */
  public void setHexagon(boolean newStatus) {
    hexagon = newStatus;
    if (hexagon) {
      neighborEvenColIndex = new int[]{-1, -1, 0, 1, 0, -1};
      neighborOddColIndex = new int[]{-1, 0, 1, 1, 1, 0};
      neighborRowIndex = new int[]{0, -1, -1, 0, 1, 1};
    }
  }
}

