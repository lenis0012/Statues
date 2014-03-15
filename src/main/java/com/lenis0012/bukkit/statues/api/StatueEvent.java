package com.lenis0012.bukkit.statues.api;

import org.bukkit.event.Event;

import com.lenis0012.bukkit.statues.core.PlayerStatue;
import com.lenis0012.bukkit.statues.core.Statue;
import com.lenis0012.bukkit.statues.core.MobStatue;

public abstract class StatueEvent extends Event {
	private Statue statue;
	
	public StatueEvent(Statue statue) {
		this.statue = statue;
	}
	
	public Statue getStatue() {
		return this.statue;
	}
	
	public boolean isMobStatue() {
		return (statue instanceof MobStatue);
	}
	
	public boolean isPlayerStatue() {
		return (statue instanceof PlayerStatue);
	}
}