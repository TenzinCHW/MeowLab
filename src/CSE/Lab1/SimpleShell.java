package CSE.Lab1;

import java.io.*;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SimpleShell {
    static File pwd = new File(System.getProperty("user.dir"));
    static ProcessBuilder builder = new ProcessBuilder();

    public static void main(String[] args) throws IOException {
        String commandLine;
        ArrayList<String> commands = new ArrayList<String>();
        BufferedReader console = new BufferedReader(new InputStreamReader(System.in));
        InputStream stream;
        InputStreamReader reader;
        BufferedReader buffered;
        Process process;
        ArrayList<String> history = new ArrayList<String>();


        while (true) {
            // read what the user entered
            System.out.print("jsh>");
            commandLine = console.readLine();
            history.add(commandLine);
            builder.directory(pwd);


            if (commandLine.equals("!!")) {
                if (history.size() > 1) {
                    history.remove(history.size() - 1);
                    commandLine = history.get(history.size() - 1);
                    commands = getCommand(commandLine);
                    if (commandLine.startsWith("cd") && commandLine.length() != 3) {
                        changeDir(commandLine);
                        continue;
                    }
                } else {
                    System.out.println("Die liao. Not enuff history.");
                }
            } else if (isInt(commandLine)) {
                history.remove(history.size() - 1);
                if (history.size() > Integer.parseInt(commandLine) && Integer.parseInt(commandLine) >= 0) {
                    commandLine = history.get(Integer.parseInt(commandLine));
                    history.add(commandLine);
                    commands = getCommand(commandLine);
                    if (commandLine.startsWith("cd") && commandLine.length() != 3) {
                        changeDir(commandLine);
                        continue;
                    }
                } else {
                    if (Integer.parseInt(commandLine) < 0) {
                        System.out.println("History don't go so far back.");
                    } else {
                        System.out.println("Die liao. Not enuff history.");
                    }
                    continue;
                }
            } else if (commandLine.equals("exit")) {
                System.out.println("BYE!");
                System.exit(0);
            } else if (commandLine.equals("")) {
                history.remove("");
                continue;
            } else if (commandLine.equals("history")) {
                history.remove(history.size() - 1);
                for (int i = 0; i < history.size(); i++) {
                    System.out.println(i + " " + history.get(i));
                }
                continue;
            } else if (commandLine.startsWith("cd") && commandLine.length() != 3) {
                changeDir(commandLine);
                continue;
            } else {
                commands = getCommand(commandLine);
            }

            try {
                builder.command(commands);
                process = builder.start();
                stream = process.getInputStream();
                reader = new InputStreamReader(stream);
                buffered = new BufferedReader(reader);
                String line;
                while ((line = buffered.readLine()) != null) {
                    System.out.println(line);
                }
                buffered.close();
            } catch (Exception e) {
                System.out.println(e.getMessage());
                ;
            }
        }
    }

    private static boolean isInt(String command) {
        try {
            Integer.parseInt(command);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private static ArrayList<String> getCommand(String command) {
        String commandArr[] = command.split(" ");
        ArrayList<String> commands = new ArrayList<String>();
        for (String word :
                commandArr) {
            commands.add(word);
        }
        return commands;
    }

    private static void changeDir(String cd) {
        String newDirStr = "";
        File newDir = new File(newDirStr);
        Pattern homePatt = Pattern.compile("cd(\\s)*");
        Matcher homeMatch = homePatt.matcher(cd);
        try {
            if (homeMatch.matches()) {
                newDir = new File(System.getProperty("user.dir"));
            } else if (cd.length() > 3) {
                Pattern parentPatt = Pattern.compile("cd \\.\\.(\\s)*");
                Matcher parentMatch = parentPatt.matcher(newDirStr);
                newDirStr = cd.substring(3);
                if (cd.charAt(3) == '/') {
                    newDir = new File(newDirStr);
                } else if (parentMatch.matches()) {
                    newDirStr = System.getProperty("user.dir").substring(0, System.getProperty("user.dir").lastIndexOf("/"));
                    newDir = new File(newDirStr);
                } else {
                    newDir = new File(pwd + "/" + newDirStr);
                }
            }
            builder.directory(newDir);
            ArrayList<String> test = new ArrayList<String>();
            test.add("ls");
            builder.command(test);
            builder.start();
        } catch (Exception e) {
            System.out.println("There ain't no directory here called " + newDirStr);
            return;
        }
        pwd = newDir;
    }
}