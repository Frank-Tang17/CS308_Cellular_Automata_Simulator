package cellsociety;

import cellsociety.cells.*;

import java.util.ArrayList;

/**
 * The Grid object that holds all the cells
 */
public class Grid {

  private Cell[][] grid;
  private int height;
  private int width;
  private int[] nColIndex;
  private int[] nRowIndex;
  private static final int simulationScreenWidth = 450;
  private static final int simulationScreenHeight = 450;
  private Configuration myConfig;
  private int numberofCellState0 = 0;
  private int numberofCellState1 = 0;
  private int numberofCellState2 = 0;


  /**
   * Constructor for a new Grid
   * @param simulationLoaded the simulation loaded object the grid is being created in
   */
  public Grid(Configuration simulationLoaded) {
    myConfig = simulationLoaded;
    width = myConfig.getWidth();
    height = myConfig.getHeight();
    nColIndex = myConfig.getnColIndex();
    nRowIndex = myConfig.getnRowIndex();
    grid = new Cell[height][width];

    fillInitState(myConfig.getInitState());
  }

  private void fillInitState(ArrayList<Integer> init_state) {
    int k = 0;
    String sim_type = myConfig.getType();
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        switch (sim_type) {
          case "Fire": {
            grid[i][j] = new FireCell(i, j, init_state.get(k), this.nRowIndex, this.nColIndex);
            FireCell temp = (FireCell) grid[i][j];
            temp.setProb(myConfig.getProb());
            break;
          }
          case "GameOfLife":
            grid[i][j] = new GameOfLifeCell(i, j, init_state.get(k), this.nRowIndex,
                this.nColIndex);
            break;
          case "Percolation":
            grid[i][j] = new PercolationCell(i, j, init_state.get(k), this.nRowIndex,
                this.nColIndex);
            break;
          case "Segregation": {
            grid[i][j] = new SegregationCell(i, j, init_state.get(k), this.nRowIndex,
                this.nColIndex);
            SegregationCell temp = (SegregationCell) grid[i][j];
            temp.setThreshold(myConfig.getThreshold());
            break;
          }
          case "PredatorPrey": {
            grid[i][j] = new PredatorPreyCell(i, j, init_state.get(k), this.nRowIndex,
                this.nColIndex);
            PredatorPreyCell temp = (PredatorPreyCell) grid[i][j];
            createPredatorPreyCell(temp);
            break;
          }
          case "Rps": {
            grid[i][j] = new RPSCell(i, j, init_state.get(k), this.nRowIndex, this.nColIndex);
            RPSCell temp = (RPSCell) grid[i][j];
            temp.setThreshold(myConfig.getThreshold());
            break;
          }
        }
        grid[i][j].setToroidal(myConfig.isToroidal());
        grid[i][j].setHexagon(myConfig.isHexagonal());
        k++;
      }
    }
  }

  private void createPredatorPreyCell(PredatorPreyCell temp) {
    temp.setEnergyPerFish(myConfig.getEnergyInFish());
    temp.setStartingSharkEnergy(myConfig.getStartingEnergy());
    temp.setNewFishTime(myConfig.getFramesForFish());
    temp.setNewSharkTime(myConfig.getFramesForShark());
  }

  /**
   * Getter method for height (number of rows)
   * @return the height (number of rows)
   */
  public int getHeight() {
    return height;
  }

  /**
   * Getter method for width (number of cols)
   * @return the width (number of cols)
   */
  public int getWidth() {
    return width;
  }

  /**
   * Update the newGrid based on the oldGrid
   * @param oldGrid the grid to update based on
   */
  public void updateGrid(Grid oldGrid) {
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        grid[i][j].update(oldGrid, this);
      }
    }
  }

  /**
   * Mkae a copy of the grid passed in
   * @param gridnew the grid to cope based on
   */
  public void copyGrid(Grid gridnew) {
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        grid[i][j].setCellState(gridnew.getCell(i, j).getCurrentState());
        grid[i][j].justSwitched = false;
      }
    }
  }

  /**
   * Getter method for a cell
   * @param row the row for the cell
   * @param col the col for the cell
   * @return the cell
   */
  public Cell getCell(int row, int col) {
    return grid[row][col];
  }

  /**
   * Checks to see if the row and col exist in the grid
   * @param row the row
   * @param col the col
   * @return wether it is valid or not
   */
  public boolean isValidIndex(int row, int col) {
    return (row >= 0 && row < grid.length && col >= 0 && col < grid[0].length);
  }

  /**
   * Update the total number of each states
   */
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

  /**
   * Getter method for the number of cells with state 0
   * @return number of cells with state 0
   */
  public int getNumberofCellState0() {
    return numberofCellState0;
  }

  /**
   * Getter method for the number of cells with state 1
   * @return number of cells with state 1
   */
  public int getNumberofCellState1() {
    return numberofCellState1;
  }

  /**
   * Getter method for the number of cells with state 2
   * @return number of cells with state 2
   */
  public int getNumberofCellState2() {
    return numberofCellState2;
  }

   public double getSimulationScreenWidth(){
    return simulationScreenWidth;
   }
  public double getSimulationScreenHeight(){
    return simulationScreenHeight;
  }


}