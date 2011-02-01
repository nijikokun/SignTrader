package com.bukkit.darknesschaos.SignTrader;
import org.bukkit.event.player.PlayerListener;

/**
 * Handle events for all Player related events
 * @author Darknesschaos
 */
public class SignTraderPlayerListener extends PlayerListener
{
    @SuppressWarnings("unused")
	private final SignTrader plugin;

    public SignTraderPlayerListener(SignTrader instance)
    {
        plugin = instance;
    }
}