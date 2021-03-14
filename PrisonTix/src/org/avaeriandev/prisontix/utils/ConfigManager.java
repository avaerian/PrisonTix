package org.avaeriandev.prisontix.utils;

import java.io.File;
import java.io.IOException;

import org.avaeriandev.prisontix.Main;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

public class ConfigManager {
	
	private Main plugin;
	private Player plr;
	private FileConfiguration fc;
	private File file;
	
	public ConfigManager(Main plugin, Player plr) {
		this.plugin = plugin;
		this.plr = plr;
	}
	
	public Player getOwner() {
		
		if(plr == null) {
			try {
				throw new Exception();
			} catch (Exception e) {
				plugin.getLogger().warning("Err Player is Null!");
				e.printStackTrace();
			}
		}
		return plr;
	}
	
	public boolean exists() {
		if(fc == null || file == null) {
			File temp = new File(getDataFolder(), getOwner().getUniqueId().toString() + ".yml");
			if (!temp.exists()) {
				return false;
			} else {
				file = temp;
			}
		}
		return true;
	}
	
	public File getDataFolder() {
		File dir = new File(Main.class.getProtectionDomain().getCodeSource().getLocation().getPath().replaceAll("%20", " "));
		File d = new File(dir.getParentFile().getPath(), plugin.getName());
		if (!d.exists()) {
			d.mkdirs();
		}
		return d;
	}
	
	public File getFile() {
		if(file == null) {
			file = new File(getDataFolder(), getOwner().getUniqueId().toString() + ".yml");
			if (!(file.exists())) {
				try {
					file.createNewFile();
				} catch  (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return file;
	}
	
	public FileConfiguration getConfig() {
		if(fc == null) {
			fc = YamlConfiguration.loadConfiguration(getFile());
		}
		return fc;
	}
	
	public void saveConfig() {
		try {
			getConfig().save(getFile());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
