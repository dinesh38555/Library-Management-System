package services;

import java.io.*;

public class FileHandler {

    // Saves a serializable object to a file
    public static <T> void saveToFile(String filename, T data) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(data);
        } catch (IOException e) {
            System.err.println("Error saving to \"" + filename + "\": " + e.getMessage());
        }
    }

    // Loads a serializable object from a file
    @SuppressWarnings("unchecked")
    public static <T> T loadFromFile(String filename) {
        File file = new File(filename);
        if (!file.exists()) {
            return null;
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            return (T) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error loading from \"" + filename + "\": " + e.getMessage());
            return null;
        }
    }
}
