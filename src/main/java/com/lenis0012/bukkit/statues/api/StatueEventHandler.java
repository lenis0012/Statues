package com.lenis0012.bukkit.statues.api;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.lenis0012.bukkit.statues.core.Statue;

public class StatueEventHandler {
	
	public static boolean callCreateEvent(Player player, Statue statue) {
		StatueCreateEvent event = new StatueCreateEvent(statue, player);
		Bukkit.getPluginManager().callEvent(event);
		return !event.isCancelled();
	}
}