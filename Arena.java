import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;

public class Arena {
    public enum Row {Front, Back}

    ;    //enum for specifying the front or back row

    public enum Team {A, B}

    ;        //enum for specifying team A or B

    private Player[][] teamA = null;    //two dimensional array representing the players of Team A
    private Player[][] teamB = null;    //two dimensional array representing the players of Team B

    public static final int MAXROUNDS = 100;    //Max number of turn
    public static final int MAXEACHTYPE = 3;    //Max number of players of each type, in each team.
    private final Path logFile = Paths.get("battle_log.txt");

    public int numRounds = 0;    //keep track of the number of rounds so far
    public static int numRowPlayer;

    private double minHP;
    //private double dead;
    private static double sumHP = 0;
    public int allmember;
    //private int row;
    //private int column;

    public Player target;
    public Player myteamminHP;
    public Player[][] myteam;
    public Player[][] theirteam;

    /**
     * Constructor.
     *
     * @param _numRows       is the number of row in each team.
     * @param _numRowPlayers is the number of player in each row.
     */
    public Arena(int _numRowPlayers) {
        //INSERT YOUR CODE HERE
        this.teamA = new Player[2][_numRowPlayers];
        this.teamB = new Player[2][_numRowPlayers];
        Arena.numRowPlayer = _numRowPlayers;
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < numRowPlayer; j++) {
                allmember = 2 * numRowPlayer;
            }
        }


        ////Keep this block of code. You need it for initialize the log file.
        ////(You will learn how to deal with files later)
        try {
            Files.deleteIfExists(logFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        /////////////////////////////////////////

    }

    /**
     * Returns true if "player" is a member of "team", false otherwise.
     * Assumption: team can be either Team.A or Team.B
     *
     * @param player
     * @param team
     * @return
     */
    public boolean isMemberOf(Player player, Team team) {
        //INSERT YOUR CODE HERE
        if (team == Team.A) {
            for (int i = 0; i < 2; i++) {
                for (int j = 0; j < numRowPlayer; j++) {
                    if (teamA[i][j].getType() == player.getType()) {
                        return true;
                    }
                }
            }
        } else if (team == Team.B)
            for (int i = 0; i < 2; i++) {
                for (int j = 0; j < numRowPlayer; j++) {
                    if (teamB[i][j].getType() == player.getType()) {
                        return true;
                    }
                }
            }
        return false;
    }


    /**
     * This methods receives a player configuration (i.e., team, type, row, and position),
     * creates a new player instance, and places him at the specified position.
     *
     * @param team     is either Team.A or Team.B
     * @param pType    is one of the Player.Type  {Healer, Tank, Samurai, BlackMage, Phoenix}
     * @param row      either Row.Front or Row.Back
     * @param position is the position of the player in the row. Note that position starts from 1, 2, 3....
     */
    public void addPlayer(Team team, Player.PlayerType pType, Row row, int position) {
        //INSERT YOUR CODE HERE

        if (team == Arena.Team.A) {
            if (row == Row.Front) {
                this.teamA[0][position - 1] = new Player(pType);
                this.teamA[0][position - 1].setPosition(team,pType,row,position);
            } else {
                this.teamA[1][position - 1] = new Player(pType);
                this.teamA[1][position - 1].setPosition(team,pType,row,position);
            }
            //System.out.println(team+" "+pType+""+row+" "+ position);

        } else if (team == Arena.Team.B) {
            if (row == Row.Front) {
                this.teamB[0][position - 1] = new Player(pType);
                this.teamB[0][position - 1].setPosition(team,pType,row,position);
            } else {
                this.teamB[1][position - 1] = new Player(pType);
                this.teamB[1][position - 1].setPosition(team,pType,row,position);
            }
            //System.out.println(team+" "+pType+""+row+" "+ position);
        }
    }


    /**
     * Validate the players in both Team A and B. Returns true if all of the following conditions hold:
     * <p>
     * 1. All the positions are filled. That is, there each team must have exactly numRow*numRowPlayers players.
     * 2. There can be at most MAXEACHTYPE players of each type in each team. For example, if MAXEACHTYPE = 3
     * then each team can have at most 3 Healers, 3 Tanks, 3 Samurais, 3 BlackMages, and 3 Phoenixes.
     * <p>
     * Returns true if all the conditions above are satisfied, false otherwise.
     *
     * @return
     */
    public int maxEachType(Player[][] team, Player.PlayerType pType) {
        int count = 0;
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < numRowPlayer; j++) {
                if (team[i][j].getType() == pType) {
                    count++;
                }
            }
        }
        return count;

    }

    public boolean validatePlayers() {
        //INSERT YOUR CODE HERE
        //  System.out.print(numRowPlayer);
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < numRowPlayer; j++) {
                if (teamA[i][j] == null || teamB[i][j] == null) {
                    return false;
                }
            }
        }

        if (maxEachType(teamA, Player.PlayerType.Healer) > MAXEACHTYPE) return false;
        if (maxEachType(teamA, Player.PlayerType.Tank) > MAXEACHTYPE) return false;
        if (maxEachType(teamA, Player.PlayerType.Samurai) > MAXEACHTYPE) return false;
        if (maxEachType(teamA, Player.PlayerType.BlackMage) > MAXEACHTYPE) return false;
        if (maxEachType(teamA, Player.PlayerType.Phoenix) > MAXEACHTYPE) return false;
        if (maxEachType(teamB, Player.PlayerType.Healer) > MAXEACHTYPE) return false;
        if (maxEachType(teamB, Player.PlayerType.Tank) > MAXEACHTYPE) return false;
        if (maxEachType(teamB, Player.PlayerType.Samurai) > MAXEACHTYPE) return false;
        if (maxEachType(teamB, Player.PlayerType.BlackMage) > MAXEACHTYPE) return false;
        if (maxEachType(teamB, Player.PlayerType.Phoenix) > MAXEACHTYPE) return false;

        return true;

    }


    /**
     * Returns the sum of HP of all the players in the given "team"
     *
     * @param team
     * @return
     */
    public static double getSumHP(Player[][] team) {
        //INSERT YOUR CODE HERE
        double sumHP = 0;
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < numRowPlayer; j++) {
                sumHP += team[i][j].getCurrentHP();

            }
        }


        return sumHP;
    }

    /**
     * Return the team (either teamA or teamB) whose number of alive players is higher than the other.
     * <p>
     * If the two teams have an equal number of alive players, then the team whose sum of HP of all the
     * players is higher is returned.
     * <p>
     * If the sums of HP of all the players of both teams are equal, return teamA.
     *
     * @return
     */
    public Player[][] getWinningTeam() {
        int countA = 0, countB = 0, countA2 = 0, countB2 = 0;
        //INSERT YOUR CODE HERE
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < numRowPlayer; j++) {
                if (teamA[i][j].isAlive()) {
                    countA++;
                }
                if (teamB[i][j].isAlive()) {
                    countB++;
                }
                if (countA > countB) {
                    return teamA;
                } else if (countA < countB) {
                    return teamB;
                } else if (countA == countB) {
                    if (getSumHP(teamA) > getSumHP(teamB)) {
                        return teamA;
                    } else if (getSumHP(teamB) > getSumHP(teamA)) {
                        return teamB;

                    } else {
                        return teamA;
                    }

                } else return teamA;
            }
        }

        return null;
    }

    /**
     * This method simulates the battle between teamA and teamB. The method should have a loop that signifies
     * a round of the battle. In each round, each player in teamA invokes the method takeAction(). The players'
     * turns are ordered by its position in the team. Once all the players in teamA have invoked takeAction(),
     * not it is teamB's turn to do the same.
     * <p>
     * The battle terminates if one of the following two conditions is met:
     * <p>
     * 1. All the players in a team has been eliminated.
     * 2. The number of rounds exceeds MAXROUNDS
     * <p>
     * After the battle terminates, report the winning team, which is determined by getWinningTeam().
     */
    public void startBattle() {
        //INSERT YOUR CODE HERE
        //System.out.println("x");
        while (true) {

            if (numRounds == MAXROUNDS || isDead()) {

                if(teamA==getWinningTeam()) {
                    System.out.println("@@@ Team A won");
                }else {
                    System.out.println("@@@ Team B won");
                }
                break;
            } else {
                numRounds++;
                System.out.println("@ Round " + numRounds);
                for (int i = 0; i < 2; i++) {

                    for (int j = 0; j < numRowPlayer; j++) {
                        this.target = this.isMinHP(teamB);
                        if(this.target!=null){
                            this.myteam = this.teamA;
                            this.theirteam = this.teamB;
                            this.myteamminHP = this.isMinHP(teamA);
                            teamA[i][j].takeAction(this);
                        }

                    }
                }
                for (int i = 0; i < 2; i++) {
                    for (int j = 0; j < numRowPlayer; j++) {
                        teamB[i][j].reTaunt();
                        teamA[i][j].reCurse();

                    }
                }

                for (int i = 0; i < 2; i++) {
                    for (int j = 0; j < numRowPlayer; j++) {
                        this.target = this.isMinHP(teamA);
                        if(this.target != null){
                            this.myteam = this.teamB;
                            this.theirteam = this.teamA;
                            this.myteamminHP = this.isMinHP(teamB);
                            teamB[i][j].takeAction(this);
                        }
                    }

                }
                for (int i = 0; i < 2; i++) {
                    for (int j = 0; j < numRowPlayer; j++) {
                        teamA[i][j].reTaunt();
                        teamB[i][j].reCurse();

                    }
                }
                Arena.displayArea(this, true);
                this.logAfterEachRound();


            }

        }

    }

    public boolean isDead() {
        //INSERT YOUR CODE HERE
        int countA = 0, countB = 0;
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < numRowPlayer; j++) {
                if (teamA[i][j].isAlive() == false) {
                    countA++;
                }
                if (teamB[i][j].isAlive() == false) {
                    countB++;
                }
            }
        }
        if (countA == this.allmember || countB == this.allmember) {
            return true;
        }

        return false;
    }

    public Player isMinHP(Player[][] team) {
        //INSERT YOUR CODE HERE
        Player minHPeiei = null;
        minHP = 10000000;
        for(int i=0;i<2;i++){
            for(int j=0;j<numRowPlayer;j++){
                if(team[i][j].isAlive()&&team[i][j].getCurrentHP() < minHP )
                {
                    minHP=team[i][j].getCurrentHP();
                    minHPeiei=team[i][j];

                }
            }
            if(minHPeiei!=null){
                break;
            }
        }
        return minHPeiei;
    }

//    public boolean checkDeadRow1() {
//        int countA = 0, countB = 0;
//        for (int j = 0; j < numRowPlayer; j++) {
//            if (teamA[0][j].isAlive() == true) {
//                countA++;
//            }
//            if (teamB[0][j].isAlive() == true) {
//                countB++;
//            }
//        }
//        if (countA == 0 || countB == 0) {
//            return true;
//        }
//        return false;
//    }


    /**
     * This method displays the current area state, and is already implemented for you.
     * In startBattle(), you should call this method once before the battle starts, and
     * after each round ends.
     *
     * @param arena
     * @param verbose
     */
    public static void displayArea(Arena arena, boolean verbose) {
        if (arena == null) return;

        StringBuilder str = new StringBuilder();
        if (verbose) {
            str.append(String.format("%38s   %35s", "Team A", "") + "\t\t" + String.format("%-33s%-35s", "", "Team B") + "\n");
            str.append(String.format("%38s", "BACK ROW") + String.format("%38s", "FRONT ROW") + "  |  " + String.format("%-38s", "FRONT ROW") + "\t" + String.format("%-38s", "BACK ROW") + "\n");
            for (int i = 0; i < arena.teamA[0].length; i++) {
                str.append(String.format("%38s", arena.teamA[1][i]) + String.format("%38s", arena.teamA[0][i]) + "  |  " + String.format("%-38s", arena.teamB[0][i]) + String.format("%-38s", arena.teamB[1][i]) + "\n");
            }
        }

        str.append("@ Total HP of Team A = " + getSumHP(arena.teamA) + ". @ Total HP of Team B = " + getSumHP(arena.teamB) + "\n\n");
        System.out.print(str.toString());


    }

    /**
     * This method writes a log (as round number, sum of HP of teamA, and sum of HP of teamB) into the log file.
     * You are not to modify this method, however, this method must be call by startBattle() after each round.
     * <p>
     * The output file will be tested against the auto-grader, so make sure the output look something like:
     * <p>
     * 1	47415.0	49923.0
     * 2	44977.0	46990.0
     * 3	42092.0	43525.0
     * 4	44408.0	43210.0
     * <p>
     * Where the numbers of the first, second, and third columns specify round numbers, sum of HP of teamA, and sum of HP of teamB respectively.
     */
    private void logAfterEachRound() {
        try {
            Files.write(logFile, Arrays.asList(new String[]{numRounds + "\t" + getSumHP(teamA) + "\t" + getSumHP(teamB)}), StandardOpenOption.APPEND, StandardOpenOption.CREATE);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
