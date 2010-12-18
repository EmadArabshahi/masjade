package axelrod.behaviours;

import axelrod.ContestantAgent;
import axelrod.Output;
import axelrod.Round;
import jade.core.AID;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;

public class SendRoundResultAction extends OneShotBehaviour {

	private Round _round;

	public SendRoundResultAction(Round round) {
		_round = round;
	}

	@Override
	public void action() {
		ACLMessage resultInform = new ACLMessage(ACLMessage.INFORM);
		
		resultInform.setConversationId(_round.getConversationId());
 		resultInform.addReceiver(_round.getContestant1());
		resultInform.addReceiver(_round.getContestant2());
		resultInform.setOntology("result");
		resultInform.setContent(String.format("%s;%s|%s;%s", _round.getContestant1().getName(), _round.getActionContestant1(), _round.getContestant2().getName(), _round.getActionContestant2()));
		
		myAgent.send(resultInform);

		Output.AgentMessage(myAgent, String.format("Inform sent (%s): %s", _round.getConversationId(), resultInform.getContent()));
	}
}
