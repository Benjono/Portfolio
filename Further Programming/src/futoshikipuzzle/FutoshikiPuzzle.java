package futoshikipuzzle;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author admin
 */
import java.util.*;
public class FutoshikiPuzzle {
    private final FutoshikiSquare[][] futoshikiGrid;
    private final rowConstraint[][] rowConstraintGrid;
    private final columnConstraint[][] columnConstraintGrid;
    private final FutoshikiSquare[][] solutionGrid;
    private int puzzleSize = 5;
    /**
     * The constructor of FutoshikiPuzzle.
     * @param size 
     */
    public FutoshikiPuzzle(int size){
        puzzleSize = size;
        futoshikiGrid = new FutoshikiSquare[puzzleSize][puzzleSize];
        rowConstraintGrid = new rowConstraint[puzzleSize-1][puzzleSize];
        columnConstraintGrid = new columnConstraint[puzzleSize][puzzleSize-1];
        solutionGrid = new FutoshikiSquare[puzzleSize][puzzleSize];
        this.rowBlankStart();
        this.columnBlankStart();
        this.gridBlankStart();
    }
    public FutoshikiPuzzle(int size, FutoshikiSquare[][] fs, rowConstraint[][] rc, columnConstraint[][] cc){
        puzzleSize = size;
        futoshikiGrid = fs;
        rowConstraintGrid = rc;
        columnConstraintGrid = cc;
        solutionGrid = new FutoshikiSquare[puzzleSize][puzzleSize];
    }
   
    /**
     * A function to check if the board is in a legal state
     * @return boolean
     */
    public boolean isLegal(int output){
        boolean legal = true;
        ArrayList<AProblem> problems = new ArrayList<AProblem>();
        for(rowConstraint[] i: this.getRowConstraint()){
            for(rowConstraint f: i){
                if(!f.isSatisfied()&&!f.isSatisfied()){
                    if(f.getSymbol().equals(">")){
                        problems.add(new AProblem(f.getRow(),f.getColumn(),1));
                    }
                    else{
                        problems.add(new AProblem(f.getRow(),f.getColumn(),2));
                    }
                    legal = false;
                }
            }
        }
        for(columnConstraint[] i: this.getColumnConstraint()){
            for(columnConstraint f: i){
                if(!f.isSatisfied()&&!f.isSatisfied()){
                    if(f.getSymbol().equals("^")){
                        problems.add(new AProblem(f.getRow(),f.getColumn(),3));
                    }
                    else{
                        problems.add(new AProblem(f.getRow(),f.getColumn(),4));
                    }
                    legal = false;
                }
            }
        }
        for(int y = 0; y<this.puzzleSize; y++){ //for each row
            for(int x = 0; x<this.puzzleSize; x++){ //for each item
                for(int z=x; z<this.puzzleSize; z++){
                    if(this.getGridItem(x, y)==this.getGridItem(z, y) && z!=x && this.getGridItem(x,y)!=0){
                        problems.add(new AProblem(x,y,5, this.getGridItem(x, y), z));
                        legal = false;
                    }
                }
            }
        }
        for(int x = 0; x<this.puzzleSize; x++){ //for each column
            for(int y = 0; y<this.puzzleSize; y++){ //for each item
                for(int z=y; z<this.puzzleSize; z++){
                    if(this.getGridItem(x, y)==this.getGridItem(x, z) && z!=y && this.getGridItem(x,y)!=0){
                        problems.add(new AProblem(x,y,6, this.getGridItem(x, y), z));
                        legal = false;
                    }
                }
            }
        }
        if(!legal && output==0){
            System.out.println(getProblems(problems));
        }
        return legal;
    }
    /**
     * A function to check if the board is in a legal state and return 
     * @return boolean
     * @param a
     */
    public ArrayList<AProblem> isLegalReturnProblems(){
        boolean legal = true;
        ArrayList<AProblem> problems = new ArrayList<AProblem>();
        for(rowConstraint[] i: this.getRowConstraint()){
            for(rowConstraint f: i){
                if(!f.isSatisfied()){
                    if(f.getSymbol().equals(">")){
                        
                        problems.add(new AProblem(f.getRow(),f.getColumn(),1));
                    }
                    else{
                        problems.add(new AProblem(f.getRow(),f.getColumn(),2));
                    }
                    legal = false;
                }
            }
        }
        for(columnConstraint[] i: this.getColumnConstraint()){
            for(columnConstraint f: i){
                if(!f.isSatisfied()){
                    if(f.getSymbol().equals("^")){
                        problems.add(new AProblem(f.getRow(),f.getColumn(),3));
                    }
                    else{
                        problems.add(new AProblem(f.getRow(),f.getColumn(),4));
                    }
                    legal = false;
                }
            }
        }
        for(int y = 0; y<this.puzzleSize; y++){ //for each row
            for(int x = 0; x<this.puzzleSize; x++){ //for each item
                for(int z=x; z<this.puzzleSize; z++){
                    if(this.getGridItem(x, y)==this.getGridItem(z, y) && z!=x && this.getGridItem(x,y)!=0){
                        problems.add(new AProblem(x,y,5, this.getGridItem(x, y), z));
                        legal = false;
                    }
                }
            }
        }
        for(int x = 0; x<this.puzzleSize; x++){ //for each column
            for(int y = 0; y<this.puzzleSize; y++){ //for each item
                for(int z=y; z<this.puzzleSize; z++){
                    if(this.getGridItem(x, y)==this.getGridItem(x, z) && z!=y && this.getGridItem(x,y)!=0){
                        problems.add(new AProblem(x,y,6, this.getGridItem(x, y), z));
                        legal = false;
                    }
                }
            }
        }
        if(!legal){
            return problems;
        }
        return null;
    }
    /**
     * Randomly fill the puzzle with the generated latin square based on the difficulty of the puzzle
     * The higher the difficulty, the less of the solution will be filled and the fewer constraints will be inputted
     * @param size
     * @param difficulty 
     */
    public void fillPuzzlewithLatinSquare(int size, int difficulty){
        int[][] theSquare = latinSquare(size);
        Random r = new Random();
        for(int i = 0; i<size;i++){
            for(int j=0;j<size;j++){
                this.setSolutionSquare(i, j, theSquare[i][j]);
            }
        }
        int newDifficulty = 11-difficulty;
        newDifficulty = newDifficulty * 3 / 4;
        this.gridBlankStart();
        for(int x=0;x<puzzleSize*puzzleSize * newDifficulty/10;x++){ //randomly determin number of pre-filled numbers
            int row = r.nextInt(puzzleSize);
            int column = r.nextInt(puzzleSize);
            setSquare(row,column,this.getSolutionInt(row, column), false);
        }
        for(int x=0;x<puzzleSize*(puzzleSize-1)*newDifficulty/10;x++){ //randomly determin number of row constraints
            int row = r.nextInt(puzzleSize-1);
            int column = r.nextInt(puzzleSize);
            int number1 = this.getSolutionInt(row,column);
            int number2 = this.getSolutionInt(row+1,column);
            if(number1<number2){
                setRowConstraint(row, column,"<");   
            }
            else if(number1>number2){
                setRowConstraint(row, column,">");
            }
        }
        for(int x=0;x<puzzleSize*(puzzleSize-1)*newDifficulty/10;x++){ //randomly determin number of row constraints
            int row = r.nextInt(puzzleSize);
            int column = r.nextInt(puzzleSize-1);
            int number1 = this.getSolutionInt(row,column);
            int number2 = this.getSolutionInt(row,column+1);
            if(number1<number2){
                setColumnConstraint(row, column,"^");
            }
            else if(number1>number2){
                setColumnConstraint(row, column,"v");
            }
        }
    }
    /**
     * Generates a size by size latin square.
     * Will always be the same latin square every time
     * @param size
     * @return int[][]
     */
    
    private int[][] latinSquare(int size){
        int[][] fillGrid = new int[size][size];
        // A variable to control the  
        // rotation point. 
        int k = size; 
        //loop to create the array
        for (int i = 0; i < size; i++){ 
            int pos = 0; //controller for position
            int temp = k; 
            //fill up from k to size
            while (temp < size) {
                fillGrid[i][pos] = temp+1;
                temp++; 
                pos++;
            } 
            // fill up from 1 to k-1. 
            for (int j = 0; j < k; j++) {
                fillGrid[i][pos] = j+1;
                pos++;
            }
            k--; 
        } 
        fillGrid = scrambleGrid(fillGrid, size); //randomly scramble the rows and columns
        //this is still legal as if rows or columns are swapped then each row and column will not have any duplicates
        return fillGrid;
    }
    /**
     * Takes the latin square and randomly scrambles it. 
     * Each random configuratoin has the same chance of being selected as all others
     * @param unscrambledGrid
     * @param gridSize
     * @return int[][]
     */
    private int[][] scrambleGrid(int[][] unscrambledGrid, int gridSize){
        int[][] scrambledGrid = new int[gridSize][gridSize];
        ArrayList<Integer> randomRows = new ArrayList<>(); 
        for (int i = 0; i<gridSize; i++){
            randomRows.add(i); //create a Arraylist with integers from 1-gridSize
        }
        ArrayList<Integer> randomColumns = new ArrayList<>();
        for (int i = 0; i<gridSize; i++){
            randomColumns.add(i); //create a Arraylist with integers from 1-gridSize
        }
        java.util.Collections.shuffle(randomRows); //randomly shuffle the row numbers
        java.util.Collections.shuffle(randomColumns); //randomly shuffle the column numbers
        int counter = 0;
        for (Integer i : randomRows){ //shuffle the rows
            for (int j = 0; j<gridSize; j++){
                scrambledGrid[counter][j] = unscrambledGrid[i][j];
            }
            counter++;
        }
        counter = 0;
        int[][] secondScramble = new int[gridSize][gridSize];
        for (Integer i : randomColumns){ //shuffle the columns
            for (int j = 0; j<gridSize; j++){
                secondScramble[j][counter] = scrambledGrid[j][i];
            }
            counter++;
        }
        return secondScramble; //return shuffled grid
    }

    public FutoshikiPuzzle solveSetup(){
        FutoshikiPuzzle fp = new FutoshikiPuzzle(puzzleSize);
        for(int i = 0; i<puzzleSize; i++){
            for(int j=0; j<puzzleSize; j++){
                fp.setSquare(j, i, this.futoshikiGrid[i][j].getCurNumber(), this.futoshikiGrid[i][j].isEditable());
            }
        }
        return fp;
    }
    public boolean solveable(FutoshikiPuzzle fp, int x, int y){
        fp.setSquare(x, y, fp.getGridItem(x, y)+1, true); //change square
        if(fp.isLegal(1)){ //if legal
            if(fp.isComplete() && fp.isLegal(1)){ //if complete
                System.out.println(fp.displayString()); //winner winner
                return true; //chicken dinner
            }
            do{
                if(y==(puzzleSize-1)){ //if at an edge
                    y=0; //go to next row
                    x++;
                } else {
                    y++; //go to next square otherwise
                }
            } while(!fp.getGridObject(x,y).isEditable());
        } else { //if not legal
            if (fp.getGridItem(x, y)==puzzleSize){ //if square at max number
                do{
                    if(y==0){ //go backwards, previous line if at start of line
                        fp.setSquare(x, y, 0, true);
                        y=puzzleSize-1;
                        x--;
                    }else{
                        fp.setSquare(x, y, 0, true);
                        y--;
                    }
                }while (!fp.getGridObject(x, y).isEditable());
            }
        }
        if (x==-1 || y==-1){
            return false;
        } else {
            return solveable(fp, x, y);
        }
    }
    
    /**
     * A function that takes an ArrayList<AProblem> and returns a String for the list of problems that isLegal found.
     * @param listOfProblems
     * @return String
     */
    public String getProblems(ArrayList<AProblem> listOfProblems){
        String theProblems = "";
        for(AProblem i: listOfProblems){
            int x = i.getX();
            int y = i.getY();
            int problem = i.getProblem();
            if(problem==1){
                theProblems += "("+(x+1)+","+(y+1)+")"+" is not greater than "+"("+(x+2)+","+(y+1)+")\n";
            }
            else if (problem==2){
                theProblems += "("+(x+1)+","+(y+1)+")"+" is not lesser than "+"("+(x+2)+","+(y+1)+")\n";
            }
            else if (problem==3){
                theProblems += "("+(x+1)+","+(y+1)+")"+" is not lesser than "+"("+(x+1)+","+(y+2)+")\n";
            }
            else if (problem==4){
                theProblems += "("+(x+1)+","+(y+1)+")"+" is not greater than "+"("+(x+1)+","+(y+2)+")\n";
            }
            else if (problem==5){
                int num = i.getNumber();
                theProblems += num+" is repeated on column "+(y+1)+"\n";
            }
            else{
                int num = i.getNumber();
                theProblems += num+" is repeated on row "+(x+1)+"\n";
            }
        }
        return theProblems;
    }
    
    public void fillPuzzlev2(int difficulty){
        int newDifficulty = 11-difficulty;
        newDifficulty = newDifficulty * 3 / 4;
        Random r = new Random();
        boolean puzzleisLegal = false;
        boolean puzzleIsSolveable = false;
        do{
            this.gridBlankStart();
            for(int x=0;x<puzzleSize*puzzleSize * newDifficulty;x++){ //randomly determin number of pre-filled numbers
                setSquare(r.nextInt(puzzleSize),r.nextInt(puzzleSize),r.nextInt(puzzleSize-1)+1, false);
            }
            puzzleisLegal = this.isLegal(1);
            if(puzzleisLegal){
                puzzleIsSolveable = this.solveable(this, 0, 0);
            }
        } while (!puzzleIsSolveable);
        
    }
    public void fillConstraints(int difficulty){
        
    }
    
    /**
     * A function which fills the puzzle randomly using java.util.Random
     */
    public void fillPuzzle(){
        Random r = new Random();
        boolean puzzleisLegal = false;
        boolean puzzleIsSolveable = false;
        do{
            this.columnBlankStart();
            this.rowBlankStart();
            this.gridBlankStart();
            for(int x=0;x<r.nextInt(puzzleSize)+1;x++){ //randomly determin number of pre-filled numbers
                setSquare(r.nextInt(puzzleSize),r.nextInt(puzzleSize),r.nextInt(puzzleSize-1)+1, false);
            }
            for(int x=0;x<r.nextInt(puzzleSize)+1;x++){ //randomly determin number of row constraints
                if(r.nextInt(2)==1){ //randomly determin which symbol it is
                    setRowConstraint(r.nextInt(puzzleSize-1),r.nextInt(puzzleSize),">"); //and where they're placed
                }
                else{
                    setRowConstraint(r.nextInt(puzzleSize-1),r.nextInt(puzzleSize),"<");
                }
            }
            for(int x=0;x<r.nextInt(puzzleSize)+1;x++){ //randomly determin number of column constraints
                if(r.nextInt(2)==1){ //randomly determin which symbol it is
                    setColumnConstraint(r.nextInt(puzzleSize),r.nextInt(puzzleSize-1),"^"); //and where they're placed
                }
                else{
                    setColumnConstraint(r.nextInt(puzzleSize),r.nextInt(puzzleSize-1),"v");
                }
            }
            puzzleisLegal = this.isLegal(1);
            if(puzzleisLegal){
                puzzleIsSolveable = this.solveable(this, 0, 0);
            }
        } while (!puzzleIsSolveable);
    }
    /**
     * A function which returns a string of the current state of the board.
     * @return String
     */
    public String displayString(){
        String returnString = "";
        int countery = 0;
        for(FutoshikiSquare[] i: futoshikiGrid){ //For each row
            for(int x=0; x<puzzleSize;x++){ //create the top line
                returnString = returnString + " ___";
            }
            returnString = returnString + "\n "; //newline + space to help line things up
            int counterx = 0;
            for(FutoshikiSquare j: i){ //for each int in i
                if(counterx>0){ //if it is between numbers
                    returnString = returnString + getRowConstraintItem(counterx-1, countery); //add the string in rowConstraint for the square at counterx-1,countery
                }
                returnString = returnString + "|"+j.getCurNumber()+"|"; //then add the numbers and the sides of the box
                counterx++;
            }
            returnString = returnString + "\n"; //newline
            for(int x=0; x<puzzleSize;x++){
                returnString = returnString + " ___"; //create the bottom line
            }
            returnString = returnString + "\n"; //Newline
            for(int x=0;x<puzzleSize;x++){ //then construct the column constraints
                if(countery<puzzleSize-1){ //except for the bottom line which doesn't have a row below it
                    returnString = returnString +"  " + getColumnConstraintItem(x,countery) +" "; //do the padding and the constraints if there is one
                }
            }
            returnString = returnString + "\n"; //newline for the next row
            countery++;
        }
        return returnString;
    }
    /**
     * A function which checks that all squares are filled and that the puzzle is still legal. 
     * @return boolean
     */
    public boolean isComplete(){
        boolean allFilled = true;
        for(int x=0; x<this.puzzleSize; x++){
            for(int y=0; y<this.puzzleSize;y++){
                if(this.getGridItem(x, y)==0){
                    allFilled=false;
                }
            }
        }
        return allFilled && this.isLegal(1);
    }
    
    /*******************************************************************
     * 
     * Setters
     * 
     ******************************************************************/
    /**
     * A function to place a int into the futoshikiGrid array at the desired column and row.
     * @param column
     * @param row
     * @param number
     * @param editable
     */
    public void setSquare(int row, int column, int number, boolean editable){
        if (column<puzzleSize && row>-1 && column>-1 && row<puzzleSize && number >= 0 && number <= puzzleSize){
            if(this.getGridObject(row, column).isEditable()){
                futoshikiGrid[row][column] = new FutoshikiSquare(editable, number);
            } else {
                System.out.println("Sorry, that square can't be edited");
            }
        }
    }
    public void setSolutionSquare(int row, int column, int number){
        solutionGrid[row][column] = new FutoshikiSquare(true, number);
    }
    /**
     * A function to place a String into rowConstraint at the desired column and row.
     * @param column
     * @param row
     * @param symbol 
     */
    public void setRowConstraint(int row, int column, String symbol){
        if (column<puzzleSize && row>-1 && column>-1 && row<puzzleSize-1 && (symbol.equals("<") || symbol.equals(">"))){
            rowConstraintGrid[row][column] = new rowConstraint(this, row, column, symbol);
        }
    }
    /**
     * A function to place a String into columnConstraint at the desired column and row.
     * @param column
     * @param row
     * @param symbol 
     */
    public void setColumnConstraint(int row, int column, String symbol){
        if (column<puzzleSize-1 && column>-1 && row>-1 && row<puzzleSize && (symbol.equals("^") || symbol.equals("v"))){ //if the inputs are legal
            columnConstraintGrid[row][column] = new columnConstraint(this, row, column, symbol); //Create a column constraint object and put it 
                                                                                                //in row,column of columnConstraintGrid
        }
    }
    
    /**********************************************************
     * 
     * For Testing
     * 
     ***********************************************************/
    /**
     * A function to fill the grid with a pre-determined grid.
     * For testing purposes
     */
    public void nonRandomFillPuzzle(){
        setSquare(0,0,1,true); 
        setSquare(3,0,4,true);
        setSquare(1,2,3,true);
        setSquare(2,3,3,true);
        setRowConstraint(3,3,">");
        setRowConstraint(1,1,">");
        setRowConstraint(3,1,"<");
        setColumnConstraint(1,1,"^");
        setColumnConstraint(0,2,"v");
        setColumnConstraint(3,2,"v");
        setColumnConstraint(3,3,"^");
    }
    public void legalPuzzle(){
        setSquare(0,0,5,true);
        setSquare(0,1,4,true);
        setSquare(1,0,4,true);
        setRowConstraint(0,0,">");
        setColumnConstraint(0,0,"v");
    }
    public void notLegalPuzzle(){
        setSquare(0,0,5,true);
        setSquare(0,1,4,true);
        setSquare(1,0,4,true);
        setSquare(0,4,5,true);
        setRowConstraint(0,0,"<");
        setColumnConstraint(0,0,"^");
    }        
    
    /****************************************************************
     * 
     * Getters
     * 
     ****************************************************************/
    
    /***************************************************************
     * Contents of an object
     **************************************************************/
    /**
     * A function to get an item from the two dimensional 'futoshikiGrid' array.
     * @param column
     * @param row
     * @return int
     */
    public int getGridItem(int row, int column) {
        return futoshikiGrid[row][column].getCurNumber();
    }
    /**
     * A function to get an item from the two dimensional 'rowConstraint' array
     * @param column
     * @param row
     * @return String
     */
    public String getRowConstraintItem(int row, int column) {
        return rowConstraintGrid[row][column].getSymbol();
    }
    /**
     * A function to get an item from the two dimensional 'columnConstraint' array
     * @param column
     * @param row
     * @return String
     */
    public String getColumnConstraintItem(int row, int column) {
        return columnConstraintGrid[row][column].getSymbol();
    }
    public int getSolutionInt(int row, int column){
        return solutionGrid[row][column].getCurNumber();
    }
    /**************************************************************
     * Objects
     *************************************************************/
    /**
     * Get a FutoshikiSquare Object at [row][column]
     * @param row
     * @param column
     * @return FutoshikiSquare
     */
    public FutoshikiSquare getGridObject(int row, int column){
        return futoshikiGrid[row][column];
    }
    /**
     * Get a rowConstraint object in the rowConstraintGrid at [row][column]
     * @param row
     * @param column
     * @return rowConstraint
     */
    public rowConstraint getRowConstraintObject(int row, int column){
        return rowConstraintGrid[row][column];
    }
    /**
     * Get a columnConstraint object in the columnConstraintGrid at [row][column]
     * @param row
     * @param column
     * @return columnConstraint
     */
    public columnConstraint getColumnConstraintObject(int row, int column){
        return columnConstraintGrid[row][column];
    }
    public FutoshikiSquare getSolutionObject(int row, int column){
        return solutionGrid[row][column];
    }
    /*******************************************************
     * Arrays
     *******************************************************/
    /**
     * A function to get the two dimensional array 'futoshikiGrid'
     * @return int[][]
     */
    public FutoshikiSquare[][] getFutoshikiGrid() {
        return futoshikiGrid;
    }
    /**
     * A function to get the two dimensional array 'rowConstraint'
     * @return String[][]
     */
    public rowConstraint[][] getRowConstraint() {
        return rowConstraintGrid;
    }
    /**
     * A function to get the two dimensional array 'columnConstraint'
     * @return String[][]
     */
    public columnConstraint[][] getColumnConstraint() {
        return columnConstraintGrid;
    }

    public FutoshikiSquare[][] getSolutionGrid() {
        return solutionGrid;
    }
    
    /****************************************************************
     * 
     * For setup
     * 
     ****************************************************************/
    /**
     * A function to make all items in 'rowConstraint' equal to " ".
     */
    private void rowBlankStart(){
        for(int x =0; x<puzzleSize-1; x++){ 
            for(int y=0;y<puzzleSize;y++){ 
                rowConstraintGrid[x][y] = new rowConstraint(this, x, y, " ");
            }
        }
    }
    /**
     * A function to make all items in 'columnConstraint' equal to " ".
     */
    private void columnBlankStart(){
        for(int x =0; x<puzzleSize; x++){
            for(int y=0;y<puzzleSize-1;y++){
                columnConstraintGrid[x][y] = new columnConstraint(this, x, y, " ");
            }
        }
    }
    /**
     * A function to setup the 'futoshikiGrid' array with FutoshikiSquare objects all set to 0 and that can be edited
     */
    private void gridBlankStart(){
        for(int x =0; x<puzzleSize; x++){
            for(int y=0;y<puzzleSize;y++){
                futoshikiGrid[x][y] = new FutoshikiSquare(true, 0);
            }
        }
    }
}
