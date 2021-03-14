package org.avaeriandev.prisontix.events;

import org.avaeriandev.prisontix.Main;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.mewin.WGRegionEvents.events.RegionEnterEvent;
import com.mewin.WGRegionEvents.events.RegionLeaveEvent;

public class RegionEnter implements Listener {

	@SuppressWarnings("unused")
	private Main plugin;
	
	public RegionEnter(Main plugin) {
		this.plugin = plugin;
	}
	
	String[] whitelist = {
			"emine_fix",
			"dmine_fix",
			"cmine_fix",
			"bmine_fix",
			"amine_fix",
			"hustler_fix",
			"wiseguy_fix",
			"enforcer_fix",
			"hitman_fix",
			"underboss_fix",
			"mastermind_fix",
			"escapist_fix"
	};
	
	
	// When player enters mine region, give them donor pick ability
	@EventHandler
	public void onRegionEnter(RegionEnterEvent e) {
		String plr = e.getPlayer().getName();
		for(int i = 0; i < whitelist.length; i++) {
			if(e.getRegion().getId().equalsIgnoreCase(whitelist[i])) {
				Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + plr + " remove -wpick.explode");
				Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + plr + " add wpick.explode");
				break;
			}
		}
	}
	
	
	// When player leaves mine region, remove donor pick ability
	@EventHandler
	public void onRegionLeave(RegionLeaveEvent e) {
		String plr = e.getPlayer().getName();
		for(int i = 0; i < whitelist.length; i++) {
			if(e.getRegion().getId().equalsIgnoreCase(whitelist[i])) {
				Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + plr + " remove wpick.explode");
				Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + plr + " add -wpick.explode");
				break;
			}
		}
	}
}
