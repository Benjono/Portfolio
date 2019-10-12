/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hanoi;
import java.util.Scanner;
/**
 *
 * @author Ben
 */
public class Hanoi {
    private int[][] theTowers;
    private int size;
    private int towerOneHeight;
    private int towerTwoHeight;
    private int towerThreeHeight;
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        System.out.println("How large do you want the game to be? ");
        boolean num = false;
        int number=0;
        while(!num){
            Scanner myScan = new Scanner(System.in);
            if (myScan.hasNextInt()){
                num=true;
                number=myScan.nextInt();
            } else {
                System.out.println("Please input a number: ");
            }
        }
        Hanoi hanoi = new Hanoi(number);
        hanoi.runGame();
    }
    
    public void runGame(){
        
    }
    /**
     * Check if numbers are in order on the rightmost 'post'.
     * @return boolean
     */
    public boolean checkWin(){
        boolean win = true;
        int y = 0;
        while (y<size && win){
            if (this.getNum(2, y)!=size-y){
                win=false;
            }
            y++;
        }
        return win;
    }
    /**
     * 
     * @param sourceTower
     * @param sourceHeight
     * @param destination
     * @param destinationHeight
     * @return 
     */
    public boolean moveAble(int sourceTower, int sourceHeight, int destination, int destinationHeight){
        if(destinationHeight==0){
            return true;
        } else if(destinationHeight==size-1){
            return false;
        }
        return this.getNum(sourceTower, sourceHeight)==this.getNum(destination, destinationHeight);
    }
    /**
     * The constructor. Sets up the game
     * @param number 
     */
    public Hanoi(int number){
        this.setSize(number);
        theTowers = new int[3][this.getSize()];
        for (int x=1;x<3;x++){
            for(int y=0;y<this.getSize();y++){
                setNum(x,y,0);
            }
        }
        for(int y=0;y<this.getSize();y++){
            setNum(0,y,this.getSize()-y);
        }
        this.setTowerOneHeight(getSize());
        this.setTowerTwoHeight(getSize());
        this.setTowerThreeHeight(getSize());
    }
    /*******************
     * Getters
     *******************/
    /**
     * 
     * @param x
     * @param y
     * @return int
     */
    public int getNum(int x, int y){
        return theTowers[x][y];
    }
    /**
     * 
     * @return integer array
     */
    public int[][] getTowers(){
        return theTowers;
    }
    /**
     * 
     * @return 
     */
    public int getSize(){
        return size;
    }
    /**
     * 
     * @return 
     */
    public int getTowerOneHeight() {
        return towerOneHeight;
    }
    /**
     * 
     * @return 
     */
    public int getTowerTwoHeight() {
        return towerTwoHeight;
    }
    /**
     * 
     * @return 
     */
    public int getTowerThreeHeight() {
        return towerThreeHeight;
    }
    
    /******************
     * Setters
     ******************/
    /**
     * Sets a number for a tower at a given height.
     * @param x
     * @param y
     * @param num 
     */
    public void setNum(int x, int y, int num){
        theTowers[x][y]=num;
    }
    /**
     * Sets the size of the game
     * @param number 
     */
    public void setSize(int number){
        size=number;
    }
    /**
     * Sets tower height for tower 1
     * @param towerOneHeight 
     */
    public void setTowerOneHeight(int towerOneHeight) {
        this.towerOneHeight = towerOneHeight;
    }
    /**
     * Sets tower height for tower 2
     * @param towerTwoHeight 
     */
    public void setTowerTwoHeight(int towerTwoHeight) {
        this.towerTwoHeight = towerTwoHeight;
    }
    /**
     * Sets tower height for tower 3
     * @param towerThreeHeight 
     */
    public void setTowerThreeHeight(int towerThreeHeight) {
        this.towerThreeHeight = towerThreeHeight;
    }
    
}
