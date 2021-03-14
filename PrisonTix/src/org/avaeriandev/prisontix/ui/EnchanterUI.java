package org.avaeriandev.prisontix.ui;

import org.avaeriandev.prisontix.Main;
import org.avaeriandev.prisontix.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.enchantment.PrepareItemEnchantEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class EnchanterUI {
	
	@SuppressWarnings("unused")
	private Main plugin;
	
	public EnchanterUI(Main plugin) {
		this.plugin = plugin;
	}
	
	public static Inventory inv;
	public static String inventory_name;
	public static int inv_rows = 1 * 9;
	
	public static void intialize() {
		inventory_name = Utils.chat("&5Enchanter");
		
		inv = Bukkit.createInventory(null, inv_rows);
	}
	
	// Define inventory contents
	@SuppressWarnings("deprecation")
	public static Inventory GUI (Player plr) {
		Inventory toReturn = Bukkit.createInventory(null, inv_rows, inventory_name);
		
		// Buttons
		Utils.createItem(toReturn, Material.PAPER.getId(), 1, 1, "&5Enchants 1 - 6", "&2Enchant levels 1 - 6", "&4Costs 1 ticket per level");
		Utils.createItem(toReturn, Material.PAPER.getId(), 1, 3, "&5Enchants 7 - 12", "&2Enchant levels 7 - 12", "&4Costs 1 ticket per level");
		Utils.createItem(toReturn, Material.PAPER.getId(), 1, 5, "&5Enchants 13 - 18", "&2Enchant levels 13 - 18", "&4Costs 1 ticket per level");
		Utils.createItem(toReturn, Material.PAPER.getId(), 1, 7, "&5Enchants 19 - 24", "&2Enchant levels 19 - 24", "&4Costs 1 ticket per level");
		Utils.createItem(toReturn, Material.PAPER.getId(), 1, 9, "&5Enchants 25 - 30", "&2Enchant levels 25 - 30", "&4Costs 1 ticket per level");
		
		toReturn.setContents(inv.getContents());
		return toReturn;
	}
	
	
	public static void clicked(Player plr, int slot, ItemStack clicked, Inventory inv, Main plugin) {
		
		
		
	}
	
	public void addCustomEnchantToTable(PrepareItemEnchantEvent e) {
		
	}
	
}
