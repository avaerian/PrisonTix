package org.avaeriandev.prisontix.auctionv2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

import org.avaeriandev.prisontix.Main;
import org.avaeriandev.prisontix.utils.ConfigManager;
import org.avaeriandev.prisontix.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import net.ess3.api.Economy;

public class AuctionItemPanel implements Listener {

	public static Inventory inv;
	public static String inventory_name;
	public static int inv_rows = 5 * 9;
	
	public static void intialize() {
		inventory_name = Utils.chat("&8View Item");
		
		inv = Bukkit.createInventory(null, inv_rows);
	}
	
	// Define inventory contents
	@SuppressWarnings("deprecation")
	public static Inventory GUI (Player plr, Listing listing) {
		
		inv.clear();
		
		Inventory toReturn = Bukkit.createInventory(null, inv_rows, inventory_name);
		
		for(int i = 0; i < inv.getSize(); i++) {
			Utils.createItemByte(inv, Material.STAINED_GLASS_PANE.getId(), 7, 1, (i + 1), "&r");
		}
		
		double balance;
		ConfigManager cm = new ConfigManager(Main.PLUGIN, plr);
		FileConfiguration f = cm.getConfig();
		int tickets = f.getInt("tickets");
		
		try {
			balance = Economy.getMoney(plr.getName());
		} catch (Exception e) {
			balance = 0;
		}
		
		// Item Information
		ItemStack item = new ItemStack(listing.getItem());
		
		ArrayList<String> itemInfoLore = new ArrayList<>(Arrays.asList(
			"",
			new StringBuilder().append("&7Seller: &b").append(Bukkit.getOfflinePlayer(listing.getSeller()).getName()).toString(),
			new StringBuilder().append("&7Bids: &3").append(listing.getBids().size()).toString(),
			"",
			new StringBuilder().append("&7Starting Bid: &a$").append(listing.getStartingPrice()).toString(),
			new StringBuilder().append("&7Bid Increment: &2$").append(listing.getBidIncrement()).toString(),
			new StringBuilder().append("&7Current Price: &6$").append(listing.getCurrentPrice()).toString()
		));
		
		ArrayList<String> auctionItemLore = new ArrayList<>();
		
		for(String line : AuctionUtils.getItemLore(listing)) {
			auctionItemLore.add(line);
		}
		auctionItemLore.add("&8&l&m-------------------------");
		auctionItemLore.add("&7Click to bid on the listing.");
		auctionItemLore.add("&8&l&m-------------------------");
		
		Utils.createItem(inv, new ItemStack(Material.EMPTY_MAP), 1, 12, "&aListing Information", itemInfoLore);
		Utils.createItem(inv, item, item.getAmount(), 14, AuctionUtils.getItemName(listing), auctionItemLore);
		Utils.createItemByte(inv, Material.DOUBLE_PLANT.getId(), 0, 1, 16, "&6Personal", "", "&7Balance: &a$" + balance, "&7Tickets: &b" + tickets);
		Utils.createItem(inv, Material.BARRIER.getId(), 1, 32, "&cClose");
		
		toReturn.setContents(inv.getContents());
		return toReturn;
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onInventoryClick(InventoryClickEvent e) {
		if(e.getInventory().getName().equals(AuctionItemPanel.inventory_name)) {
			
			e.setCancelled(true);
			
			Listing listing = null;
			Player plr = (Player) e.getWhoClicked();
			
			// Parse listing
			ItemStack itemInfo = e.getInventory().getItem(11);
			
			String seller = ChatColor.stripColor(itemInfo.getItemMeta().getLore().get(1));
			seller = seller.replace("Seller: ", "");
			UUID sellerUUID = Bukkit.getOfflinePlayer(seller).getUniqueId();
			
			for(Listing checkListing : AuctionMaster.listings.values()) {
				if(checkListing.getSeller().equals(sellerUUID)) {
					listing = checkListing;
					break;
				}
			}
			
			if(listing == null) {
				plr.closeInventory();
				plr.sendMessage(Utils.chat("&cAn error occurred with the auction house panel."));
				return;
			}
			
			int slot = e.getSlot() + 1;
			
			if(slot == 14) {
				
				double balance;
				try {
					balance = Economy.getMoney(plr.getName());
				} catch (Exception x) {
					balance = 0;
				}
				
				if(!(listing.getBids().containsKey(plr.getUniqueId()))) {
					if(balance >= (listing.getCurrentPrice() + listing.getBidIncrement())){
						listing.bid(plr.getUniqueId(), listing.getCurrentPrice() + listing.getBidIncrement());
						try {
							Economy.subtract(plr.getName(), listing.getCurrentPrice() + listing.getBidIncrement());
							plr.sendMessage(Utils.chat("&aYou bid on the auction house listing!"));
						} catch (Exception x) {
							plr.sendMessage(Utils.chat("&cAn error occurred while bidding for a listing!"));
						}
					} else {
						plr.sendMessage(Utils.chat("&cYou don't have enough money to bid!"));
					}
				} else {
					plr.sendMessage(Utils.chat("&cYou've already bid for this auction house listing!"));
				}
				
				plr.openInventory(AuctionItemPanel.GUI(plr, listing));
				
			} else if (slot == 32) {
				plr.closeInventory();
			}
		}
	}
	
}
