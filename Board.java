package gameOfLife;

import java.io.Serializable;
import java.util.Random;

/**Implements a Board made of cells**/

public class Board implements Serializable
{
    int width;
    int height;
    Cell grid[][];

    /**
     * @param width the width of the board
     * @param height the height of the board
     */

    public Board(int width, int height) {
        this.width = width;
        this.height = height;
        this.grid = new Cell[width][height];
        this.randomFill(0);
    }




    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    /**
     * Fills the table with cells
     * @param pAlive  the % chance of a cell starting as a living cell
     */

    public void randomFill(int pAlive)
    {
        Random rnd = new Random();
        for(int i = 0; i < this.width; i++)
        {
            for(int j = 0; j < this.height; j++)
            {

                int n = rnd.nextInt(100);
                boolean state = (n < pAlive);
                grid[i][j] = new Cell(i,j,state,false);
            }
        }
    }

    /**
     * counts the living cells on the board
     * @return the sum
     */
    public int getLiveCount(){
        int count = 0;
        for(int i = 0; i < width ; i++){
            for(int j = 0; j < height; j++){
                if(grid[i][j].getAlive())
                    count++;
            }
        }
        return count;
    }

    /**
     * returns the cell of the board which is the i. column and j. row
     * @param i column number
     * @param j row number
     * @return  the cell at (i,j) on the board.
     */
    public Cell getCellAt(int i , int j){return grid[i][j]; }

    /**
     * sets a given given cell of the board as living or dead according to the b parameter
     * @param i x coordinate of the cell
     * @param j y coordinate of the cell
     * @param b state
     */
    public void setCellAt(int i, int j, boolean b){grid[i][j].setAlive(b);}


    /**
     * determines how the board will look like in the next generations. Implements the rules of Game Of Life
     */

    public void calcNextState()
    {
        for(int i = 0 ; i < this.width ; i++)
        {
            for(int j = 0; j < this.height ; j++) {

                int neighbours = 0;

                for (int x = -1; x <= 1; x++) {
                    for (int y = -1; y <= 1; y++) {

                        if (!(y == 0 && x == 0)) {

                            if (x + i > 0 && x + i < this.width && y + j > 0 && y + j < this.height && grid[i + x][j + y].getAlive()) {
                                neighbours++;
                            }
                        }
                    }
                }

                if (grid[i][j].getAlive() == true && (neighbours == 2 || neighbours == 3))
                    grid[i][j].setNextState(true);
                else if (grid[i][j].getAlive() == false && neighbours == 3)
                    grid[i][j].setNextState(true);
                else {
                    grid[i][j].setNextState(false);
                }
            }
        }
    }

    /**
     * returns the 2D array of the board.
     * @return the grid
     */
    public Cell[][] getGrid(){
        return grid;
    }

    /**
     * updates the cells' states to their next state
     */
    public void updateState() {
        for(int i = 0; i < this.width ; i++)
        {
            for(int j = 0; j < this.height; j++)
            {
                grid[i][j].changeState();
            }
        }
    }
}
