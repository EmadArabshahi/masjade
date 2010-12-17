package axelrod.messages;

import jade.core.AID;
import jade.core.behaviours.SimpleBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

import java.util.Iterator;
import java.util.Scanner;

public class RequestMove 
{
	
	private int _roundNr;
	private int _gameNr;
	private String _conservationNr;
	private AID _player1;
	private AID _player2;
	
	
	public RequestMove(int roundNr, int gameNr, String conservationNr, AID player1, AID player2)
	{
		this._roundNr = roundNr;
		this._gameNr = gameNr;
		this._conservationNr = conservationNr;
		this._player1 = player1;
		this._player2 = player2;
	}
	
	public RequestMove(ACLMessage message)
	{
		parseContent(message.getContent());
		
		Iterator iterator = message.getAllReceiver();
		
		this._player1 = (AID)iterator.next();
		this._player2 = (AID)iterator.next();
		
		this._conservationNr = message.getConversationId();
	}
	
	
	private void parseContent(String content)
	{
		Scanner s = new Scanner(content);
		
		int roundNr = -1;
		int gameNr = -1;
		
		
		if(s.hasNextInt())
			roundNr = s.nextInt();
		if(s.hasNextInt())
			gameNr = s.nextInt();
		
		this._roundNr = roundNr;
		this._gameNr = gameNr;
	}
	
	
	
	public int getRoundNr()
	{
		return _roundNr;
	}
	public int getGameNr()
	{
		return _gameNr;
	}
	public String conservationNr()
	{
		return _conservationNr;
	}
	public AID player1()
	{
		return _player1;
	}
	public AID player2()	
	{
		return _player2;
	}

	
	
	
	
	
}
