package org.avaeriandev.prisontix.sys;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

public class PresentUtils {

	public static boolean isPresent(ItemStack item) {
		if(item != null && item.getType() == Material.SKULL_ITEM) {
			SkullMeta itemMeta = (SkullMeta) item.getItemMeta();
			if(itemMeta.hasOwner() && itemMeta.getOwner().equalsIgnoreCase("XT4")) {
				return true;
			}
		}
		return false;
	}
	
}
