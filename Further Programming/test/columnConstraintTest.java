/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import futoshikipuzzle.*;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author admin
 */
public class columnConstraintTest {
    
    public columnConstraintTest() {
    }
    
    @Test
    public void isSatisfiedTest(){
        FutoshikiPuzzle fp = new FutoshikiPuzzle(5);
        fp.setSquare(0, 0, 1,true);
        fp.setSquare(0, 1, 2,true);
        fp.setColumnConstraint(0, 0, "^");
        assertTrue(fp.getColumnConstraintObject(0,0).isSatisfied());
    }
    @Test
    public void setXTest(){
        FutoshikiPuzzle fp = new FutoshikiPuzzle(5);
        fp.setColumnConstraint(3, 3, "^");
        fp.getColumnConstraintObject(3, 3).setRow(1);
        assertEquals(1, fp.getColumnConstraintObject(3, 3).getRow());
    }
    @Test
    public void setYTest(){
        FutoshikiPuzzle fp = new FutoshikiPuzzle(5);
        fp.setColumnConstraint(3, 3, "^");
        fp.getColumnConstraintObject(3, 3).setColumn(1);
        assertEquals(1, fp.getColumnConstraintObject(3, 3).getColumn());
    }
    @Test
    public void setSymbolTest(){
        FutoshikiPuzzle fp = new FutoshikiPuzzle(5);
        fp.setColumnConstraint(3, 3, "^");
        fp.getColumnConstraintObject(3, 3).setSymbol("v");
        assertEquals("v", fp.getColumnConstraintObject(3, 3).getSymbol());
        fp.getColumnConstraintObject(3, 3).setSymbol("^");
        assertEquals("^", fp.getColumnConstraintObject(3, 3).getSymbol());
    }
    @Test
    public void setIncorrectSymbol(){
        FutoshikiPuzzle fp = new FutoshikiPuzzle(5);
        fp.setColumnConstraint(3, 3, ">");
        assertEquals(" ", fp.getColumnConstraintObject(3, 3).getSymbol());
    }
    @Test
    public void isNotSatisfiedTest(){
        FutoshikiPuzzle fp = new FutoshikiPuzzle(5);
        fp.setSquare(0, 0, 1,true);
        fp.setSquare(0, 1, 2,true);
        fp.setColumnConstraint(0, 0, "v");
        assertFalse(fp.getColumnConstraintObject(0,0).isSatisfied());
    }
}
