package lab.lab4.model;

import java.io.Serializable;

/**
 * Represents a cell in a Sudoku grid.
 */
public class Cell implements Serializable {
    private final int initialValue;     // The initial value of the cell.
    private final int solutionValue;    // The solution value of the cell.
    private int currentValue;          // The current value of the cell.

    /**
     * Initializes a new Sudoku cell with the given values.
     *
     * @param initialValue    The initial value of the cell.
     * @param solutionValue   The solution value of the cell.
     * @param currentValue   The current value of the cell.
     */
    Cell(int initialValue, int solutionValue, int currentValue) {
        this.initialValue = initialValue;
        this.solutionValue = solutionValue;
        this.currentValue = currentValue;
    }

    /**
     * Get the initial value of the cell.
     *
     * @return The initial value of the cell.
     */
    public int getInitialValue() {
        return initialValue;
    }

    /**
     * Get the solution value of the cell.
     *
     * @return The solution value of the cell.
     */
    public int getSolutionValue() {
        return solutionValue;
    }

    /**
     * Get the current value of the cell.
     *
     * @return The current value of the cell.
     */
    public int getCurrentValue() {
        return currentValue;
    }

    /**
     * Set the current value of the cell.
     *
     * @param currentValue The new eventual value to set for the cell.
     * @throws IllegalArgumentException if the provided number is invalid for a Sudoku cell.
     */
    public void setCurrentValue(int currentValue) {
        if (!isValidNumber(currentValue)) {
            throw new IllegalArgumentException("Wrong number: " + currentValue);
        }
        this.currentValue = currentValue;
    }

    private boolean isValidNumber(int number) {
        return number >= 0 && number <= 9;
    }
}