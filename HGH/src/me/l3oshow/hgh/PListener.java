package me.l3oshow.hgh;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;

public class PListener implements org.bukkit.event.Listener {
	public HGH plugin;

	public PListener(HGH plugin) {
		this.plugin = plugin;
	}

	SettingsManager settings = SettingsManager.getInstance();
	
	private String prefix = ChatColor.translateAlternateColorCodes('&', settings.getMessages().getString("prefix"));
	private String insertpsw = ChatColor.translateAlternateColorCodes('&', settings.getMessages().getString("insertpsw"));
	private String insertpswag = ChatColor.translateAlternateColorCodes('&', settings.getMessages().getString("insertpswag"));
	private String wrongpsw = ChatColor.translateAlternateColorCodes('&', settings.getMessages().getString("wrongpsw"));
	private String iprejoin = ChatColor.translateAlternateColorCodes('&', settings.getMessages().getString("ip-rejoin"));

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		if (p.isOp() || p.hasPermission("*") || p.hasPermission(plugin.config.getString("HGH.permission"))) {
			if ((!plugin.map.containsKey(p.getName()))
				|| (!plugin.map.containsValue(p.getAddress().getAddress().getHostAddress()))
				|| (!plugin.config.getBoolean("HGH.StoreAndUseIP"))) {
				
				plugin.notlogged.add(p.getName());
				p.sendMessage(prefix + " " + insertpsw);
				plugin.map.remove(p.getName());
				plugin.map.remove(p.getAddress().getAddress().getHostAddress());
			}
			else {
				p.sendMessage(prefix + " " + iprejoin.replace("$ip", p.getAddress().getAddress().getHostAddress()).replace("$name", p.getName()));
				p.sendMessage(plugin.map.toString());
			}
		}
	}

	@EventHandler
	public void onPlayerChat(AsyncPlayerChatEvent e) {
		Player p = e.getPlayer();
		if (plugin.notlogged.contains(p.getName())) {
			p.sendMessage(prefix + " " + insertpsw);			
			e.setCancelled(true);
		}
	}

	@EventHandler
	public void onPlayerCommand(PlayerCommandPreprocessEvent e) {
		Player p = e.getPlayer();
		String message = e.getMessage();
		if (plugin.notlogged.contains(p.getName())
			&& !message.equalsIgnoreCase("/logme " + plugin.config.getString("HGH.password"))) {
			p.sendMessage(prefix + " " + wrongpsw);
			p.sendMessage(prefix + " " + insertpsw);
			e.setCancelled(true);
		}
		else {
			plugin.notloggedag.add(p.getName());
			plugin.notlogged.remove(p.getName());
		}

		if (plugin.notloggedag.contains(p.getName()) && (message.startsWith("//"))
				|| message.startsWith("/sp")
				|| message.startsWith("/br")
				|| message.startsWith("/tool")
				|| message.startsWith("/pt")
				|| message.startsWith("/powertool")
				|| message.startsWith("/essentials:pt")
				|| message.startsWith("/essentials:powertool")
				|| message.startsWith("/reload")
				|| message.startsWith("/plugman")
				|| message.startsWith("/bukkit:")
				|| message.startsWith("/worldedit:")) {
			
				if(!message.equalsIgnoreCase("/antigrief " + plugin.config.getString("HGH.passwordag"))) {
					p.sendMessage(prefix + " " + insertpswag);
					e.setCancelled(true);
				}
				else {
					plugin.notloggedag.remove(p.getName());
				}
		}
	}

	@EventHandler
	public void onPlayerMove(PlayerMoveEvent e) {
		Player p = e.getPlayer();
		if (plugin.notlogged.contains(p.getName())) {
			p.sendMessage(prefix + " " + insertpsw);
			e.setTo(e.getFrom());
		}
	}

	@EventHandler
	public void onBlockPlace(BlockPlaceEvent e) {
		Player p = e.getPlayer();
		if (plugin.notlogged.contains(p.getName())) {
			p.sendMessage(prefix + " " + insertpsw);
			e.setCancelled(true);
		}
	}

	@EventHandler
	public void onBlockBreak(BlockBreakEvent e) {
		Player p = e.getPlayer();
		if (plugin.notlogged.contains(p.getName())) {
			p.sendMessage(prefix + " " + insertpsw);
			e.setCancelled(true);
		}
	}

	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		if (plugin.notlogged.contains(p.getName())) {
			p.sendMessage(prefix + " " + insertpsw);
			e.setCancelled(true);
		}
	}

	@EventHandler
	public void onPlayerDropItem(PlayerDropItemEvent e) {
		Player p = e.getPlayer();
		if (plugin.notlogged.contains(p.getName())) {
			p.sendMessage(prefix + " " + insertpsw);
			e.setCancelled(true);
		}
	}
}