package gameOfLife;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * A Dialog frame. It becomes visible after a simulation is run.Shows how the distribution of the
 * living and dead cells changed overtime
 */
public class DiagramDialog extends JDialog {

    /**
     *
     * @param parent parentframe
     * @param title title of the frame
     * @param modal specifies whether dialog blocks user input to other top-level windows when shown. If true, the modality type property is set to DEFAULT_MODALITY_TYPE, otherwise the dialog is modeless
     * @param diagPanel the JPanel where the diagram itself is drawn
     */
    public DiagramDialog(JFrame parent,String title,boolean modal,DiagramPanel diagPanel){
        super(parent,title,modal);
        setLayout(new BoxLayout(getContentPane(),BoxLayout.Y_AXIS));

        add(diagPanel);
        JButton okButton = new JButton("OK");
        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        okButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(okButton);

        pack();

    }
}
