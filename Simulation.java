package gameOfLife;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;


import java.util.LinkedList;
import java.util.Scanner;

/**
 * Responsible for controlling the board and recording data for the diagram.
 */
public class Simulation {
    private Board board;
    private int genCount = 0;
    private LinkedList<Integer> numberOfLiveCells = new LinkedList<Integer>();
    private LinkedList<Integer> numberOfDeadCells = new LinkedList<Integer>();
    private int info[];;

    /**
     * standard setup
     */
    public Simulation(){
        setSimulation(300,200, 50);
    }

    /**
     * sets up the board
     *
     * @param boardWidth width of the board
     * @param boardHeight height of the board
     * @param p probability of a cell starting as a living cell
     */
    public void setSimulation(int boardWidth, int boardHeight, int p){
        board = new Board(boardWidth,boardHeight);
        board.randomFill(p);
        clear();
        countCellTypes();
    }


    /**
     * returns the number of generations that has passed under the current simulation
     * @return number of generations
     */
    public int getGenCount(){
        return genCount;
    }

    /**
     * sets the gencount as v. Usually used for setting the gencount to 0
     * @param v new generation count
     */
    public void setGenCount(int v){
        genCount = v;
    }

    /**
     * returns the board
     * @return the board.
     */
    public Board getBoard(){
        return board;
    }

    /**
     * counts the current number of living and dead cells. Adds it to a list to save the data for later.
     */
    private void countCellTypes(){
        int countLive = 0;
        int countDead = 0;
        for(int i = 0; i < board.getWidth() ;  i++){
            for(int j = 0; j < board.getHeight() ; j++){
                if(board.getCellAt(i,j).getAlive())
                    countLive++;
                else
                    countDead++;
            }
        }
        numberOfLiveCells.add(countLive);
        numberOfDeadCells.add(countDead);
    }


    /**
     * clears the simulation data.
     */
    public void clear(){
        numberOfDeadCells.clear();
        numberOfLiveCells.clear();
        genCount = 0;
    }

    /**
     * steps the simulation by 1 step
     */
    public synchronized void nextStep(){
        board.calcNextState();
        board.updateState();
        genCount++;
        countCellTypes();
    }

    /**
     * saves a board state to a file using the serializable interface.
     * @param filename the abstract filename of the file
     */
    public void save(String filename){
        try{
            FileOutputStream f = new FileOutputStream(filename);
            ObjectOutputStream out = new ObjectOutputStream(f);
            out.writeObject(board);
            out.close();
        }
        catch(IOException ex){
            JFrame f = new JFrame();
            JOptionPane.showMessageDialog(f,"Could not save the file");
        }
    }

    /**
     * loads a previously saved file with the serializable interface.
     * @param filename the abstract filename of the file
     * @return the information describing the state of board.It will be displayed on GUI.
     */
    public int[] loadSaved(String filename){

        try{
            FileInputStream f= new FileInputStream(filename);
            ObjectInputStream in = new ObjectInputStream(f);
            board = (Board)in.readObject();
            in.close();
            int[] info = new int[3];
            info[0] = Math.round(board.getLiveCount()/(board.getHeight()*board.getWidth())*100);
            info[1] = board.getWidth();
            info[2] = board.getHeight();
            return info;

        }
        catch(IOException | ClassNotFoundException ex){
            JFrame f = new JFrame();
            JOptionPane.showMessageDialog(f,"Could not load the file : " + ex.getMessage());
        }
        int[] info = {50,300,200};
        return info;
    }


    /**
     * Loads a custom file created by the user. It contains series of 1s asd 0s representing the rows of living the dead cells
     * @param filename the abstract filename of the file
     * @return the information describing the state of board.It will be displayed on GUI.
     */
    public int[] loadCustom(String filename){

        try {
            File file = new File(filename);
            Scanner fileScanner1 = new Scanner(file);
            int firstRowLength = fileScanner1.nextLine().length();
            int numberOfLines = 1;
            while(fileScanner1.hasNextLine()){
                String line = fileScanner1.nextLine();
                if(line == null) break;
                if(line.length() != firstRowLength)
                    throw new WrongFileFormatException("Badly Formatted File");
                numberOfLines++;
            }

            fileScanner1.close();

            Scanner fileScanner2 = new Scanner(file);
            Board newBoard = new Board(firstRowLength,numberOfLines);
            int liveCount = 0;
            int deadCount = 0;

            for(int j = 0 ;j < numberOfLines ; j++){
                String line = fileScanner2.nextLine();
                StringReader sr = new StringReader(line);
                for(int i = 0; i < firstRowLength ; i++){
                    char s = (char)sr.read();
                    boolean state = (s == '1');
                    if(state)liveCount++;
                    else deadCount++;

                    newBoard.setCellAt(i,j,state);
                }
            }
            this.board = newBoard;

            int[] info = {Math.round((float)liveCount/(liveCount+deadCount)*100),firstRowLength,numberOfLines};
            return info;

        }
        catch(Exception ex){
            JFrame f = new JFrame();
            JOptionPane.showMessageDialog(f,"Could not load:\n" + ex.getMessage());
        }

        int[] info = {50,200,300};
        return info;

    }

    /**
     * returns a list describing how the number of living cells changed over time.
     * @return the list.
     */
    public LinkedList<Integer> getLiveList(){
        return numberOfLiveCells;
    }

    /**
     * returns a list describing how the number of living cells changed over time.
     * @return the list
     */
    public LinkedList<Integer> getDeadList(){
        return numberOfDeadCells;
    }



}









