//import java.lang.Math;
//
///**
//Cell class for the fire simulation
//
// */
//public class CellFire{
//
//
//    /**
//    Current state of the cell.
//    0 for empty cell with no tree or burnt tree
//    1 for a cell with an unburnt tree
//    2 for a cell with a burning tree
//     */
//    int state;
//    int prob;
//    int type;
//    int row;
//    int col;
//
//    public CellFire(int cellType, int cellState, int cellProb, int cellRow, int cellCol){
//        this.state = cellState;
//        this.prob = cellProb;
//        this.type = cellType;
//        this.row = cellRow;
//        this.col = cellCol;
//    }
//    public void updateCell(){
//        int rand = (int)(Math.random()*100);
//        int compProb = 100*prob;
//
//        if(this.state == 2){
//            this.state == 0;
//            if(isValidIndex(row+1, col) && (rand<=compProb)){
//                grid[row+1][col].setState(2);
//            }
//            if(isValidIndex(row-1, col) && (rand<=compProb)){
//                grid[row-1][col].setState(2);
//            }
//            if(isValidIndex(row, col+1) && (rand<=compProb)){
//                grid[row][col+1].setState(2);
//            }
//            if(isValidIndex(row, col-1) && (rand<=compProb)){
//                grid[row][col-1].setState(2);
//            }
//
//        }
//    }
//
//    public void setState(int new_state){
//        this.state = new_state;
//    }
//
//    public int getCellRow(){
//        return this.row;
//    }
//
//    public int getCellCol(){
//        return this.col;
//    }
//
//    /*public int[] getNeighbors(int row, int col){
//        ArrayList<Cell> neighbors = new ArrayList<Cell>();
//        int[] check = new int[]{row+1, col, row-1, col, row, col+1, row, col-1};
//
//        for(int i = 0, i<check.length; i+=2){
//            if(isValidIndex(check[i], check[i+1])){
//                neighbors.add(grid[i][i+1].getState());
//            }
//
//        }
//    }*/
//
//    public boolean isValidIndex(int row, int col){
//        return (row>=0 && row<grid.getHeight() && col>=0 && col<grid.getWidth());
//    }
//}