package axelrod.messages;

import jade.core.AID;
import jade.core.behaviours.SimpleBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.tools.logging.ontology.GetAllLoggers;

import java.util.Iterator;
import java.util.Scanner;

import axelrod.Round;

public class MoveRequest extends MapMessage
{
	private static final String[] KEYS = {"roundNr", "gameNr", "player1name", "player2name"};
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
		setPlayer1Name(player1.getName());
		setPlayer2Name(player2.getName());
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
	
	private void setPlayer1Name(String name)
	{
		setValue(KEYS[2], name);
	}
	
	private void setPlayer2Name(String name)
	{
		setValue(KEYS[3], name);
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
		AID actualAid = new AID();
		
		for (AID aid : aids)
		{
			if (aid.getName().equals(getValue(KEYS[2])))
			{
				actualAid = aid;
			}
		}
		return actualAid;
	}
	
	public AID getPlayer2()
	{
		AID[] aids = this.getReceivers();
		AID actualAid = new AID();
		
		for (AID aid : aids)
		{
			if (aid.getName().equals(getValue(KEYS[3])))
			{
				actualAid = aid;
			}
		}
		return actualAid;
	}

	
	public static MessageTemplate getMessageTemplate()
	{
		MessageTemplate mtLanguage = MessageTemplate.MatchLanguage(LANGUAGE);
		MessageTemplate mtOntology = MessageTemplate.MatchOntology(ONTOLOGY);
		
		MessageTemplate mt = MessageTemplate.and(mtOntology, mtLanguage);
		return mt;
	}
	
	
	
}
