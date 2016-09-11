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
	String alreadylogged;

	String ver = this.getDescription().getVersion();
	
	@Override
	public void onEnable() {
		settings.setup(this);
		prefix = ChatColor.translateAlternateColorCodes('&', settings.getMessages().getString("prefix"));
		loggedin = ChatColor.translateAlternateColorCodes('&', settings.getMessages().getString("loggedin"));
		loggedinag = ChatColor.translateAlternateColorCodes('&', settings.getMessages().getString("loggedinag"));
		alreadylogged = ChatColor.translateAlternateColorCodes('&', settings.getMessages().getString("already-logged"));
		Bukkit.getServer().getPluginManager().registerEvents(new PListener(this), this);
		Bukkit.getServer().getLogger().info("[HGH] HackersGoHome v" + ver + " enabled!");
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
		Bukkit.getServer().getLogger().info("[HGH] HackersGoHome v" + ver + " disabled!");
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if ((cmd.getName().equalsIgnoreCase("logme"))) {
			
			if(!(sender instanceof Player)) {
				sender.sendMessage(prefix + "Non puoi usare questo comando da console.");
			}
			else {
				Player p = (Player) sender;
				if(p.isOp()
				   || p.hasPermission("*")
				   || p.hasPermission(config.getString("HGH.permission"))) {
					
					if(args.length == 1) {
						if(notlogged.contains(p.getName()) && args[0].equals(config.getString("HGH.password"))) {
							p.sendMessage(prefix + " " + loggedin);
							map.put(p.getName(), p.getAddress().getAddress().getHostAddress());
							notlogged.remove(p.getName());
							notloggedag.add(p.getName());
							return true;
						}
					}
					
					if(!notlogged.contains(p.getName())) {
						p.sendMessage(prefix + " " + alreadylogged);
						return true;
					}
				}
			}
		}
		if ((cmd.getName().equalsIgnoreCase("antigrief"))) {
			
			if(!(sender instanceof Player)) {
				sender.sendMessage(prefix + "Non puoi usare questo comando da console.");
			}
			else {
				Player p = (Player) sender;
				if(p.isOp()
				   || p.hasPermission("*")
				   || p.hasPermission(config.getString("HGH.permissionag"))) {
					if(args.length == 1) {
						if(notloggedag.contains(p.getName()) && args[0].equals(config.getString("HGH.passwordag"))) {
							p.sendMessage(prefix + " " + loggedinag);
							map.put(p.getName(), p.getAddress().getAddress().getHostAddress());
							notloggedag.remove(p.getName());
							return true;
						}
					}
					
					if(!notloggedag.contains(p.getName())) {
						p.sendMessage(prefix + " " + alreadylogged);
						return true;
					}
				}
			}
		}
		return true;
	}
}