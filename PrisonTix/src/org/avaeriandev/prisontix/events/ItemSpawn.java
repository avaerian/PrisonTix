package org.avaeriandev.prisontix.events;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ItemSpawnEvent;
import org.bukkit.inventory.ItemStack;

public class ItemSpawn implements Listener {

	@EventHandler
	public void onItemSpawn(ItemSpawnEvent e) {
		
		if(e.getEntity().getItemStack().getType() == Material.LOG || e.getEntity().getItemStack().getType() == Material.LOG_2) {
			e.getEntity().setItemStack(new ItemStack(Material.LOG));
		}
	}
	
}
