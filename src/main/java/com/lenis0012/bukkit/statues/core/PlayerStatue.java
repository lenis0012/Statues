package com.lenis0012.bukkit.statues.core;

import org.bukkit.Location;

import com.bergerkiller.bukkit.common.wrappers.DataWatcher;
import com.lenis0012.bukkit.statues.data.StatueData;

public class PlayerStatue extends Statue {
	private String name;
	private int itemInHand;
	
	public PlayerStatue(int id, Location loc, StatueData data) {
		super(id, loc);
		this.name = data.read("name", String.class);
		this.itemInHand = data.read("itemInHand", int.class);
		this.packetGenerator = new PlayerPacketGenerator(this);
		this.initDataWatcher();
	}
	
	public PlayerStatue(int id, Location loc, String name, int itemInHand) {
		super(id, loc);
		this.name = name;
		this.itemInHand = itemInHand;
		this.packetGenerator = new PlayerPacketGenerator(this);
		this.initDataWatcher();
	}
	
	public String getName() {
		return this.name;
	}
	
	public int getItemInHand() {
		return this.itemInHand;
	}

	@Override
	public DataWatcher getDefaultDataWatcher() {
		DataWatcher dw = new DataWatcher();
		dw.set(0, (byte) 0);
		dw.set(12, (int) 0);
		
		return dw;
	}

	@Override
	public void saveKeys(StatueData data) {
		data.write("name", name);
		data.write("itemInHand", itemInHand);
		data.write("isPlayer", true);
	}
}