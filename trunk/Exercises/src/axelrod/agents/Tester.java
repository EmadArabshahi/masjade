package axelrod.agents;

import axelrod.Rules;

public class Tester extends AbstractContestantAgent 
{
	
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1244954641889807361L;

	@Override
	public int getMove() 
	{
		int round = getRoundNr();
		//first round defect
		if(round == 0)
		{
			return Rules.DEFECT;
		}
		//else if retaliated play tit for tat
		else if(getOpponentMove(0) == Rules.DEFECT)
		{
			return getOpponentMove(round-1);
		}
		//else if not retaliated switch between defect and cooperate
		else
		{
			if(getMyMove(round-1) == Rules.DEFECT)
				return Rules.COOPERATE;
			else
				return Rules.DEFECT;
		}
	}

	@Override
	public String getStrategy() {
		// TODO Auto-generated method stub
		return "TESTER";
	}

}
