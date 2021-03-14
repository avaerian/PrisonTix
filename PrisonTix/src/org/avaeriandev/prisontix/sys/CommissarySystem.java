package org.avaeriandev.prisontix.sys;

import java.util.HashMap;
import java.util.UUID;

import org.avaeriandev.prisontix.Main;
import org.avaeriandev.prisontix.utils.Utils;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

enum TimeDoneReason {
	TIME_COMPLETE, TELEPORT
}

public class CommissarySystem implements Listener{
	
	static double[] tpPos = {1298.5,1270.5,1242.5};
	
	public static HashMap<UUID, BukkitRunnable> timers = new HashMap<>();
	
	/*
	@EventHandler
	public void onTeleport(PlayerTeleportEvent e) {
		Player plr = e.getPlayer();
		if(commissaryTime.containsKey(plr.getUniqueId())) {
			long commissaryCOOLDOWN = ((commissaryTime.get(plr.getUniqueId()) / 1000) + 120) - (System.currentTimeMillis() / 1000);
			while(commissaryCOOLDOWN < 119) {
				commissaryTime.remove(plr.getUniqueId());
				plr.sendMessage(Utils.chat("&aYou teleported outside of commissary!"));
			}
		}
	}*/
	
	@EventHandler
	public void onSignUse(PlayerInteractEvent e) {
		
		Player plr = e.getPlayer();
		
		if(e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			// Set general variables
			Block block = e.getClickedBlock();
			Material blockMat = block.getType();
			
			if (blockMat == Material.WALL_SIGN) {
				// Set sign variables
				BlockState state = block.getState();
				Sign sign = (Sign)state;
				String signtext = sign.getLine(0);
				
				if("§d[Commissary]".equals(signtext)) {
					// Find commissary level
					String cLevel = sign.getLine(1);
					cLevel = cLevel.replace("C", "");
					final int fcLevel = Integer.parseInt(cLevel);
					
					if(fcLevel == 1) {
						if(plr.getItemInHand().isSimilar(CustomItems.C1Ticket())) {
							
							CommissarySystem.removeItemFromHand(plr);
							plr.teleport(new Location(plr.getWorld(),242.5,48,tpPos[fcLevel - 1],-180,0));
							plr.sendMessage(Utils.chat("&aYou have entered commissary for 120 seconds."));
							CommissarySystem.commissaryTimer(plr);
							
						} else {
							plr.sendMessage(Utils.chat("&cThis is not the correct commissary!"));
							return;
						}
					} else if(fcLevel == 2) {
						if(plr.getItemInHand().isSimilar(CustomItems.C2Ticket())) {
							
							CommissarySystem.removeItemFromHand(plr);
							plr.teleport(new Location(plr.getWorld(),242.5,48,tpPos[fcLevel - 1],-180,0));
							plr.sendMessage(Utils.chat("&aYou have entered commissary for 120 seconds."));
							CommissarySystem.commissaryTimer(plr);
							
						} else {
							plr.sendMessage(Utils.chat("&cThis is not the correct commissary!"));
							return;
						}
					} else if(fcLevel == 3) {
						if(plr.getItemInHand().isSimilar(CustomItems.C3Ticket())) {
							
							CommissarySystem.removeItemFromHand(plr);
							plr.teleport(new Location(plr.getWorld(),242.5,48,tpPos[fcLevel - 1],-180,0));
							plr.sendMessage(Utils.chat("&aYou have entered commissary for 120 seconds."));
							CommissarySystem.commissaryTimer(plr);
							
						} else {
							plr.sendMessage(Utils.chat("&cThis is not the correct commissary!"));
							return;
						}
					}
				}
			}
		}
	}
	
	@EventHandler
	public void playerTeleport(PlayerTeleportEvent e) {
		
		Player plr = e.getPlayer();
		if(timers.containsKey(plr.getUniqueId())) {
			CommissarySystem.commissaryTimerDone(plr, timers.get(plr.getUniqueId()), TimeDoneReason.TELEPORT);
		}
	}
	
	private static void removeItemFromHand(Player plr) {
		if(plr.getItemInHand().getAmount() > 1) {
			plr.getItemInHand().setAmount(plr.getItemInHand().getAmount() - 1);
		} else if(plr.getItemInHand().getAmount() == 1) {
			plr.setItemInHand(new ItemStack(Material.AIR));
		}
		
		plr.updateInventory();
	}
	
	private static void commissaryTimerDone(Player plr, BukkitRunnable timer, TimeDoneReason reason) {
		timers.remove(plr.getUniqueId());
		timer.cancel();
		
		if(reason == TimeDoneReason.TIME_COMPLETE) {
			plr.performCommand("spawn");
			plr.sendMessage(Utils.chat("&aTime's up!"));
		} else if(reason == TimeDoneReason.TELEPORT) {
			plr.sendMessage(Utils.chat("&cYour commissary timer was canceled because you teleported!"));
		}
		
	}
	
	private static void commissaryTimer(Player plr) {	
		
		new BukkitRunnable() {

			int timesRun = 0;
			@Override
			public void run() {
				timesRun++;
				if(timesRun > 1) {
					CommissarySystem.commissaryTimerDone(plr, this, TimeDoneReason.TIME_COMPLETE);
				} else {
					timers.put(plr.getUniqueId(), this);
				}
			}
			
		}.runTaskTimer(Main.PLUGIN, 0, 20 * 120);
	}
	
}
