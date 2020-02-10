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
  private int[] nColIndex;
  private int[] nRowIndex;
  private final int simulationScreenWidth = 450;
  private final int simulationScreenHeight = 450;
  private Configuration test;
  private int numberofCellState0 = 0;
  private int numberofCellState1 = 0;
  private int numberofCellState2 = 0;

  public Grid(Configuration simulationLoaded) {
    test = simulationLoaded;
    width = test.getWidth();
    height = test.getHeight();
    nColIndex = test.getnColIndex();
    nRowIndex = test.getnRowIndex();
    grid = new Cell[height][width];

    fillInitState(test.getInitState());
  }

  //Populate the grid values with the initial state//

  public void fillInitState(ArrayList<Integer> init_state) {
    //Need to figure out how the state data is incoming, whether we can store it in an ArrayList.
    double cellSize = determineCellSize(height, width);
    int k = 0;
    String sim_type = test.getType();

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        if (sim_type.equals("Fire")) {
          grid[i][j] = new FireCell(i, j, cellSize, init_state.get(k), this.nRowIndex, this.nColIndex);
        } else if (sim_type.equals("GameOfLife")) {
          grid[i][j] = new GameOfLifeCell(i, j, cellSize, init_state.get(k), this.nRowIndex, this.nColIndex);
        } else if (sim_type.equals("Percolation")) {
          grid[i][j] = new PercolationCell(i, j, cellSize, init_state.get(k), this.nRowIndex, this.nColIndex);
        } else if (sim_type.equals("Segregation")) {
          grid[i][j] = new SegregationCell(i, j, cellSize, init_state.get(k), this.nRowIndex, this.nColIndex);
        } else if (sim_type.equals("PredatorPrey")) {
          grid[i][j] = new PredatorPreyCell(i, j, cellSize, init_state.get(k), this.nRowIndex, this.nColIndex);
        }
        k++;
      }
    }
  }

//  public void gridVisualization(Group node){
//    for (int i = 0; i < height; i++) {
//      for (int j = 0; j < width; j++) {
//        node.getChildren().add(grid[i][j].getCellNode());
//      }
//    }
//  }

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

  public void updateStateTotal() {
    numberofCellState0 = 0;
    numberofCellState1 = 0;
    numberofCellState2 = 0;
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        if (this.getCell(i, j).getCurrentState() == 0) {
          numberofCellState0++;
        } else if (this.getCell(i, j).getCurrentState() == 1) {
          numberofCellState1++;
        } else {
          numberofCellState2++;
        }
      }
    }
  }

  public int getNumberofCellState0() {
    return numberofCellState0;
  }

  public int getNumberofCellState1() {
    return numberofCellState1;
  }

  public int getNumberofCellState2() {
    return numberofCellState2;
  }

  public double determineCellSize(int numRows, int numCols) {
    double maxWidth = simulationScreenWidth / numCols;
    double maxHeight = simulationScreenHeight / numRows;

    return Math.min(maxWidth, maxHeight);
  }

}