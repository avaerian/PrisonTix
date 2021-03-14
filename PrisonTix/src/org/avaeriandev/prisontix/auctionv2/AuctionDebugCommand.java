package org.avaeriandev.prisontix.auctionv2;

import org.avaeriandev.prisontix.Main;
import org.avaeriandev.prisontix.utils.Utils;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class AuctionDebugCommand implements CommandExecutor {
	
	public AuctionDebugCommand() {
		Main.PLUGIN.getCommand("ah").setExecutor(this);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if(!(sender instanceof Player)) {
			return false;
		}
		
		Player plr = (Player) sender;
		
		if(args.length > 0) {
			if(args[0].equalsIgnoreCase("sell")) {
				ItemStack item = plr.getItemInHand();
				
				if(item == null || item.getType() == Material.AIR) {
					plr.sendMessage(Utils.chat("&cYou need to be holding an item!"));
					return false;
				}
				
				try {
					AuctionMaster.createListing(plr, item, Integer.parseInt(args[1]), Integer.parseInt(args[2]));
				} catch (Exception e) {
					e.printStackTrace();
					plr.sendMessage(Utils.chat("&cInvalid arguments!"));
					return false;
				}
			} else if (args[0].equalsIgnoreCase("remove")) {
				AuctionMaster.removeListing(plr);
			}
		}
		
		return true;
	}

	
	
}
