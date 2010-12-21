package axelrod.agents;
import java.util.ArrayList;
import java.util.List;

import axelrod.Rules;
import axelrod.behaviours.ReceiveMoveRequestBehaviour;
import axelrod.behaviours.ReceiveRoundResultBehaviour;
import axelrod.behaviours.SendMoveReplyAction;
import axelrod.messages.MoveRequest;
import jade.core.Agent;
import jade.core.behaviours.FSMBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;

public abstract class AbstractContestantAgent extends Agent
{
	private MoveRequest _currentMoveRequest;
	private List<Integer> _myRoundMoveHistory;
	private List<Integer> _opponentsRoundMoveHistory;
	
	public void setup()
	{
		_myRoundMoveHistory = new ArrayList<Integer>();
		_opponentsRoundMoveHistory = new ArrayList<Integer>();
		
		registerServices();
		
		FSMBehaviour fsm = new FSMBehaviour();
		
		fsm.registerFirstState(new ReceiveMoveRequestBehaviour(), "receiveMoveRequest");
		fsm.registerState(new SendMoveReplyAction(), "sendMoveReply");
		fsm.registerState(new ReceiveRoundResultBehaviour(), "receiveRoundResult");
		
		fsm.registerTransition("receiveMoveRequest", "sendMoveReply", ReceiveMoveRequestBehaviour.RECEIVED_MOVE_REQUEST);
		fsm.registerTransition("sendMoveReply", "receiveRoundResult", SendMoveReplyAction.SENT_MOVE_REPLY);
		fsm.registerTransition("receiveRoundResult", "receiveMoveRequest", ReceiveRoundResultBehaviour.RECEIVED_ROUND_RESULT);
		
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

	public void takedown()
	{
		try 
		{ 
			DFService.deregister(this); 
		}  
		catch (FIPAException fe) 
		{ 
			fe.printStackTrace(); 
		}
	}
	
	public abstract int getMove();
	
	
	public void clearCurrentMoveRequest()
	{
		_currentMoveRequest = null;
	}
	
	public void addMoveToMyRoundHistory(int move)
	{
		_myRoundMoveHistory.add(move);
	}
	
	public void addMoveToOpponentRoundHistory(int move)
	{
		_opponentsRoundMoveHistory.add(move);
	}
	
	public void clearRoundHistory()
	{
		_myRoundMoveHistory.clear();
		_opponentsRoundMoveHistory.clear();
	}

	public MoveRequest getCurrentMoveRequest() {
		return _currentMoveRequest;
	}

	public void setCurrentMoveRequest(MoveRequest request) {
		_currentMoveRequest = request;
	}



}