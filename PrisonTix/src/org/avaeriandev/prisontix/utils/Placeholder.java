package org.avaeriandev.prisontix.utils;

import org.avaeriandev.prisontix.Main;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;

public class Placeholder extends PlaceholderExpansion{
	
	private Main plugin;

    @Override
    public boolean canRegister() {
        return true;
    }

    @Override
    public String getAuthor() {
        return "AvaerianDev";
    }
    @Override
    public String getIdentifier() {
        return "prisontix";
    }

    @Override
    public String getPlugin() {
        return null;
    }

    @Override
    public String getVersion() {
        return "1.0.0";
    }

    @Override
    public String onPlaceholderRequest(Player plr, String identifier) {

        // %prisontix_tickets%
        if (identifier.equals("tickets")) {
        	ConfigManager cm = new ConfigManager(plugin, plr);
			FileConfiguration f = cm.getConfig();
			int tempTix = f.getInt("tickets");	
			
			return Integer.toString(tempTix);
        }
        return null;
    }

}
