package com.duckblade.runelite.togindicator;

import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.overlay.OverlayManager;

import javax.inject.Inject;

@PluginDescriptor(
        name = "ToG Indicator"
)
public class TOGIndicatorPlugin extends Plugin {

    @Inject
    private OverlayManager overlayManager;

    @Inject
    private TOGIndicatorOverlay overlay;

    @Override
    protected void startUp() throws Exception {
        overlayManager.add(overlay);
    }

    @Override
    protected void shutDown() throws Exception {
        overlayManager.remove(overlay);
    }

}
