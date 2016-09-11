package me.l3oshow.hgh;

import java.io.File;
import java.io.IOException;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;

public class SettingsManager {
	public static SettingsManager instance = new SettingsManager();
	Plugin p;

	public static SettingsManager getInstance() {
		return instance;
	}

	FileConfiguration config;

	File cfile;

	FileConfiguration messages;

	File mfile;

	public void setup(Plugin p) {
		cfile = new File(p.getDataFolder(), "config.yml");
		mfile = new File(p.getDataFolder(), "messages.yml");
		config = p.getConfig();

		if (!p.getDataFolder().exists()) {
			p.getDataFolder().mkdir();
		}

		if (!mfile.exists()) {
			try {
				mfile.createNewFile();
				p.saveResource("messages.yml", true);
			}
			catch (IOException e) {
				Bukkit.getServer().getLogger().severe(ChatColor.RED + "Could not create messages.yml!");
			}
		}

		messages = YamlConfiguration.loadConfiguration(mfile);
	}

	public FileConfiguration getMessages() {
		return messages;
	}

	public void saveMessages() {
		try {
			messages.save(mfile);
		}
		catch (IOException e) {
			Bukkit.getServer().getLogger().severe(ChatColor.RED + "Could not save messages.yml!");
		}
	}

	public void reloadMessages() {
		messages = YamlConfiguration.loadConfiguration(mfile);
	}

	public FileConfiguration getConfig() {
		return config;
	}

	public void saveConfig() {
		try {
			config.save(cfile);
		}
		catch (IOException e) {
			Bukkit.getServer().getLogger().severe(ChatColor.RED + "Could not save config.yml!");
		}
	}

	public void reloadConfig() {
		config = YamlConfiguration.loadConfiguration(cfile);
	}

	public PluginDescriptionFile getDescription() {
		return p.getDescription();
	}
}