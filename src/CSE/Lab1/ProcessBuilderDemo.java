package CSE.Lab1;

/**
 * Created by HanWei on 26/1/2017.
 */
public class ProcessBuilderDemo {

    public static void main(String[] args) {

        // create a new list of arguments for our process
        String[] list = {"notepad.exe", "test.txt"};

        // create the process builder
        ProcessBuilder pb = new ProcessBuilder(list);

        // set the command list
        pb.command(list);

        // print the new command list
        System.out.println("" + pb.command());
    }
}
