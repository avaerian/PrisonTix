package org.avaeriandev.prisontix.ticketshop;

import org.avaeriandev.prisontix.Main;
import org.avaeriandev.prisontix.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class TicketShopUI {

	public static Inventory inv;
	public static String inventory_name;
	public static int inv_rows = 3 * 9;
	
	public static void intialize() {
		inventory_name = Utils.chat("Ticket Shop");
		
		inv = Bukkit.createInventory(null, inv_rows);
	}
	
	// Define inventory contents
	@SuppressWarnings("deprecation")
	public static Inventory GUI (Player plr) {
		
		try {
			inv.clear();
		} catch (Exception e) {
		}
		
		Inventory toReturn = Bukkit.createInventory(null, inv_rows, inventory_name);
		
		// Window Panes
		for(int i = 0; i < inv.getSize(); i++) {
			Utils.createItemByte(inv, Material.STAINED_GLASS_PANE.getId(), 7, 1, (i + 1), "&r");
		}
		
		// Donor items
		Utils.createItem(inv, TicketShopMaster.getIcon(plr, 1), 1, 11, TicketShopMaster.getStatus(plr, 1), TicketShopMaster.getLore(plr, 1));
		Utils.createItem(inv, TicketShopMaster.getIcon(plr, 2), 1, 14, TicketShopMaster.getStatus(plr, 2), TicketShopMaster.getLore(plr, 2));
		Utils.createItem(inv, TicketShopMaster.getIcon(plr, 3), 1, 17, TicketShopMaster.getStatus(plr, 3), TicketShopMaster.getLore(plr, 3));
		
		toReturn.setContents(inv.getContents());
		return toReturn;
	}
	
	
	public static void clicked(Player plr, int slot, ItemStack clicked, Inventory inv, Main plugin) {
		
		int chosenSlot;
		
		if(slot == 10) {
			chosenSlot = 1;
		} else if(slot == 13) {
			chosenSlot = 2;
		} else if(slot == 16) {
			chosenSlot = 3;
		} else {
			return;
		}
		
		TicketReward ticketReward = TicketShopMaster.getChosenTicketReward(chosenSlot);
		TicketShopMaster.purchaseItem(plr, ticketReward, chosenSlot);
		plr.openInventory(TicketShopUI.GUI(plr));
		
	}
	
}
