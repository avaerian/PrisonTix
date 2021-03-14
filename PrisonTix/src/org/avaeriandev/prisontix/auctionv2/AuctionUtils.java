package org.avaeriandev.prisontix.auctionv2;

import java.util.ArrayList;
import java.util.Arrays;

import org.avaeriandev.prisontix.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import net.milkbowl.vault.item.Items;

public class AuctionUtils {

	private static World world = Bukkit.getWorld("Titan");
	private static ArrayList<LocationDirection> locations = new ArrayList<>();
	
	public static void defineLocations() {
		locations = new ArrayList<>(Arrays.asList(
			
			new LocationDirection(new Location(world, 184.5, 51.6, 1306.5), Direction.WEST),
			new LocationDirection(new Location(world, 184.5, 51.6, 1304.5), Direction.WEST),
			new LocationDirection(new Location(world, 184.5, 51.6, 1302.5), Direction.WEST)
			
		));
	}
	
	public static ArrayList<LocationDirection> getLocations() {
		return locations;
	}
	
	public static Location getBlockLocation(Location loc) {
		
		double x = Math.floor(loc.getX());
		double y = Math.floor(loc.getY());
		double z = Math.floor(loc.getZ());
		
		return new Location(loc.getWorld(), x, y, z);
	}
	
	public static String getItemName(Listing listing) {
		try {
			
			if(listing.getItem().getItemMeta().hasDisplayName()) {
				return listing.getItem().getItemMeta().getDisplayName();
			} else {
				return "&f" + Items.itemByType(listing.getItem().getType()).getName();
			}
			
		} catch (Exception e) {
		}
		
		return "NULL";
	}
	
	public static ArrayList<String> getItemLore(Listing listing) {
		try {
			return new ArrayList<>(listing.getItem().getItemMeta().getLore());
		} catch (Exception e) {
			return new ArrayList<>();
		}
	}
}
