package simulation.environment;

import java.awt.Point;
import java.util.Set;

public class Environment 
{
	
	
	public static int sleepTimeInMs = 1000;
	
	public static Object lockObject = new Object();
	
	public static boolean east(String sAgent)
	{
		System.out.println(sAgent+" tries to go east");
		boolean result = false;
		
	    synchronized(lockObject)
	    {
	    
	    	LogicalEnv environment = LogicalEnv.getEnv();
	    	environment._blockingActions++;
	    	result = environment.east(sAgent);
	    	
	    	if(environment._blockingActions == environment._agents.size())
	    	{
	    		environment._blockingActions = 0;
	    		environment._round++;
	    		lockObject.notifyAll();
	    	}
	    	else
	    	{
	    		try
	    		{
	    			lockObject.wait();
	    		}
	    		catch(InterruptedException e)
	    		{
	    			//continue
	    		}
	    	}
	    }
	    
	    
		try 
		{
			Thread.sleep(sleepTimeInMs);
		}
		catch (InterruptedException e) 
		{
			e.printStackTrace();
		}
		
		return result;
	}
	
	public static boolean enter(String s,Point p,String color)
	{
		synchronized(lockObject)
		{
			//System.out.println(s+" tries to enter ");
			return LogicalEnv.getEnv().enter(s,(double)p.x,(double)p.y,color);
		}
	}
	
    public static boolean west(String sAgent)
	{
    	System.out.println(sAgent+" tries to go westt");
    	
		boolean result = false;
		
	    synchronized(lockObject)
	    {
	    
	    	LogicalEnv environment = LogicalEnv.getEnv();
	    	environment._blockingActions++;
	    	result = environment.west(sAgent);
	    	
	    	if(environment._blockingActions == environment._agents.size())
	    	{
	    		environment._blockingActions = 0;
	    		environment._round++;
	    		lockObject.notifyAll();
	    	}
	    	else
	    	{
	    		try
	    		{
	    			lockObject.wait();
	    		}
	    		catch(InterruptedException e)
	    		{
	    			//continue
	    		}
	    	}
	    }
	    
	    
		try 
		{
			Thread.sleep(sleepTimeInMs);
		}
		catch (InterruptedException e) 
		{
			e.printStackTrace();
		}
		
		return result;
	}
	
    public static boolean north(String sAgent)
	{
    	System.out.println(sAgent+" tries to go north");
		boolean result = false;
		
	    synchronized(lockObject)
	    {
	    
	    	LogicalEnv environment = LogicalEnv.getEnv();
	    	environment._blockingActions++;
	    	result = environment.north(sAgent);
	    	
	    	if(environment._blockingActions == environment._agents.size())
	    	{
	    		environment._blockingActions = 0;
	    		environment._round++;
	    		lockObject.notifyAll();
	    	}
	    	else
	    	{
	    		try
	    		{
	    			lockObject.wait();
	    		}
	    		catch(InterruptedException e)
	    		{
	    			//continue
	    		}
	    	}
	    }
	    
	    
		try 
		{
			Thread.sleep(sleepTimeInMs);
		}
		catch (InterruptedException e) 
		{
			e.printStackTrace();
		}
		
		return result;
	}
	
    public static boolean south(String sAgent)
	{
    	System.out.println(sAgent+" tries to go south");
		boolean result = false;
		
	    synchronized(lockObject)
	    {
	    
	    	LogicalEnv environment = LogicalEnv.getEnv();
	    	environment._blockingActions++;
	    	result = environment.south(sAgent);
	    	
	    	if(environment._blockingActions == environment._agents.size())
	    	{
	    		environment._blockingActions = 0;
	    		environment._round++;
	    		lockObject.notifyAll();
	    	}
	    	else
	    	{
	    		try
	    		{
	    			lockObject.wait();
	    		}
	    		catch(InterruptedException e)
	    		{
	    			//continue
	    		}
	    	}
	    }
	    
	    
		try 
		{
			Thread.sleep(sleepTimeInMs);
		}
		catch (InterruptedException e) 
		{
			e.printStackTrace();
		}
		
		return result;
	}
	
    public static Point getPosition(String sAgent)
	{
		System.out.println(sAgent+" tries to learn her position");
		
		synchronized(lockObject)
		{	
			return LogicalEnv.getEnv().getPosition(sAgent);
		}
	}
	
	public static Set<Point> senseStones(String sAgent)
	{
		System.out.println(sAgent + " tries to sense Stones");
		
		synchronized(lockObject)
		{
			return LogicalEnv.getEnv().senseStones(sAgent); 
		}
	}
	
	public static Set<Point> senseAgents(String sAgent)
	{
		System.out.println(sAgent + " tries to sense Agents");
		
		synchronized(lockObject)
		{
			return LogicalEnv.getEnv().senseAgents(sAgent); 
		}
	}
	
	public static Set<Point> senseApples(String sAgent)
	{
		System.out.println(sAgent+" tries to sense bombs");
		
		synchronized(lockObject)
		{
			return LogicalEnv.getEnv().senseApples(sAgent);		
		}
	}
	
	public static boolean takeApple(String sAgent)
	{
		System.out.println(sAgent+" tries to take an apple");
		boolean result = false;
		
	    synchronized(lockObject)
	    {
	    
	    	LogicalEnv environment = LogicalEnv.getEnv();
	    	environment._blockingActions++;
	    	result = environment.pickupApple(sAgent);
	    	
	    	if(environment._blockingActions == environment._agents.size())
	    	{
	    		environment._blockingActions = 0;
	    		environment._round++;
	    		lockObject.notifyAll();
	    	}
	    	else
	    	{
	    		try
	    		{
	    			lockObject.wait();
	    		}
	    		catch(InterruptedException e)
	    		{
	    			//continue
	    		}
	    	}
	    }
	    
	    
		try 
		{
			Thread.sleep(sleepTimeInMs);
		}
		catch (InterruptedException e) 
		{
			e.printStackTrace();
		}
		
		return result;

	}
	
	
	public static int getMaxAppleCapacity()
	{
		synchronized(lockObject)
		{
			return LogicalEnv.getEnv().getMaximumAppleCapacity();
		}
	}

	

}
