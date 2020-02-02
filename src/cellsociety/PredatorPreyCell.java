package cellsociety;

import java.util.ArrayList;
import javafx.scene.paint.Color;

public class PredatorPreyCell extends Cell {
  private final int emptyState = 0;
  private final int typeFish = 1;
  private final int typeShark = 2;
  private final int newFishTime = 4;
  private final int newSharkTime = 10;
  private int numFramesAlive;
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
    } else if (myState == typeShark) {
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
    }

  }
}
