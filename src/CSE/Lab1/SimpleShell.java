package CSE.Lab1;

import java.io.*;
import java.util.ArrayList;

public class SimpleShell {
    public static void main(String[] args) throws IOException {
        String commandLine;
        ArrayList<String> commands = new ArrayList<String>();
        BufferedReader console = new BufferedReader(new InputStreamReader(System.in));
        ProcessBuilder builder = new ProcessBuilder();
        InputStream stream;
        InputStreamReader reader;
        BufferedReader buffered;
        Process process;
        File pwd = new File(System.getProperty("user.dir"));
        ArrayList<String> history = new ArrayList<String>();


        while (true) {
            // read what the user entered
            System.out.print("jsh>");
            commandLine = console.readLine();
            history.add(commandLine);
            builder.directory(pwd);


            if (commandLine.equals("!!")) {
                if (history.size() > 1) {
                    commands = getCommand(history.get(history.size() - 2));
                } else {
                    System.out.println("Die liao. Not enuff history.");
                }
                history.remove(history.size() - 1);
            } else if (isInt(commandLine)) {
                history.remove(history.size() - 1);
                if (history.size() >= Integer.parseInt(commandLine) + 1 && Integer.parseInt(commandLine) >= 0) {
                    String hold = history.get(Integer.parseInt(commandLine));
                    history.add(hold);
                    commands = getCommand(hold);
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
                continue;
            } else if (commandLine.equals("history")) {
                history.remove(history.size() - 1);
                for (int i = 0; i < history.size(); i++) {
                    System.out.println(i + " " + history.get(i));
                }
                continue;
            } else if (commandLine.startsWith("cd ")) {
                String newDirStr = commandLine.substring(3);
                File newDir;
                    try {
                        if (commandLine.charAt(3) == '/') {
                            newDir = new File(newDirStr);
                        } else {
                            newDir = new File(pwd + "/" + newDirStr);
                        }
                        builder.directory(newDir);
                        ArrayList<String> test = new ArrayList<String>();
                        test.add("ls");
                        builder.command(test);
                        builder.start();
                    } catch (Exception e) {
                        System.out.println("There ain't no directory here called " + newDirStr);
                        continue;
                    }
                    pwd = new File(pwd + "/" + newDir);
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
}