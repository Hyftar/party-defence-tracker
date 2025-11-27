package com.partydefencetracker;

import java.awt.Color;
import java.awt.image.BufferedImage;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.ui.overlay.infobox.InfoBox;
import javax.inject.Inject;

@ToString
public class DefenceInfoBox extends InfoBox
{
    @Inject
    private final DefenceTrackerConfig config;

    @Getter
    private final long minimumDefence;

    @Getter
    @Setter
    private long currentDefence;

    public DefenceInfoBox(BufferedImage image, Plugin plugin, long minimumDefence, long currentDefence, DefenceTrackerConfig config)
    {
        super(image, plugin);
        this.currentDefence = currentDefence;
        this.minimumDefence = minimumDefence;
        this.config = config;
    }

    @Override
    public String getText()
    {
        long displayedDefence =
            config.displayFullDefenceLevel()
                ? currentDefence
                : currentDefence - minimumDefence;

        return Long.toString(displayedDefence);
    }

    @Override
    public Color getTextColor()
    {
		if (config.disableIBColor())
		{
			return Color.WHITE;
		}

        long relativeDefence = Math.max(currentDefence - minimumDefence, 0);

        if (relativeDefence == 0)
        {
            return config.cappedDefColor();
        }

        if (relativeDefence <= config.lowDef())
        {
            return config.lowDefColor();
        }

        return config.highDefColor();
    }
}