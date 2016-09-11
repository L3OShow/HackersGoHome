package me.l3oshow.hgh;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class HGH extends org.bukkit.plugin.java.JavaPlugin {
	public FileConfiguration config;
	public ArrayList<String> notlogged = new ArrayList<String>();
	public ArrayList<String> notloggedag = new ArrayList<String>();
	public Map<String, String> map = new HashMap<String, String>();

	SettingsManager settings = SettingsManager.getInstance();
	
	String prefix;
	String loggedin;
	String loggedinag;

	@Override
	public void onEnable() {
		settings.setup(this);
		prefix = ChatColor.translateAlternateColorCodes('&', settings.getMessages().getString("prefix"));
		loggedin = ChatColor.translateAlternateColorCodes('&', settings.getMessages().getString("loggedin"));
		loggedinag = ChatColor.translateAlternateColorCodes('&', settings.getMessages().getString("loggedinag"));
		Bukkit.getServer().getPluginManager().registerEvents(new PListener(this), this);
		Bukkit.getServer().getLogger().info("[HGH] HackersGoHome enabled!");
		config = getConfig();
		config.addDefault("HGH.password", "default123");
		config.addDefault("HGH.passwordag", "defaultag");
		config.addDefault("HGH.permission", "null");
		config.addDefault("HGH.permissionag", "null");
		config.addDefault("HGH.StoreAndUseIP", Boolean.valueOf(true));
		config.options().copyDefaults(true);
		saveConfig();
	}

	@Override
	public void onDisable() {
		Bukkit.getServer().getLogger().info("[HGH] HackersGoHome disabled!");
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if ((cmd.getName().equalsIgnoreCase("logme"))
			 && sender.isOp()
			 || sender.hasPermission("*")
			 || sender.hasPermission(config.getString("HGH.permission"))
			 && args.length == 1
			 && notlogged.contains(sender.getName())) {
			
			Player p = (Player) sender;
			
			if (args[0].equals(config.getString("HGH.password"))) {
				notlogged.remove(sender.getName());
				sender.sendMessage(prefix + " " + loggedin);
				map.put(sender.getName(), p.getAddress().getAddress().getHostAddress());
			}
		}
		if ((cmd.getName().equalsIgnoreCase("antigrief"))
			 && sender.isOp()
			 || sender.hasPermission("*")
			 || sender.hasPermission(config.getString("HGH.permissionag"))
			 && args.length == 1
			 && notloggedag.contains(sender.getName())) {
			
			Player p = (Player) sender;
			
			if (args[0].equals(config.getString("HGH.passwordag"))) {
				notloggedag.remove(sender.getName());
				sender.sendMessage(prefix + " " + loggedinag);
				map.put(sender.getName(), p.getAddress().getAddress().getHostAddress());
			}
		}
		return true;
	}
}