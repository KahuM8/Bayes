package bayes;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

class Parser {

    /**
     * Reads all the lines from the file
     * 
     * @param filename is the name of the file to read
     * @return a list of strings that contains all the lines from the file
     */
    public static List<String> readAllLines(String filename) {
        try {
            return Files.readAllLines(Paths.get(filename));
        } catch (IOException e) {
            System.err.println("ROL Failed: " + e);
        }
        return new ArrayList<String>();
    }

    /**
     * Parses the data from the file into a 2D array of instances
     * 
     * @param lines is the list of strings the read all lines method returns
     * @return a 2d array of instances
     */
    public static String[][] parseData(List<String> lines) {
        String[][] variables = new String[lines.size() - 1][];
        for (int i = 1; i < lines.size(); i++) {
            variables[i - 1] = lines.get(i).split(",");
        }
        return variables;
    }

    // to text file
    public static void toFile(String filename, String content) {
        try {
            File f = new File(filename);
            f.createNewFile();
            PrintStream out = new PrintStream(f);
            out.print(content);
            out.close();
        } catch (IOException e) {
            System.err.println("Saving failed " + e);
        }
    }
}