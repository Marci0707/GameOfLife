package gameOfLife;

/**
 * If the user wants to load a custom board setup and the corresponding file is badly formatted ( row length is not constant),
 * this exception is thrown.
 */
public class WrongFileFormatException extends Exception{
    public WrongFileFormatException(String s){
        super(s);
    }
}
