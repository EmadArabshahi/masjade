package simulation.environment;

import java.awt.Point;
import java.util.Set;

public class Environment 
{
	
	public static Object lockObject = new Object();
	
	public static Object startLockObject = new Object();
	
	public static boolean east(String sAgent)
	{

		long sleepTimeInMs = 0;
		boolean result = false;
		
	    synchronized(lockObject)
	    {
	    
	    	LogicalEnv environment = LogicalEnv.getEnv();
	    	environment._blockingActions++;
	    	sleepTimeInMs = environment.getSleepTimeInMs();
	    	result = environment.east(sAgent);
	    	
	    	if(environment._blockingActions == environment._agents.size())
	    	{
	    		if(environment._mode == LogicalEnv.CONTINUES_MODE)
	    		{
	    			environment._blockingActions = 0;
	    			environment._round++;
	    			lockObject.notifyAll();
	    		}
	    		else if(environment._mode == LogicalEnv.STEP_BY_STEP_MODE)
	    		{
	    			if(environment._nextRoundPermitted)
	    			{
	    				environment._blockingActions = 0;
	    				environment._round++;
	    				environment._nextRoundPermitted = false;
	    				
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
	    					e.printStackTrace();
	    				}
	    			}
	    		}
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
	
	/*
	public static boolean enter(String s,Point p,String color)
	{
		synchronized(lockObject)
		{
			//System.out.println(s+" tries to enter ");
			return LogicalEnv.getEnv().enter(s,(double)p.x,(double)p.y,color);
		}
	}
	*/
    public static boolean west(String sAgent)
	{

    	long sleepTimeInMs = 0;
		boolean result = false;
		
	    synchronized(lockObject)
	    {
	    
	    	LogicalEnv environment = LogicalEnv.getEnv();
	    	environment._blockingActions++;
	    	sleepTimeInMs = environment.getSleepTimeInMs();
	    	result = environment.west(sAgent);
	    	
	    	if(environment._blockingActions == environment._agents.size())
	    	{
	    		if(environment._mode == LogicalEnv.CONTINUES_MODE)
	    		{
	    			environment._blockingActions = 0;
	    			environment._round++;
	    			lockObject.notifyAll();
	    		}
	    		else if(environment._mode == LogicalEnv.STEP_BY_STEP_MODE)
	    		{
	    			if(environment._nextRoundPermitted)
	    			{
	    				environment._blockingActions = 0;
	    				environment._round++;
	    				environment._nextRoundPermitted = false;
	    				
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
	    					e.printStackTrace();
	    				}
	    			}
	    		}
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
    	
		boolean result = false;
		long sleepTimeInMs = 0;
		
	    synchronized(lockObject)
	    {
	    
	    	LogicalEnv environment = LogicalEnv.getEnv();
	    	environment._blockingActions++;
	    	sleepTimeInMs = environment.getSleepTimeInMs();
	    	result = environment.north(sAgent);
	    	
	    	
	    	if(environment._blockingActions == environment._agents.size())
	    	{
	    		if(environment._mode == LogicalEnv.CONTINUES_MODE)
	    		{
	    			environment._blockingActions = 0;
	    			environment._round++;
	    			lockObject.notifyAll();
	    		}
	    		else if(environment._mode == LogicalEnv.STEP_BY_STEP_MODE)
	    		{
	    			if(environment._nextRoundPermitted)
	    			{
	    				environment._blockingActions = 0;
	    				environment._round++;
	    				environment._nextRoundPermitted = false;
	    				
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
    	long sleepTimeInMs = 0;
		boolean result = false;
		
	    synchronized(lockObject)
	    {
	    
	    	LogicalEnv environment = LogicalEnv.getEnv();
	    	environment._blockingActions++;
	    	sleepTimeInMs = environment.getSleepTimeInMs();
	    	result = environment.south(sAgent);
	    
	    	System.out.println("AgentS: " + environment._agents.size());
	    	
	    	if(environment._blockingActions == environment._agents.size())
	    	{
	    		if(environment._mode == LogicalEnv.CONTINUES_MODE)
	    		{
	    			environment._blockingActions = 0;
	    			environment._round++;
	    			lockObject.notifyAll();
	    		}
	    		else if(environment._mode == LogicalEnv.STEP_BY_STEP_MODE)
	    		{
	    			if(environment._nextRoundPermitted)
	    			{
	    				environment._blockingActions = 0;
	    				environment._round++;
	    				environment._nextRoundPermitted = false;
	    				
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
	    					e.printStackTrace();
	    				}
	    			}
	    		}
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
		long sleepTimeInMs = 0;
		
	    synchronized(lockObject)
	    {
	    
	    	LogicalEnv environment = LogicalEnv.getEnv();
	    	environment._blockingActions++;
	    	sleepTimeInMs = environment.getSleepTimeInMs();
	    	result = environment.pickupApple(sAgent);
	    	
	    	if(environment._blockingActions == environment._agents.size())
	    	{
	    		if(environment._mode == LogicalEnv.CONTINUES_MODE)
	    		{
	    			environment._blockingActions = 0;
	    			environment._round++;
	    			
	    			lockObject.notifyAll();
	    		}
	    		else if(environment._mode == LogicalEnv.STEP_BY_STEP_MODE)
	    		{
	    			if(environment._nextRoundPermitted)
	    			{
	    				environment._blockingActions = 0;
	    				environment._round++;
	    				environment._nextRoundPermitted = false;
	    				
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

	public static void gotoNextStep()
	{
		 synchronized(lockObject)
		 {
			 LogicalEnv environment = LogicalEnv.getEnv();
		     
			 if(environment._blockingActions == environment._agents.size())
			 {
				 environment._blockingActions = 0;
 				environment._round++;
 				environment._nextRoundPermitted = false;
 				
 				lockObject.notifyAll();
			 }
			 else
			 {
				 environment._nextRoundPermitted = true;
			 }
		 }
	}
	
	public static void init()
	{
		synchronized(lockObject)
		{
			LogicalEnv environment = LogicalEnv.getEnv();
			
			environment.initialize();
		}
	}
	
	public static void waitForStart()
	{
		synchronized(startLockObject)
		{
			try
			{
				startLockObject.wait();
			}
			catch(InterruptedException e)
			{
				//do nothing
			}
		}
		
	}

	public static void start()
	{
		synchronized(startLockObject)
		{
			startLockObject.notifyAll();
		}
	}
}
