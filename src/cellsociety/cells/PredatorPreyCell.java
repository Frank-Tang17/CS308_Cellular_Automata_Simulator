package cellsociety.cells;

import cellsociety.Cell;
import cellsociety.Grid;
import java.util.ArrayList;

/**
 * PredatorPrey Cell class
 */
public class PredatorPreyCell extends Cell {

  private final int EMPTY_STATE = 0;
  private final int TYPE_FISH = 1;
  private final int TYPE_SHARK = 2;
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
    if (getCurrentState() == TYPE_SHARK) {
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

    if (getCurrentState() == this.TYPE_FISH) { // if I am a fish
      isFish(theOldGrid, theNewGrid, neighborStatesAsList);
    } else if (getCurrentState() == TYPE_SHARK) {
      if (myEnergy == 0) {
        setCellState(EMPTY_STATE);
        numFramesAlive = 0;
        return;
      }
      isAShark(theOldGrid, theNewGrid, neighborStatesAsList);
      if (myEnergy > 0) {
        myEnergy -= 1;
      }
      if (myEnergy == 0) {
        setCellState(EMPTY_STATE);
      }
    }
    numFramesAlive += 1;
  }

  private void isFish(Grid theOldGrid, Grid theNewGrid, ArrayList<Integer> neighborStatesAsList) {
    int[] neighborColIndex = getNeighborColIndex();
    int[] neighborRowIndex = getNeighborRowIndex();
    if (neighborStatesAsList.contains(EMPTY_STATE)) {
      for (int i = 0; i < neighborColIndex.length; i++) {
        if (theNewGrid.isValidIndex(getRowAndCol()[0] + neighborRowIndex[i],
            getRowAndCol()[1] + neighborColIndex[i])) {
          if (theOldGrid.getCell(getRowAndCol()[0] + neighborRowIndex[i], getRowAndCol()[1]
              + neighborColIndex[i]).getCurrentState() == EMPTY_STATE && theNewGrid
              .getCell(getRowAndCol()[0] + neighborRowIndex[i],
                  getRowAndCol()[1] + neighborColIndex[i]).getCurrentState() == EMPTY_STATE) {
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
        getRowAndCol()[1] + neighborColIndex[i]).setCellState(TYPE_FISH);

    try {
      PredatorPreyCell temp = (PredatorPreyCell) theNewGrid
          .getCell(getRowAndCol()[0] + neighborRowIndex[i],
              getRowAndCol()[1] + neighborColIndex[i]);
      temp.numFramesAlive = numFramesAlive;
    } catch (Exception ignored) {

    }

    if (numFramesAlive % newFishTime != 0 || numFramesAlive == 0) {
      setCellState(EMPTY_STATE);
    }
    numFramesAlive = 0;
  }

  private void isAShark(Grid theOldGrid, Grid theNewGrid, ArrayList<Integer> neighborStatesAsList) {
    int[] neighborColIndex = getNeighborColIndex();
    int[] neighborRowIndex = getNeighborRowIndex();
    if (neighborStatesAsList.contains(TYPE_FISH)) {
      for (int i = 0; i < neighborColIndex.length; i++) {
        if (theNewGrid.isValidIndex(getRowAndCol()[0] + neighborRowIndex[i],
            getRowAndCol()[1] + neighborColIndex[i])) {
          if (theOldGrid.getCell(getRowAndCol()[0] + neighborRowIndex[i], getRowAndCol()[1]
              + neighborColIndex[i]).getCurrentState() == TYPE_FISH && theNewGrid
              .getCell(getRowAndCol()[0] + neighborRowIndex[i],
                  getRowAndCol()[1] + neighborColIndex[i]).getCurrentState() == TYPE_FISH) {
            myEnergy += energyPerFish;
            moveSharkAndCheckReproduce(theNewGrid, neighborRowIndex[i], neighborColIndex[i]);
          }
        }
      }
    } else if (neighborStatesAsList.contains(EMPTY_STATE)) {
      for (int i = 0; i < neighborColIndex.length; i++) {
        if (theNewGrid.isValidIndex(getRowAndCol()[0] + neighborRowIndex[i],
            getRowAndCol()[1] + neighborColIndex[i])) {
          if (theOldGrid.getCell(getRowAndCol()[0] + neighborRowIndex[i], getRowAndCol()[1]
              + neighborColIndex[i]).getCurrentState() == EMPTY_STATE && theNewGrid
              .getCell(getRowAndCol()[0] + neighborRowIndex[i],
                  getRowAndCol()[1] + neighborColIndex[i]).getCurrentState() == EMPTY_STATE) {
            moveSharkAndCheckReproduce(theNewGrid, neighborRowIndex[i], neighborColIndex[i]);
          }
        }
      }
    }
  }

  private void moveSharkAndCheckReproduce(Grid theNewGrid, int neighborRowShift,
      int neighborColShift) {
    theNewGrid.getCell(getRowAndCol()[0] + neighborRowShift, getRowAndCol()[1] + neighborColShift)
        .setCellState(TYPE_SHARK);
    try {
      PredatorPreyCell temp = (PredatorPreyCell) theNewGrid
          .getCell(getRowAndCol()[0] + neighborRowShift, getRowAndCol()[1] + neighborColShift);
      temp.numFramesAlive = numFramesAlive;
      temp.myEnergy = this.myEnergy;
    } catch (Exception ignored) {

    }
    myEnergy = 0;
    if (numFramesAlive % newSharkTime != 0 || numFramesAlive == 0) { // If I should NOT REPRODUCE
      setCellState(EMPTY_STATE);
    } else if (numFramesAlive % newSharkTime == 0) {
      myEnergy = startingSharkEnergy;
    }
    numFramesAlive = 0;
  }

  /**
   * Setter method for the number of frames needed to make a new fish
   * @param fishTime the new time for a fish to reproduce
   */
  public void setNewFishTime(int fishTime) {
    newFishTime = fishTime;
  }

  /**
   * Setter method for the number of frames needed to make a new shark
   * @param sharkTime the new time for a shark to reproduce
   */
  public void setNewSharkTime(int sharkTime) {
    newSharkTime = sharkTime;
  }

  /**
   * Setter method for the starting energy of a shark
   * @param newStartingSharkEnergy the new starting shark energy
   */
  public void setStartingSharkEnergy(int newStartingSharkEnergy) {
    startingSharkEnergy = newStartingSharkEnergy;
  }

  /**
   * Setter method for the energy in a fish
   * @param newEnergyPerFish the new energy per fish
   */
  public void  setEnergyPerFish(int newEnergyPerFish) {
    energyPerFish = newEnergyPerFish;
  }
}
