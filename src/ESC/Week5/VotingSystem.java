package ESC.Week5;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

/**
 * Created by HanWei on 27/2/2017.
 */
public class VotingSystem {
    private int A;
    private int B;
    private DatagramSocket socket;
    private int port;
    InetAddress group;

    public static void main(String[] args) {
        VotingSystem election = new VotingSystem();
        election.beginElection();
    }

    public void beginElection() {
        while (A + B < 5) {
            echoToAll();
        }

        String winner = null;
        if (A > B) {
            winner = "B";
        } else if (B > A) {
            winner = "B";
        } else {
            winner = "no one";
        }

        String winningMsg = "And the winner is... " + winner + "!";
        DatagramPacket packet = new DatagramPacket(winningMsg.getBytes(), winningMsg.getBytes().length, group, 5432);
        try {
            socket.send(packet);
        } catch (IOException e) {
            System.out.println("Could not complete election...");
        }
    }

    public VotingSystem() {
        A = 0;
        B = 0;
        port = 6789;
        try {
            socket = new DatagramSocket(port);
        } catch (IOException e) {
            System.out.println("Could not make connection.");
        }
    }

    public void echoToAll() {
        byte buffer[] = new byte[1000];
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
        try {
            socket.receive(packet);
            String msg[] = new String(packet.getData()).split(" ");
            String name = msg[0];
            String vote = msg[1];
            parseVote(vote);
            System.out.println(name);
            System.out.println(vote);
            String message = name + " voted for " + vote;
            packet = new DatagramPacket(message.getBytes(), message.getBytes().length, group, port);
            socket.send(packet);
        } catch (IOException e) {
            System.out.println("Error receiving votes or sending them back out.");
        }
    }

    private void parseVote(String vote) {
        if (vote.equals("A")) A++;
        else if (vote.equals("B")) B++;
    }
}

class Electorate {
    private boolean usedVote;
    private boolean AorB;
    private String name;
    private int port;
    private DatagramSocket socket;
    private InetAddress group;
    private Scanner in;

    public static void main(String[] args) {
        Electorate newElectorate = new Electorate("Jane");
        newElectorate.castVote();
        String sentence;
        while (!(sentence = newElectorate.receiveMessage()).startsWith("And the winner is...")) {
            System.out.println(sentence);
        }
        System.out.println(sentence);

    }

    public Electorate(String name) {
        this.name = name;
        usedVote = false;
        port = 6789;
        in = new Scanner(System.in);
        try {
            socket = new DatagramSocket();
            group = InetAddress.getByName("localhost");
        } catch (IOException e) {
            System.out.println("Sorry, " + name + ", your vote cannot be cast.");
        }
    }

    public void castVote() {
        String vote = null;
        System.out.println("Please vote for A or B only.");
        vote = in.nextLine();

        while (!AorB(vote)) {
            System.out.println("That's not a valid candidate.");
            vote = in.nextLine();
        }
        AorB = vote.equals("A");
        usedVote = true;

        sendVote(vote);
    }

    private void sendVote(String AorB) {
        byte[] buffer = new byte[1000];
        String message = name + " " + AorB;
        buffer = message.getBytes();
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length, group, port);
        try {
            socket.send(packet);
        } catch (IOException e) {
            System.out.println("Could not cast vote.");
        }
    }

    private String receiveMessage() {
        byte buffer[] = new byte[1000];
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
        try {
            socket.receive(packet);
            return new String(packet.getData());
        } catch (Exception e) {
            System.out.println("Could not receive votes from the others.");
        }
        return null;
    }

    private boolean AorB(String letter) {
        return letter.matches("[A,a,B,b]");
    }
}