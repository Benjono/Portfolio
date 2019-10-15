/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hanoigui;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import java.io.*;

/**
 *
 * @author Ben
 */
public class HanoiGUI extends Application {
    
    private Hanoi hanoi; //The game
    private int size; //The games size
    private Label[][] towers; //Labels to show the towers
    
    @Override
    public void start(Stage primaryStage) {
        GridPane hanoiGrid = new GridPane();
        
        updateGame(hanoiGrid);
        
        VBox controls = new VBox();
        
        controlSetup(controls,hanoiGrid);
        
        BorderPane root = new BorderPane();
        root.setPrefSize(800, 600);
        root.setCenter(hanoiGrid);
        root.setRight(controls);
        
        Scene scene = new Scene(root, 800, 600);
        
        primaryStage.setTitle("Hanoi Towers Game");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    public void setup(){
        
    }
    
    public void controlSetup(VBox controlArea, GridPane gameArea){
        TextField sizeOfGame = new TextField("");
        sizeOfGame.setPromptText("Size"); 
        sizeOfGame.setPrefColumnCount(3); 
        
        Button newGrid = new Button("New Puzzle"); 
        newGrid.setOnAction(e -> {if(sizeOfGame.getText().matches("^[2-9]$") && Integer.parseInt(sizeOfGame.getText())<10){ 
                size = Integer.parseInt(sizeOfGame.getText());
                sizeOfGame.setText("");
                updateGame(gameArea);
            }
        });
        
        Button Quit = new Button("Quit"); 
        Quit.setOnAction(e -> {responseToQuit(e);}); 
        
        controlArea.getChildren().addAll(sizeOfGame, newGrid, Quit);
    }
    
    public void updateGame(GridPane gameArea){
        
    }
    private void responseToQuit(ActionEvent e){
        Label responseLabel = new Label("Are you sure you want to quit?");
        ButtonType cancelButtonType = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        ButtonType yesButtonType = new ButtonType("Save and Quit", ButtonBar.ButtonData.YES);
        ButtonType noButtonType = new ButtonType("Quit",ButtonBar.ButtonData.NO);
        
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.getDialogPane().getButtonTypes().add(noButtonType);
        dialog.getDialogPane().getButtonTypes().add(yesButtonType);
        dialog.getDialogPane().getButtonTypes().add(cancelButtonType);
        dialog.getDialogPane().setContent(responseLabel);
        
        boolean disabled = false; // computed based on content of text fields, for example
        dialog.getDialogPane().lookupButton(noButtonType).setDisable(disabled);
        dialog.getDialogPane().lookupButton(yesButtonType).setDisable(disabled);
        dialog.getDialogPane().lookupButton(cancelButtonType).setDisable(disabled);
        
        dialog.showAndWait().ifPresent(response -> {
            if (response==yesButtonType) {
                //saveGame();
                leaveGame(e);
            } else if (response==noButtonType){
                leaveGame(e);
            }
        });
    }
    private void leaveGame(ActionEvent e){
        Label Label = new Label("Better luck next time!");
        ButtonType okButtonType = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);

        Dialog<ButtonType> dialogCommiserations = new Dialog<>();
        dialogCommiserations.getDialogPane().getButtonTypes().add(okButtonType);
        dialogCommiserations.getDialogPane().setContent(Label);
        boolean disabled = false;
        dialogCommiserations.getDialogPane().lookupButton(okButtonType).setDisable(disabled);

        dialogCommiserations.showAndWait().ifPresent(acknowledge -> {});
        ((Stage)(((Button)e.getSource()).getScene().getWindow())).close();
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
