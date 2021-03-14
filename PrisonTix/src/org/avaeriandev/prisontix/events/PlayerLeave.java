package org.avaeriandev.prisontix.events;

import org.avaeriandev.prisontix.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerLeave implements Listener {

	@EventHandler
	public void onPlayerLeave(PlayerQuitEvent e) {
		
		Player plr = e.getPlayer();	
		
		Bukkit.getServer().broadcast(Utils.chat("&8[&c-&8] &7" + plr.getName()), "minerift.admin");
	}
	
}
