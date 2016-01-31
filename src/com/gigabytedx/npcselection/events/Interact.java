package com.gigabytedx.npcselection.events;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.Configuration;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.gigabytedx.npcselection.Main;

public class Interact implements Listener {
	Main plugin;
	Configuration config;

	public Interact(Main plugin, Configuration config) {
		this.plugin = plugin;
		this.config = config;
	}

	@EventHandler
	public void onEntityInteract(PlayerInteractAtEntityEvent event) {
		Player player = event.getPlayer();
		if (event.getRightClicked() instanceof LivingEntity) {
			LivingEntity entity = (LivingEntity) event.getRightClicked();
			String npcName = entity.getCustomName();
			Set<String> npcNames = config.getConfigurationSection("npc").getKeys(false);
			if (npcNames.contains(npcName)) {
				player.openInventory(getInventory(config, npcName));
			}
		}
	}

	private Inventory getInventory(Configuration config, String npcName) {
		Inventory inventoryScreen = Bukkit.createInventory(null, 9, "NPC: " + npcName);
		Set<String> list = config.getConfigurationSection("npc." + npcName + ".options").getKeys(false);
		for (String itemData : list) {
			ItemStack itemStack = null;
			String materialName = config.getString("npc." + npcName + ".options." + itemData + ".material");
			try {
				itemStack = new ItemStack(Material.valueOf(materialName));
			} catch (IllegalArgumentException e) {
				plugin.logError("Could not find an item named '" + materialName + "'. Please check the config file");
				continue;
			}
			ItemMeta meta = itemStack.getItemMeta();
			meta.setDisplayName(ChatColor.GREEN + itemData);

			List<String> lore = new ArrayList<>();
			String loreText = config.getString("npc." + npcName + ".options." + itemData + ".lore");
			plugin.logDebug("Lore Text" + loreText);
			try {
				String[] words = loreText.split("\\s+");

				int count = 0;
				String sentence = "";
				for (String word : words) {
					sentence = sentence.concat(word + " ");
					if (++count > 6) {
						lore.add(sentence);
						sentence = "";
						count = 0;
					}
				}
				lore.add(sentence);
			} catch (NullPointerException e) {
				e.printStackTrace();
			}

			meta.setLore(lore);
			itemStack.setItemMeta(meta);
			inventoryScreen.addItem(itemStack);
		}
		return inventoryScreen;
	}
}
