package org.avaeriandev.prisontix.enchantments.handlers;

import java.util.Random;

import org.avaeriandev.prisontix.enchantments.Enchant;
import org.avaeriandev.prisontix.enchantments.EnchantMaster;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

public class Molten implements Listener {

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onBlockBreak(BlockBreakEvent e) {
		
		Player plr = e.getPlayer();
		Block block = e.getBlock();
		int random = new Random().nextInt(100);
		int[] chances = {10, 20, 35, 40, 50, 65, 75, 80, 90, 100};
		
		ItemStack toolUsed = plr.getItemInHand();
		
		if(EnchantMaster.isUsingCorrectTool(toolUsed, EnchantMaster.getEnchantment(Enchant.MOLTEN))) {
			if(EnchantMaster.hasEnchantment(toolUsed, EnchantMaster.getEnchantment(Enchant.MOLTEN))) {
				
				if(random <= chances[EnchantMaster.getLevel(toolUsed, EnchantMaster.getEnchantment(Enchant.MOLTEN)) - 1]) {
					// Play effect
					block.getWorld().playEffect(block.getLocation(), Effect.MOBSPAWNER_FLAMES, 1);
					block.getWorld().playSound(block.getLocation(), Sound.FIZZ, 1, 1);
					
					// Replace drops
					block.setType(Material.AIR);
					block.getWorld().dropItemNaturally(block.getLocation(), new ItemStack(Material.DIAMOND));
				}
			}
		}
		
	}
	
}
