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
    private Integer[] numberOfWins; // number of wins per teamId
    private Integer[] numberOfLosses; // number of losses per teamId
    private Integer[] numberOfRemainingGames; // number of remaining games per teamId
    private Integer[][] remainingAgainstTeam; // remaining number against teamId per teamId
    private FordFulkerson fordFulkerson; // the key algorithm

    // create a baseball division from given filename in format specified below
    public BaseballElimination(String filename) {
        In in = new In(filename);

        n = Integer.parseInt(in.readLine());
        nameToId = new HashMap<>();
        IdToName = new HashMap<>();
        numberOfWins = new Integer[n];
        numberOfLosses = new Integer[n];
        numberOfRemainingGames = new Integer[n];
        remainingAgainstTeam = new Integer[n-1][n-1];

        String[] lines = in.readAllLines();
        for (int i = 0; i < lines.length; i++) {
            String[] parts = lines[i].split(" ");
            nameToId.put(parts[0], i);
            IdToName.put(i, parts[0]);
            numberOfWins[i] = Integer.parseInt(parts[1]);
            numberOfLosses[i] = Integer.parseInt(parts[2]);
            numberOfRemainingGames[i] = Integer.parseInt(parts[3]);
            for (int j = 4; j < parts.length; j++) {
                remainingAgainstTeam[i][j-4] = Integer.parseInt(parts[j]);
            }
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
        Integer teamId = nameToId.get(team);
        return numberOfWins[teamId];
    }

    // number of losses for given team
    public              int losses(String team) {
        //        invalid team
        if (team == null || team.equals("") || !nameToId.containsKey(team)) {
            throw new IllegalArgumentException();
        }
        Integer teamId = nameToId.get(team);
        return numberOfLosses[teamId];
    }

    // number of remaining games for given team
    public              int remaining(String team) {
        //        invalid team
        if (team == null || team.equals("") || !nameToId.containsKey(team)) {
            throw new IllegalArgumentException();
        }
        Integer teamId = nameToId.get(team);
        return numberOfRemainingGames[teamId];
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
        Integer teamId1 = nameToId.get(team1);
        Integer teamId2 = nameToId.get(team2);
        return remainingAgainstTeam[teamId1][teamId2];
    }

    // is given team eliminated?
    public          boolean isEliminated(String team) {
        //        invalid team
        if (team == null || team.equals("") || !nameToId.containsKey(team)) {
            throw new IllegalArgumentException();
        }
        // todo uitwerken!!!
        return true;
    }

    // subset R of teams that eliminates given team; null if not eliminated
    public Iterable<String> certificateOfElimination(String team) {
        //        invalid team
        if (team == null || team.equals("") || !nameToId.containsKey(team)) {
            throw new IllegalArgumentException();
        }
        // todo uitwerken!!!
        return null;
    }

    private FlowNetwork createFlowNetwork(int n) {
        int V = 2 + (n - 1) * (n - 2) / 2 + n;
        FlowNetwork fn = new FlowNetwork(V);

        // todo uitwerken!!!
        return fn;
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
