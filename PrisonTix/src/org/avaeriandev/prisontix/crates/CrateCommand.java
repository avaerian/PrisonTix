package org.avaeriandev.prisontix.crates;

import org.avaeriandev.prisontix.Main;
import org.avaeriandev.prisontix.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CrateCommand implements CommandExecutor {

	public CrateCommand() {
		Main.PLUGIN.getCommand("crate").setExecutor(this);
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if(!(sender.hasPermission("minerift.admin"))) {
			return false;
		}
		
		if(args.length > 0) {
			if(args[0].equalsIgnoreCase("give")) {
				try {
					Player plr = Bukkit.getPlayer(args[1]);
					Crate crate = CrateMaster.getCrateByIdentifier(args[2]);
					
					if(crate == null) {
						sender.sendMessage(Utils.chat("&cInvalid crate!"));
						return false;
					}
					
					plr.getInventory().addItem(crate.getCrateItem());
				} catch (Exception e) {
					sender.sendMessage(Utils.chat("&cInvalid arguments!"));
				}
			} else if(args[0].equalsIgnoreCase("test")) {
				Player plr = (Player) sender;
				if(CrateMaster.isCrate(plr.getItemInHand())) {
					plr.sendMessage(Utils.chat("&aThis is a crate!"));
				} else {
					plr.sendMessage(Utils.chat("&cThis is not a crate!"));
				}
			}
		}
		
		return true;
	}

}
