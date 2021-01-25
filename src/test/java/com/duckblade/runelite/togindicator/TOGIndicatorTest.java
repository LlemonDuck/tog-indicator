package com.duckblade.runelite.togindicator;

import net.runelite.client.RuneLite;
import net.runelite.client.externalplugins.ExternalPluginManager;

public class TOGIndicatorTest
{
	public static void main(String[] args) throws Exception
	{
		ExternalPluginManager.loadBuiltin(TOGIndicatorPlugin.class);
		RuneLite.main(args);
	}
}
