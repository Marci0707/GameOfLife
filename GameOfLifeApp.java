package gameOfLife;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;


import static java.lang.Thread.sleep;

/**
 * An application simulating the Game Of Life from John Horton Conway.
 * The users can enter their own parameters like the width, height of the space
 * where the simulation is played.They can also save and load previous simulations, set colors
 * and the speed of the simulation.
 * @author Schneider Marcell
 */

public class GameOfLifeApp extends JFrame {
    JPanel topRow;
    private JButton controllerButton;
    private PauseButton pauseButton;
    private JButton generateButton;
    private JSlider pSlider;

    private Color livingCellsColor = new Color(153,217,234);
    private Color deadCellsColor = new Color(112,146,190);
    private JTextField widthTextField;
    private JTextField heightTextField;
    private JTextArea infoArea;
    private JLabel heightLabel;
    private JLabel widthLabel;
    private JLabel pLabel;
    private boolean onlyChangeSliderValue = false;

    private int p = 50;
    private int boardWidth = 300;
    private int boardHeight = 200;
    private JLabel genCountLabel;

    private boolean paused; //the simulation is paused
    private boolean running; //the simulation is running
    private SimulationPanel simulationPanel;

    private int speed = 70; //speed of the simulation
    private boolean getGraph = true;  //wether the user wants a graph at the end of the simulation

    private Thread t;
    private Simulation simulation;


    public GameOfLifeApp() {
        initComponents();
        this.setTitle("Game Of Life");
        this.setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    /**
     * Initializes the components of the GUI.
     */
    private void initComponents() {

        simulation = new Simulation();
        simulationPanel = new SimulationPanel(simulation,livingCellsColor,deadCellsColor);
        simulationPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

/**MENUBAR SETUP*********************************************/
        JMenuBar menuBar = new JMenuBar();
        /**FILE MENU**/
        JMenu fileMenu = new JMenu("File");
        JMenu loadSubMenu = new JMenu("Load");

        JMenuItem loadSavedFile = new JMenuItem("Saved");
        loadSavedFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fileHandling("load",false);
            }
        });

        JMenuItem  loadCustomFile = new JMenuItem("Custom");
        loadCustomFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) { fileHandling("load", true); }
        });


        loadSubMenu.add(loadCustomFile);
        loadSubMenu.add(loadSavedFile);


        JMenuItem saveOption = new JMenuItem("Save");
        saveOption.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fileHandling("save", false);
            }
        });
        JMenuItem exitOption = new JMenuItem("Exit");
        exitOption.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        fileMenu.add(loadSubMenu);
        fileMenu.add(saveOption);
        fileMenu.add(exitOption);



        /**VIEW MENU**/
        JMenu viewMenu = new JMenu("View");
        JMenuItem color1Option = new JMenuItem("Live Color");
        color1Option.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                colorChoiceOption("living");
            }
        });
        JMenuItem color2Option = new JMenuItem("Dead Color");
        color2Option.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                colorChoiceOption("dead");
            }
        });
        JCheckBoxMenuItem gridOption = new JCheckBoxMenuItem("show grid");
        gridOption.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                simulationPanel.showGrid(gridOption.getState());
                repaint();
            }
        });
        JCheckBoxMenuItem graphOption = new JCheckBoxMenuItem("get graph");
        graphOption.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                getGraph = !getGraph;
                System.out.println(getGraph);
            }
        });
        graphOption.setSelected(true);

        viewMenu.add(color1Option);
        viewMenu.add(color2Option);
        viewMenu.add(gridOption);
        viewMenu.add(graphOption);

        /**SETTINGS MENU**/
        JMenu settingsMenu = new JMenu("Settings");
        ButtonGroup speedGroup = new ButtonGroup();
        JRadioButton speedOptionFast = new JRadioButton("fast");
        JRadioButton speedOptionNormal = new JRadioButton("normal");
        JRadioButton speedOptionSlow = new JRadioButton("slow");
        speedGroup.add(speedOptionFast);
        speedGroup.add(speedOptionNormal);
        speedGroup.add(speedOptionSlow);
        speedOptionFast.setActionCommand("fast");
        speedOptionNormal.setActionCommand("normal");
        speedOptionSlow.setActionCommand("slow");
        ActionListener al = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setSpeed(e.getActionCommand());
            }
        };
        speedOptionFast.addActionListener(al);
        speedOptionNormal.addActionListener(al);
        speedOptionSlow.addActionListener(al);
        settingsMenu.add(speedOptionFast);
        settingsMenu.add(speedOptionNormal);
        settingsMenu.add(speedOptionSlow);
        speedOptionNormal.setSelected(true);



        /**SET MENUBAR**/
        menuBar.add(fileMenu);
        menuBar.add(viewMenu);
        menuBar.add(settingsMenu);
        setJMenuBar(menuBar);


/***TOP BUTTONS AND INPUT*************************************/

        controllerButton = new JButton("Start");
        controllerButton.setName("start");
        controllerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controllerButtonPressed();
            }
        });
        pauseButton = new PauseButton(PauseButton.States.PAUSEICONSTATE);
        pauseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pauseButtonPressed();
            }
        });

        generateButton = new JButton("Generate new");
        generateButton.setName("generate");
        generateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                generateButtonPressed();
            }
        });

        heightLabel = new JLabel("Height:");
        heightTextField = new JTextField(4);
        heightTextField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    dimensionEntered(true, heightTextField);
                }
            }
        });

        widthLabel = new JLabel("Width");
        widthTextField = new JTextField(4);
        widthTextField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    dimensionEntered(false, widthTextField);
                }
            }
        });


        pLabel = new JLabel("p:");
        pSlider = new JSlider(JSlider.HORIZONTAL,0,100,50);
        pSlider.setMajorTickSpacing(50);
        pSlider.setPaintTicks(true);
        pSlider.setPaintLabels(true);
        pSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent sc) {
                pChanged();
            }
        });


        infoArea = new JTextArea(3, 7);
        infoArea.setEditable(false);

        topRow = new JPanel(new FlowLayout(FlowLayout.LEFT));

        topRow.add(controllerButton);
        topRow.add(pauseButton);
        topRow.add(generateButton);
        topRow.add(heightLabel);
        topRow.add(heightTextField);
        topRow.add(widthLabel);
        topRow.add(widthTextField);
        topRow.add(pLabel);
        topRow.add(pSlider);
        topRow.add(infoArea);
        topRow.setBackground(new Color(215,240,247));
/**BOTTOM PANEL**/
        genCountLabel = new JLabel("0. Generation");
        genCountLabel.setHorizontalAlignment(SwingConstants.CENTER);
/**JFRAME SETUP*******************/

        this.setLayout(new BorderLayout());
        this.add(topRow, BorderLayout.NORTH);
        this.add(simulationPanel, BorderLayout.CENTER);
        this.add(genCountLabel,BorderLayout.SOUTH);
        genNewSimulation();
        updateInfo();


    }
/**END OF initComponents()**********/


    /**
     * the controller button. If the user presses start the simulation begins on another thread.
     * if the user presses stop, according to the settings, a DiagramDialog appears
     */
    private void controllerButtonPressed() {

        switch (controllerButton.getName()) {
            case ("start") -> {
                simulation.setGenCount(-1);
                running = true;
                paused = false;
                controllerButton.setText("End");
                controllerButton.setName("end");
                setSetupEnabled(false);
                pauseButton.setEnabled(true);
                t = new Thread(() -> {
                    while (running) {
                        synchronized (this) {
                            if (!paused) {
                                simulation.nextStep();
                                //simulationPanel.repaint();
                                if(simulation.getGenCount()%25 == 0)
                                    genCountLabel.setText("~" + Integer.toString(simulation.getGenCount())+". Generation");
                                repaint();
                                try {
                                    sleep(speed);
                                } catch (InterruptedException ex) {
                                    System.err.println(ex.getMessage());
                                }
                            } else {
                                try {
                                    this.wait();
                                } catch (InterruptedException ex) {
                                    System.err.println(ex.getMessage());
                                }
                            }
                        }
                    }
                });
                t.start();
            }
            case ("end") -> {
                running = false;
                if(getGraph) {
                    DiagramPanel diagPanel = new DiagramPanel(simulation.getLiveList(), simulation.getDeadList(), livingCellsColor, deadCellsColor);
                    DiagramDialog diag = new DiagramDialog(this, "Live-Dead Cell Distribution", true, diagPanel);
                    diag.setVisible(true);
                }
                controllerButton.setText("Start");
                controllerButton.setName("start");
                setSetupEnabled(true);
                pauseButton.switchState(PauseButton.States.PAUSEICONSTATE);
                pauseButton.setEnabled(false);
                simulation.clear();
            }
        }
    }


    /**
     * pauses the simulation
     */
    private void pauseButtonPressed() {
        if (pauseButton.getState() == PauseButton.States.CONICONSTATE) {
            synchronized (this) {
                notify();
            }
            paused = false;
            pauseButton.switchState(PauseButton.States.PAUSEICONSTATE);
        }

        else{
            paused = true;
            pauseButton.switchState(PauseButton.States.CONICONSTATE);
        }

        pauseButton.repaint();
    }


    /**
     * generates a new board with same settings
     */
    private void generateButtonPressed(){
            simulation.setSimulation(boardWidth,boardHeight,p);
            repaint();
    }

    /**
     * updates the p parameter according to the JSlider
     */
    private void pChanged(){
        p = pSlider.getValue();
        if(onlyChangeSliderValue)
            return;
        genNewSimulation();
        updateInfo();
    }


    /**
     *
     * @param cellGroup =='living' or 'dead'.Comes up with a dialog and sets the Color of choice
     *                  to the corresponding cellGroup
     */
    private void colorChoiceOption(String cellGroup){
        Color initialColor = Color.BLUE;
        Color color = JColorChooser.showDialog(this,"Choose the color of " + cellGroup +" cells",initialColor);
        if(cellGroup.equals("living"))
            simulationPanel.setLivingColor(color);
        else
            simulationPanel.setDeadColor((color));
        simulationPanel.repaint();
    }


    /**
     * updates the dimensions of the board
     * @param height a flag, true if the height is changed, false if the width of the board is changed
     * @param textField the input textfield object
     */
    private void dimensionEntered(boolean height,JTextField textField){
        try{
            int newDim = Integer.parseInt(textField.getText());
            if(height)
                boardHeight = newDim;

            else
                boardWidth = newDim;
            genNewSimulation();
            updateInfo();
            textField.setText("");
        }
        catch(NumberFormatException ex){
            JFrame f = new JFrame();
            JOptionPane.showMessageDialog(f,"This is not a number -.-");
        }
    }

    /**
     * Gets a filename from the user through a dialogpanel
     * @return the chosen filename, ==null if the user did not choose anything
     */
    private String getFileName(){
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter(".txt file","txt");
        chooser.setFileFilter(filter);
        int returnVal = chooser.showDialog(this, "Select");
        String fname = "";
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            fname = chooser.getSelectedFile().getAbsolutePath();
        }
        return fname;
    }

    /**
     * responsible for the file loading and saving
     * @param operation 'load' or 'save' according the to operation
     * @param custom true if the user wants to load a custom input file where
     *               he/she tells the initial setup of the board. false otherwise
     */
    private void fileHandling(String operation, boolean custom ) { //op = save / load
        String filename = getFileName();
        if(filename == null){return;}

        int[] info;
        if (operation.equals("load")){
            if(custom)
                info = simulation.loadCustom(filename);
            else
                info = simulation.loadSaved(filename);
            p = info[0];
            boardWidth = info[1];
            boardHeight = info[2];
        }
        else {
            simulation.save(filename);
        }
        onlyChangeSliderValue = true; //slider.setValue(p) calls listener, need a flag in pChanged()
        pSlider.setValue(p);
        onlyChangeSliderValue = false;

        updateInfo();
        simulationPanel.repaint();

    }

    /**
     * generates a new board
     */
    private void genNewSimulation(){
        simulation.setSimulation(boardWidth,boardHeight,p);
        repaint();
    }

    /**
     * updates the information about the board
     */
    private void updateInfo(){
        infoArea.setText("p:        " + p + "%" + '\n'
                        + "width: " + boardWidth + '\n'
                        + "height:" + boardHeight);

        simulationPanel.updatePreferredSize();
        pack();
    }


    /**
     * when the simulation begins all the input options become disabled
     * @param b wether to enable or disable the input fields. true if to enable
     */
    void setSetupEnabled(boolean b){
        widthTextField.setEnabled(b);
        heightTextField.setEnabled(b);
        generateButton.setEnabled(b);
        pSlider.setEnabled(b);
        widthLabel.setEnabled(b);
        heightLabel.setEnabled(b);
        pLabel.setEnabled(b);
    }


    /**
     * changes the speed of the simulation
     * @param command 'fast'/'normal'/'slow' according to the chosen radioButton
     */
    void setSpeed(String command){
        speed = switch (command){
            case("fast")->15;
            case("normal")->70;
            case("slow")->1000;
            default -> 70;
        };
    }


    /**
     * creates the applicationm
     * @param args cmd args
     */
    public static void main (String[] args){
        GameOfLifeApp appWindow = new GameOfLifeApp();
    }


}
