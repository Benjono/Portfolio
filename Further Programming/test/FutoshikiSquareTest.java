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
public class FutoshikiSquareTest {
    
    public FutoshikiSquareTest() {
    }
    @Test
    public void isEditableTrueTest(){
        FutoshikiPuzzle fp = new FutoshikiPuzzle(5);
        fp.setSquare(2, 2, 2, true);
        assertTrue(fp.getGridObject(2, 2).isEditable());
    }
    @Test
    public void isEditableFalseTest(){
        FutoshikiPuzzle fp = new FutoshikiPuzzle(5);
        fp.setSquare(2, 2, 2, false);
        assertFalse(fp.getGridObject(2, 2).isEditable());
    }
    @Test
    public void getNumberTest(){
        FutoshikiSquare fs = new FutoshikiSquare(true, 3);
        assertTrue(fs.getCurNumber()==3);
    }
    @Test
    public void editableTest(){
        FutoshikiPuzzle fp = new FutoshikiPuzzle(5);
        fp.setSquare(2, 2, 2, true);
        fp.setSquare(2, 2, 3, false);
        assertTrue(3==fp.getGridObject(2, 2).getCurNumber());
    }
    @Test
    public void uneditableTest(){
        FutoshikiPuzzle fp = new FutoshikiPuzzle(5);
        fp.setSquare(2, 2, 2, false);
        fp.setSquare(2, 2, 3, false);
        assertTrue(2==fp.getGridObject(2, 2).getCurNumber());
    }
    @Test
    public void emptyWhileFalseTest(){
        FutoshikiPuzzle fp = new FutoshikiPuzzle(5);
        fp.setSquare(2, 2, 2, false);
        fp.getGridObject(2, 2).empty();
        assertTrue(2==fp.getGridObject(2, 2).getCurNumber());
    }
    @Test
    public void emptyWhileTrueTest(){
        FutoshikiPuzzle fp = new FutoshikiPuzzle(5);
        fp.setSquare(2, 2, 2, true);
        fp.getGridObject(2, 2).empty();
        assertTrue(0==fp.getGridObject(2, 2).getCurNumber());
    }
}