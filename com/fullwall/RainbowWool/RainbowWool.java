package com.fullwall.RainbowWool;


import java.util.logging.Logger;

import org.bukkit.event.Event.Priority;
import org.bukkit.event.Event;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
/**
 * RainbowWool for Bukkit
 * 
 * @author fullwall
 */
public class RainbowWool extends JavaPlugin {

	public BlockListen l = new BlockListen(this);

	private static final String codename = "ROYGBIV";
	public static Logger log = Logger.getLogger("Minecraft");
	
	public void onEnable() {

		PluginManager pm = getServer().getPluginManager();
		pm.registerEvent(Event.Type.REDSTONE_CHANGE, l, Priority.Normal, this);
		pm.registerEvent(Event.Type.BLOCK_BREAK, l, Priority.Normal, this);
		PluginDescriptionFile pdfFile = this.getDescription();
		// EXAMPLE: Custom code, here we just output some info so we can check
		// all is well.
		log.info("[" + pdfFile.getName() + "]: version [" + pdfFile.getVersion()
				+ "] (" + codename + ") loaded");

	}

	public void onDisable() {
		PluginDescriptionFile pdfFile = this.getDescription();
		log.info("[" + pdfFile.getName() + "]: version [" + pdfFile.getVersion()
				+ "] (" + codename + ") disabled");
	}
}