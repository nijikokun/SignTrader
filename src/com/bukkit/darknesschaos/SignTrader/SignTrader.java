package com.bukkit.darknesschaos.SignTrader;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginLoader;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * SignTrader for Bukkit
 *
 * @author Darknesschaos
 */
public class SignTrader extends JavaPlugin {
    //private final SignTraderPlayerListener playerListener = new SignTraderPlayerListener(this);
    private final SignTraderBlockListener blockListener = new SignTraderBlockListener(this);
    @SuppressWarnings("unused")
	private final SignTraderPlayerListener playerListener = new SignTraderPlayerListener(this);
    private final HashMap<Player, Boolean> debugees = new HashMap<Player, Boolean>();
    private ArrayList<String> gameOps;
    ArrayList<int[]> SignLocs;

    public SignTrader(PluginLoader pluginLoader, Server instance,
            PluginDescriptionFile desc, File folder, File plugin,
            ClassLoader cLoader) {
        super(pluginLoader, instance, desc, folder, plugin, cLoader);
        // TODO: Place any custom initialisation code here
        // NOTE: Event registration should be done in onEnable not here as all events are unregistered when a plugin is disabled
        this.gameOps = new ArrayList<String>();
    }

   

    public void onEnable() {
        // TODO: Place any custom enable code here including the registration of any event
        // Register our events
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvent(Event.Type.BLOCK_RIGHTCLICKED, this.blockListener, Event.Priority.Normal, this);
        // EXAMPLE: Custom code, here we just output some info so we can check all is well
        addOps();
        PluginDescriptionFile pdfFile = this.getDescription();
        System.out.println( pdfFile.getName() + " version " + pdfFile.getVersion() + " is enabled!" );
    }
    public void onDisable() {
        // TODO: Place any custom disable code here
        // NOTE: All registered events are automatically unregistered when a plugin is disabled
        // EXAMPLE: Custom code, here we just output some info so we can check all is well
        System.out.println("Goodbye world!");
    }
    public boolean isDebugging(final Player player) {
        if (debugees.containsKey(player)) {
            return debugees.get(player);
        } else {
            return false;
        }
    }

    public void setDebugging(final Player player, final boolean value) {
        debugees.put(player, value);
    }
    
    public boolean isOp(String playerName)
    {
      for (String player : this.gameOps)
        if (player.compareToIgnoreCase(playerName) == 0)
          return true;
      return false;
    }
    
    public void addOps()
    {
        try
        {
            // Open the file that is the first 
            // command line parameter
            FileInputStream fstream = new FileInputStream("ops.txt");
            
            // Get the object of DataInputStream
            DataInputStream in = new DataInputStream(fstream);
                BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String strLine;
            
            //Read File Line By Line
            while ((strLine = br.readLine().toLowerCase(Locale.ENGLISH)) != null)
            {
            	// Print the content on the console
            	//System.out.println(strLine);
            	gameOps.add(strLine);
            }
            //Close the input stream
            in.close();
            }catch (Exception e){//Catch exception if any
              System.err.println("Error: " + e.getMessage());
              }
    }
    
    public void addSigns()
    {
    	try
        {
            // Open the file that is the first 
            // command line parameter
            FileInputStream fstream = new FileInputStream("signs.txt");
            
            // Get the object of DataInputStream
            DataInputStream in = new DataInputStream(fstream);
                BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String strLine;
            
            //Read File Line By Line
            
            while ((strLine = br.readLine().toLowerCase(Locale.ENGLISH)) != null)
            {
            	if(!strLine.startsWith("#"))
            	{
                	String[] breaker = strLine.split(":");
                	if (breaker.length == 3)
                	{
                		try
                		{
                			// the String to int conversion happens here
                			int[] loc = new int[3];
                			int x = Integer.parseInt(breaker[0].trim());
                			int z = Integer.parseInt(breaker[1].trim());
                			int y = Integer.parseInt(breaker[2].trim());
                			loc[0] = x;
                			loc[1] = y;
                			loc[2] = z;
                			SignLocs.add(loc);
                		}
                		catch (Exception e)
                		{
                			System.err.println("Error: there was something wrong with the line at the save.");
                		}
                	}
            	}
            }
            //Close the input stream
            in.close();
            }catch (Exception e){//Catch exception if any
              System.err.println("Error: " + e.getMessage());
              }
    }
}

