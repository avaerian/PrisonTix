package org.avaeriandev.prisontix.commands;

import java.util.ArrayList;

import org.avaeriandev.prisontix.Main;
import org.avaeriandev.prisontix.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class GiveXPPickaxe implements CommandExecutor{
	
	@SuppressWarnings("unused")
	private Main plugin;
	
	public GiveXPPickaxe(Main plugin) {
		this.plugin = plugin;
		
		plugin.getCommand("xppick").setExecutor(this);
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if(sender.hasPermission("admin.xppick")) {
			
			Player plr = Bukkit.getPlayer(args[0]);
			
			ItemStack xppick = new ItemStack(Material.DIAMOND_PICKAXE);
			ItemMeta meta = xppick.getItemMeta();
			
			ArrayList<String> lore = new ArrayList<String>();
			lore.add("Earn bonus tickets and experience!");
			meta.setDisplayName(Utils.chat("&bExperience Pickaxe"));
			meta.setLore(lore);
			meta.spigot().setUnbreakable(true);
			xppick.setItemMeta(meta);
			
			plr.getInventory().addItem(xppick);
			
		}
		
		return true;
	}
	
}
