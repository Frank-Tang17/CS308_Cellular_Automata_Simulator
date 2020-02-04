package cellsociety;

import java.util.ArrayList;
import javax.swing.text.html.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * Cell acts as a node of the grid for the simulation
 */
public abstract class Cell {

  protected int myRow, myCol;
  public int myState;
  private Rectangle myRect;
  private ImageView myImage; // This may be on a subclass by subclass basis
  protected int[] neighborColIndex;
  protected int[] neighborRowIndex;
  protected Color[] cellFillColors;
  protected Color[] cellStrokeColors;

  public boolean justSwitched;
  public int numFramesAlive;
  public int myEnergy;

  private final int upperLeftX = 75;
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
    myRect.setStrokeType(Simulator.cellStrokeType);
    myRect.setStrokeWidth(Simulator.cellStrokeProportion * size);
  }
    /*
    public Cell(int row, int col, int xCoor, int yCoor, int width, int height, int startingState) {
        myState = startingState;
        myRow = row;
        myCol = col;
        myRect = new Rectangle(xCoor, yCoor, width, height);
        myRect.setStrokeType(Main.cellStrokeType);
        myRect.setStrokeWidth(Main.cellStrokeProportion * 1);
    }
    */

    /**
     * Getter method for row and column
     *
     * @return array of Cell row and col in that order
     */
    public int[] getRowAndCol() {
        return new int[] {myRow, myCol};
    }

    /**
     * Returns the states of each neighboring cell
     *
     * @param theGrid current Grid to update state based on
     * @return array of neighboring states
     */
    public ArrayList<Integer> getNeighborStates(Grid theGrid) {
        ArrayList<Integer> neighborStates = new ArrayList();
        for (int i = 0; i < neighborColIndex.length; i++) {
            if (theGrid.isValidIndex(myRow + neighborRowIndex[i], myCol + neighborColIndex[i])) {
                neighborStates.add(theGrid.getCell((myRow + neighborRowIndex[i]),(myCol + neighborColIndex[i])).getCurrentState());
            }
        }

        return neighborStates;
    }


  public void setCellState(int newState) {
    this.myState = newState;
  }

    public Rectangle getCellNode(){
      return this.myRect;
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

/*
public enum CellTypeEnum {

  GAMEOFLIFE {
    @Override
    Cell create(String name) {
      return null;
    }

    public Cell create(int i, int j, double size, int type) {
      return new GameOfLifeCell(i, j, size, type);
    }
  },
  FIRE {
    public Cell create(int i, int j, double size, int type) {
      return new FireCell(i, j, size, type);
    }
  },
  PREDATORPREY {
    public Cell create(int i, int j, double size, int type) {
      return new PredatorPreyCell(i, j, size, type);
    }
  };

  abstract Cell create(String name);
}
*/
