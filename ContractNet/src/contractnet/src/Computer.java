package contractnet.src;

import java.io.Serializable;


public class Computer implements Serializable {
	
	private static final long serialVersionUID = -5433744768986166357L;
	private Component cpu;
	private Component gpu;
	private Component motherboard;
	
	public Computer( Component cpu, Component gpu, Component motherboard)
	{
		this.cpu = cpu;
		this.gpu = gpu;
		this.motherboard = motherboard;
	}

	public void setCpu(Component cpu) {
		this.cpu = cpu;
	}

	public Component getCpu() {
		return cpu;
	}

	public void setGpu(Component gpu) {
		this.gpu = gpu;
	}

	public Component getGpu() {
		return gpu;
	}

	public void setMotherboard(Component motherboard) {
		this.motherboard = motherboard;
	}

	public Component getMotherboard() {
		return motherboard;
	}

}
