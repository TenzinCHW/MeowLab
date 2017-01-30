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

        System.out.println(System.getProperties());

		while (true) {
			// read what the user entered
			System.out.print("jsh>");
			commandLine = console.readLine();
            builder.directory(pwd);

            
			if (commandLine.equals("!!")) {
                commands = getCommand(history.get(history.size() - 1));
            } else if (isInt(commandLine)) {
                commands = getCommand(history.get(Integer.parseInt(commandLine) - 1));
            } else if (commandLine.equals("exit")) {
			    System.exit(0);
            } else if (commandLine.equals("")) {
				continue;
			} else if (commandLine.equals("history")) {
                for (String hist :
                        history) {
                    System.out.println(hist);
                }
                continue;
            } else if (commandLine.startsWith("cd ")) {
			    String newDir = commandLine.substring(3);
			    if (commandLine.charAt(3) == '/') {
			        pwd = new File(newDir);
                } else {
			        pwd = new File(pwd + "/" + newDir);
                }
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
                while ((line = buffered.readLine()) !=null) {
                    System.out.println(line);
                }
                buffered.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

            history.add(commandLine);
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