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
    }
    
    
    
    
    /**
     * The constructor. Sets up the game
     * @param number 
     */
    public Hanoi(int number){
        theTowers = new int[3][number];
        for (int x=1;x<3;x++){
            for(int y=0;y<number;y++){
                setNum(x,y,0);
            }
        }
        for(int y=0;y<number;y++){
            setNum(0,y,number-y);
        }
    }
    /**
     * Getters
     */
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
     * Setters
     */
    /**
     * 
     * @param x
     * @param y
     * @param num 
     */
    public void setNum(int x, int y, int num){
        theTowers[x][y]=num;
    }
    
}
