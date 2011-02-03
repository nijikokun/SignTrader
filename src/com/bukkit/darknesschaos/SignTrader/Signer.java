package com.bukkit.darknesschaos.SignTrader;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Signer
{
	
	private static SignTrader plugin;
	
	@SuppressWarnings("static-access")
	public void SignTraderPlayerListener(SignTrader instance) {
        this.plugin = instance;
    }
	
	public static void createSign(Block b, Player p)
	{
		if((b.getType() != Material.SIGN_POST) ||
			(b.getType() != Material.WALL_SIGN))
				return;
		
		int[] loc = new int[3];
		loc[0] = b.getX();
		loc[1] = b.getY();
		loc[2] = b.getZ();
		
		if (SignTrader.SignLocs.contains(loc))
		{
			p.sendMessage("Sign already activated.");
			return;
		}
		
		plugin.addSign(loc[0],loc[1],loc[2]);
	}

	public static boolean useSign(Block b, Player p)
	{
		if((b.getType() != Material.SIGN_POST) &&
			(b.getType() != Material.WALL_SIGN))
			return false;
		
		int[] loc = new int[3];
		loc[0] = b.getX();
		loc[1] = b.getY();
		loc[2] = b.getZ();
		
//		if (!SignTrader.SignLocs.contains(loc)) // checks the activation of the sign
//		{
//			p.sendMessage("Sorry, Sign not activated.");
//			return;
//		}
		
		if (!(b.getState() instanceof Sign))
			return false;// this should not happen at this point
		
		BlockState block = b.getState();
		Sign s = (Sign) block;
		
		String[] costText = s.getLine(1).split("x");
		String[] recieveText = s.getLine(3).split("x");
		
		int[] costValue = new int[2];
		int[] recieveValue = new int[2];
		
		if ((s.getLine(1).startsWith("Free")) && (recieveText.length == 2))
		{
			try
			{
				recieveValue[0] = Integer.parseInt(recieveText[0]);
				recieveValue[1] = Integer.parseInt(recieveText[1]);
				if (!giveItem(recieveValue[0], recieveValue[1], b))
					p.sendMessage("The item " + recieveText[1] + " specified does not exist!");
			}
			catch (Exception e)
			{
				p.sendMessage("Sign not in correct format.");
				return false;
			}
		}
		else if ((costText.length == 2) && (recieveText.length == 2))
		{
			try
			{
				costValue[0] = Integer.parseInt(costText[0]);
				costValue[1] = Integer.parseInt(costText[1]);
				recieveValue[0] = Integer.parseInt(recieveText[0]);
				recieveValue[1] = Integer.parseInt(recieveText[1]);
				if( Material.getMaterial(costValue[1]) == null )
				{
					p.sendMessage("The item " + recieveText[1] + " specified does not exist!");
				    return true; //if the item specified doesnt exist...
				}
				else
				{
					if ((p.getItemInHand().getTypeId() == costValue[1]) && (p.getItemInHand().getAmount() >= costValue[0]))
					{
						if (p.getItemInHand().getAmount() == costValue[0])
							p.setItemInHand(null);
						else
							p.getItemInHand().setAmount(p.getItemInHand().getAmount()-costValue[0]);
						
						if (!giveItem(recieveValue[0], recieveValue[1], b))
							p.sendMessage("The item " + recieveText[1] + " specified does not exist!");
						return true;
					}
					else
					{
						p.sendMessage("You dont have enough " + Material.getMaterial(costValue[1]) + "!");
					}
				}
			}
			catch (Exception e)
			{
				p.sendMessage("Sign not in correct format.");
				return true;
			}
		}
		else
		{
			p.sendMessage("Sign is in an incorrect format.");
			return true;
		}
		return false;
	}
	
	public static boolean giveItem(int amount, int itemId, Block block)
	{
	    if( Material.getMaterial(itemId) == null )
		    return false; //if the item specified doesnt exist...

		ItemStack item = new ItemStack(itemId, amount);
		block.getWorld().dropItem(block.getLocation(), item);
		
		return true;
	}
}
