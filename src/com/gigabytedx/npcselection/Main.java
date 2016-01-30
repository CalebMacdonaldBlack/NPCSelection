package com.gigabytedx.npcselection;

import java.util.logging.Logger;

import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.gigabytedx.npcselection.events.Interact;

public class Main extends JavaPlugin{
	public void onEnable() {
		PluginDescriptionFile pdfFile = getDescription();
		Logger logger = getLogger();
		registerEvents();
		registerCommands();
		registerConfigFile();
		logger.info(pdfFile.getName() + " has been enabled (V." + pdfFile.getVersion() + ")");
	}
	
	private void registerConfigFile(){
		
	}
	private void registerCommands() {
		//getCommand("command").setExecutor(new commandclass(this));
	}

	private void registerEvents() {
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(new Interact(this), this);
	}

	public void onDisable() {
		PluginDescriptionFile pdfFile = getDescription();
		Logger logger = getLogger();

		logger.info(pdfFile.getName() + " has been disabled (V." + pdfFile.getVersion() + ")");
	}
}
