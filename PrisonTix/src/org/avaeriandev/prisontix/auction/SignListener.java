package org.avaeriandev.prisontix.auction;

import java.util.ArrayList;
import java.util.List;

import org.avaeriandev.prisontix.Main;
import org.avaeriandev.prisontix.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.material.MaterialData;

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.managers.RegionManager;

import net.milkbowl.vault.economy.Economy;

public class SignListener implements Listener {
	
	private static Economy econ = Bukkit.getServer().getServicesManager().getRegistration(Economy.class).getProvider();
	
	@SuppressWarnings("unused")
	private Main plugin;
	
	public SignListener(Main plugin) {
		this.plugin = plugin;
	}
	
	static List<Block> chests = new ArrayList<Block>();
	
	Location bLoc = null;
	
	@SuppressWarnings("deprecation")
	@EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
	public void onSignUse(PlayerInteractEvent e) {
		
		if(e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			Block block = e.getClickedBlock();
			if(block.getType() == Material.WALL_SIGN) {
				Sign sign = (Sign) block.getState();
				String signtext = sign.getLine(0);
				
				if("§e[BuyChest]".equals(signtext)) {
					if(econ.getBalance(e.getPlayer()) >= Integer.valueOf(sign.getLine(3))) {
						
						econ.withdrawPlayer(e.getPlayer().getName(), Integer.valueOf(sign.getLine(3)));
						e.getPlayer().sendMessage(ChatColor.GREEN + "You successfully purchased " + sign.getLine(1) + "'s auction chest!");
						
						sign.setLine(0, Utils.chat("&4[BuyChest]"));
						sign.setLine(1, "");
						sign.setLine(2, "Owned by:");
						sign.setLine(3, Utils.chat("&c") + e.getPlayer().getName());
						sign.update();
						
						/*
						 * South = + 1 Z
						 * North = - 1 Z
						 * East = + 1 X
						 * West = - 1 X
						 * 
						 * BUT REVERSE
						 * 
						 */
						Byte[] dirList = {2,3,4,5};
						
						// find sign direction
						if(sign.getData().equals(new MaterialData(Material.WALL_SIGN,dirList[0]))) { // NORTH
							bLoc = sign.getBlock().getLocation().add(0, 0, 1);
						}
						if(sign.getData().equals(new MaterialData(Material.WALL_SIGN,dirList[1]))) { // SOUTH
							bLoc = sign.getBlock().getLocation().subtract(0, 0, 1);
						}
						if(sign.getData().equals(new MaterialData(Material.WALL_SIGN,dirList[2]))) { // WEST
							bLoc = sign.getBlock().getLocation().add(1, 0, 0);
						}
						if(sign.getData().equals(new MaterialData(Material.WALL_SIGN,dirList[3]))) { // EAST
							bLoc = sign.getBlock().getLocation().subtract(1, 0, 0);
						}
							
						// add chest to check list
						
						chests.add(bLoc.getBlock());
						
					} else {
						e.getPlayer().sendMessage(ChatColor.RED + "You don't have enough money!");
					}
				} else if ("§4[BuyChest]".equals(signtext)) {
					if ((ChatColor.RED + e.getPlayer().getName()).equals(sign.getLine(3))) {
						// this is their chest! 
						Byte[] dirList = {2,3,4,5};
						
						// find sign direction
						if(sign.getData().equals(new MaterialData(Material.WALL_SIGN,dirList[0]))) { // NORTH
							bLoc = sign.getBlock().getLocation().add(0, 0, 1);
						}
						if(sign.getData().equals(new MaterialData(Material.WALL_SIGN,dirList[1]))) { // SOUTH
							bLoc = sign.getBlock().getLocation().subtract(0, 0, 1);
						}
						if(sign.getData().equals(new MaterialData(Material.WALL_SIGN,dirList[2]))) { // WEST
							bLoc = sign.getBlock().getLocation().add(1, 0, 0);
						}
						if(sign.getData().equals(new MaterialData(Material.WALL_SIGN,dirList[3]))) { // EAST
							bLoc = sign.getBlock().getLocation().subtract(1, 0, 0);
						}
						
						String rgList[] = {
								"Eah",
								"Dah",
								"Cah",
								"Bah",
								"Aah"
						};
						
						Chest chest = (Chest) bLoc.getBlock().getState();
						
						RegionManager rm = WorldGuardPlugin.inst().getRegionManager(e.getPlayer().getWorld());
						//ApplicableRegionSet set = rm.getApplicableRegions(block.getLocation());
						
						for(int i = 0; i < rgList.length; i++) {
							if(rm.getRegion(rgList[i]).contains(chest.getX(),chest.getY(),chest.getZ())) {
								Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + e.getPlayer().getName() + " group add tempChest");
								e.getPlayer().openInventory(chest.getInventory());
								return;
							}
						}
					} else {
						e.getPlayer().sendMessage(ChatColor.RED + "This isn't your chest!");
					}
					
				} else {
					return;
				}
			}
		}
	}
	
	@SuppressWarnings({ "deprecation" })
	public static void checkChests() {
		for(int i = 0; i < chests.size(); i++) {
			
			Byte[] dirList = {2,3,4,5};
			Chest chest = (Chest) chests.get(i).getLocation().getBlock().getState();
			Location signLoc = null;
			
			// find sign direction
			if(chest.getData().equals(new MaterialData(Material.CHEST,dirList[0]))) { // NORTH
				signLoc = chest.getBlock().getLocation().subtract(0, 0, 1);
			}
			if(chest.getData().equals(new MaterialData(Material.CHEST,dirList[1]))) { // SOUTH
				signLoc = chest.getBlock().getLocation().add(0, 0, 1);
			}
			if(chest.getData().equals(new MaterialData(Material.CHEST,dirList[2]))) { // WEST
				signLoc = chest.getBlock().getLocation().subtract(1, 0, 0);
			}
			if(chest.getData().equals(new MaterialData(Material.CHEST,dirList[3]))) { // EAST
				signLoc = chest.getLocation().add(1, 0, 0);
			}
			
			chest.getInventory().clear();
			signLoc.getBlock().setType(Material.AIR);
			chest.getBlock().setType(Material.BARRIER);
			
		}
		chests.clear();
		return;
	}
	
	@EventHandler
	public void onPlayerCloseInventory(InventoryCloseEvent e) {
		Player plr = (Player)e.getPlayer();
		Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + plr.getName() + " group remove tempChest");
	}
	
}
