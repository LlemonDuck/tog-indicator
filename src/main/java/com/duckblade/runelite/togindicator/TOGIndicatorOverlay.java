package com.duckblade.runelite.togindicator;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import javax.inject.Inject;
import javax.inject.Singleton;
import net.runelite.api.Client;
import net.runelite.api.Skill;
import net.runelite.api.widgets.JavaScriptCallback;
import net.runelite.api.widgets.Widget;
import net.runelite.api.widgets.WidgetInfo;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayLayer;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.client.util.ImageUtil;

@Singleton
public class TOGIndicatorOverlay extends Overlay
{

	private static final BiMap<Skill, Integer> widgetIds = HashBiMap.create(24);

	static
	{
		widgetIds.put(Skill.ATTACK, 20971521);
		widgetIds.put(Skill.STRENGTH, 20971522);
		widgetIds.put(Skill.DEFENCE, 20971523);
		widgetIds.put(Skill.RANGED, 20971524);
		widgetIds.put(Skill.PRAYER, 20971525);
		widgetIds.put(Skill.MAGIC, 20971526);
		widgetIds.put(Skill.RUNECRAFT, 20971527);
		widgetIds.put(Skill.CONSTRUCTION, 20971528);
		widgetIds.put(Skill.HITPOINTS, 20971529);
		widgetIds.put(Skill.AGILITY, 20971530);
		widgetIds.put(Skill.HERBLORE, 20971531);
		widgetIds.put(Skill.THIEVING, 20971532);
		widgetIds.put(Skill.CRAFTING, 20971533);
		widgetIds.put(Skill.FLETCHING, 20971534);
		widgetIds.put(Skill.SLAYER, 20971535);
		widgetIds.put(Skill.HUNTER, 20971536);
		widgetIds.put(Skill.MINING, 20971537);
		widgetIds.put(Skill.SMITHING, 20971538);
		widgetIds.put(Skill.FISHING, 20971539);
		widgetIds.put(Skill.COOKING, 20971540);
		widgetIds.put(Skill.FIREMAKING, 20971541);
		widgetIds.put(Skill.WOODCUTTING, 20971542);
		widgetIds.put(Skill.FARMING, 20971543);
		widgetIds.put(Skill.OVERALL, 20971544);
	}

	private final Client client;

	private final BufferedImage questIcon;

	private Skill hoveredSkill;

	@Inject
	public TOGIndicatorOverlay(Client client)
	{
		this.client = client;
		this.questIcon = ImageUtil.loadImageResource(TOGIndicatorOverlay.class, "/quest-icon.png");
		setPosition(OverlayPosition.DYNAMIC);
		setLayer(OverlayLayer.ABOVE_WIDGETS);
	}

	@Override
	public Dimension render(Graphics2D graphics)
	{
		Widget skillsContainer = client.getWidget(WidgetInfo.SKILLS_CONTAINER);
		if (skillsContainer == null || skillsContainer.isHidden())
			return null;

		int minExp = Integer.MAX_VALUE;
		for (Skill s : Skill.values())
		{
			int skillExp = client.getSkillExperience(s);
			if (skillExp < minExp)
			{
				minExp = skillExp;
			}
		}

		for (Skill s : Skill.values())
		{
			if (hoveredSkill != null && s != hoveredSkill)
				continue;

			// check for min xp
			int skillExp = client.getSkillExperience(s);
			if (skillExp != minExp)
				continue;

			// get widget id
			int widgetId = widgetIds.getOrDefault(s, -1);
			if (widgetId == -1)
				continue;

			// find widget from id
			Widget skillWidget = null;
			for (Widget w : skillsContainer.getStaticChildren())
			{
				if (w.getId() == widgetId)
				{
					skillWidget = w;
					break;
				}
			}
			if (skillWidget == null)
				continue;

			// draw icon over skill box
			Rectangle r = skillWidget.getBounds();
			graphics.drawImage(questIcon, r.x + r.width - questIcon.getWidth(), r.y, null);
		}

		return null;
	}

	public void attachHoverListeners()
	{
		Widget skillsContainer = client.getWidget(WidgetInfo.SKILLS_CONTAINER);
		if (skillsContainer == null)
		{
			return;
		}

		for (Widget skillWidget : skillsContainer.getStaticChildren())
		{
			final Skill skill = widgetIds.inverse().get(skillWidget.getId());
			if (skill != null)
			{
				skillWidget.setOnMouseOverListener((JavaScriptCallback) event -> hoveredSkill = skill);
			}
		}
		skillsContainer.setOnMouseLeaveListener((JavaScriptCallback) event -> hoveredSkill = null);
	}

}
