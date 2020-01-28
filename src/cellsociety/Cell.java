package cellsociety;

import javax.swing.text.html.ImageView;
import java.awt.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

public class Cell {
    private int myRow, myCol;
    private int myState;
    private Rectangle myRect;
    private ImageView myImage; // This may be on a subclass by subclass basis
    private static int[] neighborColIndex = {0, 1, 0, -1};
    private static int[] neighborRowIndex = {-1, 0, 1, 0};

    public Cell(int row, int col, int xCoor, int yCoor, int width, int height, int startingState) {
        myState = startingState;
        myRow = row;
        myCol = col;
        myRect = new Rectangle(xCoor, yCoor, width, height);
        myRect.setFill(Color.WHITE);
        myRect.setStroke(Color.BLACK);
;    }

    public int[] getRowAndCol() {
        return new int[] {myRow, myCol};
    }

    private int[] getNeighborStates(Grid theGrid) {
        int[] neighborStates = new int [neighborColIndex.length];
        for (int i = 0; i < neighborColIndex.length; i++) {
            neighborStates[i] = theGrid.getMyGrid[myRow + neighborRowIndex[i]][myCol + neighborColIndex[i]].getCurrentState();
        }
        return neighborStates;
    }

    public void update(Grid theGrid) {

    }

    public int getCurrentState() {
        return myState;
    }

    private void setCellImage() {

    }

}
