package cellsociety;

import javafx.scene.Group;

public class Grid {
  private Cell[][] grid;
  private int height;
  private int width;
  private final int simulationScreenWidth = 450;
  private final int simulationScreenHeight = 450;

  public Grid(int row, int col) {
    grid = new Cell[row][col];
    width = col;
    height = row;
    double size  = determineCellSize(row, col);
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        if (j % 50 == 0){
          grid[i][j] = new FireCell(i, j, size, 2);
        }
        else {
          grid[i][j] = new FireCell(i, j, size, 1);
        }
      }
    }
  }


  //Populate the grid values with the initial state//
    /*
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
    */
    
  public void gridVisualization(Group node){
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        node.getChildren().add(grid[i][j].getCellNode());
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

/*
  public void updateGrid(Grid gridnew) {
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        gridnew.getCell(i, j).update(this);
        */


  public Cell[][] getGrid() {
    return grid;
  }

  public void copyGrid(Grid gridnew) {
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        grid[i][j].setCellState(gridnew.getCell(i, j).getCurrentState());
        grid[i][j].updateRectangle();
      }
    }
  }
  public Cell getCell(int row, int col) {
    return grid[row][col];
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