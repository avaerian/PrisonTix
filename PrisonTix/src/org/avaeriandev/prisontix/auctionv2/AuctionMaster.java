package org.avaeriandev.prisontix.auctionv2;

import java.util.Arrays;
import java.util.HashMap;
import java.util.UUID;

import org.avaeriandev.prisontix.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.block.Sign;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.Vector;

public class AuctionMaster {

	public static HashMap<UUID, Listing> listings = new HashMap<>();
	private static HashMap<Location, Listing> listingLocations = new HashMap<>();
	
	public static Listing getListing(OfflinePlayer plr) {
		if(listings.containsKey(plr.getUniqueId())) {
			Listing listing = listings.get(plr.getUniqueId());
			return listing;
		} else {
			return null;
		}
	}
	
	@SuppressWarnings("deprecation")
	public static void createListing(OfflinePlayer plr, ItemStack item, int startingPrice, int bidIncrement) {
		
		if(listings.containsKey(plr.getUniqueId())) {
			if(plr.isOnline()) {
				Bukkit.getPlayer(plr.getUniqueId()).sendMessage(Utils.chat("&cYou already have a listing in the auction house!"));
			}
			return;
		}
		
		Listing listing = new Listing(item, startingPrice, bidIncrement, plr.getUniqueId());
		listings.put(plr.getUniqueId(), listing);
		
		// Physical Location
		
		for(LocationDirection locDir : AuctionUtils.getLocations()) {
			Location location = locDir.getLocation();
			if(!(AuctionMaster.isLocationOccupied(location))) {
				listingLocations.put(location, listing);
				
				// ArmorStand
				ArmorStand as = location.getWorld().spawn(location, ArmorStand.class);
								
				as.setHelmet(new ItemStack(Material.GLASS));
				as.setBasePlate(false);
				as.setArms(false);
				as.setVisible(false);
				as.setCanPickupItems(false);
				as.setGravity(false);
				as.setCustomName(listing.getSeller().toString());
				as.setCustomNameVisible(false);
				
				listing.setLocation(locDir);
				listing.setArmorStand(as);
				
				ItemStack droppedItem = new ItemStack(item);
				ItemMeta itemMeta = droppedItem.getItemMeta();
				itemMeta.setLore(Arrays.asList("VACUUM BYPASS"));
				droppedItem.setItemMeta(itemMeta);
				
				// Item
				Item entityItem = location.getWorld().dropItem(AuctionUtils.getBlockLocation(location).add(0.5, 2, 0.5), droppedItem);
				entityItem.setPickupDelay(Integer.MAX_VALUE);
				entityItem.setVelocity(new Vector(0, 0, 0));
				
				// Sign
				Location signLoc = LocationUtils.getLocationFromDirection(locDir);
				
				signLoc.getBlock().setType(Material.WALL_SIGN);
				listing.setSign(signLoc.getBlock());
				
				Sign s  = (Sign) signLoc.getBlock().getState();
				
				// Temporary Data
				int price = listing.getCurrentPrice();
				int bids = listing.getBids().size();
				
				s.setRawData(LocationUtils.getDirectionID(locDir.getDirection()));
				s.setLine(0, Utils.chat("&0&l[Bid]"));
				s.setLine(1, Utils.chat("&6&l" + price + " Coins"));
				s.setLine(2, Utils.chat("&2" + bids + " Bids"));
				s.setLine(3, Utils.chat("&a24H"));
				s.update();
				
				listing.setEntityItem(entityItem);
				break;
			}
		}
		
		if(plr.isOnline()) {
			Bukkit.getPlayer(plr.getUniqueId()).sendMessage(Utils.chat("&aYou put an item on the auction house!"));
		}
	}
	
	public static void removeListing(OfflinePlayer plr) {
		
		Listing listing = listings.get(plr.getUniqueId());
		LocationDirection locDir = listing.getLocation();
		Location location = locDir.getLocation();
		
		listings.remove(plr.getUniqueId());
		
		Location signLoc = LocationUtils.getLocationFromDirection(listing.getLocation());
		signLoc.getBlock().setType(Material.AIR);
		
		try {
			listingLocations.remove(location);
			listing.getArmorStand().remove();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		listing.getEntityItem().remove();
		
		if(plr.isOnline()) {
			Bukkit.getPlayer(plr.getUniqueId()).sendMessage(Utils.chat("&cYou removed your auction house listing!"));
		}
	}
	
	
	
	public static boolean isLocationOccupied(Location location) {
		if(listingLocations.containsKey(location)) {
			return true;
		} else {
			return false;
		}
	}
	
}
