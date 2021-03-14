package org.avaeriandev.prisontix.commands;

import java.util.Random;

import org.avaeriandev.prisontix.Main;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.FireworkMeta;

public class InstantFireworks implements CommandExecutor{

	@SuppressWarnings("unused")
	private Main plugin;
	
	public InstantFireworks(Main plugin) {
		this.plugin = plugin;
		
		plugin.getCommand("fw").setExecutor(this);
	}
	
	public static void playFireworkEffect(Player plr) {
		FireworkEffect.Builder fwB = FireworkEffect.builder();
		Random r = new Random();
		
		Firework fw = (Firework) plr.getWorld().spawn(plr.getLocation(), Firework.class);
		
		fwB.flicker(r.nextBoolean());
		fwB.trail(r.nextBoolean());
		fwB.with(FireworkEffect.Type.BALL);
		fwB.withColor(Color.fromRGB(r.nextInt(256), r.nextInt(256), r.nextInt(256)));
		fwB.withFade(Color.fromRGB(r.nextInt(256), r.nextInt(256), r.nextInt(256)));
		FireworkEffect fe = fwB.build();
		
		FireworkMeta fm = fw.getFireworkMeta();
		fm.clearEffects();
		fm.addEffect(fe);
		
		fw.setFireworkMeta(fm);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if(sender instanceof Player) {
			return false;
		}
		
		Player plr = Bukkit.getPlayer(args[0]);
		
		InstantFireworks.playFireworkEffect(plr);
		
		return true;
	}

}
