package CSE.Lab1;

import java.io.*;
import java.util.ArrayList;

public class SimpleShell {
	public static void main(String[] args) throws IOException {
		String commandLine;
        ArrayList<String> commands = new ArrayList<String>();
		BufferedReader console = new BufferedReader(new InputStreamReader(System.in));
        ProcessBuilder builder = new ProcessBuilder();
        Process process;
        ArrayList<String> history = new ArrayList<String>();

		while (true) {
			// read what the user entered
			System.out.print("jsh>");
			commandLine = console.readLine();
            

			// TODO: adding a history feature
            history.add(commandLine);
            
			if (commandLine.equals("!!")) {
                commands = getCommand(history.get(history.size() - 1));
                //TODO: execute last command
            } else if (isInt(commandLine)) {
                commands = getCommand(history.get(Integer.parseInt(commandLine) - 1));
                // TODO: execute the commandLine-th command from history
            }

			// if the user entered a return, just loop again
			if (commandLine.equals("")) {
				continue;
			}

			try {
                // TODO: creating the external process and executing the command in that process
                commands = getCommand(commandLine);
                builder.command(commands);
                process = builder.start();
                System.out.println(process.getInputStream());
            } catch (Exception e) {
                e.printStackTrace();
            }

            // TODO: modifying the shell to allow changing directories
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