package lab.lab4.view;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import lab.lab4.controller.SudokuController;

public class Buttons {
    private Button[] numberButtons;
    private SudokuController controller;
    private GridView grid;

    public Buttons(SudokuController controller, GridView grid) {
        this.grid = grid;
        this.controller = controller;
        this.numberButtons = initializeButtons();
    }

    private Button[] initializeButtons() {
        numberButtons = new Button[12];  // Changed size to accommodate 12 buttons

        for (int i = 0; i < 9; i++) {
            numberButtons[i] = new Button(String.valueOf(i+1));
            numberButtons[i].setOnMouseClicked(numberButtonClickHandler);
        }

        numberButtons[9] = new Button("C");
        numberButtons[9].setOnMouseClicked(clearButtonClickHandler);

        // Add "Check" button
        numberButtons[10] = new Button("Check");
        numberButtons[10].setOnMouseClicked(checkButtonClickHandler);

        // Add "Hint" button
        numberButtons[11] = new Button("Hint");
        numberButtons[11].setOnMouseClicked(hintButtonClickHandler);
        return numberButtons;
    }

    public VBox getRightButtonBox() {
        VBox buttonBox = new VBox(10);  // 10 pixels spacing between buttons
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setPadding(new Insets(10));  // Add padding

        for (int i = 0; i < 10; i++) {
            buttonBox.getChildren().add(numberButtons[i]);
        }

        return buttonBox;
    }

    public VBox getLeftButtonBox() {
        VBox buttonBox = new VBox(10);  // 10 pixels spacing between buttons
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setPadding(new Insets(10));  // Add padding

        for (int i = 10; i < 12; i++) {
            buttonBox.getChildren().add(numberButtons[i]);
        }

        return buttonBox;
    }

    private final EventHandler<MouseEvent> numberButtonClickHandler = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
            Button sourceButton = (Button) event.getSource();
            int number = Integer.parseInt(sourceButton.getText());
            controller.handleFillCell(number, grid.getSelectedRow(), grid.getSelectedColumn());
        }
    };

    private final EventHandler<MouseEvent> clearButtonClickHandler = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
            controller.handleClearCell(grid.getSelectedRow(), grid.getSelectedColumn());
        }
    };

    private final EventHandler<MouseEvent> checkButtonClickHandler = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
            controller.handleCheck();
        }
    };

    private final EventHandler<MouseEvent> hintButtonClickHandler = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
            controller.handleHint();
        }
    };
}
