package org.avaeriandev.prisontix.commands;

import org.avaeriandev.prisontix.Main;
import org.avaeriandev.prisontix.sys.CustomItems;
import org.avaeriandev.prisontix.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommissaryCommand implements CommandExecutor {

	public CommissaryCommand() {
		Main.PLUGIN.getCommand("commissary").setExecutor(this);
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if(sender.hasPermission("minerift.admin")) {
			try {
				Player plr = Bukkit.getPlayer(args[0]);
				
				if(args[1].equalsIgnoreCase("c1")) {
					plr.getInventory().addItem(CustomItems.C1Ticket());
				} else if (args[1].equalsIgnoreCase("c2")) {
					plr.getInventory().addItem(CustomItems.C2Ticket());
				} else if (args[1].equalsIgnoreCase("c3")) {
					plr.getInventory().addItem(CustomItems.C3Ticket());
				}
				
				return true;
			} catch (Exception e) {
				sender.sendMessage(Utils.chat("&cAn error occurred trying to give player a commissary ticket."));
				e.printStackTrace();
				return false;
			}
		}
		
		return false;
	}

}
