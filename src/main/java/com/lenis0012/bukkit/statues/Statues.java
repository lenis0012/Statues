package com.lenis0012.bukkit.statues;

import org.bukkit.command.CommandSender;

import com.bergerkiller.bukkit.common.PluginBase;

public class Statues extends PluginBase {
	public static int NEXT_ENTITY_ID = Short.MAX_VALUE;
	
	@Override
	public void enable() {
		//TODO: Start coding ;)
	}
	
	@Override
	public void disable() {
		
	}

	@Override
	public boolean command(CommandSender sender, String label, String[] args) {
		return false;
	}

	@Override
	public int getMinimumLibVersion() {
		return 157;
	}

}