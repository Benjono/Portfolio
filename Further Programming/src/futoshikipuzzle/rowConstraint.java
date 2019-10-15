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
public class rowConstraint extends Constraint {
    /**
     * Constructor. Reference to the FutoshikiPuzzle, the row and column coordinates of where it is in columnConstraintGrid and the starting symbol 
     * @param fp
     * @param row
     * @param column
     * @param initSymbol 
     */
    public rowConstraint(FutoshikiPuzzle fp, int row, int column, String initSymbol){
        this.setRow(row);
        this.setColumn(column);
        this.setSymbol(initSymbol);
        this.setFp(fp);
    }
    /**
     * A function that returns if this constraint is satisfied. True if it is, false if it isn't.
     * The constraint must have '>' or '<' in it, otherwise it'll return true regardless.
     * @return boolean
     */
    @Override
    public boolean isSatisfied(){
        int number1 = getFp().getGridItem(this.getRow(), this.getColumn());
        int number2 = getFp().getGridItem(this.getRow()+1, this.getColumn());
        if(number1 !=0 && number2 !=0){
            if(getSymbol().equals("<")){
                return number1<number2;
            }
            else if(getSymbol().equals(">")){
                return number1>number2;
            }
        }
        return true;
    }
    /********************************************************
     * 
     * Setter
     * 
     ******************************************************/
    /**
     * Function to set the symbol of the column constraint 
     * @param symbol 
     */
    @Override
    public void setSymbol(String symbol){
        if(symbol.equals( "<") || symbol.equals(">") || symbol.equals(" ")){ //if input correct
            super.setSymbol(symbol); //set symbol
        }
        else{
            super.setSymbol(" "); //otherwise override with " "
        }
    }
}
