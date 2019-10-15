/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import futoshikipuzzle.*;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.Random;

/**
 *
 * @author admin
 */
public class FutoshikiPuzzleTest {
    
    public FutoshikiPuzzleTest() {
    }
    /*********************************************************
     * 
     * Getter/Setter tests
     * 
     ********************************************************/
    
    /*******************************************************
     * FutoshikiSquare
     *****************************************************/
    @Test
    public void setSquareTest(){
        FutoshikiPuzzle fp = new FutoshikiPuzzle(5);
        fp.setSquare(4, 3, 4,true);
        assertEquals(4, fp.getGridItem(4, 3)); //place item
        fp.setSquare(0, 0, 1,true);
        assertEquals(1, fp.getGridItem(0, 0)); //first item
        fp.setSquare(0,0,4,true);
        assertEquals(4, fp.getGridItem(0,0)); //replace item
        fp.setSquare(4, 4, 5,true);
        assertEquals(5, fp.getGridItem(4,4)); //last possible item
        fp.setSquare(4, 4, 0,true);
        assertEquals(0, fp.getGridItem(4,4)); //replace with starting number (0)
    }
    @Test
    public void randomFullSquareTest(){
        Random r = new Random(); //create a Random number generator
        FutoshikiPuzzle fp = new FutoshikiPuzzle(5); //create Puzzle
        for (int x=0; x<5;x++){ //for each column
            for(int y=0;y<5;y++){ //for each item in that column
                fp.setSquare(x, y, r.nextInt(5)+1,true); //set it to a random number
                assertFalse(fp.getGridItem(x, y)==0); //test it has been set to a random number
            }
        }
    }
    @Test
    public void outOfBoundsTestSquare(){
        FutoshikiPuzzle fp = new FutoshikiPuzzle(5);
        fp.setSquare(4,3,2,true);
        fp.setSquare(4, 3, 6,true);
        assertTrue(2 == fp.getGridItem(4, 3)); //place item too large
        fp.setSquare(4, 3, -6,true);
        assertTrue(2 == fp.getGridItem(4, 3)); //place item too large
        fp.setSquare(6, 3, 3,true);
        //because it hasn't crashed it worked
        fp.setSquare(3, 6, 3,true);
        //because it hasn't crashed it worked        
    }
    /******************************************************
     * Row Constraints
     *****************************************************/
    @Test
    public void setRowConstraintTest(){
        FutoshikiPuzzle fp = new FutoshikiPuzzle(5);
        fp.setRowConstraint(2, 0, ">");
        assertEquals(">", fp.getRowConstraintItem(2, 0)); //test to place
        fp.setRowConstraint(2, 0, "<");
        assertEquals("<", fp.getRowConstraintItem(2, 0)); //test to change
        fp.setRowConstraint(0, 0, ">");
        assertEquals(">", fp.getRowConstraintItem(0, 0)); //first item
        fp.setRowConstraint(3, 4, ">");
        assertEquals(">", fp.getRowConstraintItem(3, 4)); //last possible item
    }
    @Test
    public void fullRowConstraintTest(){
        Random r = new Random(); //create a Random number generator
        FutoshikiPuzzle fp = new FutoshikiPuzzle(5); //create Puzzle
        for (int x=0; x<4;x++){ //for each column
            for(int y=0;y<5;y++){ //for each item in that column
                if(r.nextInt(2)==0){ //set randomly to ">" or "<"
                    fp.setRowConstraint(x, y, "<");
                }
                else{
                    fp.setRowConstraint(x, y, ">");
                }
                assertFalse(fp.getRowConstraintItem(x, y).equals(" ")); //test it has been set to a random symbol
            }
        }
    }
    @Test
    public void outOfBoundsTestRow(){
        FutoshikiPuzzle fp = new FutoshikiPuzzle(5);
        fp.setRowConstraint(2, 0, ">");
        fp.setRowConstraint(2, 0, "^");
        assertEquals(">", fp.getRowConstraintItem(2, 0)); //test to place
        fp.setRowConstraint(2, 0, "z");
        assertEquals(">", fp.getRowConstraintItem(2, 0)); //test to place
        fp.setRowConstraint(6, 0, ">");
        //because it hasn't crashed it worked 
        fp.setRowConstraint(2, 6, ">");
        //because it hasn't crashed it worked 
    }
    /***********************************************************
     * Column Constraints
     *********************************************************/
    @Test
    public void setColumnConstraintTest(){
        FutoshikiPuzzle fp = new FutoshikiPuzzle(5);
        fp.setColumnConstraint(2, 0, "^");
        assertEquals("^", fp.getColumnConstraintItem(2, 0)); //place item, ^
        fp.setColumnConstraint(2, 0, "v");
        assertEquals("v", fp.getColumnConstraintItem(2, 0)); //place item, v. Replace item
        fp.setColumnConstraint(0, 0, "^");
        assertEquals("^", fp.getColumnConstraintItem(0, 0)); //first item
        fp.setColumnConstraint(4, 3, "^");
        assertEquals("^", fp.getColumnConstraintItem(4, 3)); //last item
    }
    @Test
    public void fullColumnConstraintTest(){
        Random r = new Random(); //create a Random number generator
        FutoshikiPuzzle fp = new FutoshikiPuzzle(5); //create Puzzle
        for (int x=0; x<5;x++){ //for each column
            for(int y=0;y<4;y++){ //for each item in that column
                if(r.nextInt(2)==0){ //set randomly to "^" or "v"
                    fp.setColumnConstraint(x, y, "^");
                }
                else{
                    fp.setColumnConstraint(x, y, "v");
                }
                assertFalse(fp.getColumnConstraintItem(x, y).equals(" ")); //test it has been set to a random symbol
            }
        }
    }
    @Test
    public void outOfBoundsTestColumn(){
        FutoshikiPuzzle fp = new FutoshikiPuzzle(5);
        fp.setColumnConstraint(2, 0, "^");
        fp.setColumnConstraint(2, 0, ">"); 
        assertEquals("^", fp.getColumnConstraintItem(2, 0)); //test if ignored incorrect, but similar, String
        fp.setColumnConstraint(2, 0, "z");
        assertEquals("^", fp.getColumnConstraintItem(2, 0)); //test to ignore incorrect String
        fp.setColumnConstraint(6, 0, "^");
        //because it hasn't crashed it worked 
        fp.setColumnConstraint(2, 6, "^");
        //because it hasn't crashed it worked 
    }
    /*************************************************************
     * 
     * Legality, FIlling puzzle
     * 
     ************************************************************/
    @Test
    public void tryNewFill(){
        FutoshikiPuzzle fp = new FutoshikiPuzzle(5);
        fp.fillPuzzlewithLatinSquare(5, 5);
        int countRowCon = 0;
        int countNum = 0;
        int countColCon = 0;
        for (FutoshikiSquare[] i: fp.getFutoshikiGrid()) { //for each array in 'grid'
            for (FutoshikiSquare f: i){ //get each item from that array
                if (f.getCurNumber()!=0){ //check if it's not equals to zero
                    countNum++; //increase if it isn't
                }
            }
        }
        for (rowConstraint[] i: fp.getRowConstraint()) { //for each array in 'rowConstraint'
            for (rowConstraint f: i){ //go through each String from that array
                if (!f.getSymbol().equals(" ")){ //check if it's not equals to " "
                    countRowCon++; //increase if it isn't
                }
            }
        }
        for (columnConstraint[] i: fp.getColumnConstraint()) {//for each array in 'rowCsontraint'
            for (columnConstraint f: i){ //for each String in that array
                if (!f.getSymbol().equals(" ")){ //check that it isn't equals to " "
                    countColCon++; //increase if it isn't
                }
            }
        }
        assertTrue(countNum<45/4); //5*5*(11-5)*3/4/10 = 45/4
        assertTrue(countRowCon<45/4);
        assertTrue(countColCon<45/4);
    }
    @Test
    public void tryNewFillMin(){
        FutoshikiPuzzle fp = new FutoshikiPuzzle(2);
        fp.fillPuzzlewithLatinSquare(2, 5);
        int countRowCon = 0;
        int countNum = 0;
        int countColCon = 0;
        for (FutoshikiSquare[] i: fp.getFutoshikiGrid()) { //for each array in 'grid'
            for (FutoshikiSquare f: i){ //get each item from that array
                if (f.getCurNumber()!=0){ //check if it's not equals to zero
                    countNum++; //increase if it isn't
                }
            }
        }
        for (rowConstraint[] i: fp.getRowConstraint()) { //for each array in 'rowConstraint'
            for (rowConstraint f: i){ //go through each String from that array
                if (!f.getSymbol().equals(" ")){ //check if it's not equals to " "
                    countRowCon++; //increase if it isn't
                }
            }
        }
        for (columnConstraint[] i: fp.getColumnConstraint()) {//for each array in 'rowCsontraint'
            for (columnConstraint f: i){ //for each String in that array
                if (!f.getSymbol().equals(" ")){ //check that it isn't equals to " "
                    countColCon++; //increase if it isn't
                }
            }
        }
        assertTrue(countNum<75/4); //5*5*(11-1)*3/4/10 = 75/4
        assertTrue(countRowCon<75/4);
        assertTrue(countColCon<75/4);
    }
    @Test
    public void tryNewFillMax(){
        FutoshikiPuzzle fp = new FutoshikiPuzzle(9);
        fp.fillPuzzlewithLatinSquare(5, 9);
        int countRowCon = 0;
        int countNum = 0;
        int countColCon = 0;
        for (FutoshikiSquare[] i: fp.getFutoshikiGrid()) { //for each array in 'grid'
            for (FutoshikiSquare f: i){ //get each item from that array
                if (f.getCurNumber()!=0){ //check if it's not equals to zero
                    countNum++; //increase if it isn't
                }
            }
        }
        for (rowConstraint[] i: fp.getRowConstraint()) { //for each array in 'rowConstraint'
            for (rowConstraint f: i){ //go through each String from that array
                if (!f.getSymbol().equals(" ")){ //check if it's not equals to " "
                    countRowCon++; //increase if it isn't
                }
            }
        }
        for (columnConstraint[] i: fp.getColumnConstraint()) {//for each array in 'rowCsontraint'
            for (columnConstraint f: i){ //for each String in that array
                if (!f.getSymbol().equals(" ")){ //check that it isn't equals to " "
                    countColCon++; //increase if it isn't
                }
            }
        }
        assertTrue(countNum<45/4); //5*5*(11-5)*3/4/10 = 45/4
        assertTrue(countRowCon<45/4);
        assertTrue(countColCon<45/4);
    }
    @Test
    public void checkCompleteTest(){
        FutoshikiPuzzle fp = new FutoshikiPuzzle(5);
        assertTrue(fp.solveable(fp, 0, 0));
    }
    //Solveable is causing stack overflow errors
    @Test
    public void randomGenTest(){
        FutoshikiPuzzle fp = new FutoshikiPuzzle(5);
        System.out.println("Test randomGenTest");
        fp.fillPuzzle();
        System.out.println(fp.solveable(fp, 0, 0));
    }
    //Solveable is causing stack overflow errors
    @Test
    public void checkLegalTest(){
        FutoshikiPuzzle fp = new FutoshikiPuzzle(5);
        fp.legalPuzzle();
        System.out.println("checkLegalTest");
        System.out.println(fp.displayString());
        assertTrue(fp.isLegal(1));
    }
    @Test
    public void checkNotLegalTest(){
        FutoshikiPuzzle fp = new FutoshikiPuzzle(5);
        fp.notLegalPuzzle();
        System.out.println("checkNotLegalTest");
        System.out.println(fp.displayString());
        assertFalse(fp.isLegal(1));
    }
    @Test
    public void nonRandomFillPuzzleTest(){ //testing setup for the puzzle as well as populating all three grids. 
        FutoshikiPuzzle fp = new FutoshikiPuzzle(5);
        int[][] ideal = new int[][]{{1,0,0,4,0},{0,0,0,0,0},{0,3,0,0,0},{0,0,3,0,0},{0,0,0,0,0}};
        String[][] idealRow = new String[][]{{" "," "," "," "},{" ",">"," ","<"},{" "," "," "," "},{" "," "," ",">"},{" "," "," "," "}};
        String[][] idealColumn = new String[][]{{" "," "," "," "," "},{" ","^"," "," "," "},{"v"," "," ","v"," "},{" "," "," ","^"," "}};
        fp.nonRandomFillPuzzle();
        int county=0; //initialise the counter to 0
        for (FutoshikiSquare[] i: fp.getFutoshikiGrid()) { //get 'grid' then go through each array in grid
            int countx = 0;
            for (FutoshikiSquare f: i){ //get each item in i, which is an array in the two dimensional array 'grid'
                assertEquals(ideal[countx][county], f.getCurNumber()); //use the fact it is pre-determined to make sure each item in ideal matches to the one at the same index. 
                countx++;
            }
            county++;
        }
        county=0; //reset counter
        for (rowConstraint[] i: fp.getRowConstraint()) { //get 'rowConstraint' and then go through each String array in it
            int countx = 0;
            for (rowConstraint f: i){ //go through each String in the one dimensional String array i.
                assertEquals(idealRow[countx][county], f.getSymbol()); //use the fact it is pre-determined to make sure that each item in idealRow matches to the one in the smae index. 
                countx++;
            }
            county++;
        }
        county=0; //reset the counter
        for (columnConstraint[] i: fp.getColumnConstraint()) { //get 'columnConstraint' and then go through each String array in it
            int countx = 0;
            for (columnConstraint f: i){ //go through each String in the one dimensional String array i.
                assertEquals(idealColumn[countx][county], f.getSymbol()); //use the fact it is pre-determined to make sure that each item in idealColumn matches to the one in the smae index. 
                countx++;
            }
            county++;
        }
    }
    @Test
    public void fillPuzzleTest(){
        FutoshikiPuzzle fp = new FutoshikiPuzzle(5);
        fp.fillPuzzle();
        int countNum = 0;
        int countRowCon = 0;
        int countColCon = 0;
        for (FutoshikiSquare[] i: fp.getFutoshikiGrid()) { //for each array in 'grid'
            for (FutoshikiSquare f: i){ //get each item from that array
                if (f.getCurNumber()!=0){ //check if it's not equals to zero
                    countNum++; //increase if it isn't
                }
            }
        }
        for (rowConstraint[] i: fp.getRowConstraint()) { //for each array in 'rowConstraint'
            for (rowConstraint f: i){ //go through each String from that array
                if (!f.getSymbol().equals(" ")){ //check if it's not equals to " "
                    countRowCon++; //increase if it isn't
                }
            }
        }
        for (columnConstraint[] i: fp.getColumnConstraint()) {//for each array in 'rowCsontraint'
            for (columnConstraint f: i){ //for each String in that array
                if (!f.getSymbol().equals(" ")){ //check that it isn't equals to " "
                    countColCon++; //increase if it isn't
                }
            }
        }
        assertTrue(countNum<6);
        assertTrue(countRowCon<5);
        assertTrue(countColCon<5);
    }
    //Causes stack overflow errors
    @Test
    public void fillPuzzleTestMin(){
        FutoshikiPuzzle fp = new FutoshikiPuzzle(2);
        fp.fillPuzzle();
        int countNum = 0;
        int countRowCon = 0;
        int countColCon = 0;
        for (FutoshikiSquare[] i: fp.getFutoshikiGrid()) { //for each array in 'grid'
            for (FutoshikiSquare f: i){ //get each item from that array
                if (f.getCurNumber()!=0){ //check if it's not equals to zero
                    countNum++; //increase if it isn't
                }
            }
        }
        for (rowConstraint[] i: fp.getRowConstraint()) { //for each array in 'rowConstraint'
            for (rowConstraint f: i){ //go through each String from that array
                if (!f.getSymbol().equals(" ")){ //check if it's not equals to " "
                    countRowCon++; //increase if it isn't
                }
            }
        }
        for (columnConstraint[] i: fp.getColumnConstraint()) {//for each array in 'rowCsontraint'
            for (columnConstraint f: i){ //for each String in that array
                if (!f.getSymbol().equals(" ")){ //check that it isn't equals to " "
                    countColCon++; //increase if it isn't
                }
            }
        }
        assertTrue(countNum<3);
        assertTrue(countRowCon<2);
        assertTrue(countColCon<2);
    }
    @Test
    public void isNotCompleteTest(){
        FutoshikiPuzzle fp = new FutoshikiPuzzle(5);
        fp.nonRandomFillPuzzle();
        assertFalse(fp.isComplete());
    }
    /************************************************************
     * 
     * Display String Tests
     * 
     ************************************************************/
    @Test
    public void displayStringTestForNonRandom(){ 
        FutoshikiPuzzle fp = new FutoshikiPuzzle(5); //instantiate the Puzzle
        fp.nonRandomFillPuzzle(); //call the non-random Puzzle
        System.out.println("Non-Random"); //print that it's non-random for testing
        System.out.print(fp.displayString()); //print the string for visual evaluation
        String s = fp.displayString();
        assertTrue(s.matches("(( ___)+\\n([ |<|>]\\|[0-5]\\|)+\\n( ___)+(\\n)?(  [ |^|v] )*\\n)+")); //checks the whole string if it matches the layout of the regex
    }
    @Test
    public void displayStringTest(){
        FutoshikiPuzzle fp = new FutoshikiPuzzle(5); //instantiate the puzzle, size 5
        fp.fillPuzzlewithLatinSquare(5,5); //call the random generator for the puzzle
        System.out.println("Random"); //print that it's a random for testing
        System.out.print(fp.displayString()); //print the string for visual evaluation
        String s = fp.displayString(); //put it into a variable to allow for assertions
        assertTrue(s.matches("(( ___)+\\n" //Check the first line, for the ' ___' repeating pattern
                + "([ |<|>]\\|[0-5]\\|)+\\n" //Check the next line for the '(' ' or '<' or '>')|Digit|' pattern
                + "( ___)+\\n" //Check the first line for the ' ___' repeating pattern
                + "(  [ |^|v] )*\\n" //Check that, if there is this line, then it follows the pattern '  (' ' or '^' or 'v') '
                + ")+")); //then repeat this through the rest of the string to check the whole thing matches
    }
    @Test
    public void otherSizeTest(){
        for (int x=2;x<10;x++){
            FutoshikiPuzzle fp = new FutoshikiPuzzle(x); //instantiate the puzzle, size x
            fp.fillPuzzlewithLatinSquare(x,5);//call the random number generator for the puzzle
            System.out.println("Size= " + x); //print the size
            System.out.print(fp.displayString()); //print the string for visual evaluation
            String s = fp.displayString(); //put it into a variable to allow for assertions
            assertTrue(s.matches("(( ___)+\\n" //Check the first line, for the ' ___' repeating pattern
                    + "([ |<|>]\\|\\d+\\|)+\\n" //Check the next line for the '(' ' or '<' or '>')|Digit|' pattern
                    + "( ___)+\\n" //Check the first line for the ' ___' repeating pattern
                    + "(  [ |^|v] )*\\n" //Check that, if there is this line, then it follows the pattern '  (' ' or '^' or 'v') '
                    + ")+")); //then repeat this through the rest of the string to check the whole thing matches
        }
    }
}
