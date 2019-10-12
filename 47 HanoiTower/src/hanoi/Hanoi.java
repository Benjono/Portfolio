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
    private int[] towerHeights;
    
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
    /**
     * The constructor. Sets up the game
     * @param number 
     */
    public Hanoi(int number){
        this.setSize(number);
        this.towerHeights=new int[3];
        this.theTowers = new int[3][this.getSize()];
        for (int x=1;x<3;x++){
            for(int y=0;y<this.getSize();y++){
                setNum(x,y,0);
            }
        }
        for(int y=0;y<this.getSize();y++){
            setNum(0,y,y+1);
        }
        
        this.setTowerHeight(0, getSize());
        this.setTowerHeight(1, 0);
        this.setTowerHeight(2, 0);
    }
    public void runGame(){
        System.out.println("H for help, M x to y to move the top item from tower x to tower y, x and y are integers");
        System.out.println("Q to quit");
        System.out.println("...");
        printGame();
        Scanner myScan = new Scanner(System.in);
        String input = myScan.nextLine();
        while("q".equals(input.toLowerCase())){
            if("h".equals(input.toLowerCase())){
                System.out.println("H for help, M x to y to move the top item from tower x to tower y, x and y are integers");
                System.out.println("Q to quit");
                System.out.println("...");
            } else if (input.toLowerCase().matches("m [1-3] to [1-3]")){
                System.out.println("E"); //Temporary
            } else{
                System.out.println("There seems to be an invalid input, please try again.");
            }
            input = myScan.nextLine();
        }
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
    public void printGame(){
        for(int y=0;y<size;y++){
            for(int x=0;x<3;x++){
                System.out.print(this.getNum(x, y));
            }
            System.out.println("");
        }
    }
    /**
     * Checks if a piece on one of the towers can be moved.
     * @param sourceTower
     * @param sourceHeight
     * @param destination
     * @param destinationHeight
     * @return boolean
     */
    public boolean moveAble(int sourceTower, int sourceHeight, int destination, int destinationHeight){
        if(destinationHeight==0){
            return true;
        } else if(destinationHeight==size-1){
            return false;
        }
        return this.getNum(sourceTower, sourceHeight)<=this.getNum(destination, destinationHeight-1);
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
     * getter for the tower array
     * @return integer array
     */
    public int[][] getTowers(){
        return theTowers;
    }
    /**
     * getter for size
     * @return int
     */
    public int getSize(){
        return size;
    }
    /**
     * getter for the height of a tower
     * @param tower
     * @return 
     */
    public int getTowerHeight(int tower){
        return this.towerHeights[tower];
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
     * Sets a towers current height
     * @param tower
     * @param number 
     */
    public void setTowerHeight(int tower, int number){
        towerHeights[tower]=number;
    }
    
}
