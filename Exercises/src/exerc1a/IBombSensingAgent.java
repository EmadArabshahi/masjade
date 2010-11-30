package exerc1a;

import java.awt.Point;
import java.util.Set;

public interface IBombSensingAgent 
{
	void bombsSensed(Set<Point> bombPositions);
	
	String getAgentName();
}
