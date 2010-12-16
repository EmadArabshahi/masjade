
public class Round {
	private AxelrodAgent _contestant1;
	private AxelrodAgent _contestant2;
	
	private boolean _cooperationContestant1;
	private boolean _cooperationContestant2;
	private boolean _played;
	
	public Round(AxelrodAgent contestant1, AxelrodAgent contestant2)
	{
		_contestant1 = contestant1;
		_contestant2 = contestant2;
	}

	public void play() {
		_cooperationContestant1 = _contestant1.getCooperation();
		_cooperationContestant2 = _contestant2.getCooperation();
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
