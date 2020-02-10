package cellsociety.cells;

import cellsociety.Cell;
import cellsociety.Grid;
import java.util.ArrayList;

public class PredatorPreyCell extends Cell {

  private final int emptyState = 0;
  private final int typeFish = 1;
  private final int typeShark = 2;
  private final int newFishTime = 4;
  private final int newSharkTime = 20;
  private final int startingSharkEnergy = 5;
  private final int energyPerFish = 2;

  private int numFramesAlive;
  private int myEnergy;


  /**
   * Constructor for master class Cell object
   *
   * @param row           the cells row in the grid
   * @param col           the cells col in the grid
   * @param size          the width and height of cell
   * @param startingState the starting state of the cell
   *
   */
  public PredatorPreyCell(int row, int col, double size, int startingState,
      int[] neighborRowIndexes,
      int[] neighborColIndexes) {
    super(row, col, size, startingState, neighborRowIndexes, neighborColIndexes);
    numFramesAlive = 0;
    if (getCurrentState() == typeShark) {
      myEnergy = startingSharkEnergy;
    }
  }

  @Override
  public void update(Grid theOldGrid, Grid theNewGrid) {
    ArrayList<Integer> neighborStatesAsList = new ArrayList<>(this.getNeighborStates(theOldGrid));

    int[] neighborColIndex = getNeighborColIndex();
    int[] neighborRowIndex = getNeighborRowIndex();


    if (getCurrentState() == this.typeFish) { // if I am a fish
      if (neighborStatesAsList.contains(emptyState)) {
        for (int i = 0; i < neighborColIndex.length; i++) {
          if (theNewGrid.isValidIndex(getRowAndCol()[0] + neighborRowIndex[i],
              getRowAndCol()[1] + neighborColIndex[i])) {
            if (theOldGrid.getCell(getRowAndCol()[0] + neighborRowIndex[i], getRowAndCol()[1]
                + neighborColIndex[i]).getCurrentState() == emptyState && theNewGrid
                .getCell(getRowAndCol()[0] + neighborRowIndex[i],
                    getRowAndCol()[1] + neighborColIndex[i]).getCurrentState() == emptyState) {

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
          }
        }
      }
    } else if (getCurrentState() == typeShark) {
      if (myEnergy == 0) {
        setCellState(emptyState);
        numFramesAlive = 0;
        return;
      }
      if (neighborStatesAsList.contains(typeFish)) {
        for (int i = 0; i < neighborColIndex.length; i++) {
          if (theNewGrid.isValidIndex(getRowAndCol()[0] + neighborRowIndex[i],
              getRowAndCol()[1] + neighborColIndex[i])) {
            if (theOldGrid.getCell(getRowAndCol()[0] + neighborRowIndex[i], getRowAndCol()[1]
                + neighborColIndex[i]).getCurrentState() == typeFish && theNewGrid
                .getCell(getRowAndCol()[1] + neighborRowIndex[i],
                    getRowAndCol()[1] + neighborColIndex[i]).getCurrentState() == typeFish) {
              myEnergy += energyPerFish;
              System.out.println("Hi 1");
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
      System.out.println(myEnergy);
      if (myEnergy > 0) {
        myEnergy -= 1;
      }
      if (myEnergy == 0) {
        setCellState(emptyState);
      }
    }
    numFramesAlive += 1;
  }

  private Cell loop(ArrayList<Integer> neighborStatesAsList, int stateLookingFor, Grid theOldGrid,
      Grid theNewGrid) {

    int[] neighborColIndex = getNeighborColIndex();
    int[] neighborRowIndex = getNeighborRowIndex();

    for (int i = 0; i < neighborColIndex.length; i++) {
      if (theNewGrid.isValidIndex(getRowAndCol()[0] + neighborRowIndex[i],
          getRowAndCol()[1] + neighborColIndex[i])) {
        if (theOldGrid.getCell(getRowAndCol()[0] + neighborRowIndex[i], getRowAndCol()[1]
            + neighborColIndex[i]).getCurrentState() == stateLookingFor &&
            theNewGrid.getCell(getRowAndCol()[0] + neighborRowIndex[i],
                getRowAndCol()[1] + neighborColIndex[i])
                .getCurrentState() == stateLookingFor) {
          return theNewGrid.getCell(getRowAndCol()[0] + neighborRowIndex[i],
              getRowAndCol()[1] + neighborColIndex[i]);
        }
      }
    }
    return null;
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
}
