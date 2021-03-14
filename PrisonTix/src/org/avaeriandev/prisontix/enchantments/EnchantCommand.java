package org.avaeriandev.prisontix.enchantments;

import java.util.ArrayList;
import java.util.Arrays;

import org.avaeriandev.prisontix.Main;
import org.avaeriandev.prisontix.utils.Utils;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class EnchantCommand implements CommandExecutor {
	
	public EnchantCommand() {
		Main.PLUGIN.getCommand("enchantdebug").setExecutor(this);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if(!(sender instanceof Player)) {
			return false;
		}
		
		Player plr = (Player) sender;
		
		if(!(plr.hasPermission("minerift.admin"))) {
			plr.sendMessage(Utils.chat("&cYou don't have permission!"));
			return false;
		}
		
		ItemStack testItem = new ItemStack(Material.DIAMOND_PICKAXE);
		ItemMeta itemMeta = testItem.getItemMeta();
		ArrayList<String> lore = new ArrayList<>(Arrays.asList(Utils.chat("&cMolten 1")));
		itemMeta.setLore(lore);
		testItem.setItemMeta(itemMeta);
		
		plr.getInventory().addItem(testItem);
		
		return true;
	}
}