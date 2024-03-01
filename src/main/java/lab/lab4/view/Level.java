package lab.lab4.view;

import javafx.scene.control.ChoiceDialog;
import lab.lab4.model.SudokuUtilities;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class Level {
    public static SudokuUtilities.SudokuLevel newGame() {
        List<String> choices = Arrays.asList("Easy", "Medium", "Hard");

        ChoiceDialog<String> dialog = new ChoiceDialog<>("Easy", choices);
        dialog.setTitle("Choose Difficulty");
        dialog.setHeaderText("Choose a difficulty level:");
        dialog.setContentText("Level:");

        Optional<String> result = dialog.showAndWait();

        if (result.isPresent()) {
            String selectedLevel = result.get();
            SudokuUtilities.SudokuLevel level;

            // Set the SudokuLevel based on the user's choice
            if (selectedLevel.equals("Easy")) {
                level = SudokuUtilities.SudokuLevel.EASY;
            } else if (selectedLevel.equals("Medium")) {
                level = SudokuUtilities.SudokuLevel.MEDIUM;
            } else {
                level = SudokuUtilities.SudokuLevel.HARD;
            }

            // Pass the selected level to the controller for a new game
            return level;
        }

        return SudokuUtilities.SudokuLevel.EASY;  // Default to EASY if no selection
    }

    private Level() {}
}
