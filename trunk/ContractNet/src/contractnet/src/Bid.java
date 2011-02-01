package contractnet.src;

import jade.core.AID;

import java.io.Serializable;
import java.util.ArrayList;

public class Bid implements Serializable {

	private static final long serialVersionUID = 1203262705351327223L;
	
	public ArrayList<Computer> computerList;
	public AID bidderID;
	
	public Bid()
	{
		computerList = new ArrayList<Computer>();
		bidderID = null;
	}

}
