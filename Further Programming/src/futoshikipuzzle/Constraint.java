/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package futoshikipuzzle;

/**
 *
 * @author admin
 */
public abstract class Constraint {
    private int row;
    private int column;
    private String symbol;
    private FutoshikiPuzzle fp;
    /**
     * The abstract method that needs to be implemented by subclasses
     * @return boolean 
     */
    public abstract boolean isSatisfied();
    /*********************************************************
     * 
     * Getters
     * 
     *********************************************************/
    /**
     * Getter for row variable
     * @return int
     */
    public int getRow() {
        return row;
    }
    /**
     * Getter for the column variable
     * @return int 
     */
    public int getColumn() {
        return column;
    }
    /**
     * Getter for the symbol variable
     * @return String
     */
    public String getSymbol() {
        return symbol;
    }
    /**
     * Getter for the reference to the FutoshikiPuzzle
     * @return FutoshikiPuzzle 
     */
    public FutoshikiPuzzle getFp() {
        return fp;
    }
    /********************************************************
     * 
     * Setters
     * 
     ********************************************************/
    /**
     * Setter for the row variable
     * @param row 
     */
    public void setRow(int row) {
        this.row = row;
    }
    /**
     * Setter for the column variable
     * @param column 
     */
    public void setColumn(int column) {
        this.column = column;
    }
    /**
     * Setter for the symbol variable
     * @param symbol 
     */
    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }
    /**
     * Setter for the fp variable
     * @param fp 
     */
    public void setFp(FutoshikiPuzzle fp) {
        this.fp = fp;
    }
}
