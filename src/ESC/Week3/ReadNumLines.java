package ESC.Week3;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Scanner;

import static ESC.Week3.CastVote.getPositiveInt;

/**
 * Created by HanWei on 9/2/2017.
 */
public class ReadNumLines {
    public static void main(String[] args) {
        int numFiles;
        String fileName;
        HashMap<String, Integer> fileLineMap = new HashMap<>();
        Scanner in = new Scanner(System.in);

        System.out.println("How many files do you want to check?");
        numFiles = getPositiveInt(in);

        for (int i = 0; i < numFiles; i++) {
            System.out.println("What is the pathname of the " + (i + 1) + "th file?");
            while (!Files.exists(Paths.get((fileName = in.next())))) {
                System.out.println("That file does not exist. Please enter a valid filepath.");
                fileName = in.next();
            }
            fileLineMap.put(fileName, getNumLines(fileName));
        }


        for (String name :
                fileLineMap.keySet()) {
            System.out.println(name + " contains " + fileLineMap.get(name) + " lines.");
        }
    }

    public static int getNumLines(String fileName) {
        int result = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                result++;
            }
        } catch (IOException x) {
            System.out.println("There was a problem reading the file.");
            System.err.format("IOException: %s%n", x);
        }
        return result;
    }
}
