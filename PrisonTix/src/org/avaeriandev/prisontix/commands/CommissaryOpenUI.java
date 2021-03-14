package org.avaeriandev.prisontix.commands;

import org.avaeriandev.prisontix.Main;
import org.avaeriandev.prisontix.ui.CommissaryUI;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommissaryOpenUI implements CommandExecutor{
	
	@SuppressWarnings("unused")
	private Main plugin;
	
	public CommissaryOpenUI(Main plugin) {
		this.plugin = plugin;
		
		plugin.getCommand("commissaryui").setExecutor(this);
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if(!(sender instanceof Player)) {
			// This is console
			return true;
		}
		
		Player plr = (Player) sender;
		
		if(plr.hasPermission("test.test")) {
			plr.openInventory(CommissaryUI.GUI(plr));
		}
		
		return false;
	}
	
}
