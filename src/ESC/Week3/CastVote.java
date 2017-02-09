package ESC.Week3;

import java.util.HashMap;
import java.util.Scanner;

/**
 * Created by HanWei on 9/2/2017.
 */
public class CastVote {
    public static void main(String[] args) {
        int numCandidates = 0;
        int numElectorates = 0;
        String name;
        HashMap<String, Candidate> candidates = new HashMap<>();
        Scanner in = new Scanner(System.in);

        System.out.println("How many candidates are there?");
        numCandidates = getPositiveInt(in);

        for (int i = 0; i < numCandidates; i++) {
            System.out.println("What is the " + (i + 1) + "th candidate's name?");
            name = in.next();
            candidates.put(name, new Candidate(name));
        }

        System.out.println("How many electorates are there?");

        numElectorates = getPositiveInt(in);

        for (int i = 0; i < numElectorates; i++) {
            System.out.println("What is the name of the " + (i + 1) + "th electorate?");
            name = in.next();
            Electorate electorate = new Electorate(name);

            System.out.println("Who will " + name + " vote for?");
            name = in.next();
            while (!candidates.containsKey(name)) {
                System.out.println("That is not a valid candidate. Please enter a valid candidate name.");
                name = in.next();
            }
            electorate.vote(candidates.get(name));
        }

        System.out.println("And the winner is... " + getWinner(candidates).getName().toUpperCase() + "!!!");
    }

    private static Candidate getWinner(HashMap<String, Candidate> candidateHashMap) {
        int maxVotes = 0;
        Candidate winner = new Candidate("no one");
        Candidate potential;

        for (String name :
                candidateHashMap.keySet()) {
            potential = candidateHashMap.get(name);
            if (potential.getNumVotes() > maxVotes) {
                winner = potential;
                maxVotes = potential.getNumVotes();
            }
        }
        return winner;
    }

    public static int getPositiveInt(Scanner in) {
        int result = 0;
        while (true) {
            try {

                result = in.nextInt();
                if (result <= 0) {
                    System.out.println("Please enter a positive number.");
                    continue;
                }
                return result;
            } catch (Exception e) {
                System.out.println("Please enter a number.");
            }
        }
    }
}

class Candidate {
    private String name;
    private int numVotes;

    public Candidate(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getNumVotes() {
        return numVotes;
    }

    public void voteFor(Electorate electorate) {
        numVotes++;
    }
}

class Electorate {
    private String name;
    private String votedFor;
    private boolean votesCast;

    public Electorate(String name) {
        this.name = name;
        votesCast = false;
    }

    public boolean vote(Candidate candidate) {
        if (!votesCast) {
            candidate.voteFor(this);
            votedFor = candidate.getName();
            votesCast = true;
            return true;
        }
        System.out.println("Sorry, you have already voted.");
        return false;
    }
}