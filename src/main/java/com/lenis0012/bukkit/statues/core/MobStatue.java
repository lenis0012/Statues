package com.lenis0012.bukkit.statues.core;

import org.bukkit.Location;
import org.bukkit.entity.EntityType;

import com.bergerkiller.bukkit.common.wrappers.DataWatcher;

public class MobStatue extends Statue {
	private EntityType type;
	
	public MobStatue(int id, Location loc, EntityType type) {
		super(id, loc);
		this.type = type;
		this.packetGenerator = new MobPacketGenerator(this);
	}
	
	public EntityType getType() {
		return this.type;
	}

	@Override
	public DataWatcher getDefaultDataWatcher() {
		DataWatcher dataWatcher = new DataWatcher();
		dataWatcher.set(0, (byte) 0);
		if(type == EntityType.BAT) {
			dataWatcher.set(16, (byte) -2);
		} if(type.toString().contains("ZOMBIE")) {
			dataWatcher.set(12, (byte) 0);
		} else {
			dataWatcher.set(12, (int) 0);
		}
		
		return dataWatcher;
	}
}