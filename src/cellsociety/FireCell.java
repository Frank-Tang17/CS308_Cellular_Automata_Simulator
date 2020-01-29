package cellsociety;

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
    this.neighborColIndex = new int[]{0, 1, 0, -1}; // Define sets of coordinates for neighbors
    this.neighborRowIndex = new int[]{-1, 0, 1, 0}; // Define sets of coordinates for neighbors
    this.cellFillColors = new Color[]{Color.WHITE,
        Color.BLACK}; // Colors should align will cell state number
    this.cellStrokeColors = new Color[]{Color.GREY,
        Color.GREY}; // Colors should align will cell state number
    this.updateRectangle();
  }

  @Override
  public void update(Grid theGrid) {
    int rand = (int) (Math.random() * 100);
    int compProb = 100 * prob;

    if (this.myState == 2) {
      this.myState = 0;

    }
  }
}
