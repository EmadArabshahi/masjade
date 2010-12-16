
public class Round {
	private Contestant _contestant1;
	private Contestant _contestant2;
	
	private boolean _cooperationContestant1;
	private boolean _cooperationContestant2;
	private boolean _played;
	
	public Round(Contestant contestant1, Contestant contestant2)
	{
		_contestant1 = contestant1;
		_contestant2 = contestant2;
	}

	public void play() {
		_cooperationContestant1 = _contestant1.sendCooperation();
		_cooperationContestant2 = _contestant2.sendCooperation();
		_played = true;
	}
	
	public int getUtilityContestant1() throws Exception
	{
		int utility = -1;
		if (_played)
		{
			utility = Rules.getUtility(_cooperationContestant1, _cooperationContestant2);
		}
		else
		{
			throw new Exception("Round has not yet been played");
		}
		return utility;
	}
	
	public int getUtilityContestant2() throws Exception
	{
		int utility = -1;
		if (_played)
		{
			utility = Rules.getUtility(_cooperationContestant2, _cooperationContestant1);
		}
		else
		{
			throw new Exception("Round has not yet been played");
		}
		return utility;
	}
}
