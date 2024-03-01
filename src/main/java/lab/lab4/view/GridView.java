package lab.lab4.view;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.TilePane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import lab.lab4.model.Cell;

import static lab.lab4.model.SudokuUtilities.*;

public class GridView {
    private Label[][] numberTiles; // the tiles/squares to show in the ui grid
    private TilePane numberPane;
    private int selectedRow;
    private int selectedColumn;

    public GridView() {
        this.selectedRow = this.selectedColumn = 0;
        numberTiles = new Label[GRID_SIZE][GRID_SIZE];
        initNumberTiles();
        numberPane = makeNumberPane();
        updateTileStyles();
    }

    public int getSelectedRow() {
        return selectedRow;
    }
    public int getSelectedColumn() {
        return selectedColumn;
    }
    public TilePane getNumberPane() {
        return numberPane;
    }

    // called by constructor (only)
    private final void initNumberTiles() {
        Font font = Font.font("Monospaced", FontWeight.NORMAL, 20);

        for (int row = 0; row < GRID_SIZE; row++) {
            for (int column = 0; column < GRID_SIZE; column++) {
                Label tile = new Label(); // data from model
                tile.setPrefWidth(48);
                tile.setPrefHeight(48);
                tile.setFont(font);
                tile.setAlignment(Pos.CENTER);
                tile.setOnMouseClicked(tileClickHandler); // add your custom event handler
                // add new tile to grid
                numberTiles[row][column] = tile;
            }
        }
    }

    private final TilePane makeNumberPane() {
        // Create the root tile pane
        TilePane root = new TilePane();
        root.setPrefColumns(SECTIONS_PER_ROW);
        root.setPrefRows(SECTIONS_PER_ROW);
        root.setStyle("-fx-border-color: black; -fx-border-width: 3.0px; -fx-background-color: white;");

        for (int srow = 0; srow < SECTIONS_PER_ROW; srow++) {
            for (int scol = 0; scol < SECTIONS_PER_ROW; scol++) {
                TilePane section = new TilePane();
                section.setPrefColumns(SECTION_SIZE);
                section.setPrefRows(SECTION_SIZE);
                section.setStyle("-fx-border-color: black; -fx-border-width: 0.5px;");

                // Add number tiles to this section
                for (int row = 0; row < SECTION_SIZE; row++) {
                    for (int col = 0; col < SECTION_SIZE; col++) {
                        section.getChildren().add(numberTiles[srow * SECTION_SIZE + row][scol * SECTION_SIZE + col]);
                    }
                }

                root.getChildren().add(section);
            }
        }

        return root;
    }

    public void updateGrid(Cell[][] cells){
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int column = 0; column < GRID_SIZE; column++) {
                if ((cells[row][column].getCurrentValue()) == 0){
                    numberTiles[row][column].setText(" ");
                }
                else {
                    if (cells[row][column].getInitialValue() == cells[row][column].getSolutionValue()) {
                        // If initial and solution values are equal, set the text to be thicker (bold)
                        numberTiles[row][column].setText(String.valueOf(cells[row][column].getCurrentValue()));

                        // Set the font thickness (weight) for the text
                        Font font = numberTiles[row][column].getFont();
                        numberTiles[row][column].setFont(Font.font(font.getFamily(), FontWeight.BOLD, font.getSize()));
                    }
                    else {
                        numberTiles[row][column].setText(String.valueOf(cells[row][column].getCurrentValue()));

                        Font font = numberTiles[row][column].getFont();
                        numberTiles[row][column].setFont(Font.font(font.getFamily(), FontWeight.NORMAL, font.getSize()));
                    }
                }
            }
        }
    }


    EventHandler<MouseEvent> tileClickHandler = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
            for (int row = 0; row < GRID_SIZE; row++) {
                for (int column = 0; column < GRID_SIZE; column++) {
                    if (event.getSource() == numberTiles[row][column]) {
                        selectedRow = row;
                        selectedColumn = column;
                        updateTileStyles();  // Update tile styles when a tile is clicked
                    }
                }
            }
        }
    };

    private void updateTileStyles() {
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int column = 0; column < GRID_SIZE; column++) {
                if (row == selectedRow && column == selectedColumn) {
                    numberTiles[row][column].setStyle("-fx-border-color: black; -fx-border-width: 0.5px; -fx-background-color: lightblue;");
                } else {
                    numberTiles[row][column].setStyle("-fx-border-color: black; -fx-border-width: 0.5px;");
                }
            }
        }
    }
}
