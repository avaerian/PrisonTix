package org.avaeriandev.prisontix.events;

import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ItemDespawnEvent;
import org.bukkit.inventory.ItemStack;

public class ItemDespawn implements Listener {

	@EventHandler
	public void onItemDespawn(ItemDespawnEvent e) {
		
		Item entityItem = e.getEntity();
		ItemStack item = entityItem.getItemStack();
		
		if(item.hasItemMeta()
				&& item.getItemMeta().hasLore()
				&& item.getItemMeta().getLore().get(0).equals("VACUUM BYPASS")) {
			e.setCancelled(true);
		}
	}
	
}
