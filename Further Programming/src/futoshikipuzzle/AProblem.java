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
public class AProblem {
    private int x;
    private int y;
    private int problem;
    private int number;
    private int secondCoord;
    /**
     * Constructor for AProblem
     * @param x
     * @param y
     * @param problem 
     */
    public AProblem(int x, int y, int problem) {
        this.x = x;
        this.y = y;
        this.problem = problem;
    }
    /**
     * Constructor for AProblem
     * @param x
     * @param y
     * @param problem 
     * @param number
     * @param secondCoord
     */
    public AProblem(int x, int y, int problem, int number, int secondCoord) {
        this.x = x;
        this.y = y;
        this.problem = problem;
        this.number = number;
        this.secondCoord = secondCoord;
    }
    
    /*********************************************************
     *     
     * Setters
     * 
    ********************************************************/
    /**
     * Setter for the variable x
     * @param x 
     */
    public void setX(int x) {
        this.x = x;
    }
    /**
     * Setter for the variable y
     * @param y 
     */
    public void setY(int y) {
        this.y = y;
    }
    /**
     * Setter for the variable problem
     * @param problem 
     */
    public void setProblem(int problem) {
        this.problem = problem;
    }
    /********************************************************
     * 
     * Getters
     * 
     *******************************************************/
    /**
     * Getter for the variable x
     * @return int
     */
    public int getX() {
        return x;
    }
    /**
     * Getter for the variable y
     * @return int 
     */
    public int getY() {
        return y;
    }
    /**
     * Getter for the variable problem
     * @return int
     */
    public int getProblem() {
        return problem;
    }

    public int getNumber() {
        return number;
    }

    public int getSecondCoord() {
        return secondCoord;
    }
}
