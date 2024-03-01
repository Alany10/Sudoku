package lab.lab4;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import lab.lab4.view.SudokuView;
import lab.lab4.model.SudokuUtilities;
import lab.lab4.view.Level;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        SudokuUtilities.SudokuLevel level = Level.newGame();
        SudokuView view = new SudokuView(level);

        VBox root = new VBox(view);
        Scene scene = new Scene(root);

        // Set the scene and show the stage
        primaryStage.setScene(scene);
        primaryStage.setTitle("Sudoku");
        primaryStage.setResizable(false);
        primaryStage.sizeToScene();
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}