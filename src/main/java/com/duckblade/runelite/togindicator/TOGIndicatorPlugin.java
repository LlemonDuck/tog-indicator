package com.duckblade.runelite.togindicator;

import javax.inject.Inject;
import net.runelite.api.Client;
import net.runelite.api.GameState;
import net.runelite.api.events.WidgetLoaded;
import net.runelite.api.widgets.WidgetInfo;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.overlay.OverlayManager;

@PluginDescriptor(
		name = "ToG Indicator"
)
public class TOGIndicatorPlugin extends Plugin
{

	@Inject
	private OverlayManager overlayManager;

	@Inject
	private TOGIndicatorOverlay overlay;

	@Inject
	private Client client;

	@Override
	protected void startUp() throws Exception
	{
		overlayManager.add(overlay);

		if (client.getGameState() == GameState.LOGGED_IN)
		{
			overlay.attachHoverListeners();
		}
	}

	@Override
	protected void shutDown() throws Exception
	{
		overlayManager.remove(overlay);
	}

	@Subscribe
	public void onWidgetLoaded(WidgetLoaded widget)
	{
		if (widget.getGroupId() == WidgetInfo.SKILLS_CONTAINER.getGroupId())
		{
			overlay.attachHoverListeners();
		}
	}

}
