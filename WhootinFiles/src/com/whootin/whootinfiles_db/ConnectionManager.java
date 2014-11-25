package com.whootin.whootinfiles_db;

import java.util.ArrayList;

/**
 * This class is ued to limit connections
 * @author Karthick K, Meenakshisundaram S
 */
public class ConnectionManager 
{
	public static final int MAX_CONNECTIONS = 5;
	
	private ArrayList<Runnable> active = new ArrayList<Runnable>();
	private ArrayList<Runnable> queue = new ArrayList<Runnable>();

	private static ConnectionManager instance;
	
	/**
	 * @return Connection manager instance
	 */
	public static ConnectionManager getInstance() 
	{
		if (instance == null)
		{
			instance = new ConnectionManager();
		}
		return instance;
	}

	public void push(Runnable runnable) 
	{
		queue.add(runnable);
	
		if (active.size() < MAX_CONNECTIONS)
		{
			startNext();
		}
	}

	private void startNext() 
	{
		if (!queue.isEmpty()) 
		{
			Runnable next = queue.get(0);
			queue.remove(0);
			active.add(next);

			new Thread(next).start();
		}
	}

	public void didComplete(Runnable runnable) 
	{
		active.remove(runnable);
		startNext();
	}
}
