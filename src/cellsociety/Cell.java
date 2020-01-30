package cellsociety;

import javax.swing.text.html.ImageView;
import java.awt.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

/**
 * Cell acts as a node of the grid for the simulation
 */
public abstract class Cell {
  protected int myRow, myCol;
  protected int myState;
  private Rectangle myRect;
  private ImageView myImage; // This may be on a subclass by subclass basis
  protected int[] neighborColIndex;
  protected int[] neighborRowIndex;
  protected Color[] cellFillColors;
  protected Color[] cellStrokeColors;

  private final int upperLeftX = 0;
  private final int upperLeftY = 30;

  /**
   * Constructor for master class Cell object
   *
   * @param row           the cells row in the grid
   * @param col           the cells col in the grid
   * @param size          the width and height of cell
   * @param startingState the starting state of the cell
   */
  public Cell(int row, int col, double size, int startingState) {
    myState = startingState;
    myRow = row;
    myCol = col;
    myRect = new Rectangle(col * size + upperLeftX, row * size + upperLeftY, size, size);
    myRect.setStrokeType(Main.cellStrokeType);
    myRect.setStrokeWidth(Main.cellStrokeProportion * size);
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
  public int[] getNeighborStates(Grid theGrid) {
    int[] neighborStates = new int[neighborColIndex.length];
    for (int i = 0; i < neighborColIndex.length; i++) {
      if (theGrid.isValidIndex(myRow + neighborRowIndex[i], myCol + neighborColIndex[i])) {
        neighborStates[i] = theGrid.getGrid()[myRow + neighborRowIndex[i]][myCol
            + neighborColIndex[i]].getCurrentState();
      }
    }
    return neighborStates;
  }

  /**
   * Cell type dependent method that changes the current state of the cell
   *
   * @param theOldGrid current Grid to update state based on
   */
  public abstract void update(Grid theOldGrid);

  /**
   * Getter method for the current state of the Cell
   *
   * @return the current state
   */
  public int getCurrentState() {
    return myState;
  }

  /**
   *
   */
  private void setCellImage() {

  }

  /**
   * Update the fill and the stroke color of the rectangle based on current state
   */
  public void updateRectangle() {
    myRect.setFill(cellFillColors[myState]);
    myRect.setStroke(cellStrokeColors[myState]);
  }

}

