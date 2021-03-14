package org.avaeriandev.prisontix.commands;

import java.util.ArrayList;
import java.util.Arrays;

import org.avaeriandev.prisontix.Main;
import org.avaeriandev.prisontix.utils.ConfigManager;
import org.avaeriandev.prisontix.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import com.connorlinfoot.actionbarapi.ActionBarAPI;

public class TicketCommand implements CommandExecutor {

	public TicketCommand() {
		Main.PLUGIN.getCommand("tix").setExecutor(this);
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
					int amount = Integer.valueOf(args[2]);
					TicketCommand.giveTickets(plr, amount);
					sender.sendMessage(Utils.chat("&aSuccessfully gave " + plr.getName() + " " + amount + " tickets!"));
					
				} catch (Exception e) {
					sender.sendMessage(Utils.chat("&cInvalid arguments!"));
				}
			} else if(args[0].equalsIgnoreCase("remove")) {
				try {
					
					Player plr = Bukkit.getPlayer(args[1]);
					int amount = Integer.valueOf(args[2]);
					TicketCommand.removeTickets(plr, amount);
					sender.sendMessage(Utils.chat("&aSuccessfully removed " + amount + " tickets from " + plr.getName() + "!"));
					
				} catch (Exception e) {
					sender.sendMessage(Utils.chat("&cInvalid arguments!"));
				}
			} else if(args[0].equalsIgnoreCase("set")) {
				
				try {
					
					Player plr = Bukkit.getPlayer(args[1]);
					int amount = Integer.valueOf(args[2]);
					TicketCommand.setTickets(plr, amount);
					sender.sendMessage(Utils.chat("&aSuccessfully set " + plr.getName() + "'s tickets to " + amount + "!"));
					
				} catch (Exception e) {
					sender.sendMessage(Utils.chat("&cInvalid arguments!"));
				}
				
			} else if(args[0].equalsIgnoreCase("help")) {
				ArrayList<String> helpMessage = new ArrayList<>(Arrays.asList(
					"&bTicket Help Page",
					"",
					"&b/tickets give <username> <amount>",
					"&b/tickets remove <username> <amount>",
					"&b/tickets set <username> <amount>",
					"&b/tickets info <username>",
					"&b/tickets help"
				));
				
				for(String line : helpMessage) {
					sender.sendMessage(Utils.chat(line));
				}
			} else if(args[0].equalsIgnoreCase("info")) {
				try {
					Player plr = Bukkit.getPlayer(args[1]);
					ConfigManager cm = new ConfigManager(Main.PLUGIN, plr);
					FileConfiguration f = cm.getConfig();
					int tickets = f.getInt("tickets");
					
					sender.sendMessage(Utils.chat("&a" + plr.getName() + " has " + tickets + " tickets."));
					
				} catch (Exception e) {
				}
			} else {
				try {
					
					Player plr = Bukkit.getPlayer(args[0]);
					
					ConfigManager cm = new ConfigManager(Main.PLUGIN, plr);
					FileConfiguration f = cm.getConfig();
					int tickets = f.getInt("tickets");
					
					f.set("tickets", tickets + 3);
					cm.saveConfig();
					
					// Play special effects
					ActionBarAPI.sendActionBar(plr, Utils.chat("&aYou have earned 3 tickets!"));
					plr.playSound(plr.getLocation(), Sound.ORB_PICKUP, 1, 2);
				} catch (Exception e) {
				}
			}
			
		}
		
		return true;
	}
	
	public static void giveTickets(Player plr, int amount) {
		ConfigManager cm = new ConfigManager(Main.PLUGIN, plr);
		FileConfiguration f = cm.getConfig();
		int tickets = f.getInt("tickets");
		
		f.set("tickets", tickets + amount);
		cm.saveConfig();
	}
	
	public static void removeTickets(Player plr, int amount) {
		ConfigManager cm = new ConfigManager(Main.PLUGIN, plr);
		FileConfiguration f = cm.getConfig();
		int tickets = f.getInt("tickets");
		
		f.set("tickets", tickets - amount);
		cm.saveConfig();
	}

	public static void setTickets(Player plr, int amount) {		
		ConfigManager cm = new ConfigManager(Main.PLUGIN, plr);
		FileConfiguration f = cm.getConfig();
		
		f.set("tickets", amount);
		cm.saveConfig();
	}
	
}
