package com.lenis0012.bukkit.statues.api;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.bergerkiller.bukkit.common.utils.CommonUtil;
import com.lenis0012.bukkit.statues.core.Statue;

public class StatueEventHandler {
	
	public static boolean callCreateEvent(Player player, Statue statue) {
		StatueCreateEvent event = new StatueCreateEvent(statue, player);
		if(CommonUtil.hasHandlers(event.getHandlers())) {
			Bukkit.getPluginManager().callEvent(event);
			return !event.isCancelled();
		} else {
			return true;
		}
	}
	
	public static boolean callRemoveEvent(Player player, Statue statue) {
		StatueRemoveEvent event = new StatueRemoveEvent(statue, player);
		if(CommonUtil.hasHandlers(event.getHandlers())) {
			Bukkit.getPluginManager().callEvent(event);
			return !event.isCancelled();
		} else {
			return true;
		}
	}
}