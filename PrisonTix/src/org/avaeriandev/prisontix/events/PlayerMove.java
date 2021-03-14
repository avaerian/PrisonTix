package org.avaeriandev.prisontix.events;

import org.avaeriandev.prisontix.Main;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.scheduler.BukkitRunnable;

import com.sk89q.worldedit.Vector;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;

public class PlayerMove implements Listener{
	
	private Main plugin;
	
	public PlayerMove(Main plugin) {
		this.plugin = plugin;
	}
	
		@EventHandler
		public void onEnterRegion(PlayerMoveEvent e, Location from, Location to) {
			Player plr = e.getPlayer();
			
			String[] blacklist = {
					"ewood",
					"dwood",
					"cwood1",
					"cwood2",
					"bwood",
					"awood"
			};
			
			new BukkitRunnable() {
				
				@SuppressWarnings("unused")
				public void run() {
			
					for(int i = 0; i < blacklist.length; i++) {
						if(WorldGuardPlugin.inst().getRegionManager(plr.getWorld()).getRegion(blacklist[i]).contains((Vector) e.getFrom().getBlock())
								&& WorldGuardPlugin.inst().getRegionManager(plr.getWorld()).getRegion(blacklist[i]).contains((Vector) e.getTo().getBlock())) {
							Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + plr + " remove wpick.explode");
							Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + plr + " add -wpick.explode");
							break;
						} else {
							Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + plr + " remove -wpick.explode");
							Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + plr + " add wpick.explode");
							break;
						}
					}
				}
			}.runTaskTimer(plugin, 0, 20L * 2);
		}
}
