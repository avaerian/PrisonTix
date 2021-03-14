package org.avaeriandev.prisontix.events;

import java.util.ArrayList;
import java.util.List;

import org.avaeriandev.prisontix.Main;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

public class PVP implements Listener {
	
	@SuppressWarnings("unused")
	private Main plugin;
	
	public PVP(Main plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onDamage(EntityDamageByEntityEvent e) {
		if(e.getEntity() instanceof Player) {
			if(e.getDamager() instanceof Player) {
				Player attacker = (Player) e.getDamager();
				Player victim = (Player) e.getEntity();
				List<Material> bloxUnderVictim = new ArrayList<Material>(); 
				List<Material> bloxUnderAttacker = new ArrayList<Material>(); 
				for(int i = 0; i < 5; i++) {
					Location underPlrVictim = victim.getLocation().subtract(0, (i), 0);
					Location underPlrAttacker = attacker.getLocation().subtract(0, (i), 0);
					bloxUnderVictim.add(underPlrVictim.getBlock().getType());
					bloxUnderAttacker.add(underPlrAttacker.getBlock().getType());
				}
				for(int i = 0; i < 5; i++) {
					if(!(bloxUnderVictim.contains(Material.BEDROCK) && bloxUnderAttacker.contains(Material.BEDROCK))) {
						e.setCancelled(true);
					}
				}
			}
		}
	}
	
	@EventHandler
	public void onKill(EntityDeathEvent e) {
		if(e.getEntity() instanceof Player) {
			if(e.getEntity().getKiller() instanceof Player) {
				Player victim = (Player) e.getEntity();
				Player killer = victim.getKiller();
				int random = (int)(Math.random() * 10);
				if(random >= 7) {
					ItemStack skull = new ItemStack(Material.SKULL_ITEM, 1, (byte) 3);
					killer.getInventory().addItem(skull);
				}
				return;
			}
		}
	}
	
}
