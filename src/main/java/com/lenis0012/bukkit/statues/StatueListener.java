package com.lenis0012.bukkit.statues;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import com.lenis0012.bukkit.statues.core.PlayerStatue;
import com.lenis0012.bukkit.statues.core.Statue;

public class StatueListener implements Listener {
	
	@EventHandler(priority = EventPriority.MONITOR)
	public void onPlayerJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		final Statue statue = new PlayerStatue(0, player.getLocation(), player.getName(), 0);
		Bukkit.getScheduler().runTaskLater(Statues.getInstance(), new Runnable() {

			@Override
			public void run() {
				statue.spawn();
			}
		}, 5);
	}
}