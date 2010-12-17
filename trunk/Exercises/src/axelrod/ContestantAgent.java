package axelrod;
import jade.core.Agent;
import jade.core.behaviours.FSMBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;

public class ContestantAgent extends Agent{
	private ACLMessage _currentMoveRequest;
	
	public void setup()
	{
		registerServices();
		
		FSMBehaviour fsm = new FSMBehaviour();
		
		fsm.registerFirstState(new ReceiveMoveRequestBehaviour(), "receiveMoveRequest");
		fsm.registerState(new SendMoveReplyAction(), "sendMoveReply");
		
		fsm.registerTransition("receiveMoveRequest", "sendMoveReply", ReceiveMoveRequestBehaviour.RECEIVED_MOVE_REQUEST);
		fsm.registerTransition("sendMoveReply", "receiveMoveRequest", SendMoveReplyAction.SENT_MOVE_REPLY);
		
		addBehaviour(fsm);
	}
	
	private void registerServices()
	{
		//The agent description
	    DFAgentDescription dfd = new DFAgentDescription(); 
	    dfd.setName(getAID()); 
	    
	    ServiceDescription sd = new ServiceDescription(); 
	    sd.setType("contestant"); 
	    sd.setName("contestant"); 
	    dfd.addServices(sd); 
	    
	    try 
	    { 
	    	DFService.register(this, dfd); 
	    }
	    catch (FIPAException fe) 
	    { 
	    	fe.printStackTrace(); 
	    }		
	}

	public int getMove() {
		return Rules.COOPERATE;
	}
	
	public void clearCurrentMoveRequest()
	{
		_currentMoveRequest = null;
	}

	public ACLMessage getCurrentMoveRequest() {
		return _currentMoveRequest;
	}

	public void setCurrentMoveRequest(ACLMessage request) {
		_currentMoveRequest = request;
	}
}