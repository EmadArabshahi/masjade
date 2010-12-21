package axelrod.agents;

import axelrod.Rules;

public class AlwaysDefect extends AbstractContestantAgent {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4326743670329822890L;

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
