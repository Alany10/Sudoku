package lab.lab4.view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;

import javafx.scene.control.*;
import lab.lab4.model.SudokuModel;
import lab.lab4.controller.SudokuController;

import java.io.IOException;

public class MenuHandler {
    private SudokuController controller;
    private SudokuModel model;
    private MenuBar menuBar;

    public MenuHandler(SudokuController controller, SudokuModel model) {
        this.model = model;
        this.controller = controller;
        this.menuBar = createMenuBar();
    }

    public MenuBar getMenuBar() {
        return menuBar;
    }

    private MenuBar createMenuBar() {
        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().addAll(createFileMenu(), createGameMenu(), createHelpMenu());
        return menuBar;
    }

    private Menu createFileMenu() {
        Menu fileMenu = new Menu("File");
        MenuItem loadGameMenuItem = new MenuItem("Load Game");
        MenuItem saveGameMenuItem = new MenuItem("Save Game");
        MenuItem exitMenuItem = new MenuItem("Exit");

        fileMenu.getItems().addAll(loadGameMenuItem, saveGameMenuItem, exitMenuItem);

        loadGameMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    controller.handleLoadGame();
                } catch (IOException | ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        saveGameMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    controller.handleSaveGame();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        exitMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                controller.handleExit();
            }
        });

        return fileMenu;
    }


    private Menu createGameMenu() {
        Menu gameMenu = new Menu("Game");
        MenuItem sameLevelMenuItem = new MenuItem("Same Level");
        MenuItem otherLevelMenuItem = new MenuItem("Other Level");

        gameMenu.getItems().addAll(sameLevelMenuItem, otherLevelMenuItem);

        sameLevelMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                controller.handleNewGame(model.getLevel());
            }
        });

        otherLevelMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                    controller.handleNewGame();
            }
        });


        return gameMenu;
    }

    private Menu createHelpMenu() {
        Menu helpMenu = new Menu("Help");
        MenuItem clearMenuItem = new MenuItem("Clear");
        MenuItem checkMenuItem = new MenuItem("Check");
        MenuItem infoMenuItem = new MenuItem("Info");

        helpMenu.getItems().addAll(clearMenuItem, checkMenuItem, infoMenuItem);

        clearMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                controller.handleClear();
            }
        });

        checkMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                controller.handleCheck();
            }
        });

        infoMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                showInfo();
            }
        });

        return helpMenu;
    }

    private void showInfo() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Sudoku Rules");
        alert.setHeaderText(null);
        alert.setContentText(
                "Sudoku is a number puzzle game based on three key rules:\n" +
                "1. Unique numbers in each row (1-9, no repeats in a row).\n" +
                "2. Unique numbers in each column (1-9, no repeats in a column).\n" +
                "3. Unique numbers in each 3x3 box (1-9, no repeats in a box).\n\n" +
                "To solve the puzzle, fill in the grid with numbers while following these rules.\n\n" +
                "Note: Numbers provided at the beginning cannot be changed.\n" +
                "Good luck and enjoy the game!"
        );

        alert.showAndWait();
    }
}
