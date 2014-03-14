package com.lenis0012.bukkit.statues;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

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
		for(Statue statue : manager.getStatues()) {
			spawnStatue(player, statue);
		}
	}
	
	private void spawnStatue(final Player player, final Statue statue) {
		Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {

			@Override
			public void run() {
				statue.spawn(player);
			}
		}, 5L);
	}
}