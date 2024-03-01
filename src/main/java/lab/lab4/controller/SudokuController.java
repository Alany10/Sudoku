package lab.lab4.controller;

import lab.lab4.model.Cell;
import lab.lab4.model.SudokuModel;
import lab.lab4.model.SudokuUtilities;
import lab.lab4.view.SudokuView;

import java.io.File;
import java.io.IOException;

public class SudokuController {
    private SudokuModel model;
    private SudokuView view;

    public SudokuController(SudokuModel model, SudokuView view){
        this.model = model;
        this.view = view;
    }

    public void handleFillCell(int number, int row, int column){
        try {
            model.fillCell(number, row, column);
        }
        catch (IllegalArgumentException e){
            view.wrongCell();
        }
        if (model.isFilled()){
            view.doneGame();
        }
            view.updateView();
    }

    public void handleClearCell(int row, int column){
        try {
            model.clearCell(row, column);
        }
        catch (IllegalArgumentException e){
            view.wrongCell();
        }
        view.updateView();
    }

    public void handleHint(){
        model.hint();
        if (model.isFilled()) {
            view.doneGame();
        }
            view.updateView();
    }

    public void handleCheck(){
        if (model.check()){
            view.check(true);
        }
        else{
            view.check(false);
        }
    }

    public void handleClear(){
        model.clearAllCells();
        view.updateView();
    }

    public void handleSaveGame() throws IOException {
        File chosenFile = view.saveFile();

        if (chosenFile != null) {
            model.serialize(model.getValues(), chosenFile.getAbsolutePath());
        }
    }

    public void handleLoadGame() throws IOException, ClassNotFoundException {
        File chosenFile = view.loadFile();  // Use LoadFile to get the chosen file

        if (chosenFile != null) {
            Cell[][] loadedData = model.deSerialize(chosenFile.getAbsolutePath());
            model.setLoadedGame(loadedData);
            view.updateView();
        }
    }

    public void handleNewGame(SudokuUtilities.SudokuLevel level){
        model.setLevel(level);
        model.generateNewGame(model.getLevel());
        view.updateView();
    }

    public void handleNewGame(){
        view.newGame();
    }

    public void handleExit(){
        view.exit();
    }
}

