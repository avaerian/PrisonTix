package org.avaeriandev.prisontix.events;

import org.avaeriandev.prisontix.Main;
import org.avaeriandev.prisontix.utils.ConfigManager;
import org.avaeriandev.prisontix.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;

public class PlayerJoin implements Listener {

	private Main plugin;

	public PlayerJoin(Main plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		Player plr = e.getPlayer();
		
		if(plr.hasPlayedBefore() == false) {
			plr.getInventory().addItem(new ItemStack(Material.BREAD,5));
		}
		
		// Create config
		ConfigManager cm = new ConfigManager(plugin, plr);
		if (!cm.exists()) {
			FileConfiguration f = cm.getConfig();
			f.set("name", plr.getName());
			f.set("tickets", 0);
			cm.saveConfig();
		}
		
		Bukkit.broadcast(Utils.chat("&8[&a+&8] &7" + plr.getName()), "minerift.admin");
	}

}
