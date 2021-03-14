package org.avaeriandev.prisontix.vexarprison;

import org.avaeriandev.prisontix.Main;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.inventivetalent.particle.ParticleEffect;

public class FlyParticles implements Listener {

	@EventHandler
	public void onFlyToggle(PlayerToggleFlightEvent e) {
		Player plr = e.getPlayer();
		boolean isFlying = e.isFlying();
		
		if(isFlying) {
			plr.getWorld().playSound(plr.getLocation(), Sound.FIREWORK_LAUNCH, 1, 0.5F);
		} else {
			plr.getWorld().playSound(plr.getLocation(), Sound.FIREWORK_BLAST, 1, 0.5F);
		}
	}
	
	public static void spawnParticlesIfApplicable(Player plr) {
		if(plr.isFlying()) {
			ParticleEffect.CLOUD.send(Bukkit.getOnlinePlayers(), plr.getLocation(), 0, 0, 0, 0, 1);
		}
	}
	
	public static void checkPlayers() {
		new BukkitRunnable() {
			@Override
			public void run() {
				for(Player plr : Bukkit.getOnlinePlayers()) {
					spawnParticlesIfApplicable(plr);
				}
			}
		}.runTaskTimer(Main.PLUGIN, 0, 1);
	}
	
}
