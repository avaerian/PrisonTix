package org.avaeriandev.prisontix.sys;

import java.util.HashMap;
import java.util.UUID;

import org.avaeriandev.prisontix.Main;
import org.avaeriandev.prisontix.utils.ConfigManager;
import org.avaeriandev.prisontix.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import me.clip.placeholderapi.PlaceholderAPI;
import me.clip.placeholderapi.PlaceholderHook;

@SuppressWarnings("unused")
public class TixSystem implements Listener{

	private Main plugin;
	
	public TixSystem(Main plugin) {
		registerPlaceholders();
		this.plugin = plugin;
	}
	
	private void registerPlaceholders() {
		PlaceholderAPI.registerPlaceholderHook("prisontix", new PlaceholderHook() {
			@Override
			public String onRequest(OfflinePlayer plr, String params) {
				if(plr!=null && plr.isOnline()) {
					return onPlaceholderRequest(plr.getPlayer(),params);
				}
				return null;
			}
			
			@Override
			public String onPlaceholderRequest(Player plr, String params) {
				if(plr==null) {
					return null;
				}
				
				if(params.equalsIgnoreCase("tickets")) {
					ConfigManager cm = new ConfigManager(plugin, plr);
					FileConfiguration f = cm.getConfig();
					int tempTix = f.getInt("tickets");
					return Integer.toString(tempTix);
				}
				return null;
			}
		});
	}
	
	/*
	
	private HashMap<UUID,Long> blocksMined = new HashMap<UUID,Long>();
	private int blocksMinedTotal = 0;
	
	@EventHandler (priority = EventPriority.HIGHEST, ignoreCancelled = true)
	public void onBlockBreak(BlockBreakEvent e) {
		Player plr = e.getPlayer();
		Material[] countBlocks = {
				// Ores
				Material.COAL_ORE,
				Material.IRON_ORE,
				Material.GOLD_ORE,
				Material.DIAMOND_ORE,
				Material.LAPIS_ORE,
				Material.REDSTONE_ORE,
				Material.EMERALD_ORE,
				Material.QUARTZ_ORE,
				// Ore Blocks
				Material.COAL_BLOCK,
				Material.IRON_BLOCK,
				Material.GOLD_BLOCK,
				Material.DIAMOND_BLOCK,
				Material.EMERALD_BLOCK,
				Material.QUARTZ_BLOCK,
				// Misc
				Material.STONE,
				Material.SANDSTONE,
				Material.STAINED_CLAY,
				Material.HARD_CLAY,
				Material.NETHERRACK,
				Material.ICE,
				Material.ENDER_STONE,
				// Wood
				Material.LOG
		};
		ConfigManager cm = new ConfigManager(plugin, plr);
		
		if (!(plr.hasPermission("prisontix.ignore"))) {
			Material block = e.getBlock().getType();
			
			for(int i = 0; i < countBlocks.length; i++) {
				if(countBlocks[i].equals(block)) {
					if(!(e.isCancelled())) {
						if(blocksMined.containsKey(plr.getUniqueId())) {
							blocksMinedTotal++;
							long blockCount = ((blocksMined.get(plr.getUniqueId())) + blocksMinedTotal);
							if(blockCount >= (int)100) {
								if(cm.exists()) {
									// Add Tickets
									FileConfiguration f = cm.getConfig();
									int tempTix = f.getInt("tickets");
									plr.sendMessage(Utils.chat("&aYou have received 2 tickets."));
									f.set("tickets", tempTix+=2);
									cm.saveConfig();
									
									// Reset Block Count
									blocksMinedTotal = 0;
									blocksMinedTotal++;
									blockCount = ((blocksMined.get(plr.getUniqueId())) + blocksMinedTotal);
								} else {
									Bukkit.getLogger().severe("CRITICAL ERROR! PrisonTix File does not exist!");
									Bukkit.broadcastMessage(Utils.chat("&c&lA CRITICAL ERROR HAS OCCURRED"));
									Bukkit.broadcastMessage(Utils.chat("&cPlugin: TICKETS"));
									Bukkit.broadcastMessage(Utils.chat("&cPLAYER STORAGE DOESN'T EXIST"));
									Bukkit.broadcastMessage(Utils.chat("&cPlease report to an owner immediately"));
									return;
								}
							}
						} else {
							blocksMinedTotal++;
							blocksMined.put(plr.getUniqueId(), (long) blocksMinedTotal);
						}
					}
				}
			}
		}
	}
	*/
}
