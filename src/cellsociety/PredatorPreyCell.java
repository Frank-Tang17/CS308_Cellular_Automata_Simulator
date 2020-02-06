package cellsociety;

import java.util.ArrayList;
import javafx.scene.paint.Color;

public class PredatorPreyCell extends Cell {
  private static final int emptyState = 0;
  private static final int typeFish = 1;
  private static final int typeShark = 2;
  private static final int newFishTime = 4;
  private static final int newSharkTime = 10;
  private static final int startingSharkEnergy = 5;
  private static final int energyPerFish = 2;


  /**
   * Constructor for master class Cell object
   *
   * @param row           the cells row in the grid
   * @param col           the cells col in the grid
   * @param size          the width and height of cell
   * @param startingState the starting state of the cell
   */
  public PredatorPreyCell(int row, int col, double size, int startingState) {
    super(row, col, size, startingState);
    neighborColIndex = new int[]{0, 1, 0, -1}; // Define sets of coordinates for neighbors
    neighborRowIndex = new int[]{-1, 0, 1, 0}; // Define sets of coordinates for neighbors
    cellFillColors = new Color[]{Color.BLUE, Color.GREEN, Color.ORANGE};
    cellStrokeColors = new Color[]{Color.BLACK, Color.BLACK, Color.BLACK};
    numFramesAlive = 0;

    updateRectangle();
  }

  @Override
  public void update(Grid theOldGrid, Grid theNewGrid) {
    ArrayList<Integer> neighborStatesAsList = this.getNeighborStates(theOldGrid);

    if (myState == typeFish) { // if I am a fish
      if (neighborStatesAsList.contains(emptyState)) {
        for (int i = 0; i < neighborColIndex.length; i++) {
          if (theNewGrid.isValidIndex(myRow + neighborRowIndex[i], myCol + neighborColIndex[i])) {
            if (theOldGrid.getGrid()[myRow + neighborRowIndex[i]][myCol
                + neighborColIndex[i]].myState == emptyState && theNewGrid.getCell(myRow + neighborRowIndex[i], myCol + neighborColIndex[i]).getCurrentState() == emptyState) {

              theNewGrid.getCell(myRow + neighborRowIndex[i], myCol + neighborColIndex[i]).myState = typeFish;
              theNewGrid.getCell(myRow + neighborRowIndex[i], myCol + neighborColIndex[i]).numFramesAlive = numFramesAlive;

              if (numFramesAlive % newFishTime != 0 || numFramesAlive == 0) {
                myState = emptyState;
              }
              numFramesAlive = 0;
            }
          }
        }
      }
    } else if (myState == typeShark) {
      if (myEnergy == 0) {
        myState = emptyState;
        numFramesAlive = 0;
        return;
      }
      if (neighborStatesAsList.contains(typeFish)) {
        for (int i = 0; i < neighborColIndex.length; i++) {
          if (theNewGrid.isValidIndex(myRow + neighborRowIndex[i], myCol + neighborColIndex[i])) {
            if (theOldGrid.getGrid()[myRow + neighborRowIndex[i]][myCol
                + neighborColIndex[i]].myState == typeFish && theNewGrid.getCell(myRow + neighborRowIndex[i], myCol + neighborColIndex[i]).getCurrentState() == typeFish) {
              myEnergy += energyPerFish;
              moveSharkAndCheckReproduce(theNewGrid, neighborRowIndex[i], neighborColIndex[i]);
            }
          }
        }
      } else if (neighborStatesAsList.contains(emptyState)) {
        for (int i = 0; i < neighborColIndex.length; i++) {
          if (theNewGrid.isValidIndex(myRow + neighborRowIndex[i], myCol + neighborColIndex[i])) {
            if (theOldGrid.getGrid()[myRow + neighborRowIndex[i]][myCol
                + neighborColIndex[i]].myState == emptyState && theNewGrid.getCell(myRow + neighborRowIndex[i], myCol + neighborColIndex[i]).getCurrentState() == emptyState) {
              moveSharkAndCheckReproduce(theNewGrid, neighborRowIndex[i], neighborColIndex[i]);
            }
          }
        }
      }
      myEnergy -= 1;
    }
  numFramesAlive += 1;
  }

  private void moveSharkAndCheckReproduce(Grid theNewGrid, int neighborRowShift, int neighborColShift) {
    theNewGrid.getCell(myRow + neighborRowShift, myCol + neighborColShift).myState = typeShark;
    theNewGrid.getCell(myRow + neighborRowShift, myCol + neighborColShift).numFramesAlive = numFramesAlive;
    theNewGrid.getCell(myRow + neighborRowShift, myCol + neighborColShift).myEnergy = myEnergy;

    if (numFramesAlive % newSharkTime != 0 || numFramesAlive == 0) { // If I should NOT REPRODUCE
      myState = emptyState;
    } else {
      myEnergy = startingSharkEnergy;
    }
    numFramesAlive = 0;
  }

}

/*

if (numFramesAlive % newFishTime == 0 && numFramesAlive != 0) { // if I should reproduce
        if (neighborStatesAsList.contains(emptyState)) { // If I can reproduce
          for (int i = 0; i < neighborColIndex.length; i++) {
            if (theNewGrid.isValidIndex(myRow + neighborRowIndex[i], myCol + neighborColIndex[i])) {
              if (theOldGrid.getGrid()[myRow + neighborRowIndex[i]][myCol + neighborColIndex[i]].myState == emptyState && !theOldGrid.getGrid()[myRow + neighborRowIndex[i]][myCol + neighborColIndex[i]].justSwitched) {
                theNewGrid.getGrid()[myRow + neighborRowIndex[i]][myCol + neighborColIndex[i]].myState = typeFish;
                theOldGrid.getGrid()[myRow + neighborRowIndex[i]][myCol + neighborColIndex[i]].justSwitched = true;
                numFramesAlive = 0;
              }
            }
          }
        } else { // If I cant reproduce but should
          numFramesAlive -= 1;
        }
      }

      if (neighborStatesAsList.contains(emptyState) && !justSwitched) {
        for (int i = 0; i < neighborColIndex.length; i++) {
          if (theNewGrid.isValidIndex(myRow + neighborRowIndex[i], myCol + neighborColIndex[i])) {
            if (theOldGrid.getGrid()[myRow + neighborRowIndex[i]][myCol + neighborColIndex[i]].myState == emptyState && !theOldGrid.getGrid()[myRow + neighborRowIndex[i]][myCol + neighborColIndex[i]].justSwitched) {
              theNewGrid.getGrid()[myRow + neighborRowIndex[i]][myCol + neighborColIndex[i]].myState = typeFish;
              theOldGrid.getGrid()[myRow + neighborRowIndex[i]][myCol + neighborColIndex[i]].justSwitched = true;
              myState = emptyState;
              justSwitched = true;
              numFramesAlive = 0;
            }
          }
        }
      }
      numFramesAlive += 1;




            if (neighborStatesAsList.contains(typeFish)) {
        for (int i = 0; i < neighborColIndex.length; i++) {
          if (theNewGrid.isValidIndex(myRow + neighborRowIndex[i], myCol + neighborColIndex[i])) {
            if (theOldGrid.getGrid()[myRow + neighborRowIndex[i]][myCol + neighborColIndex[i]].myState == typeFish && !theOldGrid.getGrid()[myRow + neighborRowIndex[i]][myCol + neighborColIndex[i]].justSwitched) {
              theNewGrid.getGrid()[myRow + neighborRowIndex[i]][myCol + neighborColIndex[i]].myState = emptyState;
              theOldGrid.getGrid()[myRow + neighborRowIndex[i]][myCol + neighborColIndex[i]].justSwitched = true;

              //numFramesAlive = 0;
            }
          }
        }
      } else if (neighborStatesAsList.contains(emptyState) && !justSwitched) {
        for (int i = 0; i < neighborColIndex.length; i++) {
          if (theNewGrid.isValidIndex(myRow + neighborRowIndex[i], myCol + neighborColIndex[i])) {
            if (theOldGrid.getGrid()[myRow + neighborRowIndex[i]][myCol + neighborColIndex[i]].myState == emptyState && !theOldGrid.getGrid()[myRow + neighborRowIndex[i]][myCol + neighborColIndex[i]].justSwitched) {
              theNewGrid.getGrid()[myRow + neighborRowIndex[i]][myCol + neighborColIndex[i]].myState = typeShark;
              theOldGrid.getGrid()[myRow + neighborRowIndex[i]][myCol + neighborColIndex[i]].justSwitched = true;
              myState = emptyState;
              justSwitched = true;
              numFramesAlive = 0;
            }
          }
        }
      }


      if (numFramesAlive % newSharkTime == 0 && numFramesAlive != 0) { // if I should reproduce
        if (neighborStatesAsList.contains(emptyState)) { // If I can reproduce
          for (int i = 0; i < neighborColIndex.length; i++) {
            if (theNewGrid.isValidIndex(myRow + neighborRowIndex[i], myCol + neighborColIndex[i])) {
              if (theOldGrid.getGrid()[myRow + neighborRowIndex[i]][myCol + neighborColIndex[i]].myState == emptyState && !theOldGrid.getGrid()[myRow + neighborRowIndex[i]][myCol + neighborColIndex[i]].justSwitched) {
                theNewGrid.getGrid()[myRow + neighborRowIndex[i]][myCol + neighborColIndex[i]].myState = typeShark;
                theOldGrid.getGrid()[myRow + neighborRowIndex[i]][myCol + neighborColIndex[i]].justSwitched = true;
                numFramesAlive = 0;
              }
            }
          }
        } else { // If I cant reproduce but should
          numFramesAlive -= 1;
        }
      }


 */
