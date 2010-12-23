/**
 * 
 */
package axelrod.agents;

import axelrod.Rules;

/**
 * @author Dirican
 *
 */
public class TitForTwoTats extends AbstractContestantAgent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6865410974844840031L;

	/* (non-Javadoc)
	 * @see axelrod.agents.AbstractContestantAgent#getMove()
	 */
	@Override
	public int getMove() {
		// TODO Auto-generated method stub
		int round = getRoundNr();
		
		if ( round == 0 || round == 1)
			return Rules.COOPERATE;		
		else if ( ( getOpponentMove( round) == Rules.DEFECT) && ( getOpponentMove( round - 1) == Rules.DEFECT))
			return Rules.DEFECT;
		else
			return Rules.COOPERATE;

	}

	/* (non-Javadoc)
	 * @see axelrod.agents.AbstractContestantAgent#getStrategy()
	 */
	@Override
	public String getStrategy() {
		// TODO Auto-generated method stub
		return "TIT-FOR-TWO-TATS";
	}

}
