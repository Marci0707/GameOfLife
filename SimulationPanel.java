package gameOfLife;

import javax.swing.*;
import java.awt.*;

/**
 * This is the JPanel where the simulation is drawn using primitive 2d graphics
 */
public class SimulationPanel extends JPanel{
    private Color livingColor;
    private Color deadColor;
    private final int cellSize = 3 ;
    private Simulation simulation;
    private boolean showGrid = false;


    /**
     * gets a simulation which is to be drawn
     * @param simulation the simulation containing the board.
     * @param livingColor Color of living cells
     * @param deadColor Color of dead cells
     */
    SimulationPanel(Simulation simulation, Color livingColor, Color deadColor){
        this.simulation = simulation;
        int boardWidth = simulation.getBoard().getWidth();
        int boardHeight = simulation.getBoard().getHeight();
        this.livingColor = livingColor;
        this.deadColor = deadColor;

        setPreferredSize(new Dimension(cellSize*boardWidth,cellSize*boardHeight));
    }

    /**
     * sets the current color of the dead cells to a new one.
     * @param c the new color
     */
    public void setDeadColor(Color c){ deadColor = c;}

    /**
     * sets the current color of living cells to a new one.
     * @param c the new color
     */
    public void setLivingColor(Color c){livingColor = c;}


    /**
     * updates the preferred size of the JPanel. Used when the board is resized.
     */
    public void updatePreferredSize(){
        setPreferredSize(new Dimension(cellSize*simulation.getBoard().getWidth(),cellSize*simulation.getBoard().getHeight()));
    }

    /**
     * Saves if the user wants to see the grid on the board.
     * @param b true if the user wants to see the grid. false if not.
     */
    public void showGrid(boolean b){
        showGrid = b;
    }

    /**
     * paint the board
     * @param g the graphics object where the board is draw.
     */
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        //System.out.println("dsa");
        Board board = simulation.getBoard();

        int horizontalShift = (int) Math.floor((getWidth()-cellSize*board.getWidth())/2);
        for(int i = 0; i < board.getWidth() ; i++){
            for(int j = 0 ; j < board.getHeight() ; j++){
                if(board.getCellAt(i,j).getAlive())
                    g.setColor(livingColor);
                else
                    g.setColor(deadColor);

                g.fillRect(horizontalShift+i*cellSize,j*cellSize,cellSize,cellSize);
            }
        }
        if(showGrid){
            g.setColor(Color.BLACK);
            for(int i = cellSize; i < board.getWidth()*cellSize ;i+=cellSize){
                g.drawLine(horizontalShift + i,0,horizontalShift + i,(board.getHeight()-1)*cellSize);
            }
            for(int j = cellSize ;  j < board.getHeight()*cellSize ; j+=cellSize){
                g.drawLine(0,j,board.getWidth()*cellSize,j);
            }
        }
    }
}
