package com.lenis0012.bukkit.statues.core;

import org.bukkit.Location;
import org.bukkit.entity.EntityType;

import com.bergerkiller.bukkit.common.wrappers.DataWatcher;
import com.lenis0012.bukkit.statues.data.StatueData;

public class MobStatue extends Statue {
	private EntityType type;
	
	public MobStatue(int id, Location loc, StatueData data) {
		super(id, loc);
		this.type = EntityType.valueOf(data.read("type", String.class));
		this.packetGenerator = new MobPacketGenerator(this);
		this.initDataWatcher();
	}
	
	public MobStatue(int id, Location loc, EntityType type) {
		super(id, loc);
		this.type = type;
		this.packetGenerator = new MobPacketGenerator(this);
		this.initDataWatcher();
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

	@Override
	public void saveKeys(StatueData data) {
		data.write("type", type.toString());
		data.write("isPlayer", false);
	}
}