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
    private static int myRow, myCol;
    public int myState;
    private Rectangle myRect;
    private ImageView myImage; // This may be on a subclass by subclass basis
    public int[] neighborColIndex;
    public int[] neighborRowIndex;
    public Color[] cellFillColors;
    public Color[] cellStrokeColors;


    // 0, 30 for upper left hand corner of grid
    // 450 across 450 from top to bottom

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
        myRect.setStrokeType(Main.cellStrokeType);
        myRect.setStrokeWidth(Main.cellStrokeProportion * width);
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
    public int[] getNeighborStates(Grid theGrid) {
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

    /**
     * Update the fill and the stroke color of the rectangle based on current state
     */
    public void updateRectangle() {
        myRect.setFill(cellFillColors[myState]);
        myRect.setStroke(cellStrokeColors[myState]);
    }

}

