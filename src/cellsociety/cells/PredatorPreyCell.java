package cellsociety.cells;

import cellsociety.Cell;
import cellsociety.Grid;
import java.util.ArrayList;
import javafx.scene.paint.Color;

public class PredatorPreyCell extends Cell {
  private final int emptyState = 0;
  private final int typeFish = 1;
  private final int typeShark = 2;
  private final int newFishTime = 4;
  private final int newSharkTime = 20;
  private final int startingSharkEnergy = 5;
  private final int energyPerFish = 2;


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
    if (myState == typeShark) {
      myEnergy = startingSharkEnergy;
    }
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
    } else if (numFramesAlive % newSharkTime == 0 && numFramesAlive != 0){
      myEnergy = startingSharkEnergy;
    }
    numFramesAlive = 0;
  }

}
