/**
 * 
 */
package axelrod.agents;

import axelrod.Rules;

/**
 * @author Dirican
 *
 */
public class Random extends AbstractContestantAgent {

	/* (non-Javadoc)
	 * @see axelrod.agents.AbstractContestantAgent#getMove()
	 */
	@Override
	public int getMove() {
		// TODO Auto-generated method stub
		if ( getRandomGenerator().nextBoolean()  == false)
			return Rules.COOPERATE;
		else
			return Rules.DEFECT;
	}

	/* (non-Javadoc)
	 * @see axelrod.agents.AbstractContestantAgent#getStrategy()
	 */
	@Override
	public String getStrategy() {
		// TODO Auto-generated method stub
		return "RANDOM";
	}

}
