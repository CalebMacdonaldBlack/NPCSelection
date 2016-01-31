package com.gigabytedx.npcselection.events;

import org.bukkit.Bukkit;
import org.bukkit.configuration.Configuration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import com.gigabytedx.npcselection.Main;

public class InventoryInteract implements Listener {
	Main plugin;
	Configuration npcMenusConfig;

	public InventoryInteract(Main plugin, Configuration npcMenusConfig) {
		this.plugin = plugin;
		this.npcMenusConfig = npcMenusConfig;
	}

	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {
		if (event.getWhoClicked() instanceof Player) {
			Player player = (Player) event.getWhoClicked();
			if (event.getInventory().getTitle().contains("NPC:")) {
				String npcName = event.getInventory().getTitle().replace("NPC: ", "");
				String itemDataName = event.getCurrentItem().getItemMeta().getDisplayName();
				itemDataName = itemDataName.substring(2);
				plugin.logDebug(npcName + "  " + itemDataName);
				String command = npcMenusConfig.getString("npc." + npcName + ".options." + itemDataName + ".command");
				command = command.replace("%p", player.getName());
				event.setCancelled(true);
				player.closeInventory();
				Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command);

			}
		}
	}

}
