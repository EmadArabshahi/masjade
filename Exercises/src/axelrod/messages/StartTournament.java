package axelrod.messages;

import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class StartTournament extends MapMessage
{
	
	private static final String[] KEYS = {};
	private static final String LANGUAGE = "axelrod-tournament";
	private static final String ONTOLOGY = "axelrod-tournament-start-tournament";
	
	public StartTournament()
	{
		super(ACLMessage.INFORM, KEYS);
		
		setLanguage(LANGUAGE);
		setOntology(ONTOLOGY);
	}
	
	public StartTournament(ACLMessage message)
	{
		super(message);
	}
	
	public static MessageTemplate getMessageTemplate()
	{
		MessageTemplate mtLanguage = MessageTemplate.MatchLanguage(LANGUAGE);
		MessageTemplate mtOntology = MessageTemplate.MatchOntology(ONTOLOGY);
		
		MessageTemplate mt = MessageTemplate.and(mtOntology, mtLanguage);
		return mt;
	}
	
	
}
