package org.avaeriandev.prisontix.sys;

import java.util.concurrent.TimeUnit;

import org.avaeriandev.prisontix.Main;
import org.avaeriandev.prisontix.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.scheduler.BukkitRunnable;

public class Vacuum {
	
	public static void clearAllItems(World world) {
		
		int itemsCleared = 0;
		
		for(Entity entity : world.getEntities()) {
			if(entity instanceof Item) {
				Item item = (Item) entity;
				
				try {
					if(!(item.getItemStack().getItemMeta().getLore().get(0).equals("VACUUM BYPASS"))) {
						item.remove();
						itemsCleared++;
					}
				} catch (Exception e) {
					item.remove();
					itemsCleared++;
				}
			}
		}
		
		Bukkit.broadcastMessage(Utils.chat("&c&l&n" + itemsCleared + " ENTITIES REMOVED!"));
	}
	
	public static void startTimer() {
		new BukkitRunnable() {

			long timer = TimeUnit.MINUTES.toSeconds(5);
			
			@Override
			public void run() {
				
				timer--;
				
				if(timer == 60) {
					Bukkit.broadcastMessage(Utils.chat("&c&lGROUND ITEMS WILL BE REMOVED IN &4&l60 &c&lSECONDS!"));
				} else if(timer == 20) {
					Bukkit.broadcastMessage(Utils.chat("&c&lGROUND ITEMS WILL BE REMOVED IN &4&l20 &c&lSECONDS!"));
				} else if(timer <= 0) {
					Vacuum.clearAllItems(Bukkit.getWorld("Titan"));
					timer = TimeUnit.MINUTES.toSeconds(5);
				}
				
			}
		}.runTaskTimer(Main.PLUGIN, 0, 20 * 1);
	}
	
}
