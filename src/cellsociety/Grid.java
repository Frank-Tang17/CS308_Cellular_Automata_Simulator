package cellsociety;

import java.util.ArrayList;

public class Grid {


  Cell[][] grid;
  int height;
  int width;

  private final int simulationScreenWidth = 450;
  private final int simulationScreenHeight = 450;

  public Grid(int row, int col /** Parameter indicating initial config */) {
    grid = new Cell[row][col];
    width = col;
    height = row;

  }

  //Populate the grid values with the initial state//

  public void fillInitState(ArrayList<Integer> init_state) {
    //Need to figure out how the state data is incoming, whether we can store it in an ArrayList.
    double cellSize = determineCellSize(height, width);
    int k = 0;
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        grid[i][j] = new FireCell(i, j, cellSize, init_state.get(k));
        k++;
      }
    }
  }

  public int getHeight() {
    return height;
  }

  public int getWidth() {
    return width;
  }

  public void updateGrid(Grid oldGrid) {
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        grid[i][j].update(oldGrid);
      }
    }
  }

  public Cell[][] getGrid() {
    return grid;
  }

  public boolean isValidIndex(int row, int col) {
    return (row >= 0 && row < grid.length && col >= 0 && col < grid[0].length);
  }

  private double determineCellSize(int numRows, int numCols) {
    double maxWidth = simulationScreenWidth / numCols;
    double maxHeight = simulationScreenHeight / numRows;

    return Math.min(maxWidth, maxHeight);

  }

}