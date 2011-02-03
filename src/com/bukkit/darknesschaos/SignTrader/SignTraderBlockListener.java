package com.bukkit.darknesschaos.SignTrader;
import org.bukkit.event.block.BlockListener;
import org.bukkit.event.block.BlockRightClickEvent;

/**
 * <pluginname> block listener
 * @author <yourname>
 */
public class SignTraderBlockListener extends BlockListener {
	@SuppressWarnings("unused")
	private final SignTrader plugin;

    public SignTraderBlockListener(final SignTrader plugin) {
        this.plugin = plugin;
    }

   public void onBlockRightClick(BlockRightClickEvent e)
   {
	   Signer.useSign(e.getBlock(), e.getPlayer());
   }
}