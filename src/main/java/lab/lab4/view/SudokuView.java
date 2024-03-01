package lab.lab4.view;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import lab.lab4.controller.SudokuController;
import lab.lab4.model.SudokuModel;
import javafx.stage.Modality;
import lab.lab4.model.SudokuUtilities;
import java.io.File;
import java.util.Optional;


public class SudokuView extends BorderPane {
    private SudokuModel model;
    private GridView gridView;
    private Buttons buttons;
    private MenuHandler menuHandler;
    private SudokuController controller;

    public SudokuView(SudokuUtilities.SudokuLevel level){
        model = new SudokuModel(level);
        controller = new SudokuController(model, this);
        gridView = new GridView();
        buttons = new Buttons(controller, gridView);
        menuHandler = new MenuHandler(controller, model);

        this.setTop(menuHandler.getMenuBar());
        this.setRight(buttons.getRightButtonBox());
        this.setLeft(buttons.getLeftButtonBox());
        this.setCenter(gridView.getNumberPane());
        this.setPadding(new Insets(10, 10, 10, 10));

        updateView();
    }

    public void updateView(){
        gridView.updateGrid(model.getValues());
    }

    public void check(boolean rightWay) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Update Status");
        alert.setHeaderText(null);

        if (rightWay) {
            alert.setContentText("You are on the right way!");
        } else {
            alert.setContentText("You are not on the right way!");
        }

        ButtonType okButton = new ButtonType("OK");
        alert.getButtonTypes().setAll(okButton);

        alert.showAndWait();  // Show and wait for the alert to be closed
    }

    public void wrongCell(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Cannot Change Cell");
        alert.setHeaderText(null);
        alert.setContentText("The chosen cell can't be changed.");
        alert.showAndWait();
    }

    public File saveFile() {
        Stage primaryStage = (Stage) getScene().getWindow();
        File initialDirectory = new File("C:\\Java\\IdeaProjects\\Lab4\\src\\main\\resources");

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Game");
        fileChooser.setInitialDirectory(initialDirectory);
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Saved Games", "*.sudoku"));

        return fileChooser.showSaveDialog(primaryStage);
    }

    public File loadFile() {
        Stage primaryStage = (Stage) getScene().getWindow();
        File initialDirectory = new File("C:\\Java\\IdeaProjects\\Lab4\\src\\main\\resources");

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Load Game");
        fileChooser.setInitialDirectory(initialDirectory);
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Saved Games", "*.sudoku"));

        return fileChooser.showOpenDialog(primaryStage);
    }

    public void doneGame() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Game Status");

        String contentText;
        if (model.solvedGame()) {
            contentText = "Congratulations! You solved the game. New Game?";
        } else {
            contentText = "Unfortunately, you didn't solve the game. New Game?";
        }

        alert.setContentText(contentText);
        alert.setHeaderText(null);
        alert.initModality(Modality.APPLICATION_MODAL);

        ButtonType yesButton = new ButtonType("Yes", ButtonBar.ButtonData.YES);
        ButtonType noButton = new ButtonType("No", ButtonBar.ButtonData.NO);

        alert.getButtonTypes().setAll(yesButton, noButton);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == yesButton) {
            controller.handleNewGame();
        } else {
            // Close the application
            exit();
        }
    }

    public void newGame(){
        SudokuUtilities.SudokuLevel level = Level.newGame();
        // Pass the selected level to the controller for a new game
        controller.handleNewGame(level);
    }

    public void exit(){
        Stage primaryStage = (Stage) getScene().getWindow();
        primaryStage.close();
    }

}

