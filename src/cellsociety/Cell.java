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
    private int myRow, myCol;
    private int myState;
    private Rectangle myRect;
    private ImageView myImage; // This may be on a subclass by subclass basis
    private static int[] neighborColIndex; // Dependent on cell type ------ = {0, 1, 0, -1};
    private static int[] neighborRowIndex; // Dependent on cell type ------ = {-1, 0, 1, 0};

    /**
     * Constructor for master class Cell object
     *
     * @param row the cells row in the grid
     * @param col the cells col in the grid
     * @param xCoor the x-coordinate of the upper left hand corner of the cell
     * @param yCoor the y-coordinate of the upper left hand corner of the cell
     * @param width the width of the cell
     * @param height the height of the cell
     * @param startingState the starting state of the cell
     */
    public Cell(int row, int col, int xCoor, int yCoor, int width, int height, int startingState) {
        myState = startingState;
        myRow = row;
        myCol = col;
        myRect = new Rectangle(xCoor, yCoor, width, height);
        myRect.setFill(Color.WHITE);
        myRect.setStroke(Color.BLACK);
;    }

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
    private int[] getNeighborStates(Grid theGrid) {
        int[] neighborStates = new int [neighborColIndex.length];
        for (int i = 0; i < neighborColIndex.length; i++) {
            if (theGrid.validIndex(myRow + neighborRowIndex[i], myCol + neighborColIndex[i])) {
                neighborStates[i] = theGrid.getMyGrid[myRow + neighborRowIndex[i]][myCol + neighborColIndex[i]].getCurrentState();
            }
        }
        return neighborStates;
    }

    /**
     * Cell type dependent method that changes the current state of the cell
     *
     * @param theGrid current Grid to update state based on
     */
    public abstract void update(Grid theGrid);

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

}
