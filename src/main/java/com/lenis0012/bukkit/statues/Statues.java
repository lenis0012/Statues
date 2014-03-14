package com.lenis0012.bukkit.statues;

import org.bukkit.command.CommandSender;

import com.bergerkiller.bukkit.common.PluginBase;

public class Statues extends PluginBase {
	private static Statues instance;
	public static int NEXT_ENTITY_ID = Short.MAX_VALUE;
	
	private static void setInstance(Statues instance) {
		Statues.instance = instance;
	}
	
	public static Statues getInstance() {
		return instance;
	}
	
	@Override
	public void enable() {
		setInstance(this);
		register(new StatueListener());
		register(new StatueCommand(this), "statue");
	}
	
	@Override
	public void disable() {
		setInstance(null);
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