package com.lenis0012.bukkit.statues;

import org.bukkit.command.CommandSender;

import com.bergerkiller.bukkit.common.PluginBase;
import com.lenis0012.bukkit.statues.core.StatueManager;
import com.lenis0012.bukkit.statues.data.DataManager;

public class Statues extends PluginBase {
	private static Statues instance;
	public static int NEXT_ENTITY_ID = Short.MAX_VALUE;
	
	private static void setInstance(Statues instance) {
		Statues.instance = instance;
	}
	
	public static Statues getInstance() {
		return instance;
	}
	
	private StatueManager statueManager;
	private DataManager dataManager;
	
	@Override
	public void enable() {
		setInstance(this);
		register(new StatueListener(this));
		register(new StatueCommand(this), "statue");
		
		this.statueManager = new StatueManager();
		this.dataManager = new DataManager(this);
	}
	
	@Override
	public void disable() {
		setInstance(null);
		dataManager.saveStatues();
	}
	
	public StatueManager getStatueManager() {
		return this.statueManager;
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