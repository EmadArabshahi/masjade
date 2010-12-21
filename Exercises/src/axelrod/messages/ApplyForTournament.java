package axelrod.messages;

import jade.core.AID;
import jade.core.behaviours.SimpleBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

import java.util.Iterator;
import java.util.Scanner;

import axelrod.Round;

public class ApplyForTournament extends MapMessage
{
	
	private static final String[] KEYS = {"strategy"};
	private static final String LANGUAGE = "axelrod-tournament";
	private static final String ONTOLOGY = "axelrod-tournament-apply-for-tournament";
		
	public ApplyForTournament(String strategy, AID tournamentHost)
	{
		super(ACLMessage.INFORM, KEYS);
		
		setLanguage(LANGUAGE);
		setOntology(ONTOLOGY);
		addReceiver(tournamentHost);
		setStrategy(strategy);
	}
	
	public ApplyForTournament(ACLMessage message)
	{
		super(message);
	}

	
	private void setStrategy(String strategy)
	{
		setValue(KEYS[0], strategy);
	}
	
	public String getStrategy()
	{
		return getValue(KEYS[0]);
	}
	
	public AID getPlayer()
	{
		AID aid = this.getSender();
		return aid;
	}
	

	public static MessageTemplate getMessageTemplate()
	{
		MessageTemplate mtLanguage = MessageTemplate.MatchLanguage(LANGUAGE);
		MessageTemplate mtOntology = MessageTemplate.MatchOntology(ONTOLOGY);
		
		MessageTemplate mt = MessageTemplate.and(mtOntology, mtLanguage);
		return mt;
	}
	
	
	
}

