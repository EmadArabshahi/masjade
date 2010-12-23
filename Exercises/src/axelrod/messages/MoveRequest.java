package axelrod.messages;

import jade.core.AID;
import jade.core.behaviours.SimpleBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

import java.util.Iterator;
import java.util.Scanner;

import axelrod.Round;

public class MoveRequest extends MapMessage
{
	
	private static final String[] KEYS = {"roundNr", "gameNr"};
	private static final String LANGUAGE = "axelrod-tournament";
	private static final String ONTOLOGY = "axelrod-tournament-move-request";
	
	public MoveRequest(Round round)
	{
		this(round.getRoundNr(), round.getGameNr(), round.getConversationId(), round.getContestant1().getAID(), round.getContestant2().getAID());
	}
	
	public MoveRequest(int roundNr, int gameNr, String conservationId, AID player1, AID player2)
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
	
	public MoveRequest(ACLMessage message)
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

	
	public static MessageTemplate getMessageTemplate()
	{
		MessageTemplate mtLanguage = MessageTemplate.MatchLanguage(LANGUAGE);
		MessageTemplate mtOntology = MessageTemplate.MatchOntology(ONTOLOGY);
		
		MessageTemplate mt = MessageTemplate.and(mtOntology, mtLanguage);
		return mt;
	}
	
	
	
}
