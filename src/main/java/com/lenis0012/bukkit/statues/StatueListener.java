package com.lenis0012.bukkit.statues;

import java.util.Collection;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

import com.lenis0012.bukkit.statues.core.Statue;
import com.lenis0012.bukkit.statues.core.StatueManager;

public class StatueListener implements Listener {
	private Statues plugin;
	
	public StatueListener(Statues plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler(priority = EventPriority.MONITOR)
	public void onPlayerJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		StatueManager manager = plugin.getStatueManager();
		spawnStatues(player, manager.getStatues());
	}
	
	@EventHandler(priority = EventPriority.MONITOR)
	public void onPlayerRespawn(PlayerRespawnEvent event) {
		Player player = event.getPlayer();
		StatueManager manager = plugin.getStatueManager();
		spawnStatues(player, manager.getStatues());
	}
	
	@EventHandler(priority = EventPriority.MONITOR)
	public void onPlayerChangedWorld(PlayerChangedWorldEvent event) {
		Player player = event.getPlayer();
		StatueManager manager = plugin.getStatueManager();
		spawnStatues(player, manager.getStatues());
	}
	
	private void spawnStatues(final Player player, final Collection<Statue> statues) {
		Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {

			@Override
			public void run() {
				for(Statue statue : statues) {
					if(statue.getWorld().equals(player.getWorld())) {
						statue.spawn(player);
					}
				}
			}
		}, 20L);
	}
	
	@SuppressWarnings("unused")
	private void spawnStatue(final Player player, final Statue statue) {
		Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {

			@Override
			public void run() {
				statue.spawn(player);
			}
		}, 20L);
	}
}