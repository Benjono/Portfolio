/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package futoshikipuzzle;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.Node;
import java.util.ArrayList;
import javafx.geometry.Pos;
import javafx.scene.text.TextAlignment;
import javafx.scene.control.ButtonBar.ButtonData;
import java.io.*;

/**
 *
 * @author admin
 */
public class FutoshikiGUI extends Application {
    private int size = 5;
    private int puzzleDifficulty = 5;
    private FutoshikiPuzzle fp;
    private Label[][] myLabels;
    private int helpCounter =0;
    /**
     * Start function for the GUI
     * @param primaryStage 
     */
    @Override
    public void start(Stage primaryStage) {
        GridPane futoshikiGrid = new GridPane(); 
        
        updateFutoshiki(futoshikiGrid); 
        
        VBox controls = new VBox(); 
        
        controlSetup(controls, futoshikiGrid); 
        
        BorderPane root = new BorderPane();
        root.setPrefSize(800, 600);
        root.setCenter(futoshikiGrid);
        root.setRight(controls);
        
        Scene scene = new Scene(root, 800, 600);
        
        primaryStage.setTitle("Futoshiki Puzzle Game");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    /**
     * Setup the controls for the GUI
     * @param vBox
     * @param futoshikiGrid 
     */
    private void controlSetup(VBox vBox, GridPane futoshikiGrid){
        TextField sizeOfGrid = new TextField("");
        sizeOfGrid.setPromptText("Size"); 
        sizeOfGrid.setPrefColumnCount(3); 
        
        TextField difficulty = new TextField(""); 
        difficulty.setPromptText("Difficulty"); 
        difficulty.setPrefColumnCount(5); 
        
        Button newGrid = new Button("New Puzzle"); 
        newGrid.setOnAction(e -> {if(sizeOfGrid.getText().matches("^[2-9]$") && Integer.parseInt(sizeOfGrid.getText())<10){ 
                size = Integer.parseInt(sizeOfGrid.getText());
                puzzleDifficulty = Integer.parseInt(difficulty.getText());
                sizeOfGrid.setText("");
                difficulty.setText("");
                updateFutoshiki(futoshikiGrid);
            }
        });
        
        Button Quit = new Button("Quit"); 
        Quit.setOnAction(e -> {responseToQuit(e);}); 
        
        Button getHelpBtn = new Button("Hint");
        getHelpBtn.setOnAction(e -> {getHelp();});
        
        TextField column = new TextField();
        column.setPromptText("Row"); 
        column.setPrefColumnCount(3); 
        TextField row = new TextField();
        row.setPromptText("Column"); 
        row.setPrefColumnCount(4); 
        
        Button getSquareHelpBtn = new Button("Hint at square: ");
        getSquareHelpBtn.setOnAction(e->{
            if(row.getText().matches("^[1-9]$")&&column.getText().matches("^[1-9]$")){
                fp.setSquare(Integer.parseInt(row.getText())-1, Integer.parseInt(column.getText())-1, fp.getSolutionInt(Integer.parseInt(row.getText())-1, Integer.parseInt(column.getText())-1), false);
            }
            updateAllLabels();
            updateChecks();
            });
        
        Button loadBtn = new Button("Load from Save");
        loadBtn.setOnAction(e->{loadSave(futoshikiGrid);});
        
        Button saveBtn = new Button("Save to file");
        saveBtn.setOnAction(e->{saveGame();});
        
        HBox helpControls = new HBox(getSquareHelpBtn, row, column);
        HBox newGridControls = new HBox();
        newGridControls.getChildren().addAll(newGrid, sizeOfGrid, difficulty);
        vBox.getChildren().addAll(newGridControls, getHelpBtn, helpControls,saveBtn, loadBtn, Quit);
    }
    /**
     * Dialogue response creation to pressing the quit button
     * @param e 
     */
    private void responseToQuit(ActionEvent e){
        Label responseLabel = new Label("Are you sure you want to quit?");
        ButtonType noButtonType = new ButtonType("No", ButtonData.NO);
        ButtonType yesButtonType = new ButtonType("Yes", ButtonData.YES);
        
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.getDialogPane().getButtonTypes().add(noButtonType);
        dialog.getDialogPane().getButtonTypes().add(yesButtonType);
        dialog.getDialogPane().setContent(responseLabel);
        
        boolean disabled = false; // computed based on content of text fields, for example
        dialog.getDialogPane().lookupButton(noButtonType).setDisable(disabled);
        dialog.getDialogPane().lookupButton(yesButtonType).setDisable(disabled);
        
        dialog.showAndWait().ifPresent(response -> {
            if (response==yesButtonType) {
                Label saveLabel = new Label("Would you like to save?");
                Dialog<ButtonType> saveDialog = new Dialog<>();
                saveDialog.getDialogPane().getButtonTypes().add(noButtonType);
                saveDialog.getDialogPane().getButtonTypes().add(yesButtonType);
                saveDialog.getDialogPane().setContent(saveLabel);
                
                saveDialog.getDialogPane().lookupButton(noButtonType).setDisable(disabled);
                saveDialog.getDialogPane().lookupButton(yesButtonType).setDisable(disabled);
                saveDialog.showAndWait().ifPresent(saveResponse -> {
                    if(saveResponse == yesButtonType){
                        saveGame();
                    }
                    leaveGame(e);
                });
            }
        });
    }
    /**
     * Dialogue response to a failure to save the file
     */
    private void cantSave(){
        Label responseLabel = new Label("Error Saving to file, aborting");
        ButtonType OKButtonType = new ButtonType("OK", ButtonData.OK_DONE);
        
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.getDialogPane().getButtonTypes().add(OKButtonType);
        dialog.getDialogPane().setContent(responseLabel);
        dialog.getDialogPane().lookupButton(OKButtonType).setDisable(false);
        
        dialog.showAndWait().ifPresent(response -> {});
    }
    /**
     * Dialogue box to close the window and offer commiserations
     * @param e 
     */
    private void leaveGame(ActionEvent e){
        Label Label = new Label("Better luck next time!");
        ButtonType okButtonType = new ButtonType("OK", ButtonData.OK_DONE);

        Dialog<ButtonType> dialogCommiserations = new Dialog<>();
        dialogCommiserations.getDialogPane().getButtonTypes().add(okButtonType);
        dialogCommiserations.getDialogPane().setContent(Label);
        boolean disabled = false;
        dialogCommiserations.getDialogPane().lookupButton(okButtonType).setDisable(disabled);

        dialogCommiserations.showAndWait().ifPresent(acknowledge -> {});
        ((Stage)(((Button)e.getSource()).getScene().getWindow())).close();
    }
    /**
     * Saves the game in it's current state
     * @return File
     */
    private File saveGame(){
        File file = new File ("saves/saveData.txt");
        try{
            BufferedWriter out = new BufferedWriter(new FileWriter(file)); 
            String text = getSaveText();
            out.write(text);
            out.close();
        } catch(Exception exception) {
            cantSave();
            System.out.println(exception.getClass());
        }
        return file;
    }
    /**
     * Generates save string
     * @return String
     */
    private String getSaveText(){
        String returnString = "";
        returnString+= size + "\r\n";
        for(int i = 0; i<size; i++){
            for(int j = 0; j<size; j++){
                returnString += fp.getSolutionInt(i, j);
            }
            returnString += "\r\n";
        }
        for(int i = 0; i<size; i++){
            for(int j = 0; j<size; j++){
                returnString += fp.getGridItem(i, j);
                if(fp.getGridObject(i, j).isEditable()){
                    returnString+="t";
                } else {
                    returnString += "f";
                }
            }
            returnString += "\r\n";
        }
        for(int i = 0; i<size-1; i++){
            for(int j = 0; j<size; j++){
                returnString+=fp.getColumnConstraintItem(j, i);
            }
            returnString += "\r\n";
        }
        for(int i = 0; i<size; i++){
            for(int j = 0; j<size-1; j++){
                returnString += fp.getRowConstraintItem(j, i);
            }
            returnString += "\r\n";
        }
        return returnString;
    }
    /**
     * Gets the file and tries to load it, generates a dialogue box on failure
     * @param fG 
     */
    private void loadSave(GridPane fG){
        File file = new File("saves/saveData.txt"); 
        BufferedReader br;
        String string = "";
        try{
            br = new BufferedReader(new FileReader(file)); 
            String str;
            while((str=br.readLine())!=null){
                string+=str;
            }
            applyLoadedFile(string, fG);
        } catch(Exception exception){
            System.out.println(exception.getClass());
            Label responseLabel = new Label("Error loading file, aborting.");
            ButtonType OKButtonType = new ButtonType("OK", ButtonData.OK_DONE);

            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.getDialogPane().getButtonTypes().add(OKButtonType);
            dialog.getDialogPane().setContent(responseLabel);
            dialog.getDialogPane().lookupButton(OKButtonType).setDisable(false);
            dialog.showAndWait().ifPresent(response -> {});
        }
    }
    /**
     * Applies the found and loaded file to a new futoshikiPuzzle, then generates the grid in the GridPane
     * @param string
     * @param fG 
     */
    private void applyLoadedFile(String string, GridPane fG){
        int count = 0; 
        int controller = 0;
        char[] charArrayForString = string.toCharArray();
        size = Integer.parseInt(""+charArrayForString[0]);
        
        fp = new FutoshikiPuzzle(size);
        count++;
        for(int i = 0; i<size; i++){
            for(int j = 0; j<size; j++){
                fp.setSolutionSquare(i, j, Integer.parseInt(""+charArrayForString[count-controller]));
                count++;
            }
        }
        for(int i=0; i<size;i++){
            for(int j=0;j<size;j++){
                fp.setSquare(i, j, Integer.parseInt(""+charArrayForString[count]), charArrayForString[count+1]=='t');
                count+=2;
            }
        }
        for(int i=0; i<size-1;i++){
            for(int j=0; j<size;j++){
                fp.setColumnConstraint(j, i, ""+charArrayForString[count]);
                count++;
            }
        }
        for(int i=0; i<size;i++){
            for(int j=0;j<size-1;j++){
                fp.setRowConstraint(j, i, ""+charArrayForString[count]);
                count++;
            }
        }
        loadGrid(fG);
        updateAllLabels();
    }
    /**
     * Modified function to remove the lines for generating a new grid
     * @param fG 
     */
    private void loadGrid(GridPane fG){
        fG.getChildren().clear();
        myLabels = new Label[size][size];
        for(int i = 0; i<size; i++){
            for (int j = 0; j<size; j++){
                myLabels[j][i] = createLabel(i, j);
            }
        }
        fG.getChildren().clear();
        int count = 0;
        int constraintCount=0;
        for(int i = 0; i<size; i++){
            for(int j = 0; j<size; j++){
                myLabels[j][i].setMinWidth(30);
                myLabels[j][i].setMinHeight(30);
                fG.add(myLabels[j][i], count%(size*2), count/size/2);
                count++;
                if(constraintCount%(size*4)<(size-1)*2){
                    Label temp = new Label(""+fp.getRowConstraintItem(constraintCount%(size*4)/2, constraintCount/(size*4)));
                    temp.setMinWidth(30);
                    temp.setMinHeight(30);
                    temp.setStyle("-fx-border-color:black;");
                    temp.setAlignment(Pos.CENTER);
                    fG.add(temp, constraintCount%(size*2)+1, constraintCount/size/2);
                }
                count++;
                constraintCount+=2;
            }
            if(count/(size*4)<size-1){
                for(int k = 0; k<size; k++){
                    Label temp = new Label(""+fp.getColumnConstraintItem(k, count/(size*4)));
                    temp.setMinWidth(30);
                    temp.setMinHeight(30);
                    temp.setStyle("-fx-border-color:black;");
                    temp.setAlignment(Pos.CENTER);
                    fG.add(temp, count%(size*2), count/size/2);
                    count+=2;
                }
            }
            constraintCount+=size*2;
        }
    }
    /**
     * Generates a the layout using a new futoshikiPuzzle based on the tempsize and difficulty selected
     * @param fG 
     */
    private void updateFutoshiki(GridPane fG){
        fG.getChildren().clear();
        fp = new FutoshikiPuzzle(size);
        fp.fillPuzzlewithLatinSquare(size, puzzleDifficulty);
        myLabels = new Label[size][size];
        for(int i = 0; i<size; i++){
            for (int j = 0; j<size; j++){
                myLabels[j][i] = createLabel(i, j);
            }
        }
        fG.getChildren().clear();
        int count = 0;
        int constraintCount=0;
        for(int i = 0; i<size; i++){
            for(int j = 0; j<size; j++){
                myLabels[j][i].setMinWidth(30);
                myLabels[j][i].setMinHeight(30);
                fG.add(myLabels[j][i], count%(size*2), count/size/2);
                count++;
                if(constraintCount%(size*4)<(size-1)*2){
                    Label temp = new Label(""+fp.getRowConstraintItem(constraintCount%(size*4)/2, constraintCount/(size*4)));
                    temp.setMinWidth(30);
                    temp.setMinHeight(30);
                    temp.setStyle("-fx-border-color:black;");
                    temp.setAlignment(Pos.CENTER);
                    fG.add(temp, constraintCount%(size*2)+1, constraintCount/size/2);
                }
                count++;
                constraintCount+=2;
            }
            if(count/(size*4)<size-1){
                for(int k = 0; k<size; k++){
                    Label temp = new Label(""+fp.getColumnConstraintItem(k, count/(size*4)));
                    temp.setMinWidth(30);
                    temp.setMinHeight(30);
                    temp.setStyle("-fx-border-color:black;");
                    temp.setAlignment(Pos.CENTER);
                    fG.add(temp, count%(size*2), count/size/2);
                    count+=2;
                }
            }
            constraintCount+=size*2;
        }
    }
    /**
     * Creates the label with the style and an OnMouseClicked event listener
     * @param j
     * @param i
     * @return Label
     */
    private Label createLabel(int j, int i){
        Label newLabel = new Label(""+fp.getGridItem(i, j));
        
        if(fp.getGridObject(i, j).isEditable()){
            newLabel.setStyle("-fx-border-color:black;");
        } else {
            newLabel.setStyle("-fx-border-color:blue;");
        }
        
        newLabel.setAlignment(Pos.CENTER);
        newLabel.setOnMouseClicked(e -> {
                    if(fp.getGridItem(i, j)!=size){
                        fp.setSquare(i,j,fp.getGridItem(i, j)+1, true);
                        updateAllLabels();
                        updateChecks();
                    } else {
                        fp.setSquare(i,j,0, true);
                        updateAllLabels();
                        updateChecks();
                    }
                    if(fp.isComplete()){
                        Label responseLabel = new Label("Success! Would you like to continue?");
                        ButtonType noButtonType = new ButtonType("No", ButtonData.NO);
                        ButtonType yesButtonType = new ButtonType("Yes", ButtonData.YES);

                        Dialog<ButtonType> dialog = new Dialog<>();
                        dialog.getDialogPane().getButtonTypes().add(noButtonType);
                        dialog.getDialogPane().getButtonTypes().add(yesButtonType);
                        dialog.getDialogPane().setContent(responseLabel);

                        boolean disabled = false; // computed based on content of text fields, for example
                        dialog.getDialogPane().lookupButton(noButtonType).setDisable(disabled);
                        dialog.getDialogPane().lookupButton(yesButtonType).setDisable(disabled);
                        
                        dialog.showAndWait().ifPresent(acknowledge -> {
                            if(acknowledge==noButtonType){
                                ((Stage)(((Button)e.getSource()).getScene().getWindow())).close();
                            } else {
                                greenBorder();
                            }
                        });
                    }
                });
        return newLabel;
    }
    /**
     * Turns all the borders green when the game is complete
     */
    private void greenBorder(){
        for(int i = 0; i<size; i++){
            for(int j = 0; j<size; j++){
                myLabels[i][j].setStyle("-fx-border-color:green;");
            }
        }
    }
    /**
     * Resets the board so that all tiles are the correct colours
     */
    private void setBlank(){
        for(int i = 0; i<size; i++){
            for(int j = 0; j<size; j++){
                if(fp.getGridObject(i, j).isEditable()){
                    myLabels[i][j].setStyle("-fx-border-color:black;");
                } else {
                    myLabels[i][j].setStyle("-fx-border-color:blue;");
                }
            }
        }
    }
    /**
     * Gets the errors and then sets all labels that represent numbers where errors occur to have red boarders
     */
    private void updateChecks(){
        ArrayList<AProblem> complaints = fp.isLegalReturnProblems();
        setBlank();
        if(complaints != null){
            for(AProblem i: complaints){
                int x = i.getX();
                int y = i.getY();
                int problem = i.getProblem();
                switch (problem){
                    case 1:
                        myLabels[x][y].setStyle("-fx-text-fill:red");
                        myLabels[x+1][y].setStyle("-fx-text-fill:red");
                        break;
                    case 2:
                        myLabels[x][y].setStyle("-fx-text-fill:red");
                        myLabels[x+1][y].setStyle("-fx-text-fill:red");
                        break;
                    case 3:
                        myLabels[x][y].setStyle("-fx-text-fill:red");
                        myLabels[x][y+1].setStyle("-fx-text-fill:red");
                        break;
                    case 4:    
                        myLabels[x][y].setStyle("-fx-text-fill:red");
                        myLabels[x][y+1].setStyle("-fx-text-fill:red");
                        break;
                    case 5:
                        int z = i.getSecondCoord();
                        myLabels[x][y].setStyle("-fx-text-fill:red");
                        myLabels[z][y].setStyle("-fx-text-fill:red");
                        break;
                    case 6:
                        z = i.getSecondCoord();
                        myLabels[x][y].setStyle("-fx-text-fill:red");
                        myLabels[x][z].setStyle("-fx-text-fill:red");
                        break;
                }
            }
        }
    }
    /**
     * Updates all the labels
     */
    private void updateAllLabels(){
        for(int i = 0; i<size; i++){
            for (int j = 0; j<size; j++){
                myLabels[j][i].setText(""+fp.getGridItem(j, i));
            }
        }
    }
    /**
     * Will set the top leftmost square to a known correct solution
     */
    private void getHelp(){
        boolean foundNotEqual = false;
        int countx = -1;
        int county = 0;
        do{
            countx++;
            if(countx==size){
                countx=0;
                county++;
            }
            if(fp.getSolutionInt(countx, county)!=fp.getGridItem(countx, county)){
                foundNotEqual = true;
            }
        }while (!foundNotEqual && (countx!=size-1||county!=size-1));
        fp.setSquare(countx, county, fp.getSolutionInt(countx, county), false);
        updateAllLabels();
        updateChecks();
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    public FutoshikiPuzzle returnFP(){
        return fp;
    }
}
