package gameOfLife;

import java.io.Serializable;

/**
 * Implements a single cell, a unit in the simulation
 */
public class Cell implements Serializable {
    private int x;
    private int y;
    private boolean alive;
    private boolean nextState;

    /**
     *
     * @param x x location on the board
     * @param y y location on the board
     * @param alive state of being dead or alive
     * @param nextState next state
     */
    public Cell(int x, int y, boolean alive, boolean nextState) {
        this.x = x;
        this.y = y;
        this.alive = alive;
        this.nextState = nextState;
    }

    /**
     * sets state according to b
     * @param b true if the state is living. false if the state is dead.
     */
    public void setAlive(boolean b){
        alive = b;
    }

    /**
     * returns the state of the cell
     * @return its state
     */
    public boolean getAlive(){return alive;}

    /**
     * sets next state according to b
     * @param b true if the cell's next state is living.false if the cell's next state is dead
     */
    public void setNextState(boolean b){
        nextState = b;
    }

    /**
     * updates its state to the next state
     */
    public void changeState(){
        alive = nextState;
    }

}