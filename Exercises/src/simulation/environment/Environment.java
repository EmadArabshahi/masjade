package simulation.environment;

import java.awt.Point;
import java.util.Set;
import java.util.List;

public class Environment 
{
	
	public static Object lockObject = new Object();
	
	public static Object startLockObject = new Object();
	
	private static final int MOVE_NORTH = 0;
	private static final int MOVE_EAST = 1;
	private static final int MOVE_SOUTH = 2;
	private static final int MOVE_WEST = 3;
	private static final int PICKUP_APPLE = 4;
	private static final int EAT_APPLE = 5;
	private static final int TRADE_APPLE = 6;
	
	
	
	
	/*********************************************************************************************
	 * 
	 * BLOCKING ACTIONS FOR AGENTS
	 * 
	 *********************************************************************************************/
	
	
	/**
	 * Performs a blocking action on the environment, which means the caller is released when the round ends.
	 * @param blockingActionType The type of the action to perform
	 * @param sAgent The name of the agent.
	 * @return A boolean indicating whether this method was successful.
	 */
	private static boolean doBlockingAction(int blockingActionType, String sAgent)
	{
		long sleepTimeInMs = 0;
		boolean result = false;
		
		synchronized(lockObject)
		{
	    	LogicalEnv environment = LogicalEnv.getEnv();
	    	environment._blockingActions++;
	    	sleepTimeInMs = environment.getSleepTimeInMs();
	    	
	    	if(environment._mode != LogicalEnv.STOPPED)
	    	switch(blockingActionType)
	    	{
	    		case MOVE_NORTH : result = environment.north(sAgent); break;
	    		case MOVE_EAST : result = environment.east(sAgent); break;
	    		case MOVE_SOUTH : result = environment.south(sAgent); break;
	    		case MOVE_WEST : result = environment.west(sAgent); break;
	    		case PICKUP_APPLE : result = environment.pickupApple(sAgent); break;
	    		case EAT_APPLE : result = environment.eatApple(sAgent); break;
	    		case TRADE_APPLE : result = environment.trade(sAgent); break;
	    	}
	    	   	
	    	if(environment._blockingActions >= environment._agents.size())
	    	{
	    		environment.endRound();
	    		
	    		if(environment._mode == LogicalEnv.CONTINUES_MODE)
	    		{
	    			environment.beginRound();
	    			
	    			lockObject.notifyAll();
	    		}
	    		else if(environment._mode == LogicalEnv.STEP_BY_STEP_MODE)
	    		{
	    			if(environment._nextRoundPermitted)
	    			{
	    				environment.beginRound();
	    				
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
			//do nothing
		}
		return result;
	}
	
	public static void waitForStart()
	{
		synchronized(lockObject)
		{
			try
			{
				lockObject.wait();
			}
			catch(InterruptedException e)
			{
				//do nothing.
			}
		}
	}
	
	public static boolean north(String sAgent)
	{
		return doBlockingAction(MOVE_NORTH, sAgent);
	}
	
	public static boolean east(String sAgent)
	{
		return doBlockingAction(MOVE_EAST, sAgent);
	}
	
	public static boolean south(String sAgent)
	{
		return doBlockingAction(MOVE_SOUTH, sAgent);
	}
	
	public static boolean west(String sAgent)
	{
		return doBlockingAction(MOVE_WEST, sAgent);
	}
	
	public static boolean pickupApple(String sAgent)
	{
		return doBlockingAction(PICKUP_APPLE, sAgent);
	}
	
	public static boolean eatApple(String sAgent)
	{
		return doBlockingAction(EAT_APPLE, sAgent);
	}
	
	public static boolean tradeApple(String sAgent)
	{
		return doBlockingAction(TRADE_APPLE, sAgent);
	}


	/*********************************************************************************************
	 * 
	 * NON-BLOCKING ACTIONS FOR AGENTS
	 * 
	 *********************************************************************************************/
	
    public static Point getPosition(String sAgent)
	{			
		synchronized(lockObject)
		{	
			return LogicalEnv.getEnv().getPosition(sAgent);
		}
	}
	
	public static Set<Point> senseStones(String sAgent)
	{
		synchronized(lockObject)
		{
			return LogicalEnv.getEnv().senseStones(sAgent); 
		}
	}
	
	public static Set<Point> senseAgents(String sAgent)
	{
		synchronized(lockObject)
		{
			return LogicalEnv.getEnv().senseAgents(sAgent); 
		}
	}
	
	public static Set<Point> senseApples(String sAgent)
	{
		synchronized(lockObject)
		{
			return LogicalEnv.getEnv().senseApples(sAgent);		
		}
	}
	
	public static int getStartingMoney()
	{
		return Agent.startingMoney;
	}
	
	public static int getStartingEnergyLevel()
	{
		return Agent.startingEnergyLevel;
	}
	
	public static int getMaxAppleCapacity()
	{
		synchronized(lockObject)
		{
			return LogicalEnv.getEnv().getMaximumAppleCapacity();
		}
	}

	public static int getEnergy(String sAgent)
	{
		synchronized(lockObject)
		{
			return LogicalEnv.getEnv().getEnergy(sAgent);
		}
	}
	
	public static void pleaseKillMe(String sAgent)
	{
		synchronized(lockObject)
		{
			LogicalEnv.getEnv()._blockingActions++;
			LogicalEnv.getEnv().removeAgent(sAgent);
		}
	}
	
	public static int getApples(String sAgent)
	{
		synchronized(lockObject)
		{
			return LogicalEnv.getEnv().getApples(sAgent);
		}
	}
	
	public static int getMoney(String sAgent)
	{
		synchronized(lockObject)
		{
			return LogicalEnv.getEnv().getMoney(sAgent);
		}
	}
	
	public static int getEnergyCost()
	{
		synchronized(lockObject)
		{
			return LogicalEnv.getEnv().getEnergyCost();
		}
	}
	
	public static int getEneryGain()
	{
		synchronized(lockObject)
		{
			return LogicalEnv.getEnv().getEnergyGain();
		}
	}
	
	public static int proposeToSell(String sAgent, int moneyInCents)
	{
		int proposalId = -1;
		synchronized(lockObject)
		{
			proposalId = Market.getInstance().proposeToSell(sAgent, moneyInCents);
		}
		return proposalId;
	}
	
	public static int requestToBuy(String sAgent, int moneyInCents)
	{
		int requestId = -1;
		synchronized(lockObject)
		{
			requestId = Market.getInstance().requestToBuy(sAgent, moneyInCents);
		}
		return requestId;
	}
	
	public static void removeAllProposals(String sAgent)
	{
		synchronized(lockObject)
		{
			Market.getInstance().removeAllProposals(sAgent);
		}
	}
	
	public static void removeAllRequests(String sAgent)
	{
		synchronized(lockObject)
		{
			Market.getInstance().removeAllRequest(sAgent);
		}
	}
	
	public static List<Integer> getSuccesfullBuys(String sAgent)
	{
		synchronized(lockObject)
		{
			return Market.getInstance().getSuccesfullBuys(sAgent);
		}
	}
	
	public static List<Integer> getSuccesfullSells(String sAgent)
	{
		synchronized(lockObject)
		{
			return Market.getInstance().getSuccesfullSells(sAgent);
		}
	}
	
	
	public static void removeProposal(String sAgent, int proposalId)
	{
		synchronized(lockObject)
		{
			Market.getInstance().removeProposal(sAgent, proposalId);
		}
	}
	
	public static void removeRequest(String sAgent, int requestId)
	{
		synchronized(lockObject)
		{
			Market.getInstance().removeRequest(sAgent, requestId);
		}
	}
	
	public static boolean mustTrade(String sAgent)
	{
		synchronized(lockObject)
		{
			boolean mustTrade = Market.getInstance().mustTrade(sAgent);
			return mustTrade;
		}
	}

	public static List<TradeDescription> getTradeAction(String sAgent)
	{
		List<TradeDescription> list;
		synchronized(lockObject)
		{
			list = Market.getInstance().getTradeAction(sAgent);
		}
		return list;
	}
	
	
	
	
	

	/*********************************************************************************************
	 * 
	 * NON BLOCKING ACTIONS FOR THE ENVIRONMENT MANAGER
	 * 
	 *********************************************************************************************/
	
	public static void startButtonPressed()
	{
		synchronized(lockObject)
		{
			LogicalEnv environment = LogicalEnv.getEnv();
			
			if(environment._mode == LogicalEnv.CONTINUES_MODE)
			{
				//Nothing changes, allready in continues mode.
			}
			else if(environment._mode == LogicalEnv.STEP_BY_STEP_MODE)
			{
				//Step by step mode, so have to release the next step and changes to continues.
				environment._mode = LogicalEnv.CONTINUES_MODE;
				
				//If all agents are blocked, goto next round and notify all
				 if(environment._blockingActions == environment._agents.size())
				 {
	 				environment.beginRound();
	 				lockObject.notifyAll();
				 } //otherwise, set netRoundPermitted = true;
				 else
				 {
					 environment._nextRoundPermitted = true;
				 }
			}
			else if(environment._mode == LogicalEnv.WAITING_FOR_START_SIGNAL)
			{
				//Change mode to continues
				environment._mode = LogicalEnv.CONTINUES_MODE;
				
				environment.beginRound();
				
				//Start the round by releasing the agents.
				lockObject.notifyAll();
			}
			else
			{
				//If mode is stopped, then the play button has no effect.
			}
			
		}
	}
	
	public static void stepButtonPressed()
	{
		synchronized(lockObject)
		{
			LogicalEnv environment = LogicalEnv.getEnv();
			
			if(environment._mode == LogicalEnv.STEP_BY_STEP_MODE)
			{
				//goto the next step, by either releasing all agents if they are blocked, or set the nextround permitted to true.
				//If all agents are blocked, goto next round and notify all
				 if(environment._blockingActions >= environment._agents.size())
				 {			 
					environment.beginRound();
	 				
	 				lockObject.notifyAll();
				 } //otherwise, set netRoundPermitted = true;
				 else
				 {
					 environment._nextRoundPermitted = true;
				 }
			}
			else if(environment._mode == LogicalEnv.CONTINUES_MODE)
			{
				//change mode to step by step.
				environment._mode = LogicalEnv.STEP_BY_STEP_MODE;
				environment._nextRoundPermitted = false;
			}
			else if(environment._mode == LogicalEnv.WAITING_FOR_START_SIGNAL)
			{
				//Change mode to continues
				environment._mode = LogicalEnv.STEP_BY_STEP_MODE;
				
				environment.beginRound();
				
				//Start the round by releasing the agents.
				lockObject.notifyAll();
			}
			else
			{
				//If mode is stopped, then the step button has no effect.
			}
		}
		
		
		
	}
	
	public static void stopButtonPressed()
	{
		synchronized(lockObject)
		{
			LogicalEnv environment = LogicalEnv.getEnv();
			//It does not matter what mode the environment is in. it is stopped.
			environment._mode = LogicalEnv.STOPPED;
		}
	}
	
	public static void resetButtonPressed()
	{
		synchronized(lockObject)
		{
			LogicalEnv environment = LogicalEnv.getEnv();
			//It does not matter what mode the environment is in.
			environment.initialize();
			environment._mode = LogicalEnv.WAITING_FOR_START_SIGNAL;
		}
	}
	
}
