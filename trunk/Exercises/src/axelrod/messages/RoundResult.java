package axelrod.messages;

import jade.core.AID;
import jade.core.behaviours.SimpleBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

import java.util.Iterator;
import java.util.Scanner;

import axelrod.Round;

public class RoundResult extends MapMessage
{
	
	private static final String[] KEYS = {"roundNr", "gameNr","actionPlayer1", "actionPlayer2"};
	private static final String LANGUAGE = "axelrod-tournament";
	private static final String ONTOLOGY = "axelrod-tournament-round-result";
	
	public RoundResult(Round round)
	{
		this(round.getRoundNr(), round.getGameNr(), round.getConversationId(), round.getContestant1(), round.getContestant2(), round.getActionContestant1(), round.getActionContestant2());

	}
	
	public RoundResult(int roundNr, int gameNr, String conservationId, AID player1, AID player2, int actionPlayer1, int actionPlayer2)
	{
		super(ACLMessage.INFORM, KEYS);
		
		setLanguage(LANGUAGE);
		setOntology(ONTOLOGY);
		addReceiver(player1);
		addReceiver(player2);
		setConversationId(conservationId);
		setRoundNr(roundNr);
		setGameNr(gameNr);
		setActionPlayer1(actionPlayer1);
		setActionPlayer2(actionPlayer2);
	}
	
	public RoundResult(ACLMessage message)
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
	
	private void setActionPlayer1(int actionPlayer1)
	{
		setValue(KEYS[2], "" + actionPlayer1);
	}
	
	private void setActionPlayer2(int actionPlayer2)
	{
		setValue(KEYS[3], "" + actionPlayer2);
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
	
	public int getActionPlayer1()
	{
		return getInteger(KEYS[2]);
	}
	
	public int getActionPlayer2()
	{
		return getInteger(KEYS[3]);
	}
	
	public static MessageTemplate getMessageTemplate()
	{
		MessageTemplate mtLanguage = MessageTemplate.MatchLanguage(LANGUAGE);
		MessageTemplate mtOntology = MessageTemplate.MatchOntology(ONTOLOGY);
		
		MessageTemplate mt = MessageTemplate.and(mtOntology, mtLanguage);
		return mt;
	}
	
	
	
}
