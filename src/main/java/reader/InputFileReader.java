package reader;

import exception.MissingFileException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import static org.apache.logging.log4j.Level.INFO;

public class InputFileReader {
    static Logger logger = LogManager.getLogger();

    public static ArrayList<String> getFileData (String path) throws MissingFileException {
        ArrayList<String> stringData = new ArrayList<>();

        logger.log(INFO, "Using input string.");
        try {
            Files.lines(Paths.get(path), StandardCharsets.UTF_8).forEach(stringData::add);
        } catch (IOException e) {
            throw new MissingFileException("Can't find the file!");
        }

        return stringData;
    }

    public static ArrayList<String> getFileData () throws MissingFileException {
        ArrayList<String> stringData = new ArrayList<>();
        final String DATA_PATH = "data/input.txt"; //input file path

        logger.log(INFO, "Input string is empty. Using default.");
        try {
            Files.lines(Paths.get(DATA_PATH), StandardCharsets.UTF_8).forEach(stringData::add);
        } catch (IOException e) {
            throw new MissingFileException("Can't find the file!");
        }

        return stringData;
    }
}
