package cellsociety;

import cellsociety.cells.FireCell;
import cellsociety.cells.GameOfLifeCell;
import cellsociety.cells.PercolationCell;
import cellsociety.cells.PredatorPreyCell;
import cellsociety.cells.SegregationCell;
import javafx.scene.Group;

import java.util.ArrayList;


public class Grid {
  private Cell[][] grid;
  private int height;
  private int width;
  private final int simulationScreenWidth = 450;
  private final int simulationScreenHeight = 450;
  private Configuration simulationLoaded;

  public Grid(String selectedSimulation) {
    simulationLoaded = new Configuration(selectedSimulation);
    width = simulationLoaded.getWidth();
    height = simulationLoaded.getHeight();
    grid = new Cell[height][width];

//    double size  = determineCellSize(height, width);
//    for (int i = 0; i < height; i++) {
//      for (int j = 0; j < width; j++) {
//        if (j % 50 == 0){
//          grid[i][j] = new FireCell(i, j, size, 2);
//        }
//        else {
//          grid[i][j] = new FireCell(i, j, size, 1);
//        }
//      }
//    }

    fillInitState(simulationLoaded.getInitState());
  }


  //Populate the grid values with the initial state//

  public void fillInitState(ArrayList<Integer> init_state) {
    //Need to figure out how the state data is incoming, whether we can store it in an ArrayList.
    double cellSize = determineCellSize(height, width);
    int k = 0;
    String sim_type = simulationLoaded.getType();

    System.out.println(sim_type);
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        if(sim_type.equals("Fire")){
          grid[i][j] = new FireCell(i, j, cellSize, init_state.get(k));
        }
        else if(sim_type.equals("GameOfLife")){
          grid[i][j] = new GameOfLifeCell(i, j, cellSize, init_state.get(k));
        }
        else if(sim_type.equals("Percolation")){
          grid[i][j] = new PercolationCell(i, j, cellSize, init_state.get(k));
        }
        else if(sim_type.equals("Segregation")){
          grid[i][j] = new SegregationCell(i, j, cellSize, init_state.get(k));
        }
        else if(sim_type.equals("PredatorPrey")){
          grid[i][j] = new PredatorPreyCell(i, j, cellSize, init_state.get(k));
        }
        k++;
      }
    }
  }


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
        grid[i][j].update(oldGrid, this);

      }
    }
  }

  public void copyGrid(Grid gridnew) {
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        grid[i][j].setCellState(gridnew.getCell(i, j).getCurrentState());
        grid[i][j].updateRectangle();
        grid[i][j].justSwitched = false;
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