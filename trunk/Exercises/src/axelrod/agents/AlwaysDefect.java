package axelrod.agents;

import axelrod.Rules;

public class AlwaysDefect extends AbstractContestantAgent {

	@Override
	public int getMove() {
		// TODO Auto-generated method stub
		return Rules.DEFECT;
	}

	@Override
	public String getStrategy() {
		// TODO Auto-generated method stub
		return "ALDD";
	}

}
