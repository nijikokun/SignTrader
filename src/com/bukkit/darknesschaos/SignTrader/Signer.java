package com.bukkit.darknesschaos.SignTrader;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;

public class Signer
{
	
	@SuppressWarnings("unused")
	private SignTrader plugin;
	public void SignTraderPlayerListener(SignTrader instance) {
        plugin = instance;
    }
	
	public static void createSign(Block s, Player p)
	{
		
	}

	public static void useSign(Block b, Player p)
	{
		BlockState block = b.getState();
		if (block.getType() == Material.SIGN_POST || block.getType() == Material.WALL_SIGN)
		{
			Sign s = (Sign) block;
			p.sendMessage("DERP" + s.getLine(1));
		}
	}
}
