package org.avaeriandev.prisontix.enchantments;

import java.util.ArrayList;
import java.util.Arrays;

import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class EnchantMaster {

	private static ArrayList<Enchantment> enchantments;
	
	public static void defineEnchantments() {
		enchantments = new ArrayList<>(Arrays.asList(
			new Enchantment("Molten", Enchant.MOLTEN, 10, new ArrayList<>(Arrays.asList("Has a chance to upgrade the mined ore.")), ToolType.PICKAXE)
		));
	}
	
	public static boolean hasEnchantment(ItemStack item, Enchantment enchant) {
		if(item.hasItemMeta()) {
			ItemMeta itemMeta = item.getItemMeta();
			if(itemMeta.hasLore()) {
				ArrayList<String> lore = new ArrayList<>(itemMeta.getLore());
				
				for(String line : lore) {
					if(line.contains(enchant.getName())) {
						return true;
					}
				}
				
			}
		}
		
		return false;
	}
	
	public static boolean isUsingCorrectTool(ItemStack toolUsed, Enchantment enchant) {
		
		ArrayList<String> toolMaterials = new ArrayList<>(Arrays.asList("WOOD", "STONE", "GOLD", "DIAMOND"));
		String usedMaterial = toolUsed.getType().name().replaceAll("_", "");
		
		for(int i = 0; i < toolMaterials.size(); i++) {
			usedMaterial = usedMaterial.replaceAll(toolMaterials.get(i), "");
		}
				
		ToolType toolType = enchant.getToolType();
		
		if(usedMaterial.equals(toolType.name())) {
			return true;
		}
		
		return false;
	}
	
	public static Enchantment getEnchantment(Enchant enchant) {
		for(Enchantment enchantment : enchantments) {
			if(enchantment.getIdentifier() == enchant) {
				return enchantment;
			}
		}
		return null;
	}
	
	public static int getLevel(ItemStack toolUsed, Enchantment enchant) {
		try {
			ArrayList<String> lore = new ArrayList<>(toolUsed.getItemMeta().getLore());
			
			for(String line : lore) {
				if(line.contains(enchant.getName())) {
					line = ChatColor.stripColor(line);
					line = line.replace(enchant.getName(), "");
					line = line.trim();
					int level = Integer.parseInt(line);
					return level;
				}
			}
			
			return -1;
			
		} catch (Exception e) {
			return -1;
		}
	}
}
