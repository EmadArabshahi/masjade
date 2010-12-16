
public class Rules {
	private static final int NUMBER_OF_GAMES_PER_UNIQUE_OPPONENTS = 5;
	private static final int NUMBER_OF_ROUNDS_PER_GAME = 200;
	
	private static final int UTILITY_I_COOPERATE_YOU_COOPERATE = 3;
	private static final int UTILITY_I_COOPERATE_YOU_DEFECT = 0;
	private static final int UTILITY_I_DEFECT_YOU_COOPERATE = 4;
	private static final int UTILITY_I_DEFECT_YOU_DEFECT = 1;

	// private constructor, should only contain static methods and members.
	private Rules() {}
	
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
	
	public static int getUtility(boolean cooperation, boolean cooperationOpponent)
	{
		int utility = -1;
		if (cooperation && cooperationOpponent)
		{
			utility = UTILITY_I_COOPERATE_YOU_COOPERATE;
		}
		else if (cooperation && !cooperationOpponent)
		{
			utility = UTILITY_I_COOPERATE_YOU_DEFECT;
		}
		else if (!cooperation && cooperationOpponent)
		{
			utility = UTILITY_I_DEFECT_YOU_COOPERATE;
		}
		else if (!cooperation && !cooperationOpponent)
		{
			utility = UTILITY_I_DEFECT_YOU_DEFECT;
		}
		
		return utility;
	}
}
