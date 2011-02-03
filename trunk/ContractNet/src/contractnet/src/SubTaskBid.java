package contractnet.src;

import jade.core.AID;

import java.io.Serializable;
import java.util.ArrayList;

public class SubTaskBid implements Serializable {

	private static final long serialVersionUID = -6910728373206298630L;
	public AID bidderID;
	public ArrayList<Component> componentList;
	
	public SubTaskBid(AID bidderID)
	{
		componentList = new ArrayList<Component>();
		this.bidderID = bidderID;
	}

}
