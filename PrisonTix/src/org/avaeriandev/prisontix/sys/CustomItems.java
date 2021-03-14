package org.avaeriandev.prisontix.sys;

import java.util.ArrayList;
import java.util.Arrays;

import org.avaeriandev.prisontix.utils.Utils;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

public class CustomItems {

	public static ItemStack C1Ticket() {
		
		ItemStack ticket = new ItemStack(Material.PAPER);
		ItemMeta meta = ticket.getItemMeta();
		
		ArrayList<String> lore = new ArrayList<String>();
		lore.add(Utils.chat("&7Temporary access to C1 shop."));
		
		meta.setDisplayName(Utils.chat("&b&lC1 Ticket"));
		meta.setLore(lore);
		ticket.setItemMeta(meta);
		
		return ticket; 
		
	}
	
	public static ItemStack C2Ticket() {
		
		ItemStack ticket = new ItemStack(Material.PAPER);
		ItemMeta meta = ticket.getItemMeta();
		
		ArrayList<String> lore = new ArrayList<String>();
		lore.add(Utils.chat("&7Temporary access to C2 shop."));
		
		meta.setDisplayName(Utils.chat("&a&lC2 Ticket"));
		meta.setLore(lore);
		ticket.setItemMeta(meta);
		
		return ticket;
		
	}
	
	public static ItemStack C3Ticket() {
		
		ItemStack ticket = new ItemStack(Material.PAPER);
		ItemMeta meta = ticket.getItemMeta();
		
		ArrayList<String> lore = new ArrayList<String>();
		lore.add(Utils.chat("&7Temporary access to C3 shop."));
		
		meta.setDisplayName(Utils.chat("&c&lC3 Ticket"));
		meta.setLore(lore);
		ticket.setItemMeta(meta);
		
		return ticket;
	}
	
	public static ItemStack present() {
		
		ItemStack present = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
		SkullMeta presentMeta = (SkullMeta) present.getItemMeta();
		presentMeta.setOwner("XT4");
		presentMeta.setDisplayName(Utils.chat("&aPresent"));
		presentMeta.setLore(new ArrayList<String>(Arrays.asList(Utils.chat("&7Right click to open your present!"))));
		
		present.setItemMeta(presentMeta);
		return present;
	}
	
	public static ItemStack mythicCrate() {
		
		ItemStack crate = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
		SkullMeta crateMeta = (SkullMeta) crate.getItemMeta();
		crateMeta.setOwner("KingCat1337");
		crateMeta.setDisplayName(Utils.chat("&dMythic Crate"));
		crateMeta.setLore(new ArrayList<String>(Arrays.asList(Utils.chat("&7Right click to open this &dMythic Crate!"))));
		
		crate.setItemMeta(crateMeta);
		return crate;
	}
	
}
