package gameOfLife;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;

/**
 * You can pause and continue the simulation with this button on the GUI.
 * Changes the button icon when pressed
 */

public class PauseButton extends JButton {
    private ImageIcon pauseIcon;
    private ImageIcon conIcon;
    public enum States{
        PAUSEICONSTATE,
        CONICONSTATE
    };
    private States state;

    public PauseButton(States baseState){
        super();
        try{
            Image pause = ImageIO.read(getClass().getResource("pauseButtonImg.png"));
            Image con = ImageIO.read(getClass().getResource("conButtonImg.png"));
            pauseIcon = new ImageIcon(pause);
            conIcon = new ImageIcon(con);
        }
        catch(IOException ex){
            System.err.println(ex.getMessage());
        }
        switchState(baseState);
        setEnabled(false);
    }

    /**
     * switches to the opposite state and changes the icon
     * @param newState the new state of the button
     */
    public void switchState(States newState){
        state = newState;
        setIcon( state == States.PAUSEICONSTATE ? pauseIcon : conIcon);
    }


    public States getState(){
        return state;
    }

}
