package org.avaeriandev.prisontix.sys;

import org.avaeriandev.prisontix.Main;
import org.avaeriandev.prisontix.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class StaffChat implements Listener{

	@SuppressWarnings("unused")
	private Main plugin;
	
	public StaffChat(Main plugin) {
		this.plugin = plugin;
	}
	
	public void onJoin(PlayerJoinEvent e) {
		Player plr = e.getPlayer();
		
		if(plr.hasPermission("staff.staffjoin")) {
			Bukkit.broadcast(Utils.chat("&7[&cStaff&7] " + plr.getDisplayName() + " &fhas joined the server"), "staff.staffjoin");
		}
	}
	
	public void onLeave(PlayerQuitEvent e) {
		Player plr = e.getPlayer();
		if(plr.hasPermission("staff.staffleave")) {
			Bukkit.broadcast(Utils.chat("&7[&cStaff&7] " + plr.getDisplayName() + " &fhas has the server"), "staff.staffjoin");
		}
	}
}
