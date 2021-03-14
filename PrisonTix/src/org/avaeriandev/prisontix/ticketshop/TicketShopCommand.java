package org.avaeriandev.prisontix.ticketshop;

import org.avaeriandev.prisontix.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TicketShopCommand implements CommandExecutor {

	public TicketShopCommand() {
		Main.PLUGIN.getCommand("ticketshop").setExecutor(this);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if(!(sender instanceof Player)) {
			return false;
		}
		
		Player plr = (Player) sender;
		
		plr.openInventory(TicketShopUI.GUI(plr));
		
		return true;
	}
	
}
