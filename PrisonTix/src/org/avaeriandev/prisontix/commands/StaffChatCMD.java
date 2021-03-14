package org.avaeriandev.prisontix.commands;

import org.avaeriandev.prisontix.Main;
import org.avaeriandev.prisontix.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.clip.placeholderapi.PlaceholderAPI;

public class StaffChatCMD implements CommandExecutor {

	@SuppressWarnings("unused")
	private Main plugin;
	
	public StaffChatCMD(Main plugin) {
		this.plugin = plugin;
		
		plugin.getCommand("sc").setExecutor(this);
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(sender instanceof Player) {
			Player plr = ((Player) sender);
			if(plr.hasPermission("staff.chat")) {
				if(String.join(" ", args).contentEquals("")) {
					plr.sendMessage(Utils.chat("&cMissing args"));
				} else {
					String rank = "%vault_prefix%";
					rank = PlaceholderAPI.setPlaceholders(plr, rank);
					Bukkit.broadcast(Utils.chat("&7[&cStaff&7] " + rank + "&f" + plr.getName() + "&7: &f") + String.join(" ", args), "staff.chat");
				}
			} else {
				plr.sendMessage(Utils.chat("&cYou do not have permission!"));
			}
		}
		return false;
	}
	
}
