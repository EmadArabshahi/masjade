package axelrod.agents;

import axelrod.Rules;

public class TitForTat extends AbstractContestantAgent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5843959893069547909L;

	@Override
	public int getMove() 
	{
		int round = getRoundNr();
		if(round == 0)
			return Rules.COOPERATE;
		else
			return getOpponentMove(round-1);
	}

	@Override
	public String getStrategy() 
	{
		return "TIT-FOR-TAT";
	}

}
