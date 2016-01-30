package com.gigabytedx.npcselection.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;

import com.gigabytedx.npcselection.Main;

public class Interact implements Listener {
	Main plugin;
	public Interact(Main plugin) {
		this.plugin = plugin;
	}
	
	public void onEntityInteract(PlayerInteractAtEntityEvent event){
		Player player = event.getPlayer();
		
	}
}
