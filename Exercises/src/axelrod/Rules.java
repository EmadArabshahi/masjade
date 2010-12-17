package axelrod;

public class Rules 
{
	
	private static final int NUMBER_OF_GAMES_PER_UNIQUE_OPPONENTS = 5;
	private static final int NUMBER_OF_ROUNDS_PER_GAME = 200;
	
	public final static int COOPERATE = 0;
	public final static int DEFECT = 1;
	
	private static int[][][] _utilities = defaultUtilities();
	
	private Rules() { }
	
	/**
	 * returns the number of games two same opponents play against each other
	 * @return
	 */
	public static int getNumberOfGamesPerUniqueOpponents() {
		return NUMBER_OF_GAMES_PER_UNIQUE_OPPONENTS;
	}
	
	public static int getNumberOfRoundsPerGame()
	{
		return NUMBER_OF_ROUNDS_PER_GAME;
	}
	
	
	/**
	 * Gets the defaultUtilities for axelrod.
	 * @return
	 */
	private static int[][][] defaultUtilities()
	{
		int[][][] utilities = new int[2][2][2];
		//utility for player 1 (0) and 2(1) if both cooperate
		utilities[COOPERATE][COOPERATE][0] = 3;
		utilities[COOPERATE][COOPERATE][1] = 3;
		//utility for players if player1 cooperates and player 2 defects
		utilities[COOPERATE][DEFECT][0] = 0;
		utilities[COOPERATE][DEFECT][1] = 4;
		//utilities for players if player1 defects and player 2 cooperates.
		utilities[DEFECT][COOPERATE][0] = 4;
		utilities[DEFECT][COOPERATE][1] = 0;
		//utilties for players if they both defect;
		utilities[DEFECT][DEFECT][0] = 1;
		utilities[DEFECT][DEFECT][1] = 1;
		
		return utilities;
	}
	
	/**
	 * Returns the utility for player1, given the actions.
	 * @param player1Action The action of player 1, either COOPERATE or DEFECT.
	 * @param player2Action The action of player 2, either COOPERATE of DEFECT.
	 * @return The utility for player 1.
	 */
	public static int getUtilityForPlayer1(int player1Action, int player2Action)
	{
		return _utilities[player1Action][player2Action][0];
	}
	
	/**
	 * Returns the utility for player2, given the actions.
	 * @param player1Action The action of player 1, either COOPERATE or DEFECT.
	 * @param player2Action The action of player 2, either COOPERATE of DEFECT.
	 * @return The utility for player 2.
	 */
	public static int getUtilityForPlayer2(int player1Action, int player2Action)
	{
		return _utilities[player1Action][player2Action][1];
	}


	
}
