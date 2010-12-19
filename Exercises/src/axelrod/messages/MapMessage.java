package axelrod.messages;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.StringTokenizer;

import jade.core.AID;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class MapMessage
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5098567941597045190L;
	
	private static String KEY_VALUE_DELIMITER = "=";
	private static String ELEMENTS_DELIMITER = ";";
	
	/**
	 * Indicates wether the content of the message is given at construction time.
	 */
	private boolean _contentGiven;
	
	private ACLMessage _message;
	
	private Map<String, String> _map;	
	
	protected MapMessage(int performative, String[] supportedKeys)
	{
		if(supportedKeys == null)
			throw new IllegalArgumentException("value supportedKeys cannot be null.");
		
		_message = new ACLMessage(performative);
		_map = new HashMap<String, String>(supportedKeys.length);
		_contentGiven = false;
	}
	
	
	protected MapMessage(ACLMessage aclMessage)
	{
		if(aclMessage == null)
			throw new IllegalArgumentException("value aclMessage cannot be null.");
		
		_message = aclMessage;
		parseContent();
		_contentGiven = true;
	}

	
	private void parseContent()
	{
		// Gets the message contents.
		String content = _message.getContent();
		
		String delimiters = KEY_VALUE_DELIMITER + ELEMENTS_DELIMITER;
		//searches the content for new key value pairs:  syntax(key=value;)
		StringTokenizer parser = new StringTokenizer(content, delimiters, false);
		
		while(parser.hasMoreTokens())
		{
			String key = parser.nextToken();
			String value = "";
			if(parser.hasMoreTokens())
				value = parser.nextToken();
			
			_map.put(key, value);
		}
	}
	
	private void generateContent()
	{
		StringBuilder sb = new StringBuilder();
		for(Entry<String,String> pair : _map.entrySet())
		{
			sb.append(pair.getKey());
			sb.append(KEY_VALUE_DELIMITER);
			sb.append(pair.getValue());
			sb.append(ELEMENTS_DELIMITER);
		}
		_message.setContent(sb.toString());
	}
	
	public ACLMessage getMessage()
	{
		generateContent();
		return (ACLMessage)_message.clone();
	}
	
	public String getValue(String key)
	{
		if(_map.containsKey(key))
			return _map.get(key);
		else
			throw new IllegalArgumentException("Given key is not registred with this messaage.");
	}
	
	public int getInteger(String key) throws NumberFormatException
	{
		String value = getValue(key);
		int integerValue = Integer.parseInt(value);
		return integerValue;
	}
	
	public void setValue(String key, String value)
	{
		if(_contentGiven)
			throw new IllegalStateException("Message is readonly.");
		
		if(_map.containsKey(key))
			_map.put(key, value);
		else
			throw new IllegalArgumentException("Given key is not registred with this message.");
	}
	
	public void addReceiver(AID receiver)
	{
		_message.addReceiver(receiver);
	}
	
	public void setPerformative(int perf)
	{
		_message.setPerformative(perf);
	}
	
	public void setConversationId(String id)
	{
		_message.setConversationId(id);
	}
	
	public void setLanguage(String language)
	{
		_message.setLanguage(language);
	}
	
	public void setOntology(String ontology)
	{
		_message.setOntology(ontology);
	}

	public int getPerformative()
	{
		return _message.getPerformative();
	}
	
	public String getConversationId()
	{
		return _message.getConversationId();
	}
	
	public String getLanguage()
	{
		return _message.getLanguage();
	}
	
	public String getOntology()
	{
		return _message.getOntology();
	}

	public AID[] getReceivers()
	{
		List<AID> list = new ArrayList<AID>();
		Iterator<AID> i = _message.getAllReceiver();
		while(i.hasNext())
		{
			list.add(i.next());
		}
		AID[] type = new AID[0];
		return list.toArray(type);
	}
}
