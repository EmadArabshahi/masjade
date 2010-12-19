package axelrod.messages;

import jade.core.AID;
import jade.core.behaviours.SimpleBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

import java.util.Iterator;
import java.util.Scanner;

public class RequestMove extends MapMessage
{
	
	private static final String[] KEYS = {"roundNr", "gameNr"};
	private static final String LANGUAGE = "Axelrod-tournament";
	private static final String ONTOLOGY = "Axelrod-tournament-move-request";
	
	
	public RequestMove(int roundNr, int gameNr, String conservationId, AID player1, AID player2)
	{
		super(ACLMessage.REQUEST, KEYS);
		
		setLanguage(LANGUAGE);
		setOntology(ONTOLOGY);
		addReceiver(player1);
		addReceiver(player2);
		setConversationId(conservationId);
		setRoundNr(roundNr);
		setGameNr(gameNr);
	}
	
	public RequestMove(ACLMessage message)
	{
		super(message);
	}
	

	private void setRoundNr(int roundNr)
	{
		setValue(KEYS[0], "" + roundNr);
	}
	
	private void setGameNr(int gameNr)
	{
		setValue(KEYS[1], "" + gameNr);
	}
	
	
	public int getRoundNr()
	{
		return getInteger(KEYS[0]);
	}
	public int getGameNr()
	{
		return getInteger(KEYS[1]);
	}
	
	public AID getPlayer1()
	{
		AID[] aids = this.getReceivers();
		return aids[0];
	}
	public AID getPlayer2()	
	{
		AID[] aids = this.getReceivers();
		return aids[1];
	}

	
	
	
	
	
}
