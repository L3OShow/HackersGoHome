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
import org.bukkit.plugin.java.JavaPlugin;

public class HGH extends JavaPlugin {
	
	public FileConfiguration config;
	public ArrayList<String> notlogged = new ArrayList<String>();
	public ArrayList<String> notloggedag = new ArrayList<String>();
	public Map<String, String> map = new HashMap<String, String>();
	
	public void onEnable() {
		Bukkit.getServer().getPluginManager().registerEvents(new PListener(this), this);
		Bukkit.getServer().getLogger().info("[HGH] HackersGoHome abilitato!");
		this.config = getConfig();
		config.addDefault("HGH.password", "default123");
		config.addDefault("HGH.passwordag", "defaultag");
		config.addDefault("HGH.permission", "null");
		config.addDefault("HGH.permissionag", "null");
		config.addDefault("HGH.StoreAndUseIP", true);
		config.options().copyDefaults(true);
		saveConfig();
	}
	
	public void onDisable() {
		Bukkit.getServer().getLogger().info("[HGH] HackersGoHome disabilitato!");
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(cmd.getName().equalsIgnoreCase("logme")) {
			if(sender.isOp() || sender.hasPermission("*") || sender.hasPermission(config.getString("HGH.permission"))) {
				if(args.length == 1)
					if(notlogged.contains(sender.getName())) {
						Player p = (Player) sender;
						if(args[0].equals(config.getString("HGH.password"))) {
							notlogged.remove(sender.getName());
							sender.sendMessage(ChatColor.AQUA + "[HGH] Accesso effettuato!");
							map.put(sender.getName(), p.getAddress().getAddress().getHostAddress());
						}
					}
			}
		}
		if(cmd.getName().equalsIgnoreCase("antigrief")) {
			if(sender.isOp() || sender.hasPermission("*") || sender.hasPermission(config.getString("HGH.permissionag"))) {
				if(args.length == 1)
					if(notloggedag.contains(sender.getName())) {
						Player p = (Player) sender;
						if(args[0].equals(config.getString("HGH.passwordag"))) {
							notloggedag.remove(sender.getName());
							sender.sendMessage(ChatColor.AQUA + "[HGH] Accesso effettuato!");
							map.put(sender.getName(), p.getAddress().getAddress().getHostAddress());
						}
					}
			}
		}
		return true;
	}
	
}
