package futoshikipuzzle;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.util.*;
/**
 *
 * @author admin
 */
public class FutoshikiSquare {
    private boolean editable;
    private int curNumber;
    /**
     * Constructor for the FutoshikiSquare class
     * @param canEdit
     * @param initNumber 
     */
    public FutoshikiSquare(boolean canEdit, int initNumber){
        this.setEditable(canEdit);
        this.setCurNum(initNumber);
    }
    /**
     * Sets the square back to being blank if it is editable
     */
    public void empty(){
        if(editable){
            this.setCurNum(0);
        }
    }
    /*********************************************************
     * 
     * Getters
     * 
     ********************************************************/
    /**
     * Getter for the editable variable
     * @return boolean
     */
    public boolean isEditable() {
        return editable;
    }
    /**
     * Getter for the curNumber variable, which hold the current number
     * @return curNumber 
     */
    public int getCurNumber() {
        return curNumber;
    }
    /*******************************************************************
     * 
     * Setters
     * 
     ******************************************************************/  
    /**
     * Setter for the editable variable.
     * @param editable 
     */
    private void setEditable(boolean editable) {
        this.editable = editable;
    }
    /**
     * Setter for the curNum variable
     * @param currentNumber 
     */
    public void setCurNum(int currentNumber) {
        this.curNumber = currentNumber;
    }
}
