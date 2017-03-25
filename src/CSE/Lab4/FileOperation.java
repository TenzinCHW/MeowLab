package CSE.Lab4;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;

public class FileOperation {
    private static File currentDirectory = new File(System.getProperty("user.dir"));

    public static void main(String[] args) throws IOException {

        String commandLine;

        BufferedReader console = new BufferedReader
                (new InputStreamReader(System.in));

        while (true) {
            // read what the user entered
            System.out.print("jsh>");
            commandLine = console.readLine();

            // clear the space before and after the command line
            commandLine = commandLine.trim();

            // if the user entered a return, just loop again
            if (commandLine.equals("")) {
                continue;
            }
            // if exit or quit
            else if (commandLine.equalsIgnoreCase("exit") | commandLine.equalsIgnoreCase("quit")) {
                System.exit(0);
            }

            // check the command line, separate the words
            String[] commandStr = commandLine.split(" ");
            ArrayList<String> command = new ArrayList<String>();
            for (int i = 0; i < commandStr.length; i++) {
                command.add(commandStr[i]);
            }

            // TODO: implement code to handle create here
            switch (commandStr[0]) {
                case "create":
                    Java_create(currentDirectory, commandStr[1]);
                    continue;
                case "delete":
                    Java_delete(currentDirectory, commandStr[1]);
                    continue;
                case "display":
                    Java_cat(currentDirectory, commandStr[1]);
                    continue;
                case "list":
                    switch (commandStr.length) {
                        case 1:
                            Java_ls(currentDirectory, "normal", "name");
                            continue;
                        case 2:
                            Java_ls(currentDirectory, commandStr[1], "name");
                            continue;
                        case 3:
                            Java_ls(currentDirectory, commandStr[1], commandStr[2]);
                    }
                    continue;
                case "find":
                    findFile(currentDirectory, currentDirectory.getName(), commandStr[1]);
                    continue;
                case "tree":
                    switch (commandStr.length) {
                        case 1:
                            Java_tree(currentDirectory, 0, "name");
                            continue;
                        case 2:
                            if (commandStr[1].matches("\\d"))
                                Java_tree(currentDirectory, Integer.parseInt(commandStr[1]), Integer.parseInt(commandStr[1]), "name");
                            else Java_tree(currentDirectory, 0, commandStr[1]);
                            continue;
                        case 3:
                            Java_tree(currentDirectory, Integer.parseInt(commandStr[1]), Integer.parseInt(commandStr[1]), commandStr[2]);
                            continue;
                    }
            }

            // other commands
            ProcessBuilder pBuilder = new ProcessBuilder(command);
            pBuilder.directory(currentDirectory);
            try {
                Process process = pBuilder.start();
                // obtain the input stream
                InputStream is = process.getInputStream();
                InputStreamReader isr = new InputStreamReader(is);
                BufferedReader br = new BufferedReader(isr);

                // read what is returned by the command
                String line;
                while ((line = br.readLine()) != null)
                    System.out.println(line);

                // close BufferedReader
                br.close();
            }
            // catch the IOexception and resume waiting for commands
            catch (IOException ex) {
                System.out.println(ex);
                continue;
            }
        }
    }

    /**
     * Create a file
     *
     * @param dir  - current working directory
     * @param name - name of the file to be created
     */
    public static void Java_create(File dir, String name) {
        String newFileName = dir.getAbsolutePath() + "/" + name;
        File newFile = new File(newFileName);
        try {
            newFile.createNewFile();
        } catch (IOException e) {
            System.out.println("This file already exists.");
        }
    }

    /**
     * Delete a file
     *
     * @param dir  - current working directory
     * @param name - name of the file to be deleted
     */
    public static void Java_delete(File dir, String name) {
        String newFileName = dir.getAbsolutePath() + "/" + name;
        File newFile = new File(newFileName);
        newFile.delete();
    }

    /**
     * Display the file
     *
     * @param dir  - current working directory
     * @param name - name of the file to be displayed
     */
    public static void Java_cat(File dir, String name) {
        String newFileName = dir.getAbsolutePath() + "/" + name;
        File newFile = new File(newFileName);
        try {
            BufferedReader reader = new BufferedReader(new FileReader(newFile));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.out.println("The file does not exist.");
        }
    }

    /**
     * Function to sort the file list
     *
     * @param list        - file list to be sorted
     * @param sort_method - control the sort type
     * @return sorted list - the sorted file list
     */
    private static File[] sortFileList(File[] list, String sort_method) {
        // sort the file list based on sort_method
        // if sort based on name
        if (sort_method.equalsIgnoreCase("name")) {
            Arrays.sort(list, new Comparator<File>() {
                public int compare(File f1, File f2) {
                    return (f1.getName()).compareTo(f2.getName());
                }
            });
        } else if (sort_method.equalsIgnoreCase("size")) {
            Arrays.sort(list, new Comparator<File>() {
                public int compare(File f1, File f2) {
                    return Long.valueOf(f1.length()).compareTo(f2.length());
                }
            });
        } else if (sort_method.equalsIgnoreCase("time")) {
            Arrays.sort(list, new Comparator<File>() {
                public int compare(File f1, File f2) {
                    return Long.valueOf(f1.lastModified()).compareTo(f2.lastModified());
                }
            });
        }
        return list;
    }

    /**
     * List the files under directory
     *
     * @param dir            - current directory
     * @param display_method - control the list type
     * @param sort_method    - control the sort type
     */
    public static void Java_ls(File dir, String display_method, String sort_method) {
        File[] files = sortFileList(dir.listFiles(), sort_method);
        int maxSzName = 0;
        int maxSzSize = 0;
        File lookingAt;
        for (int i = 0; i < files.length; i++) {
            lookingAt = files[i];
            if (lookingAt.getName().length() > maxSzName) maxSzName = lookingAt.getName().length();
            if (getNumDigits(lookingAt.length()) > maxSzSize) maxSzSize = getNumDigits(lookingAt.length());
        }
        if (display_method.equals("property")) {
            for (int i = 0; i < files.length; i++) {
                printFile(files[i], maxSzName, maxSzSize);
            }
        } else {
            for (int i = 0; i < files.length; i++) {
                System.out.println(files[i].getName());
            }
        }
    }

    /**
     * Find files based on input string
     *
     * @param dir  - current working directory
     * @param name - input string to find in file's name
     * @return flag - whether the input string is found in this directory and its subdirectories
     */
    public static boolean Java_find(File dir, String name) {
        boolean flag = false;
        File files[] = dir.listFiles();
        for (int i = 0; i < files.length; i++) {
            if (files[i].isDirectory()) {
                if (Java_find(files[i], name)) {
                    flag = true;
                }
            } else if (files[i].getName().contains(name)) {
                flag = true;
            }
        }
        return flag;
    }

    /**
     * Print file structure under current directory in a tree structure
     *
     * @param dir         - current working directory
     * @param depth       - maximum sub-level file to be displayed
     * @param sort_method - control the sort type
     */
    public static void Java_tree(File dir, int depth, int currentDepth, String sort_method) {
        if (depth < 1) return;
        File files[] = sortFileList(dir.listFiles(), sort_method);
        for (int i = 0; i < files.length; i++) {
            printSpace(3 * (depth - currentDepth)); // currentDepth is initially == to depth
            if (files[i].isDirectory()) {
                System.out.println(files[i].getName());
                if (currentDepth > 1) Java_tree(files[i], depth, depth - 1, sort_method);
            } else {
                System.out.println(files[i].getName());
            }
        }
    }

    public static void Java_tree(File dir, int currentDepth, String sort_method) {
        File files[] = sortFileList(dir.listFiles(), sort_method);
        for (int i = 0; i < files.length; i++) {
            printSpace(3 * currentDepth);   // currentDepth starts at 0
            if (files[i].isDirectory()) {
                System.out.println(files[i].getName());
                Java_tree(files[i], currentDepth + 1, sort_method);
            } else {
                System.out.println(files[i].getName());
            }
        }
    }

    public static int getNumDigits(long number) {
        int digits = 1;
        while (number > 10) {
            number /= 10;
            digits += 1;
        }
        return digits;
    }

    private static void printFile(File file, int maxName, int maxSz) {
        System.out.print(file.getName());
        printSpace(maxName - file.getName().length() + 5);
        System.out.print("Size: " + file.length());
        printSpace(maxSz - getNumDigits(file.length()) + 5);
        Date fileDate = new Date(file.lastModified());
        System.out.println("Last Modified: " + fileDate.toString());
    }

    private static void printSpace(int number) {
        for (int i = 0; i < number; i++) {
            System.out.print(" ");
        }
    }

    private static void findFile(File dir, String path, String name) {
        File files[] = dir.listFiles();
        for (int i = 0; i < files.length; i++) {
            if (files[i].isDirectory()) {
                if (Java_find(files[i], name)) {
                    findFile(files[i], path + "/" + files[i].getName(), name);
                }
            } else if (files[i].getName().contains(name)) {
                System.out.println(path + "/" + files[i].getName());
            }
        }
    }
}