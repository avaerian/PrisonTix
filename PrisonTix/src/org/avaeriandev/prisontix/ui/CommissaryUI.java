package org.avaeriandev.prisontix.ui;

import org.avaeriandev.prisontix.Main;
import org.avaeriandev.prisontix.sys.CustomItems;
import org.avaeriandev.prisontix.utils.ConfigManager;
import org.avaeriandev.prisontix.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class CommissaryUI {
	
	@SuppressWarnings("unused")
	private Main plugin;
	
	public CommissaryUI(Main plugin) {
		this.plugin = plugin;
	}
	
	public static Inventory inv;
	public static String inventory_name;
	public static int inv_rows = 3 * 9;
	
	static int[] cCosts = {150,300,500};
	
	public static void intialize() {
		inventory_name = Utils.chat("Commissary");
		
		inv = Bukkit.createInventory(null, inv_rows);
	}
	
	// Define inventory contents
	@SuppressWarnings("deprecation")
	public static Inventory GUI (Player plr) {
		Inventory toReturn = Bukkit.createInventory(null, inv_rows, inventory_name);
		
		// Buttons
		Utils.createItem(inv, Material.PAPER.getId(), 1, 12, "&b&lC1 Ticket", "&7Temporary access to C1 shop.", "&7Costs &c150 &7tickets.", "", "&7Click to purchase.");
		Utils.createItem(inv, Material.PAPER.getId(), 1, 14, "&a&lC2 Ticket", "&7Temporary access to C2 shop.", "&7Costs &c300 &7tickets.", "", "&7Click to purchase.");
		Utils.createItem(inv, Material.PAPER.getId(), 1, 16, "&c&lC3 Ticket", "&7Temporary access to C3 shop.", "&7Costs &c500 &7tickets.", "", "&7Click to purchase.");
		
		// Horizontal Edges
		Utils.createItemByte(inv, Material.STAINED_GLASS_PANE.getId(), 7, 1, 1, "&r");
		Utils.createItemByte(inv, Material.STAINED_GLASS_PANE.getId(), 7, 1, 10, "&r");
		Utils.createItemByte(inv, Material.STAINED_GLASS_PANE.getId(), 7, 1, 19, "&r");
		Utils.createItemByte(inv, Material.STAINED_GLASS_PANE.getId(), 7, 1, 9, "&r");
		Utils.createItemByte(inv, Material.STAINED_GLASS_PANE.getId(), 7, 1, 18, "&r");
		Utils.createItemByte(inv, Material.STAINED_GLASS_PANE.getId(), 7, 1, 27, "&r");
		
		toReturn.setContents(inv.getContents());
		return toReturn;
	}
	
	
	public static void clicked(Player plr, int slot, ItemStack clicked, Inventory inv, Main plugin) {
		ConfigManager cm = new ConfigManager(plugin, plr);
		FileConfiguration f = cm.getConfig();
		int tempTix = f.getInt("tickets");
		
		if(slot == 11) {
			// C1
			if(tempTix >= 150) {
				f.set("tickets", tempTix - 150);
				cm.saveConfig();
				
				plr.playSound(plr.getLocation(), Sound.ORB_PICKUP, 1, 1);
				plr.getInventory().addItem(CustomItems.C1Ticket());
				plr.sendMessage(Utils.chat("&aYou have purchased a C1 Ticket!"));
				
			} else {
				plr.playSound(plr.getLocation(), Sound.NOTE_PLING, 1, 0.5F);
				plr.sendMessage(Utils.chat("&cSorry! You don't have enough tickets."));
			}
		}
		if(slot == 13) {
			// C2
			if(tempTix >= 300) {
				f.set("tickets", tempTix - 300);
				cm.saveConfig();
				
				plr.playSound(plr.getLocation(), Sound.ORB_PICKUP, 1, 1);
				plr.getInventory().addItem(CustomItems.C2Ticket());
				plr.sendMessage(Utils.chat("&aYou have purchased a C2 Ticket!"));
				
			} else {
				plr.playSound(plr.getLocation(), Sound.NOTE_PLING, 1, 0.5F);
				plr.sendMessage(Utils.chat("&cSorry! You don't have enough tickets."));
			}
			
			
		}
		if(slot == 15) {
			// C3
			if(tempTix >= 500) {
				f.set("tickets", tempTix - 500);
				cm.saveConfig();
				
				plr.playSound(plr.getLocation(), Sound.ORB_PICKUP, 1, 1);
				plr.getInventory().addItem(CustomItems.C3Ticket());
				plr.sendMessage(Utils.chat("&aYou have purchased a C3 Ticket!"));
				
			} else {
				plr.playSound(plr.getLocation(), Sound.NOTE_PLING, 1, 0.5F);
				plr.sendMessage(Utils.chat("&cSorry! You don't have enough tickets."));
			}
		}
	}
}
