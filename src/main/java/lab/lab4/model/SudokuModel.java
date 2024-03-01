package lab.lab4.model;

import java.io.File;
import java.io.IOException;
import java.util.Random;

import static lab.lab4.model.SudokuUtilities.GRID_SIZE;

/**
 * SudokuModel manages the game's data and logic.
 */
public class SudokuModel {
    private Cell[][] values;
    private SudokuUtilities.SudokuLevel level;

    /**
     * Creates a new instance of SudokuModel with a given difficulty level.
     *
     * @param level The difficulty level for the game.
     */
    public SudokuModel(SudokuUtilities.SudokuLevel level){
        this.level = level;
        values = generateValues(SudokuUtilities.generateSudokuMatrix(this.level));
    }

    /**
     * Retrieves the current difficulty level.
     *
     * @return The current difficulty level.
     */
    public SudokuUtilities.SudokuLevel getLevel(){
        return this.level;
    }

    /**
     * Sets the current difficulty level.
     *
     * @param level The new difficulty level to set.
     */
    public void setLevel(SudokuUtilities.SudokuLevel level){
        this.level = level;
    }

    /**
     * Retrieves a copy of the Sudoku grid values.
     *
     * @return A copy of the Sudoku grid values.
     */
    public Cell[][] getValues() {
        Cell[][] copyValues = new Cell[GRID_SIZE][GRID_SIZE];

        // Copy the values from the original array to the new array
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int column = 0; column < GRID_SIZE; column++) {
                Cell copyCell = new Cell(values[row][column].getInitialValue(), values[row][column].getSolutionValue(), values[row][column].getCurrentValue());
                copyValues[row][column] = copyCell;
            }
        }

        return copyValues;
    }

    private Cell[][] generateValues(int[][][] sudokuMatrix){
        int[][] initialValues = new int[GRID_SIZE][GRID_SIZE];
        int[][] solutionValues = new int[GRID_SIZE][GRID_SIZE];
        Cell[][] values = new Cell[GRID_SIZE][GRID_SIZE];

        for (int row = 0; row < GRID_SIZE; row++){
            for (int column = 0; column < GRID_SIZE; column++){
                initialValues[row][column] = sudokuMatrix[row][column][0];
                solutionValues[row][column] = sudokuMatrix[row][column][1];
                values[row][column] = new Cell(initialValues[row][column], solutionValues[row][column], initialValues[row][column]);
            }
        }

        return values;
    }

    /**
     * Generates new game values based on the specified difficulty level.
     *
     * @param level The difficulty level for the new game.
     */
    public void generateNewGame(SudokuUtilities.SudokuLevel level) {
        values = generateValues(SudokuUtilities.generateSudokuMatrix(level));
    }

    /**
     * Sets the loaded game values and determines the level based on the values.
     * Used to set a game from a deSerialized file.
     *
     * @param values The loaded game values.
     */
    public void setLoadedGame(Cell[][] values){
        this.values = values;
        this.level = getLoadedLevel(this.values);
    }


    private SudokuUtilities.SudokuLevel getLoadedLevel(Cell[][] values){
        int nrOfInitialValues = 0;

        for (int row = 0; row < GRID_SIZE; row++){
            for (int column = 0; column < GRID_SIZE; column++){
                if (values[row][column].getInitialValue() ==  values[row][column].getSolutionValue()){
                    nrOfInitialValues++;
                }
            }
        }

        if (nrOfInitialValues == 31){
            return SudokuUtilities.SudokuLevel.EASY;
        }
        else if (nrOfInitialValues == 26){
            return SudokuUtilities.SudokuLevel.MEDIUM;
        }
        else {
            return SudokuUtilities.SudokuLevel.HARD;
        }
    }

    /**
     * Fills a cell in the Sudoku grid with a number if the cell is empty.
     *
     * @param number The number to fill the cell with.
     * @param row The row of the cell.
     * @param column The column of the cell.
     * @throws IllegalArgumentException if the specified cell is not empty.
     */

    public void fillCell(int number, int row, int column) throws IllegalArgumentException{
        if (!isValidCell(row, column)){
            throw new IllegalArgumentException("Wrong cell: " + row + ", " + column);
        }
        values[row][column].setCurrentValue(number);

    }


    private boolean isValidCell(int row, int column) throws IllegalArgumentException{
        return values[row][column].getInitialValue() == 0;
    }

    /**
     * Clears a cell in the Sudoku grid if it is not initially filled.
     *
     * @param row The row of the cell.
     * @param column The column of the cell.
     * @throws IllegalArgumentException if the specified cell was given from the beginning.
     */
    public void clearCell(int row, int column) throws IllegalArgumentException{
        if (!isValidCell(row, column)){
            throw new IllegalArgumentException("Wrong cell: " + row + ", " + column);
        }
        values[row][column].setCurrentValue(0);
    }

    /**
     * Clears all cells in the Sudoku grid that are not initially filled.
     * Doesn't clear the cells that were given from the beginning.
     */
    public void clearAllCells(){
        for (int row = 0; row < GRID_SIZE; row++){
            for (int column = 0; column < GRID_SIZE; column++){
                values[row][column].setCurrentValue(values[row][column].getInitialValue());
            }
        }
    }

    /**
     * Checks if the Sudoku grid is solved correctly so far.
     *
     * @return true if the grid is solved correctly, false otherwise.
     */
    public boolean check(){
        for (int row = 0; row < GRID_SIZE; row++){
            for (int column = 0; column < GRID_SIZE; column++){
                if(values[row][column].getCurrentValue() != values[row][column].getSolutionValue()
                && values[row][column].getCurrentValue() != 0){
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Checks if all cells in the Sudoku grid are filled.
     *
     * @return true if all cells are filled, false otherwise.
     */
    public boolean isFilled(){
        for (int row = 0; row < GRID_SIZE; row++){
            for (int column = 0; column < GRID_SIZE; column++){
                if(values[row][column].getCurrentValue() == 0){
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Provides a hint for the Sudoku grid by filling an empty cell with a solution value.
     *
     * @throws IllegalStateException if the grid is already completely filled.
     */
    public void hint() throws IllegalStateException{
        if (isFilled()){
            throw new IllegalStateException("You don't need a hint");
        }
        boolean done = false;
        Random random = new Random();
        int row, column;
        while (!done){
            row = random.nextInt(9);
            column = random.nextInt(9);
            if(values[row][column].getCurrentValue() == 0){
                values[row][column].setCurrentValue(values[row][column].getSolutionValue());
                done = true;
            }
        }
    }

    /**
     * Checks if the Sudoku grid is solved correctly.
     *
     * @return true if the grid is solved correctly, false otherwise.
     */
    public boolean solvedGame(){
        for (int row = 0; row < GRID_SIZE; row++){
            for (int column = 0; column < GRID_SIZE; column++){
                if(values[row][column].getCurrentValue() != values[row][column].getSolutionValue()){
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Serializes the Sudoku grid data to a file.
     *
     * @param data The Sudoku grid data to serialize.
     * @param fileName The name of the file to save the data.
     * @throws IOException if an I/O error occurs during serialization.
     */
    public void serialize(Cell[][] data, String fileName) throws IOException {
        File file = new File(fileName);
        CellsFileIO.serializeToFile(data, file);
    }

    /**
     * Deserializes the Sudoku grid data from a file.
     *
     * @param filename The name of the file to load the data from.
     * @return The deserialized Sudoku grid data.
     * @throws IOException if an I/O error occurs during deserialization.
     * @throws ClassNotFoundException if the class of the serialized object cannot be found.
     */
    public Cell[][] deSerialize(String filename) throws IOException, ClassNotFoundException {
        File file = new File(filename);
        Cell[][] data = CellsFileIO.deSerializeFromFile(file);
        return data;
    }
}
