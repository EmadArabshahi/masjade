package contractnet.src;

import java.io.Serializable;
import java.util.ArrayList;

public class SubTask implements Serializable {

	private static final long serialVersionUID = -1189731653168407722L;
	public ArrayList<Component> components;
	
	public SubTask()
	{
		components = new ArrayList<Component>();
	}

}
