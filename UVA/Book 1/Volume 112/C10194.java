import java.util.*;
import java.io.*;

public class C10194 {

	public static void main(final String [] args) throws Exception {
		final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in, "ISO-8859-1"));
		PrintWriter writer  = new PrintWriter(new OutputStreamWriter(System.out, "ISO-8859-1"));
		int T = Integer.parseInt(reader.readLine());

		while (T > 0) {
			final HashMap<String, Team> mapping = new HashMap<String, Team>();

			String tournamentName = reader.readLine();
			int teams = Integer.parseInt(reader.readLine());
			String name = "";
			for (int i = 0; i < teams; i ++) {
				name = reader.readLine();
				mapping.put(name, new Team(name, name.toLowerCase()));
			}
			int G = Integer.parseInt(reader.readLine());
			String [] line = null;
			int goalsF, goalsS;
			String teamF, teamS;
			Team instF, instS;
			for (int i = 0; i < G; i ++) {
				/** Parser **/
				line = reader.readLine().split("#|@");
				teamF = line[0];
				goalsF = Integer.parseInt(line[1]);
				goalsS = Integer.parseInt(line[2]);
				teamS = line[3];

				/** Update process **/
				instF = mapping.get(teamF);
				instS = mapping.get(teamS);
				instF.gamesPlayed ++;
				instS.gamesPlayed ++;
				instF.goals += goalsF;
				instS.goals += goalsS;
				instF.goalsAgainst += goalsS;
				instS.goalsAgainst += goalsF;

				instF.goalDifference = instF.goals - instF.goalsAgainst;
				instS.goalDifference = instS.goals - instS.goalsAgainst;
				if (goalsF > goalsS) {
					// Gano el teamF
					instF.pointsEarned += 3;
					instF.wins ++;
					instS.loses ++;
				} else if (goalsF < goalsS) {
					instS.pointsEarned += 3;
					instS.wins ++;
					instF.loses ++;
					// Gano el teamS
				} else {
					instF.pointsEarned += 1;
					instS.pointsEarned += 1;
					instF.ties ++;
					instS.ties ++;
					// Empate
				}
			}
			// Sorting and printing
			List<Team> teamsList = new ArrayList<Team>(mapping.values());
			teamsList.sort(Team::compare);
			int rank = 0;
			System.out.println(tournamentName);
			for (Team team : teamsList) {
				writer.print((++ rank) + ") " + team.unmutableName + " " + team.pointsEarned + "p, " + team.gamesPlayed + "g (" + team.wins + "-" +
					team.ties + "-" + team.loses + "), " + team.goalDifference + "gd (" + team.goals + "-" + team.goalsAgainst + ")\n");
			}
			-- T;
			if (T > 0) {
				writer.print("\n");
			}
			writer.flush();
		}
		writer.close();
	}
}

class Team {
	public String unmutableName;
	public String name;
	public int pointsEarned;
	public int gamesPlayed;
	public int wins;
	public int ties;
	public int loses;
	public int goals;
	public int goalsAgainst;
	public int goalDifference;

	public Team(final String unmutableName, final String name) {
		this.unmutableName = unmutableName;
		this.name = name;
	}

	public static int compare(Team l, Team r) {
		if (l.pointsEarned == r.pointsEarned) {
			if (l.wins == r.wins) {
				if (l.goalDifference == r.goalDifference) {
					if (l.goals == r.goals) {
						if (l.gamesPlayed == r.gamesPlayed) {
							return l.name.compareTo(r.name);
						} else {
							if (l.gamesPlayed < r.gamesPlayed) {
								return -1;
							}
							return 1;
						}
					} else {
						if (l.goals > r.goals) {
							return -1;
						}
						return 1;
					}
				} else {
					if (l.goalDifference > r.goalDifference) {
						return -1;
					}
					return 1;
				}
			} else {
				if (l.wins > r.wins) {
					return -1;
				}
				return 1;
			}
		} else {
			if (l.pointsEarned > r.pointsEarned) {
				return -1;
			}
			return 1;
		}
	}

}


