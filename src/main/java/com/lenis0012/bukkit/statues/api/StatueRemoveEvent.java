package com.lenis0012.bukkit.statues.api;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;

import com.lenis0012.bukkit.statues.core.Statue;

public class StatueRemoveEvent extends StatueEvent implements Cancellable {
	private static final HandlerList handlers = new HandlerList();
	private boolean canceled = false;
	private Player player;
	
	public StatueRemoveEvent(Statue statue, Player player) {
		super(statue);
		this.player = player;
	}
	
	public Player getPlayer() {
		return this.player;
	}

	@Override
	public boolean isCancelled() {
		return canceled;
	}

	@Override
	public void setCancelled(boolean canceled) {
		this.canceled = canceled;
	}
	
	@Override
	public HandlerList getHandlers() {
		return handlers;
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}
}