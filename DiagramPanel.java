package gameOfLife;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 * The Jpanel where the diagram is drawn using primitive 2d graphics
 */
public class DiagramPanel extends JPanel {
    private int genCount;
    LinkedList<Integer> liveList;
    LinkedList<Integer> deadList;
    Color liveColor;
    Color deadColor;


    /**
     *
     * @param liveList a list of integers. the i. element of the list tells the number of living cells in the i. generation
     * @param deadList a list of integers. the i. element of the list tells the number of dead cells in the i. generation
     * @param liveColor color of living cells
     * @param deadColor color of dead cells
     */
    public DiagramPanel(LinkedList<Integer> liveList, LinkedList<Integer> deadList, Color liveColor, Color deadColor) {
        genCount = liveList.size();
        this.liveList = liveList;
        this.deadList = deadList;

        this.liveColor = liveColor;
        this.deadColor = deadColor;

        setPreferredSize(new Dimension(600, 600));
        setMinimumSize(new Dimension(600,600));
        setMinimumSize(new Dimension(600,600));

    }

    /**
     *
     * @param g the Graphics object on which the diagram is drawn
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.PINK);

        int horizontalShift = 50;
        int verticalShift = 550;
        int cellCount = liveList.get(0) + deadList.get(0);
        double unitOnX = 500.0 / (genCount + 1);
        double unitOnY = 500.0 / (cellCount + 1);

        g.setColor(new Color(200,191,231));
        g.setColor(Color.BLACK);
        g.fillRect(0,0,getWidth(),getHeight());
        for (int i = 0; i < genCount; i++) {
            g.setColor(liveColor);
            g.fillOval(horizontalShift + (int)Math.round(i * unitOnX), (int) Math.round(verticalShift - (liveList.get(i) * unitOnY)), 5, 5);
            g.setColor(deadColor);
            g.fillOval(horizontalShift + (int)Math.round(i * unitOnX), (int) Math.round(verticalShift - (deadList.get(i) * unitOnY)), 5, 5);
        }

        g.setColor(Color.WHITE);
        g.drawLine(50,550,550,550);
        g.drawLine(50,50,50,550);
        g.drawString("Time",530,585);
        g.drawLine(550,550,540,545);
        g.drawLine(550,550,540,555);
        g.drawString("Cells",10,80);
        g.drawLine(50,50,45,60);
        g.drawLine(50,50,55,60);
        g.setColor(new Color(255,201,15));
        g.drawString(Integer.toString(genCount),540,570);
        g.drawString(Integer.toString(cellCount),0,55);


    }
}