/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import hanoi.Hanoi;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Ben
 */
public class HanoiTest {
    
    public HanoiTest() {
    }
    @Test
    public void setupTest(){
        Hanoi hanoi = new Hanoi(3);
        for(int y=0;y<3;y++){
            assertTrue(hanoi.getNum(0, y)==3-y);
        }
        for (int x=1; x<3;x++){
            for(int y=0;y<3;y++){
                assertTrue(hanoi.getNum(x,y)==0);
            }
        }
    }
    @Test
    public void visualTest(){
        Hanoi hanoi = new Hanoi(5);
        for (int x=0; x<3;x++){
            for(int y=0;y<5;y++){
                System.out.print(hanoi.getNum(x, y));
            }
            System.out.println("");
        }
    }
    

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
}
