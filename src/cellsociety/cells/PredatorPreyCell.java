package cellsociety.cells;

import cellsociety.Cell;
import cellsociety.Grid;
import java.util.ArrayList;

/**
 * PredatorPrey Cell class
 */
public class PredatorPreyCell extends Cell {

  private final int emptyState = 0;
  private final int typeFish = 1;
  private final int typeShark = 2;
  private int newFishTime = 4;
  private int newSharkTime = 20;
  private int startingSharkEnergy = 5;
  private int energyPerFish = 2;

  private int numFramesAlive;
  private int myEnergy;


  /**
   * Constructor for PredatorPreyCell class Cell object
   *
   * @param row                the cells row in the grid
   * @param col                the cells col in the grid
   * @param startingState      the starting state of the cell
   * @param neighborRowIndexes the int array that holds the row locations for the neighborhood
   * @param neighborColIndexes the int array that holds the col locations for the neighborhood
   */
  public PredatorPreyCell(int row, int col, int startingState,
      int[] neighborRowIndexes,
      int[] neighborColIndexes) {
    super(row, col, startingState, neighborRowIndexes, neighborColIndexes);
    numFramesAlive = 0;
    if (getCurrentState() == typeShark) {
      myEnergy = startingSharkEnergy;
    }
  }

  /**
   * Updates the PredatorPreyCell depending on its own set of rules
   *
   * @param theOldGrid current Grid to update state based on
   * @param theNewGrid the grid that is being updated
   */
  @Override
  public void update(Grid theOldGrid, Grid theNewGrid) {
    ArrayList<Integer> neighborStatesAsList = new ArrayList<>(this.getNeighborStates(theOldGrid));

    int[] neighborColIndex = getNeighborColIndex();
    int[] neighborRowIndex = getNeighborRowIndex();

    if (getCurrentState() == this.typeFish) { // if I am a fish
      isFish(theOldGrid, theNewGrid, neighborStatesAsList);
    } else if (getCurrentState() == typeShark) {
      if (myEnergy == 0) {
        setCellState(emptyState);
        numFramesAlive = 0;
        return;
      }
      isAShark(theOldGrid, theNewGrid, neighborStatesAsList);
      if (myEnergy > 0) {
        myEnergy -= 1;
      }
      if (myEnergy == 0) {
        setCellState(emptyState);
      }
    }
    numFramesAlive += 1;
  }

  private void isFish(Grid theOldGrid, Grid theNewGrid, ArrayList<Integer> neighborStatesAsList) {
    int[] neighborColIndex = getNeighborColIndex();
    int[] neighborRowIndex = getNeighborRowIndex();
    if (neighborStatesAsList.contains(emptyState)) {
      for (int i = 0; i < neighborColIndex.length; i++) {
        if (theNewGrid.isValidIndex(getRowAndCol()[0] + neighborRowIndex[i],
            getRowAndCol()[1] + neighborColIndex[i])) {
          if (theOldGrid.getCell(getRowAndCol()[0] + neighborRowIndex[i], getRowAndCol()[1]
              + neighborColIndex[i]).getCurrentState() == emptyState && theNewGrid
              .getCell(getRowAndCol()[0] + neighborRowIndex[i],
                  getRowAndCol()[1] + neighborColIndex[i]).getCurrentState() == emptyState) {
            alterFish(theNewGrid, i);
          }
        }
      }
    }
  }

  private void alterFish(Grid theNewGrid, int i) {
    int[] neighborColIndex = getNeighborColIndex();
    int[] neighborRowIndex = getNeighborRowIndex();
    theNewGrid.getCell(getRowAndCol()[0] + neighborRowIndex[i],
        getRowAndCol()[1] + neighborColIndex[i]).setCellState(typeFish);

    try {
      PredatorPreyCell temp = (PredatorPreyCell) theNewGrid
          .getCell(getRowAndCol()[0] + neighborRowIndex[i],
              getRowAndCol()[1] + neighborColIndex[i]);
      temp.numFramesAlive = numFramesAlive;
    } catch (Exception ignored) {

    }

    if (numFramesAlive % newFishTime != 0 || numFramesAlive == 0) {
      setCellState(emptyState);
    }
    numFramesAlive = 0;
  }

  private void isAShark(Grid theOldGrid, Grid theNewGrid, ArrayList<Integer> neighborStatesAsList) {
    int[] neighborColIndex = getNeighborColIndex();
    int[] neighborRowIndex = getNeighborRowIndex();
    if (neighborStatesAsList.contains(typeFish)) {
      for (int i = 0; i < neighborColIndex.length; i++) {
        if (theNewGrid.isValidIndex(getRowAndCol()[0] + neighborRowIndex[i],
            getRowAndCol()[1] + neighborColIndex[i])) {
          if (theOldGrid.getCell(getRowAndCol()[0] + neighborRowIndex[i], getRowAndCol()[1]
              + neighborColIndex[i]).getCurrentState() == typeFish && theNewGrid
              .getCell(getRowAndCol()[0] + neighborRowIndex[i],
                  getRowAndCol()[1] + neighborColIndex[i]).getCurrentState() == typeFish) {
            myEnergy += energyPerFish;
            moveSharkAndCheckReproduce(theNewGrid, neighborRowIndex[i], neighborColIndex[i]);
          }
        }
      }
    } else if (neighborStatesAsList.contains(emptyState)) {
      for (int i = 0; i < neighborColIndex.length; i++) {
        if (theNewGrid.isValidIndex(getRowAndCol()[0] + neighborRowIndex[i],
            getRowAndCol()[1] + neighborColIndex[i])) {
          if (theOldGrid.getCell(getRowAndCol()[0] + neighborRowIndex[i], getRowAndCol()[1]
              + neighborColIndex[i]).getCurrentState() == emptyState && theNewGrid
              .getCell(getRowAndCol()[0] + neighborRowIndex[i],
                  getRowAndCol()[1] + neighborColIndex[i]).getCurrentState() == emptyState) {
            moveSharkAndCheckReproduce(theNewGrid, neighborRowIndex[i], neighborColIndex[i]);
          }
        }
      }
    }
  }

  private void moveSharkAndCheckReproduce(Grid theNewGrid, int neighborRowShift,
      int neighborColShift) {
    theNewGrid.getCell(getRowAndCol()[0] + neighborRowShift, getRowAndCol()[1] + neighborColShift)
        .setCellState(typeShark);
    try {
      PredatorPreyCell temp = (PredatorPreyCell) theNewGrid
          .getCell(getRowAndCol()[0] + neighborRowShift, getRowAndCol()[1] + neighborColShift);
      temp.numFramesAlive = numFramesAlive;
      temp.myEnergy = this.myEnergy;
    } catch (Exception ignored) {

    }
    myEnergy = 0;
    if (numFramesAlive % newSharkTime != 0 || numFramesAlive == 0) { // If I should NOT REPRODUCE
      setCellState(emptyState);
    } else if (numFramesAlive % newSharkTime == 0) {
      myEnergy = startingSharkEnergy;
    }
    numFramesAlive = 0;
  }

  /**
   * Setter method for the number of frames needed to make a new fish
   * @param fishTime
   */
  public void setNewFishTime(int fishTime) {
    newFishTime = fishTime;
  }

  /**
   * Setter method for the number of frames needed to make a new shark
   * @param sharkTime
   */
  public void setNewSharkTime(int sharkTime) {
    newSharkTime = sharkTime;
  }

  /**
   * Setter method for the starting energy of a shark
   * @param newStartingSharkEnergy
   */
  public void setStartingSharkEnergy(int newStartingSharkEnergy) {
    startingSharkEnergy = newStartingSharkEnergy;
  }

  /**
   * Setter method for the energy in a fish
   * @param newEnergyPerFish
   */
  public void  setEnergyPerFish(int newEnergyPerFish) {
    energyPerFish = newEnergyPerFish;
  }
}
