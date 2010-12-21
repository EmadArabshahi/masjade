package axelrod.behaviours;

import axelrod.Output;
import axelrod.Round;
import axelrod.agents.AbstractContestantAgent;
import axelrod.messages.RoundResult;
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
		
		RoundResult roundResult = new RoundResult(_round);
		myAgent.send(roundResult.getMessage());

		Output.AgentMessage(myAgent, String.format("Inform sent (%s): %s", roundResult.getConversationId(), roundResult.getMessage().getContent()));
	}
}
