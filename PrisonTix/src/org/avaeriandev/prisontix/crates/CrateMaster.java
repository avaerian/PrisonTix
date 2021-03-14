package org.avaeriandev.prisontix.crates;

import java.util.ArrayList;
import java.util.Arrays;

import org.avaeriandev.prisontix.Main;
import org.avaeriandev.prisontix.sys.CustomItems;
import org.avaeriandev.prisontix.utils.CustomEntityFirework;
import org.avaeriandev.prisontix.utils.Utils;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.scheduler.BukkitRunnable;

public class CrateMaster implements Listener {

	private static ArrayList<Crate> crates = new ArrayList<>();
	private static long rewardDelay = 20; // ticks (20 = 1s)
	
	public static void defineCrates() {
		crates = new ArrayList<>(Arrays.asList(
			//new Crate("&dMythic Crate", "mythic", CustomItems.mythicCrate())))
		));
	}
	
	@EventHandler
	public static void onRightClick(PlayerInteractEvent e) {
		Player plr = e.getPlayer();
		if(CrateMaster.isCrate(plr.getItemInHand())) {
			if(e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
				
				e.setCancelled(true);
				
				Crate crate = CrateMaster.getCrateByItem(plr.getItemInHand());
				
				if(plr.getItemInHand().getAmount() > 1) {
					plr.getItemInHand().setAmount(plr.getItemInHand().getAmount() - 1);
				} else {
					plr.setItemInHand(new ItemStack(Material.AIR));
				}
				plr.updateInventory();
				
				plr.sendMessage(Utils.chat("&aYou opened a " + crate.getDisplayName() + "!"));
				plr.playSound(plr.getLocation(), Sound.CHEST_OPEN, 1, 0.5F);
				
				new BukkitRunnable() {
					@Override
					public void run() {
						
						Location location = plr.getEyeLocation();
						
						// Firework Effect
						FireworkEffect.Builder builder = FireworkEffect.builder();
						FireworkEffect effect = builder.flicker(true).trail(false).with(FireworkEffect.Type.BALL).withColor(Color.FUCHSIA).withFade(Color.PURPLE).build();
						CustomEntityFirework.spawn(location, effect);
						CustomEntityFirework.spawn(location, effect, plr);
						
						plr.playSound(plr.getLocation(), Sound.LEVEL_UP, 1, 0.5F);
						plr.sendMessage(Utils.chat("&aYou received a REWARD HERE!"));
					}
				}.runTaskLater(Main.PLUGIN, rewardDelay);
			}
		}
	}
	
	public static boolean isCrate(ItemStack item) {
		if(item != null && item.getType() == Material.SKULL_ITEM) {
			SkullMeta itemMeta = (SkullMeta) item.getItemMeta();
			for(Crate crate : crates) {
				SkullMeta crateMeta = (SkullMeta) crate.getCrateItem().getItemMeta();
				String plrSkin = crateMeta.getOwner();
				if(itemMeta.hasOwner() && itemMeta.getOwner().equalsIgnoreCase(plrSkin)) {
					return true;
				}
			}
		}
		return false;
	}
	
	public static Crate getCrateByIdentifier(String identifier) {
		for(Crate crate : crates) {
			if(crate.getIdentifier().equalsIgnoreCase(identifier)) {
				return crate;
			}
		}
		
		return null;
	}
	
	public static Crate getCrateByDisplayName(String displayName) {
		for(Crate crate : crates) {
			if(crate.getDisplayName().equalsIgnoreCase(displayName)) {
				return crate;
			}
		}
		
		return null;
	}
	
	public static Crate getCrateByItem(ItemStack item) {
		for(Crate crate : crates) {
			try {
				if(item.getItemMeta().getDisplayName().equals(item.getItemMeta().getDisplayName())) {
					return crate;
				}
			} catch (Exception e) {
			}
		}
		
		return null;
	}
	
}
