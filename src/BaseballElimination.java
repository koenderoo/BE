import edu.princeton.cs.algs4.FlowNetwork;
import edu.princeton.cs.algs4.FordFulkerson;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

import java.util.HashMap;
import java.util.Map;

public class BaseballElimination {

    private int n; // total number of teams
    private Map<String, Integer> nameToId; // transfer from name to id
    private Map<Integer, String> IdToName; // transfer back from id to name
    private FordFulkerson fordFulkerson; // the key algorithm

    // create a baseball division from given filename in format specified below
    public BaseballElimination(String filename) {
        In in = new In(filename);

        n = Integer.parseInt(in.readLine());
        nameToId = new HashMap<>();
        IdToName = new HashMap<>();

        String[] lines = in.readAllLines();
        for (String line : lines) {

        }
    }

    // number of teams
    public              int numberOfTeams() {
        return n;
    }

    // all teams
    public Iterable<String> teams() {
        return nameToId.keySet();
    }

    // number of wins for given team
    public              int wins(String team) {
        //        invalid team
        if (team == null || team.equals("") || !nameToId.containsKey(team)) {
            throw new IllegalArgumentException();
        }
    }

    // number of losses for given team
    public              int losses(String team) {
        //        invalid team
        if (team == null || team.equals("") || !nameToId.containsKey(team)) {
            throw new IllegalArgumentException();
        }
    }

    // number of remaining games for given team
    public              int remaining(String team) {
        //        invalid team
        if (team == null || team.equals("") || !nameToId.containsKey(team)) {
            throw new IllegalArgumentException();
        }
    }

    // number of remaining games between team1 and team2
    public              int against(String team1, String team2) {
        //        invalid team1
        if (team1 == null || team1.equals("") || !nameToId.containsKey(team1)) {
            throw new IllegalArgumentException();
        }
        //        invalid team2
        if (team2 == null || team2.equals("") || !nameToId.containsKey(team2)) {
            throw new IllegalArgumentException();
        }

    }

    // is given team eliminated?
    public          boolean isEliminated(String team) {
        //        invalid team
        if (team == null || team.equals("") || !nameToId.containsKey(team)) {
            throw new IllegalArgumentException();
        }

    }

    // subset R of teams that eliminates given team; null if not eliminated
    public Iterable<String> certificateOfElimination(String team) {
        //        invalid team
        if (team == null || team.equals("") || !nameToId.containsKey(team)) {
            throw new IllegalArgumentException();
        }

    }

    private FlowNetwork createFlowNetwork(int n) {
        int V = 2 + (n - 1) * (n - 2) / 2 + n;
        FlowNetwork fn = new FlowNetwork(V);
    }

    public static void main(String[] args) {
        BaseballElimination division = new BaseballElimination(args[0]);
        for (String team : division.teams()) {
            if (division.isEliminated(team)) {
                StdOut.print(team + " is eliminated by the subset R = { ");
                for (String t : division.certificateOfElimination(team)) {
                    StdOut.print(t + " ");
                }
                StdOut.println("}");
            }
            else {
                StdOut.println(team + " is not eliminated");
            }
        }
    }
}
