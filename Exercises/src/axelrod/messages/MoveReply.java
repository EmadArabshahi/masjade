package axelrod.messages;

import jade.core.AID;
import jade.core.behaviours.SimpleBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

import java.util.Iterator;
import java.util.Scanner;

public class MoveReply extends MapMessage
{
	
	private static final String[] KEYS = {"roundNr", "gameNr", "action"};
	private static final String LANGUAGE = "axelrod-tournament";
	private static final String ONTOLOGY = "axelrod-tournament-move-reply";
	
	
		
	public MoveReply(MoveRequest messageToReply, int action)
	{
		super(messageToReply.getMessage().createReply(), KEYS);
		this.parseContent(messageToReply.getMessage().getContent());
		
		setLanguage(LANGUAGE);
		setOntology(ONTOLOGY);
		setAction(action);
	}
	
	public MoveReply(ACLMessage message)
	{
		super(message);
	}
	

	private void setAction(int action)
	{
		setValue(KEYS[2], "" + action);
	}
	
	private void setRoundNr(int roundNr)
	{
		setValue(KEYS[0], "" + roundNr);
	}
	
	private void setGameNr(int gameNr)
	{
		setValue(KEYS[1], "" + gameNr);
	}
	
	
	public int getAction()
	{
		return getInteger(KEYS[2]);
	}
	
	public int getRoundNr()
	{
		return getInteger(KEYS[0]);
	}
	
	public int getGameNr()
	{
		return getInteger(KEYS[1]);
	}
	
	public AID getPlayer()
	{
		return getSender();
	}
	
	public static MessageTemplate getMessageTemplate(String conversationId)
	{
		MessageTemplate mtOntology = MessageTemplate.MatchOntology(ONTOLOGY);
		MessageTemplate mtLanguage = MessageTemplate.MatchLanguage(LANGUAGE);
		MessageTemplate mtConversationId = MessageTemplate.MatchConversationId(conversationId);
		
		MessageTemplate mt1 = MessageTemplate.and(mtOntology, mtLanguage);
		MessageTemplate mt = MessageTemplate.and(mt1, mtConversationId);
		return mt;
	}
	
	
	
	
	
}
