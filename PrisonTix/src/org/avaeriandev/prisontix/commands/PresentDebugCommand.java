package org.avaeriandev.prisontix.commands;

import org.avaeriandev.prisontix.Main;
import org.avaeriandev.prisontix.sys.CustomItems;
import org.avaeriandev.prisontix.sys.PresentUtils;
import org.avaeriandev.prisontix.utils.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PresentDebugCommand implements CommandExecutor {
	
	public PresentDebugCommand() {
		Main.PLUGIN.getCommand("present").setExecutor(this);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if(!(sender instanceof Player)) {
			return false;
		}
		
		Player plr = (Player) sender;
		
		if(args.length > 0) {
			if(args[0].equalsIgnoreCase("test")) {
				
				if(PresentUtils.isPresent(plr.getItemInHand())) {
					plr.sendMessage(Utils.chat("&aThis is a present!"));
				} else {
					plr.sendMessage(Utils.chat("&cThis is not a present!"));
				}
			}
		} else {
			plr.getInventory().addItem(CustomItems.present());
		}
		
		return true;
	}

	
	
}
