package org.avaeriandev.prisontix.sys;

import java.util.HashMap;
import java.util.UUID;

import org.avaeriandev.prisontix.Main;
import org.avaeriandev.prisontix.utils.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class ToggleSpeed implements CommandExecutor {

	private HashMap<UUID,Boolean> toggle = new HashMap<UUID,Boolean>();
	
	@SuppressWarnings("unused")
	private Main plugin;
	
	public ToggleSpeed(Main plugin) {
		this.plugin = plugin;
		
		plugin.getCommand("speed").setExecutor(this);
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if(!(sender instanceof Player)) {
			// This is console
			return false;
		}
		
		Player plr = (Player) sender;
		
		if(plr.hasPermission("donor.speed")) {
			
			if(!(toggle.containsKey(plr.getUniqueId()))) {
				toggle.put(plr.getUniqueId(), false);
			}
			
			if(toggle.get(plr.getUniqueId()) == false) {
				toggle.put(plr.getUniqueId(), true);
				plr.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 99999999, 1));
				plr.sendMessage(Utils.chat("&aSpeed enabled!"));
			} else {
				toggle.put(plr.getUniqueId(), false);
				plr.removePotionEffect(PotionEffectType.SPEED);
				plr.sendMessage(Utils.chat("&cSpeed disabled!"));
			}
			
			return true;
		} else {
			plr.sendMessage(Utils.chat("&cPurchase a donor rank to have access to exclusive perks!"));
			return false;
		}
	}
	
}
