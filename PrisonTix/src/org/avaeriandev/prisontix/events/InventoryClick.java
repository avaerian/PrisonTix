package org.avaeriandev.prisontix.events;

import org.avaeriandev.prisontix.Main;
import org.avaeriandev.prisontix.ticketshop.TicketShopUI;
import org.avaeriandev.prisontix.ui.CommissaryUI;
import org.avaeriandev.prisontix.ui.WardenUI;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class InventoryClick implements Listener{

	private Main plugin;
	
	public InventoryClick(Main plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onClick(InventoryClickEvent e) {
		String title= e.getInventory().getTitle();
		
		if(title.equals(CommissaryUI.inventory_name)) {
			e.setCancelled(true);
			if (e.getCurrentItem() == null) {
				return;
			}
			if (title.equals(CommissaryUI.inventory_name)) {
				CommissaryUI.clicked((Player) e.getWhoClicked(), e.getSlot(), e.getCurrentItem(), e.getInventory(), plugin);
			}
		}
		if(title.equals(WardenUI.inventory_name)) {
			e.setCancelled(true);
			if (e.getCurrentItem() == null) {
				return;
			}
			if (title.equals(WardenUI.inventory_name)) {
				WardenUI.clicked((Player) e.getWhoClicked(), e.getSlot(), e.getCurrentItem(), e.getInventory(), plugin);
			}
		}
		
		if(title.equals(TicketShopUI.inventory_name)) {
			e.setCancelled(true);
			if (e.getCurrentItem() == null) {
				return;
			}
			if (title.equals(TicketShopUI.inventory_name)) {
				TicketShopUI.clicked((Player) e.getWhoClicked(), e.getSlot(), e.getCurrentItem(), e.getInventory(), plugin);
			}
		}
	}
}
