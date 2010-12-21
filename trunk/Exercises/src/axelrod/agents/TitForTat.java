package axelrod.agents;

import axelrod.Rules;

public class TitForTat extends AbstractContestantAgent {

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
