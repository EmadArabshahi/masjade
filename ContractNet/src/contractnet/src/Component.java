package contractnet.src;

import java.io.Serializable;

public class Component implements Serializable {
	
	private static final long serialVersionUID = 391256102292479745L;
	private String manufacturer;
	private double quality;
	private double price;
	private ComponentType type;
	
	public enum ComponentType
	{
		CPU,
		GPU,
		Motherboard
	}
	
	public Component( ComponentType type, String manufacturer, double quality, double price)
	{
		this.setType(type);
		this.setManufacturer(manufacturer);
		this.setQuality(quality);
		this.setPrice(price);
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public String getManufacturer() {
		return manufacturer;
	}


	public void setType(ComponentType type) {
		this.type = type;
	}

	public ComponentType getType() {
		return type;
	}

	public void setQuality(double quality) {
		this.quality = quality;
	}

	public double getQuality() {
		return quality;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public double getPrice() {
		return price;
	}
	
	

}
