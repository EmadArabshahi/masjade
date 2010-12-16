import jade.core.Agent;

public abstract class AxelrodAgent extends Agent{
	// should return true when cooperate, false when defect.
	public abstract boolean getCooperation();
}
