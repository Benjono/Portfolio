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
public class columnConstraint extends Constraint{
    /**
     * Constructor. Reference to the FutoshikiPuzzle, the row and column coordinates of where it is in columnConstraintGrid and the starting symbol 
     * @param fp
     * @param initX
     * @param initY
     * @param initSymbol 
     */
    public columnConstraint(FutoshikiPuzzle fp, int initX, int initY, String initSymbol){
        this.setRow(initX);
        this.setColumn(initY);
        this.setSymbol(initSymbol);
        this.setFp(fp);
    }
    /**
     * A function that returns if this constraint is satisfied. True if it is, false if it isn't.
     * The constraint must have '^' or 'v' in it, otherwise it'll return true regardless.
     * @return boolean
     */
    @Override
    public boolean isSatisfied(){
        int number1 = getFp().getGridItem(this.getRow(), this.getColumn());
        int number2 = getFp().getGridItem(this.getRow(), this.getColumn()+1);
        if(number1 !=0 && number2 !=0){    
            if(getSymbol().equals("^")){
                return number2>number1;
            }
            else if(getSymbol().equals("v")){
                return number2<number1;
            }
        }
        return true;
    }
    /******************************************************
     * 
     * Setter
     * 
     *****************************************************/
    /**
     * Function to set the symbol of the column constraint 
     * @param symbol 
     */
    @Override
    public void setSymbol(String symbol){
        if(symbol.equals("^") || symbol.equals("v") || symbol.equals(" ")){ //if the input symbol is correct
            super.setSymbol(symbol); //set the symbol
        }
        else{
            super.setSymbol(" "); //override with a blank space
        }
    }
}
