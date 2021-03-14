package org.avaeriandev.prisontix.commands;

import org.avaeriandev.prisontix.Main;
import org.avaeriandev.prisontix.ui.WardenUI;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class WardenOpenUI implements CommandExecutor{
	
	@SuppressWarnings("unused")
	private Main plugin;
	
	public WardenOpenUI(Main plugin) {
		this.plugin = plugin;
		
		plugin.getCommand("wardenui").setExecutor(this);
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if(!(sender instanceof Player)) {
			// This is console
			return true;
		}
		
		Player plr = (Player) sender;
		
		if(plr.hasPermission("test.test")) {
			plr.openInventory(WardenUI.GUI(plr));
		}
		
		return false;
	}
	
}
