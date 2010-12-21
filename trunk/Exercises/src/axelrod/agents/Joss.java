package axelrod.agents;

import axelrod.Rules;

public class Joss extends AbstractContestantAgent 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -9148955447869080410L;
	private final static double CHANCE_TO_DEFECT = 0.1d;
	
	@Override
	public int getMove() 
	{
		int round = getRoundNr();
		if(round == 0)
		{
			return Rules.COOPERATE;
		}
		else
		{
			if(getRandomGenerator().nextDouble() < CHANCE_TO_DEFECT)
			{
				return Rules.DEFECT;
			}
			else
			{
				return getOpponentMove(round-1);
			}
		}
		
	}

	@Override
	public String getStrategy() 
	{
		return "JOSS";
	}

}
