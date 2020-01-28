package cellsociety;

import java.awt.*;
import java.util.*;
import java.util.List;
import javafx.scene.paint.Color;

public class GameOfLifeCell extends Cell {

    /**
     * Constructor for master class Cell object
     *
     * @param row           the cells row in the grid
     * @param col           the cells col in the grid
     * @param xCoor         the x-coordinate of the upper left hand corner of the cell
     * @param yCoor         the y-coordinate of the upper left hand corner of the cell
     * @param width         the width of the cell
     * @param height        the height of the cell
     * @param startingState the starting state of the cell
     */
    public GameOfLifeCell(int row, int col, int xCoor, int yCoor, int width, int height, int startingState) {
        super(row, col, xCoor, yCoor, width, height, startingState);
        this.neighborColIndex = new int[] {0, 1, 0, -1};
        this.neighborRowIndex = new int[] {-1, 0, 1, 0};
        this.cellFillColors = new Color[] {Color.WHITE, Color.BLACK};
        this.cellStrokeColors = new Color[] {Color.WHITE, Color.BLACK};

    }

    @Override
    public void update(Grid theGrid) {
        List neighborStatesAsList = Arrays.asList(this.getNeighborStates(theGrid));

        int numNeighborAlive = Collections.frequency(neighborStatesAsList, 1);

        if (this.myState == 0 && numNeighborAlive == 3) {
            this.myState = 1;
        } else if (this.myState == 1 && (numNeighborAlive < 2 || numNeighborAlive > 3)) {
            this.myState = 0;
        }

        this.updateRectangle();
    }
}
