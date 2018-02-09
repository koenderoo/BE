import edu.princeton.cs.algs4.*;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class BaseballElimination {

    private int n; // total number of teams
    private Map<String, Integer> nameToId; // transfer from name to id
    private Map<Integer, String> idToName; // transfer back from id to name
    private Integer[] numberOfWins; // number of wins per teamId
    private Integer[] numberOfLosses; // number of losses per teamId
    private Integer[] numberOfRemainingGames; // number of remaining games per teamId
    private Integer[][] remainingAgainstTeam; // remaining number against teamId per teamId
    private FordFulkerson fordFulkerson; // the key algorithm
    private Set<String> teamSet;

    // create a baseball division from given filename in format specified below
    public BaseballElimination(String filename) {
        In in = new In(filename);

        n = Integer.parseInt(in.readLine());
        nameToId = new HashMap<>();
        idToName = new HashMap<>();
        numberOfWins = new Integer[n];
        numberOfLosses = new Integer[n];
        numberOfRemainingGames = new Integer[n];
        remainingAgainstTeam = new Integer[n][n];

        String[] lines = in.readAllLines();
        for (int i = 0; i < lines.length; i++) {
            String[] parts = lines[i].trim().split("\\s+");
            nameToId.put(parts[0], i);
            idToName.put(i, parts[0]);
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
        Integer teamId = nameToId.get(team);
        teamSet = new HashSet<>();
        FlowNetwork flowNetwork = createFlowNetwork(teamId);
        int s = flowNetwork.V() -2;
        int t = flowNetwork.V() -1;
        fordFulkerson = new FordFulkerson(flowNetwork, s, t);

        for (FlowEdge e : flowNetwork.adj(s))
            if (e.flow() < e.capacity()) return true;
        return false;
    }

    // subset R of teams that eliminates given team; null if not eliminated
    public Iterable<String> certificateOfElimination(String team) {
        //        invalid team
        if (team == null || team.equals("") || !nameToId.containsKey(team)) {
            throw new IllegalArgumentException();
        }
        if (!isEliminated(team)) {
            return null;
        }
        else {
            Integer teamId = nameToId.get(team);
            for (int v = 0; v < n; v++) {
                if (v != teamId && fordFulkerson.inCut(v))
                    teamSet.add(idToName.get(v));
                return teamSet;
            }

        }
        // overbodige return, maar ja...
        return null;
    }

    private FlowNetwork createFlowNetwork(int x) {
        int V = 2 + (n - 1) * (n - 2) / 2 + n;
        FlowNetwork fn = new FlowNetwork(V);
        int s = V -2, t = V -1;
        for (int v  = 0; v < n; v++) {
            if (v != x) {
                int capacity = numberOfWins[x] + numberOfRemainingGames[x] - numberOfWins[v];
                if (capacity < 0) {
                    teamSet.add(idToName.get(v));
                }
                FlowEdge e = new FlowEdge(v, t, Math.max(capacity, 0));
                fn.addEdge(e);
            }
        }

        int offset = 0;
        for (int i =0; i < n; i ++)
            for (int j = 0; j < n; j++) {
                if (i != x && j != x && i < j) {
                    int v = n + (offset++);
                    fn.addEdge(new FlowEdge(s, v, remainingAgainstTeam[i][j]));
                    fn.addEdge(new FlowEdge(v, i, Double.POSITIVE_INFINITY));
                    fn.addEdge(new FlowEdge(v, j, Double.POSITIVE_INFINITY));
                }
            }
        return fn;
    }

    public static void main(String[] args) {
        BaseballElimination division = new BaseballElimination("e:/dev/BE/baseball-testing/baseball/teams4.txt");
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
