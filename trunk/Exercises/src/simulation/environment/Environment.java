package simulation.environment;

import java.awt.Point;
import java.util.Set;

public class Environment 
{
	
	public static int sleepTimeInMs = 1000;
	
	public static boolean east(String sAgent)
	{
		System.out.println(sAgent+" tries to go east");
		try {
			Thread.sleep(sleepTimeInMs);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return LogicalEnv.getEnv().east(sAgent);
	}
	
	public static boolean enter(String s,Point p,String color)
	{
		//System.out.println(s+" tries to enter ");
		return LogicalEnv.getEnv().enter(s,(double)p.x,(double)p.y,color);
	}
	
    public static boolean west(String sAgent)
	{
		//System.out.println(sAgent+" tries to go west");
		try {
			Thread.sleep(sleepTimeInMs);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return LogicalEnv.getEnv().west(sAgent);
	}
	
    public static boolean north(String sAgent)
	{
		//System.out.println(sAgent+" tries to go north");
		try {
			Thread.sleep(sleepTimeInMs);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return LogicalEnv.getEnv().north(sAgent);
	}
	
    public static boolean south(String sAgent)
	{
		//System.out.println(sAgent+" tries to go south");
		try {
			Thread.sleep(sleepTimeInMs);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return LogicalEnv.getEnv().south(sAgent);
	}
	
    public static Point getPosition(String sAgent)
	{
		//System.out.println(sAgent+" tries to learn her position");
		return LogicalEnv.getEnv().getPosition(sAgent);
	}
	
	public static Set<Point> senseStones(String sAgent)
	{
		//System.out.println(sAgent + " tries to sense Stones");
		return LogicalEnv.getEnv().senseStones(sAgent); 
	}
	
	public static Set<Point> senseAgents(String sAgent)
	{
		//System.out.println(sAgent + " tries to sense Agents");
		return LogicalEnv.getEnv().senseAgents(sAgent); 
	}
	
	public static Set<Point> senseApples(String sAgent)
	{
		//System.out.println(sAgent+" tries to sense bombs");
		return LogicalEnv.getEnv().senseApples(sAgent);		
	}
	
	public static boolean takeApple(String sAgent)
	{
		System.out.println(sAgent+" tries to take an apple");
		return LogicalEnv.getEnv().pickupApple(sAgent);

	}
	
	

	

}
