package simulation.environment;



// Standard java imports

import simulation.environment.graphs.EnergyGraph;
import simulation.environment.lib.ObsVect;
import simulation.environment.lib.ObsVectListener;
import simulation.environment.lib.Signal;

import simulation.agents.*;

import java.awt.Point;
import java.util.*;

import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import simulation.environment.Agent;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

import jade.core.IMTPManager;
import jade.core.MainContainerImpl;
import jade.core.ProfileException;
import jade.core.ResourceManager;
import jade.core.ServiceFinder;
import jade.core.ServiceManager;
import jade.core.management.*;
import jade.util.leap.List;
import jade.util.leap.Properties;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;

public class LogicalEnv implements ObsVectListener
{
    static LogicalEnv instance = null;
    
    
    protected jade.core.Agent 						_owner;
    // To hold our reference to the window
    final protected Window                  m_window;
    
    final protected Random					_randomGenerator = new Random();
    
    // size of environment
    protected Dimension                     m_size = new Dimension( 16, 16 );
    
    private HashMap<String,Agent>           agentmap = new HashMap<String,Agent>();
    
    /* ---- ALL the stuff in the environment -----*/
    
    // list of agents (Agent)
    protected ObsVect                       _agents = new ObsVect( this );
  
    // list of coordinates (Point) containing stones
    protected ObsVect                       _stones = new ObsVect();
    // list of coordinates (Point) containing bombs
    protected ObsVect                       _apples = new ObsVect();
    // id for identifiable objects
    protected String                        _objType = "default";   
    
    protected long						_sleepTimeInMs = 1000;
    
    protected volatile int							_round = 0;
    
    protected volatile  int 							_blockingActions = 0;
    
    protected volatile boolean 						_nextRoundPermitted = false;
    
    //Sense Range R: max distance of cells visible to each agent
    protected int                           _senserange = 5;
	//Energy Cost K:
	protected int 							 _energyCost = 5; 
     //EnergyGain L:
	protected int 							 _energyGain = 25;
	//Apple Distribution p:
	protected double					     _appleDistribution = 0.15;
	//Maximum Apple capacity A:
	protected int 							 _maxAppleCapacity = 5;
	//Agent distribution.
    protected int[]		 					 _agentDistribution = {2,2,2,2};
	
    //0 is continues, 1 = step by step
	protected volatile int 							_mode = 0;
    
    /* ------------------------------------------*/

	public static final int WAITING_FOR_START_SIGNAL = 0;
	public static final int CONTINUES_MODE = 1;
	public static final int STEP_BY_STEP_MODE = 2;
	public static final int STOPPED = 3;
	

    public transient Signal signalSenseRangeChanged = new Signal("env sense range changed" );

    // emitted if environment is resized
    public transient Signal signalSizeChanged = new Signal( "env size changed" );

    public transient Signal signalProppertyChanged = new Signal("env propperty changed changed.");
    
    /* ------------------------------------------*/ 

    
    // The default constructor
    private LogicalEnv(jade.core.Agent owner)
    {
            
        // Create the window
        m_window = new Window( this );
        
        _owner = owner;
        
        initialize();
    }
    
    public synchronized static void host(jade.core.Agent owner)
    {
    	instance = new LogicalEnv(owner);
    }
    
    public synchronized static LogicalEnv getEnv()
    {
      //if (instance == null)
      //{
       //   instance = new LogicalEnv();
      //}
      return instance;
    }
    
    private void newAgent(String agentName, String className, Object[] arg)
    {
    	try
    	{
    		ContainerController cc = _owner.getContainerController();
    		AgentController ac = cc.createNewAgent(agentName, className, arg);
    		ac.start();
    	}
    	catch(Exception e)
    	{
    		e.printStackTrace();
    	}
    }
    
    private void killAgent(String agentName)
    {
    	try
    	{
    		ContainerController cc = _owner.getContainerController();
    		cc.getAgent(agentName).kill();
    	}
    	catch(Exception e)
    	{
    		e.printStackTrace();
    	}
    }
    
    public long getSleepTimeInMs()
    {
    	if(_mode == CONTINUES_MODE)
    		return this._sleepTimeInMs;
    	else
    		return 0;
    }
    
 // Remove everything
    public void clear() 
    {
        _stones.removeAllElements();
        _apples.removeAllElements();
        
        
        synchronized(_agents)
    	{
        	while(_agents.size() > 0)
        	{
        		Agent a = (Agent)_agents.get(0);
        		removeAgent(a.getName());
        	}
        	
    	}
       
    }
    
    public void initialize()
    {
    	clear();
    	
       _round = 0;
       	_blockingActions = 0;
       _nextRoundPermitted = false;
    	
       
       int randomNumber = _randomGenerator.nextInt(1000);
       
    	if(_agentDistribution.length > 0)
    	for(int j=0; j<_agentDistribution[0]; j++)
    	{
    		String agentName = randomNumber + " - randomWalker" + j;
    		newAgent(agentName, "simulation.agents.RandomWalker", null);
    		enter(agentName, "red");
    	}
    	
    	if(_agentDistribution.length > 1)
    	for(int j=0; j<_agentDistribution[1]; j++)
    	{
    		String agentName = randomNumber + " - randomWalkert" + j;
    		newAgent(agentName, "simulation.agents.RandomWalker", null);
    		enter(agentName, "blue");
    	}
    	
    	
    	for(int i=0; i<getWidth(); i++)
    	{
    		for(int j=0; j<getHeight(); j++)
    		{
    			if(_appleDistribution > _randomGenerator.nextDouble())
    			{
    				addApple(new Point(i,j));
    			}
    		}
    	}
    	
    }
   
    
    public void nextRound()
    {
    	int[][] energyLevels = new int[][]{{100,100},{100,100},{100,100},{100,100}};
    	//update Graph
    	EnergyGraph.update(_round, energyLevels);
    	
    	
    	//DO OUTSTANDING TRADES!!
    	
    	
    	_blockingActions = 0;
		_round++;
		
		if(_mode == STEP_BY_STEP_MODE)
			_nextRoundPermitted = false;
		
		
		//FIND MATCHES
    }
    
    
    // Get the environment width
    public int getWidth() { return m_size.width; }

    // Get the environment height
    public int getHeight() { return m_size.height; }
    
 // helper function, calls setSize(Dimension)
    public void setSize( int width, int height ) 
    {
        setSize( new Dimension( width, height ) );
    }
    
    // resize world
    public void setSize( Dimension size ) 
    {
        m_size = size;
        signalSizeChanged.emit();

        Iterator i = _apples.iterator();
        while( i.hasNext() ) {
            if( outOfBounds( ((TypeObject) i.next()).getPosition() ) )
                i.remove();
        }
        i = _stones.iterator();
        while( i.hasNext() ) {
            if( outOfBounds( (Point) i.next() ) )
                i.remove();
        }
    }
    
    // Get senserange
    public int getSenseRange() 
    {
        return _senserange;
    }
    
    // Set the senserange
    public void setSenseRange( int senserange ) 
    {
        _senserange = senserange;
        signalSenseRangeChanged.emit();
    }
    
    public int getMaximumAppleCapacity()
    {
    	return _maxAppleCapacity;
    }
    
    public void setMaximumAppleCapacity(int capacity)
    {
    	_maxAppleCapacity = capacity;
    	signalProppertyChanged.emit();
    }

    
    public int getEnergyCost()
    {
    	return _energyCost;
    }
    
    public void setEnergyCost(int energyCost)
    {
    	_energyCost = energyCost;
    	signalProppertyChanged.emit();
    }
    
    
    public int getEnergyGain()
    {
    	return _energyGain;
    }
    
    public void setEnergyGain(int energyGain)
    {
    	_energyGain = energyGain;
    	signalProppertyChanged.emit();
    }
    
    public double getAppleDistribution()
    {
    	return _appleDistribution;
    }
    
    public void setAppleDistribution(double appleDistribution)
    {
    	_appleDistribution = appleDistribution;
    	signalProppertyChanged.emit();
    }
    
    public int[] getAgentDistribution()
    {
    	int[] copyOfDistribution = new int[_agentDistribution.length];
    	for(int i=0; i<copyOfDistribution.length; i++)
    	{
    		copyOfDistribution[i] = _agentDistribution[i];
    	}
    	return copyOfDistribution;
    }
    
    public void setAgentDistribution(int[] newDistribution)
    {
    	_agentDistribution = new int[newDistribution.length];
    	for(int i=0; i<newDistribution.length; i++)
    	{
    		_agentDistribution[i] = newDistribution[i];
    	}
    	
    	signalProppertyChanged.emit();
    }
    
 
    // Which color does the agent want to be!
    private int getColorID(String sColor)
    {
        if (sColor.equals("army") )
        {
            return 0;
        }
        else if (sColor.equals("blue") )
        {
            return 1;
        }
        else if (sColor.equals("gray") )
        {
            return 2;
        }
        else if (sColor.equals("green") )
        {
            return 3;
        }
        else if (sColor.equals("orange") )
        {
            return 4;
        }
        else if (sColor.equals("pink") )
        {
            return 5;
        }
        else if (sColor.equals("purple") )
        {
            return 6;
        }
        else if (sColor.equals("red") )
        {
            return 7;
        }
        else if (sColor.equals("teal") )
        {
            return 8;
        }
        else if (sColor.equals("yellow") )
        {
            return 9;
        }
        
        // Red is the default
        return 7;
    }

    
    // what kind of object is it, bomb, stone, wall ?
    public String getObjType() 
    {
        return _objType;
    }

    // what kind of object is it, bomb, stone, wall ?
    public void setObjType(String objType) 
    {
        _objType = objType;
    }

    
    // Return the agents
    public Collection getAgents() 
    {
        return _agents;
    }
    // Get the agent from its name
    public Agent getAgent(String name)
    {
        Agent a = null;
        a = agentmap.get(name);
        return a;    
    }
    
    
 
    

   
    public boolean enter(String sAgent, String sColor)
    {
    	
    	int attempts = 0;
    	int x = _randomGenerator.nextInt( getWidth() );
    	int y = _randomGenerator.nextInt( getHeight() );
    	Point position = new Point(x, y);
    	while(!isFree(position) && attempts < 10)
    	{
    		attempts++;
    		x = _randomGenerator.nextInt( getWidth() );
    		y = _randomGenerator.nextInt( getHeight() );
    		position = new Point(x,y);
    	}
    	
    	if(attempts >= 10)
    	{
    		return false;
    	}
    	
    	
    	return enter(sAgent, x, y, sColor);
    	
    }
    
    /* Called from Jade agents */
    
    // Enter the agent into the world
    // Succesful returns true, else the ExternalActionFailedExeption exception is thrown
    public boolean enter( String sAgent, int x, int y, String sColor ) 
    {
    	
        Point position = new Point(x,y);
        if(!isFree(position))
        {
        	System.out.println(sAgent+" tries to enter in "+position+" which is not empty");
        	return false;
        }
        // Get the agent
        Agent agent = getAgent(sAgent);
        if( agent!=null ) 
        {
        	System.out.println(sAgent+" is already entered");
            writeToLog( "agent already entered" );
            return false;
//          throw new ExternalActionFailedExeption("Agent \""+agent.getName()+"\" has already entered.");
        }
        addAgent(sAgent);
        agent = getAgent(sAgent);
        
        // Give a signal that we want to move
        agent.signalMove.emit();
        
        writeToLog( "Agent entered: " +agent.getName());
        

        String pos = "("+x+","+y+")";
        
        // Agent already entered

        
        
        // Update the agent his position
        agent._position = position;
        
        // Which color does the agent want to be
        int nColorID = getColorID(sColor);
        agent._colorID = nColorID;
    
        // Redraw so we can see the agent
        validatewindow();
        m_window.repaint();
        
        // We came so far, this means success!
        agent.signalMoveSucces.emit();
        return true;

    }
    

    // Move the agent north
    public boolean north(String sAgent) 
    {
        // Get the correct agent
        Agent agent = getAgent(sAgent);
        
        // Get the agent his position
        Point p = (Point) agent.getPosition().clone();
        p.y = p.y - 1;
        
        // Set the position for the agent
        if(! setAgentPosition( agent, p))
        {
        	System.out.println(sAgent+" tries to go east but cannot !");
        	return false;
        }
        

        // Redraw the window
        validatewindow();
        m_window.repaint();
        return true;
    }
    
        // Move the agent east
    public boolean east(String sAgent)
    {
        // Get the correct agent
        Agent agent = getAgent(sAgent);
        
        // Get the agent his position
        Point p = (Point) agent.getPosition().clone();
        p.x = p.x + 1;
        
        // Set the position for the agent
        if(! setAgentPosition( agent, p))
        {
        	System.out.println(sAgent+" tries to go east but cannot !");
        	return false;
        }
        
        // Redraw the window
        validatewindow();
        m_window.repaint();
        return true;
    }
    

    // Move the agent south
    public boolean south(String sAgent)
    {
        // Get the correct agent
        Agent agent = getAgent(sAgent);
        
        // Get the agent his position
        Point p = (Point) agent.getPosition().clone();
        p.y = p.y + 1;
        
        // Set the position for the agent
        if(! setAgentPosition( agent, p))
        {
        	System.out.println(sAgent+" tries to go east but cannot !");
        	return false;
        }
        

        // Redraw the window
        validatewindow();
        m_window.repaint();
        return true;
    }
    

    // Move the agent west
    public boolean west(String sAgent)
    {
        // Get the correct agent
        Agent agent = getAgent(sAgent);
        
        // Get the agent his position
        Point p = (Point) agent.getPosition().clone();
        p.x = p.x - 1;
        
        // Set the position for the agent
        if(! setAgentPosition( agent, p))
        {
        	System.out.println(sAgent+" tries to go west but cannot !");
        	return false;
        }
        

        // Redraw the window
        validatewindow();
        m_window.repaint();
        return true;
    }

    
    // Pickup an apple
    public boolean pickupApple( String sAgent )
    {
        // Get the agent
        Agent agent = getAgent(sAgent);
        
        // Let everyone know we are going to pick up a bomb 
        agent.signalPickupApple.emit();

        // see if we are not already carrying too much apples
        if( agent.getApples() >= _maxAppleCapacity) 
        {
        	System.out.println(sAgent+" is carying allready maximum apples.");
            writeToLog( "Pickup apple failed" );
            return false;
        }

        // we are not already carying an apple so get this one
        TypeObject apple = removeApple( agent.getPosition() );
        if( apple == null ) 
        {
        	System.out.println(sAgent+
        			" is trying to pick an apple in a place without apples");
            writeToLog( "Pickup bomb failed" );
            return false;
        }

        // Yes
        agent.signalPickupAppleSucces.emit();

        // there was a bomb at that position, so set token
        agent.pickupApple(apple);
        
        // show what happened
        validatewindow();
        m_window.repaint();
        return true;        
    }
    
    public boolean eatApple(String sAgent)
    {
    	return false;
    }
    
    public boolean trade(String sAgent)
    {
    	return false;
    }
    
    
    // what is it
    public String getDescription() { return "Blockworld Environment"; }

    
    public String toString() { return "gridworld"; }

    
    public void refresh() { }

    
        // Add an agent to the environment
    public void addAgent(String sAgent)
    {
        final Agent agent = new Agent( sAgent );
        _agents.add( agent );
        agentmap.put(sAgent,agent);
        
        writeToLog( "agent " + agent + " added" );
    }

    
    // Remove the agent from the environment
    public void removeAgent(String sAgent)
    {
        try 
        {
            Agent a = getAgent(sAgent);
            a.reset();
            //ObsVector.remove IS NOT WORKING CORRECTLY
            
            //!!! REMOVE Agent, not the name of the agent!!!!!!!!!!!!           
            _agents.remove( a );
            agentmap.remove( sAgent );
            
            killAgent(sAgent);
            writeToLog("Agent removed: " + sAgent);
    
            synchronized( this ) 
            {
                notifyAll();
            }
        }
        catch (Exception e) {}
    }
    
    /* END Standard functions --------------------------------------*/
    
    
    
    // Redrawing the window is a nightmare, this does some redraw stuff
    private void validatewindow()
    {
        Runnable addTab = new Runnable()
        {
            public void run()
            {
                //try {Thread.sleep(500);} catch(Exception e) {}
                m_window.doLayout();
                // Changed SA: NO you may not close
                if (!m_window.isVisible())
                {
                    m_window.setVisible( true );
                }
            }
        };
        SwingUtilities.invokeLater(addTab);
    }
    
    // Move the agent
    private boolean setAgentPosition( Agent agent, Point position) 
    {
        agent.signalMove.emit();

        if( outOfBounds( position ) )
            return false;

        // suspend thread if some other agent is blocking our entrance
        
        // Is the position free?
        if( !isFree( position ) )
            return false;

        agent.signalMoveSucces.emit();

        // there may be other threads blocked because this agent was in the way,
        // notify
        // them of the changed state of environment
        synchronized( this ) 
        {
            notifyAll();
        }

        // set the agent position
        agent._position = position;
        return true;
    }

    // Sense a bom in the senserange of the agent
    public Set<Point> senseApples(String agent) 
    {
        // Get the agent his position
        Point position = getPosition(agent);
                
        // iterate over all bombs and decide according to distance if it is in
        // vision range
        Set<Point> visible = new java.util.HashSet<Point>();
        
        if(position == null)
        	return visible;
        
        synchronized(_apples){
	        Iterator i = _apples.iterator();
	        while( i.hasNext() ) 
	        {
	            TypeObject b = (TypeObject) i.next();
	            if( position.distance( b.getPosition() ) <= _senserange )
	                visible.add( b.getPosition() );
	        }
        }
        
        return visible;
    }
    
  //Senses traps in the sense range of the agent.
    public Set<Point> senseAgents(String agent)
    {
    	//gets the agent his position.
    	Point position = getPosition(agent);
    	
    	// iterate over all traps and decide according to distance if it is in
        // vision range
    	Set<Point> visible = new java.util.HashSet<Point>();
    	
    	
    	if(position == null)
    		return visible;
    	
    	synchronized(_agents)
    	{
    		Iterator<Agent> i = _agents.iterator();
    		while(i.hasNext())
    		{
    			Agent agentB = i.next();
    			if(agentB.getPosition() != null)
    			if( position.distance(agentB.getPosition()) <= _senserange && agentB.getName() != agent )
    				visible.add(agentB.getPosition());
    		}
    	}
    	return visible;
    }
    
    //Senses stones in the sense range of the agent.
    public Set<Point> senseStones(String agent)
    {
    	Point position = getPosition(agent);
    	
    	//The set to store the positions of the stone.
    	Set<Point> visible = new java.util.HashSet<Point>();
    	
    	if(position == null)
    		return visible;
    	
    	//place artificial stones at the border of the environment.
    	//is inneffient for large grid.
    	//loop through x-axis start by -1, because the border width is 2 squares longer than the actual width.
    	// As you can see in this picture: S=square, B = border.
    	//B B B B B B
    	//B S S S S B
    	//B S S S S B
    	//B S S S S B
    	//B B B B B B
    	
    	//first loop for y=-1
    	for(int x=-1; x<m_size.width+1; x++)
    	{
    		Point artificialStone = new Point(x,-1);
    		if (position.distance(artificialStone) <= _senserange)
    			visible.add(artificialStone);
    	}
    	//for y = m_size.height
    	for(int x=-1; x<m_size.width+1; x++)
    	{
    		Point artificialStone = new Point(x,m_size.height);
    		if (position.distance(artificialStone) <= _senserange)
    			visible.add(artificialStone);
    	}
    	
    	//now loop through y axis for x = -1
    	for(int y=0; y<m_size.height; y++)
    	{
    		Point artificialStone = new Point(-1,y);
    		if (position.distance(artificialStone) <= _senserange)
    			visible.add(artificialStone);
    	}
    	
    	//now loop through y axis for x = m_size.width
    	for(int y=0; y<m_size.height; y++)
    	{
    		Point artificialStone = new Point(m_size.width,y);
    		if (position.distance(artificialStone) <= _senserange)
    			visible.add(artificialStone);
    	}
    	
    	// iterate over all traps and decide according to distance if it is in
        // vision range
    	
    	
    	synchronized(_stones)
    	{
    		Iterator i = _stones.iterator();
    		while(i.hasNext())
    		{
    			TypeObject stone = (TypeObject) i.next();
    			if( position.distance(stone.getPosition()) <= _senserange )
    				visible.add(stone.getPosition());
    		}
    	}
    	return visible;
    }
    
    
    // check if point is within environment boundaries
    // return false is p is within bounds
    protected boolean outOfBounds( Point p ) 
    {
        if( (p.x >= m_size.getWidth()) || (p.x < 0) || (p.y >= m_size.getHeight()) || (p.y < 0) )
        {
            return true;
        }
        
        return false;
    }
    
    // Is the position free?
    public boolean isFree( final Point position ) 
    {
        return (isStone( position )) == null && (isAgent( position ) == null);
    }
    
     // Check for agent at coordinate. \return Null if there is no agent at the
     // specified coordinate. Otherwise return a reference to the agent there.
    public Agent isAgent( final Point p ) 
    {
        Iterator i = _agents.iterator();
        while( i.hasNext() ) {
            final Agent agent = (Agent) i.next();
            if( p.equals( agent.getPosition() ) )
                return agent;
        }
        return null;
    }
    
    // Is there a stone at this point
    public TypeObject isStone( Point p ) 
    {
        Iterator i = _stones.iterator();
        while( i.hasNext() ) {
            TypeObject stones = (TypeObject) i.next();
            if( p.equals( stones.getPosition() ) )
                return stones;
        }
        return null;
    }
    
  
    public TypeObject isApple( Point p ) 
    {
    	synchronized(_apples){
	        Iterator i = _apples.iterator();
	        while( i.hasNext() ) {
	            TypeObject apple = (TypeObject) i.next();
	            if( p.equals( apple.getPosition() ) )
	                return apple;
	        }
    	}
        return null;
    }
    
    // Remove bomb at position TODO Jaap; why is this different then remove stone???
    public TypeObject removeApple(Point position )
    {
        // find bomb in bombs list
    	synchronized(_apples){
        Iterator i = _apples.iterator();

	        while (i.hasNext())
	        {
	            TypeObject apple = (TypeObject) i.next();
	            if (position.equals(apple.getPosition()))
	            {
	                i.remove();
	                return apple;
	            }
	        }
    	}
        return null;
    }
    
    // remove stone at position
    public boolean removeStone( Point position )
    {
        // find stone in stones list
        Iterator i = _stones.iterator();

        while (i.hasNext())
            //if( position.equals( i.next() ) ) {
            // Changed SA:
            if (position.equals(((TypeObject)i.next()).getPosition()))
            {
                i.remove();
                

                // there may be other threads blocked because this agent was in
                // the way, notify
                // them of the changed state of environment
                synchronized( this ) 
                {
                    notifyAll();
                }

                return true;
            }

        return false;
    }
    
    
    // Add a stone at the given position
    public boolean addStone( Point position ) throws IndexOutOfBoundsException 
    {
        // valid coordinate
        if( outOfBounds( position ) )
            throw new IndexOutOfBoundsException( "setStone out of range: "
                    + position + ", " + m_size );

        // is position clear of other stuff
        // Changed SA:
        if( isApple( position ) != null || isStone( position ) != null)
            return false;

        _stones.add( new TypeObject(_objType,position) );
        
//      notifyEvent("wallAt", position);

        return true;
    }
    
    // Add a bomb to the environment
    public boolean addApple( Point position ) throws IndexOutOfBoundsException
    {
        if( outOfBounds( position ) )
            throw new IndexOutOfBoundsException( "addApple outOfBounds: "
                    + position + ", " + m_size );

        // is position clear of other stuff
        if( isApple( position ) != null ||  isStone( position ) != null )
            return false;

        // all clear, accept bomb
        synchronized(_apples){
            _apples.add( new TypeObject(_objType,position) );        	
        }

//      notifyEvent("bombAt", position);

        return true;
    }
    
    
    // Print a message to the console
    static public void writeToLog( String message ) { System.out.println( "gridworld: " + message ); }
    
    
   
    // Save the environment
    public void save( OutputStream destination ) throws IOException 
    {
        ObjectOutputStream stream = new ObjectOutputStream( destination );
        stream.writeObject( m_size );

        stream.writeInt( _senserange );
        stream.writeInt(_maxAppleCapacity);
        
        stream.writeObject( (Vector) _stones );
        stream.writeObject( (Vector) _apples );
        stream.flush();
    }

    // Load the environment
    public void load( InputStream source ) throws IOException, ClassNotFoundException 
    {
        ObjectInputStream stream = new ObjectInputStream( source );
        Dimension size = (Dimension) stream.readObject();

        int senserange = stream.readInt();
        int maxAppleCapacity = stream.readInt();
        
        Vector stones = (Vector) stream.readObject();
        Vector apples = (Vector) stream.readObject();
       
        // delay assignments until complete load is succesfull
        m_size = size;
        _senserange = senserange;
        _maxAppleCapacity = maxAppleCapacity;
        
        signalSizeChanged.emit();
        signalSenseRangeChanged.emit();
        signalProppertyChanged.emit();
        clear();

        _stones.addAll( stones );
        _apples.addAll( apples );
    }
    
    /* END Helper functions --------------------------------------*/
    
    /* Listeners ------------------------------------------------*/
    
    // / This listener is notified upon changes regarding the Agent list.
    // / Please note that this only involves registering new agents or
    // / removing existing agents. To track agent position changes, add
    // / a listener to that specific agent.
    // / \sa Agent
    public void addAgentListener( ObsVectListener o ) 
    {
        _agents.addListener( o );
    }

    // / This listener is notified upon changes regarding the Stones list.
    public void addStonesListener( ObsVectListener o ) 
    {
        _stones.addListener( o );
    }

    // / This listener is notified upon changes regarding the Bombs list.
    public void addApplesListener( ObsVectListener o ) 
    {
        _apples.addListener( o );
    }


    
    /* END Liseteners ------------------------------------------*/
    

    /* Overrides for ObsVector ---------------------------------*/
    
    public void onAdd( int index, Object o ) {}

    public void onRemove( int index, Object o ) 
    {
        ((Agent) o).deleteObservers();
    }

	public Point getPosition(String localName) 
	{
		Agent a = getAgent(localName);
		if(a==null)
			return null;
		else
			return a.getPosition();
	}
    
    /* END Overrides for ObsVector ---------------------------------*/
}
