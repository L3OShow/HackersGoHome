package me.l3oshow.hgh;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;

public class PListener implements Listener {
	
	public HGH plugin;
	
	public PListener(HGH plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e) {
		if(e.getPlayer().isOp() || e.getPlayer().hasPermission("*") || e.getPlayer().hasPermission(plugin.config.getString("HGH.permission"))) {
			if (!(plugin.map.containsKey(e.getPlayer().getName()) && plugin.map.containsValue(e.getPlayer().getAddress().getAddress().getHostAddress())) || plugin.config.getBoolean("HGH.StoreAndUseIP") == false) {
				plugin.notlogged.add(e.getPlayer().getName());
				plugin.notloggedag.add(e.getPlayer().getName());
				e.getPlayer().sendMessage(ChatColor.AQUA + "[HGH] Devi inserire la password!");
				plugin.map.remove(e.getPlayer().getName());
				plugin.map.remove(e.getPlayer().getAddress().getAddress().getHostAddress());
			}
			else {
				e.getPlayer().sendMessage(ChatColor.AQUA + "[HGH] " + e.getPlayer().getName() + " rientra in gioco con l'ip: " + e.getPlayer().getAddress().getAddress().getHostAddress() + ". Non devi reinserire la password!");
				e.getPlayer().sendMessage(plugin.map.toString());
			}
		}
	}
	
	@EventHandler
	public void onPlayerChat(AsyncPlayerChatEvent e) {
		if(plugin.notlogged.contains(e.getPlayer().getName())) {
			e.getPlayer().sendMessage(ChatColor.AQUA + "[HGH] Devi inserire la password!");
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onPlayerCommand(PlayerCommandPreprocessEvent e) {
		if(plugin.notlogged.contains(e.getPlayer().getName())) {
			if(!e.getMessage().equalsIgnoreCase("/logme " + plugin.config.getString("HGH.password"))) {
				e.getPlayer().sendMessage(ChatColor.AQUA + "[HGH] Password errata!");
				e.getPlayer().sendMessage(ChatColor.AQUA + "[HGH] Devi inserire la password!");
				e.setCancelled(true);
			}
		}
		if(plugin.notloggedag.contains(e.getPlayer().getName())) {
			if(e.getMessage().startsWith("//") || e.getMessage().startsWith("/sp") || e.getMessage().startsWith("/br") || e.getMessage().startsWith("/tool") || e.getMessage().startsWith("/pt") || e.getMessage().startsWith("/powertool") || e.getMessage().startsWith("/essentials:pt") || e.getMessage().startsWith("/essentials:powertool")|| e.getMessage().startsWith("/reload") || e.getMessage().startsWith("/plugman") || e.getMessage().startsWith("/bukkit:") || e.getMessage().startsWith("/worldedit:")) {
				e.getPlayer().sendMessage(ChatColor.AQUA + "[HGH] Devi inserire la password per eseguire il comando!");
				e.setCancelled(true);
			}
		}
		
	}
	
	@EventHandler
	public void onPlayerMove(PlayerMoveEvent e) {
		if(plugin.notlogged.contains(e.getPlayer().getName())) {
			e.getPlayer().sendMessage(ChatColor.AQUA + "[HGH] Devi inserire la password!");
			e.setTo(e.getFrom());
		}
	}
	
	@EventHandler
	public void onBlockPlace(BlockPlaceEvent e) {
		if(plugin.notlogged.contains(e.getPlayer().getName())) {
			e.getPlayer().sendMessage(ChatColor.AQUA + "[HGH] Devi inserire la password!");
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onBlockBreak(BlockBreakEvent e) {
		if(plugin.notlogged.contains(e.getPlayer().getName())) {
			e.getPlayer().sendMessage(ChatColor.AQUA + "[HGH] Devi inserire la password!");
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent e) {
		if(plugin.notlogged.contains(e.getPlayer().getName())) {
			e.getPlayer().sendMessage(ChatColor.AQUA + "[HGH] Devi inserire la password!");
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onPlayerDropItem(PlayerDropItemEvent e) {
		if(plugin.notlogged.contains(e.getPlayer().getName())) {
			e.getPlayer().sendMessage(ChatColor.AQUA + "[HGH] Devi inserire la password!");
			e.setCancelled(true);
		}
	}
	
}
