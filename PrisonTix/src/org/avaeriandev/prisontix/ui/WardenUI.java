package org.avaeriandev.prisontix.ui;

import org.avaeriandev.prisontix.Main;
import org.avaeriandev.prisontix.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;

import me.clip.placeholderapi.PlaceholderAPI;

public class WardenUI {
	
	@SuppressWarnings("unused")
	private Main plugin;
	
	public WardenUI(Main plugin) {
		this.plugin = plugin;
	}
	
	public static Inventory inv;
	public static String inventory_name;
	public static int inv_rows = 3 * 9;
	
	public static void intialize() {
		inventory_name = Utils.chat("Warden");
		
		inv = Bukkit.createInventory(null, inv_rows);
	}
	
	// Define inventory contents
	@SuppressWarnings("deprecation")
	public static Inventory GUI (Player plr) {
		Inventory toReturn = Bukkit.createInventory(null, inv_rows, inventory_name);
		
		for(int i = 0; i < inv.getSize(); i++) {
			Utils.createItemByte(inv, Material.STAINED_GLASS_PANE.getId(), 7, 1, (i + 1), "&r");
		}
		
		// Buttons
		Utils.createItemByte(inv, Material.INK_SACK.getId(), 10, 1, 11, "&aConfirm");
		Utils.createItemByte(inv, Material.INK_SACK.getId(), 8, 1, 14, "&dRankup Information", "&7Ranking up allows you access to higher tier mines, ", "&7unlock better rewards and more to progress", "&7on your journey to freedom!", "", "&c&lWARNING", "&7Ward rankups will unrent your cell!");
		Utils.createItemByte(inv, Material.INK_SACK.getId(), 1, 1, 17, "&cCancel");
		
		toReturn.setContents(inv.getContents());
		return toReturn;
	}
	
	
	@SuppressWarnings("deprecation")
	public static void clicked(Player plr, int slot, ItemStack clicked, Inventory inv, Main plugin) {
		
		if(clicked.getType() == Material.INK_SACK) {
			MaterialData clickedData = clicked.getData();
			if(clickedData.getData() == 10) { // Confirm
				// Get rank info
				String rank = "%ezrankspro_rank%";
				rank = PlaceholderAPI.setPlaceholders(plr, rank);
				
				// Prestige if necessary
				if(rank.contains("A1")) {
					plr.performCommand("prestige");
				} else {
					plr.performCommand("rankup");
				}
				
			} else if(clickedData.getData() == 1) { // Cancel
				plr.closeInventory();
			} else {
				return;
			}
		} else {
			return;
		}
	}
	
}
