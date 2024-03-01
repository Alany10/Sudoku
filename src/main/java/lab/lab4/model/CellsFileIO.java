package lab.lab4.model;

import java.io.*;

/**
 * Provides methods to serialize and deserialize a two-dimensional array
 * of Sudoku cell objects to and from a file.
 */
public class CellsFileIO {
    /**
     * Serializes the given two-dimensional array of Sudoku cells to a file.
     *
     * @param data     The Sudoku cell data to be serialized.
     * @param fileName The file to which the data will be saved.
     * @throws IOException if an I/O error occurs during serialization.
     */
    public static void serializeToFile(Cell[][] data, File fileName) throws IOException {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(fileName))) {
            out.writeObject(data);
        }
    }

    /**
     * Deserializes a two-dimensional array of Sudoku cells from a file.
     *
     * @param fileName The file from which the data will be read.
     * @return The deserialized Sudoku cell data.
     * @throws IOException if an I/O error occurs during deserialization.
     * @throws ClassNotFoundException if the class of the serialized object cannot be found.
     */
    public static Cell[][] deSerializeFromFile(File fileName) throws IOException, ClassNotFoundException {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(fileName))) {
            Cell[][] data = (Cell[][]) in.readObject();
            return data;
        }
    }

    private CellsFileIO() {}
}
