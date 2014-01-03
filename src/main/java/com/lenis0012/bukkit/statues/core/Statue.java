package com.lenis0012.bukkit.statues.core;

import org.bukkit.Location;

import com.lenis0012.bukkit.statues.Statues;

public abstract class Statue {
	protected int id;
	protected int entityId;
	protected Location loc;
	
	public Statue(int id, Location loc) {
		this.id = id;
		this.entityId = Statues.NEXT_ENTITY_ID++;
		this.loc = loc;
	}
	
	public int getId() {
		return this.id;
	}
	
	public int getEntityId() {
		return this.entityId;
	}
	
	public Location getLocation() {
		return this.loc;
	}
}