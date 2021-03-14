package org.avaeriandev.prisontix.events;

import java.util.HashMap;
import java.util.UUID;

import org.avaeriandev.prisontix.Main;
import org.avaeriandev.prisontix.utils.ConfigManager;
import org.avaeriandev.prisontix.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.flags.DefaultFlag;
import com.sk89q.worldguard.protection.flags.StateFlag.State;
import com.sk89q.worldguard.protection.managers.RegionManager;

public class BlockBreak implements Listener{
	
	public static HashMap<UUID,Long> blocksMined = new HashMap<UUID,Long>();

	private Main plugin;
	
	public BlockBreak(Main plugin) {
		this.plugin = plugin;
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onBreak(BlockBreakEvent e) {
		Player plr = e.getPlayer();
		Block block = e.getBlock();
		WorldGuardPlugin wgp = getWorldGuard();
		RegionManager rm = wgp.getRegionManager(plr.getWorld());
		//ApplicableRegionSet set = rm.getApplicableRegions(block.getLocation());
		
		String[] woodRGList = {
				"ewood",
				"dwood",
				"cwood1",
				"cwood2",
				"bwood",
				"awood"
		};
		
		@SuppressWarnings("unused")
		String[] mineRGList = {
				"emine",
				"dmine",
				"cmine",
				"bmine",
				"amine"
		};
		
		for(int i = 0; i < woodRGList.length; i++) {
			if(rm.getRegion(woodRGList[i]).getFlag(DefaultFlag.BLOCK_BREAK) == State.ALLOW) {
				if(rm.getRegion(woodRGList[i]).contains(block.getX(), block.getY(), block.getZ())) {
					if(!(rm.getRegion(woodRGList[i]).isOwner(plr.getName()))) {
						if(!(e.getPlayer().getItemInHand().getType() == Material.DIAMOND_PICKAXE)) {
							if(!(block.getType() == Material.LOG
									|| block.getType() == Material.LOG_2
									|| block.getType() == Material.LEAVES
									|| block.getType() == Material.LEAVES_2
									|| block.getType() == Material.TRAP_DOOR
									|| block.getType() == Material.LADDER
									|| block.getType() == Material.WOODEN_DOOR)) {
								
								e.setCancelled(true);
								return;
							} else {
								if(plr.getItemInHand().getType() == Material.SHEARS) {
									if(e.getBlock().getType() == Material.LEAVES || e.getBlock().getType() == Material.LEAVES_2) {
										
										ItemStack toolUsed = e.getPlayer().getItemInHand();
										
										e.setCancelled(true);
										plr.updateInventory();
										
										e.getBlock().setType(Material.AIR);
										e.getBlock().getDrops().clear();
										e.getBlock().getWorld().dropItem(e.getBlock().getLocation(), new ItemStack(Material.LEAVES, 1));
										
										toolUsed.setDurability((short) (toolUsed.getDurability() + 1));
										
										if(toolUsed.getDurability() >= toolUsed.getType().getMaxDurability()) {
											plr.setItemInHand(new ItemStack(Material.AIR));
											plr.playSound(plr.getLocation(), Sound.ITEM_BREAK, 1, 1);
										}
										
										plr.updateInventory();
									}
								}
							}
						} else {
							e.setCancelled(true);
							break;
						}
					} else {
						break;
					}
				}
			}
		}
		
		if(block.getType() == Material.ICE) {
			if(rm.getApplicableRegions(block.getLocation()).allows(DefaultFlag.BLOCK_BREAK)) {
				e.setCancelled(true);
				block.setType(Material.AIR);
			}
		}
		
		// Experience Pickaxe
		
		ItemStack item = plr.getItemInHand();
		
		if(item.getType().equals(Material.DIAMOND_PICKAXE) && item.hasItemMeta() && item.getItemMeta().hasDisplayName()) {
			if(plr.getItemInHand().getItemMeta().getDisplayName().equals(Utils.chat("&bExperience Pickaxe"))){
			
				if(!(blocksMined.containsKey(plr.getUniqueId()))) {
					blocksMined.put(plr.getUniqueId(), (long) 0);
				}
				
				ConfigManager cm = new ConfigManager(plugin, plr);
				FileConfiguration f = cm.getConfig();
				int tempTix = f.getInt("tickets");
				
				// Give tickets
				if(blocksMined.get(plr.getUniqueId()) >= 30) {
					f.set("tickets", tempTix + 3);
					cm.saveConfig();
					plr.sendMessage(Utils.chat("&aYou have received 3 tickets."));
					blocksMined.put(plr.getUniqueId(), (long) 0);
				}
				
				blocksMined.put(plr.getUniqueId(), blocksMined.get(plr.getUniqueId()) + 1);
				
				// Give XP
				plr.giveExp(1);
				
			}	
		}
		
		return;
	}
	
	private WorldGuardPlugin getWorldGuard() {
	    Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin("WorldGuard");

	    // WorldGuard may not be loaded
	    if (plugin == null || !(plugin instanceof WorldGuardPlugin)) {
	        //May disable Plugin
	        return null; // Maybe you want throw an exception instead
	    }

	    return (WorldGuardPlugin) plugin;
	}
	
}
