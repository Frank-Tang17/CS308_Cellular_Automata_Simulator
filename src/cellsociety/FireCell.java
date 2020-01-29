package cellsociety;

import java.util.Arrays;
import java.util.List;
import javafx.scene.paint.Color;

public class FireCell extends Cell {
  private int prob;

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
  public FireCell(int row, int col, int xCoor, int yCoor, int width, int height,
      int startingState) {
    super(row, col, xCoor, yCoor, width, height, startingState);
    neighborColIndex = new int[]{0, 1, 0, -1}; // Define sets of coordinates for neighbors
    neighborRowIndex = new int[]{-1, 0, 1, 0}; // Define sets of coordinates for neighbors
    cellFillColors = new Color[]{Color.YELLOW, Color.GREEN, Color.FIREBRICK};
    cellStrokeColors = new Color[]{Color.BLACK, Color.BLACK, Color.BLACK};
    updateRectangle();
  }

  @Override
  public void update(Grid theGrid) {
    List neighborStatesAsList = Arrays.asList(this.getNeighborStates(theGrid));

    int rand = (int) (Math.random() * 100);
    int compProb = 100 * prob;

    if (this.myState == 2) {
      this.myState = 0;
    } else if (neighborStatesAsList.contains(2) && myState == 1) {
      myState = 2;
    }

    updateRectangle();

  }
}
