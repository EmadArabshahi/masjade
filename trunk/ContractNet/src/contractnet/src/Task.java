package contractnet.src;

import java.io.Serializable;
import java.util.ArrayList;

import contractnet.src.Component.ComponentType;

public class Task implements Serializable {

	private static final long serialVersionUID = -4296914243665659181L;
	
	public ArrayList<Computer> computerList;
	
	public Task()
	{
		computerList = new ArrayList<Computer>();
		
		Component cpu = new Component( ComponentType.CPU, "intel", 3000, 1000);
		Component motherboard = new Component( ComponentType.Motherboard, "hp", 2000, 100);
		Component gpu = new Component( ComponentType.GPU, "compaq", 1000, 100);
		computerList.add( new Computer(cpu, gpu, motherboard));
	}

}
