package cellsociety;

import java.util.ArrayList;

public class Grid {


  private Cell[][] grid;
  private int height;
  private int width;

  public Grid(int row, int col) {
    grid = new Cell[row][col];
    width = col;
    height = row;
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        if (i % 4 == 0 || j % 2 == 0){
          grid[i][j] = new GameOfLifeCell(j, i, (j * 20) + 100, (i * 20) + 100, 7 , 7, 1);
        }
        else{
          grid[i][j] = new GameOfLifeCell(j, i, (j * 20) + 100, (i * 20) + 100, 7, 7, 0);
        }
      }
    }
  }

  public void gridVisualization(){
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        SimulationLoader.grid.getChildren().add(grid[i][j].getCellNode());
      }
    }
  }
  //Populate the grid values with the initial state//

  public void fillInitState(ArrayList<Integer> init_state) {
    //Need to figure out how the state data is incoming, whether we can store it in an ArrayList.
    int k = 0;
//        for(int i = 0; i<height; i++){
//            for(int j= 0; j<width; j++){
//                grid[i][j] = init_state.get(k);
//                k++;
//            }
//        }

  }

  public int getHeight() {
    return height;
  }

  public int getWidth() {
    return width;
  }

  public void updateGrid(Grid gridnew) {
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        gridnew.getCell(i, j).update(this);
      }
    }
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

}