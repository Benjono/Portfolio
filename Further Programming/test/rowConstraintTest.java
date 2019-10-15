/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import futoshikipuzzle.FutoshikiPuzzle;
import futoshikipuzzle.rowConstraint;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author admin
 */
public class rowConstraintTest {
    
    public rowConstraintTest() {
    }
    
    @Test
    public void isSatisfiedTest(){
        FutoshikiPuzzle fp = new FutoshikiPuzzle(5);
        fp.setSquare(0, 0, 1,true);
        fp.setSquare(1, 0, 2,true);
        fp.setRowConstraint(0, 0, "<");
        assertTrue(fp.getRowConstraintObject(0,0).isSatisfied());
    }
    @Test
    public void setXTest(){
        FutoshikiPuzzle fp = new FutoshikiPuzzle(5);
        fp.setRowConstraint(3, 3, ">");
        fp.getRowConstraintObject(3, 3).setRow(3);
        assertEquals(3, fp.getRowConstraintObject(3, 3).getRow());
    }
    @Test
    public void setYTest(){
        FutoshikiPuzzle fp = new FutoshikiPuzzle(5);
        fp.setRowConstraint(3, 3, ">");
        fp.getRowConstraintObject(3, 3).setColumn(1);
        assertEquals(1, fp.getRowConstraintObject(3, 3).getColumn());
    }
    @Test
    public void setSymbolTest(){
        FutoshikiPuzzle fp = new FutoshikiPuzzle(5);
        fp.setRowConstraint(3, 3, ">");
        fp.getRowConstraintObject(3, 3).setSymbol("<");
        assertEquals("<", fp.getRowConstraintObject(3, 3).getSymbol());
        fp.getRowConstraintObject(3, 3).setSymbol(">");
        assertEquals(">", fp.getRowConstraintObject(3, 3).getSymbol());
    }
    @Test
    public void setIncorrectSymbol(){
        FutoshikiPuzzle fp = new FutoshikiPuzzle(5);
        fp.setRowConstraint(3, 3, "^");
        assertEquals(" ", fp.getRowConstraintObject(3, 3).getSymbol());
    }
    @Test
    public void isNotSatisfiedTest(){
        FutoshikiPuzzle fp = new FutoshikiPuzzle(5);
        fp.setSquare(1, 1, 1,true);
        assertTrue(fp.getGridItem(1, 1)==1);
        fp.setSquare(0, 1, 2,true);
        assertTrue(fp.getGridItem(0, 1)==2);
        fp.setRowConstraint(0, 1, "<");
        assertTrue(fp.getRowConstraintObject(0, 1).getSymbol().equals("<"));
        assertTrue(fp.getRowConstraintObject(0, 1).getColumn()==1);
        assertTrue(fp.getRowConstraintObject(0, 1).getRow()==0);
        assertTrue(fp.getGridItem(fp.getRowConstraintObject(0, 1).getRow(),fp.getRowConstraintObject(0, 1).getColumn())==2);
        assertTrue(fp.getGridItem(fp.getRowConstraintObject(0, 1).getRow()+1,fp.getRowConstraintObject(0, 1).getColumn())==1);
        
        assertFalse(fp.getRowConstraintObject(0,1).isSatisfied());
    }
    @Test
    public void isNotSatisfiedTest2(){
        FutoshikiPuzzle fp = new FutoshikiPuzzle(5);
        fp.setSquare(0, 3, 1,true);
        fp.setSquare(1, 3, 2,true);
        fp.setRowConstraint(0, 3, ">");
        assertTrue(fp.getRowConstraintObject(0, 3).getSymbol().equals(">"));
        assertTrue(fp.getRowConstraintObject(0, 3).getColumn()==3);
        assertTrue(fp.getRowConstraintObject(0, 3).getRow()==0);
        assertFalse(fp.getRowConstraintObject(0,3).isSatisfied());
    }
}
