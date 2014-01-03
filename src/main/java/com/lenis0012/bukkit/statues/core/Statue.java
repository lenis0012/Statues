package com.lenis0012.bukkit.statues.core;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import com.bergerkiller.bukkit.common.protocol.CommonPacket;
import com.bergerkiller.bukkit.common.utils.PacketUtil;
import com.bergerkiller.bukkit.common.wrappers.DataWatcher;
import com.lenis0012.bukkit.statues.Helper;
import com.lenis0012.bukkit.statues.Statues;

public abstract class Statue {
	protected int id;
	protected int entityId;
	protected Location loc;
	protected DataWatcher dataWatcher;
	protected PacketGenerator packetGenerator;
	protected boolean spawned = false;
	
	public Statue(int id, Location loc) {
		this.id = id;
		this.entityId = Statues.NEXT_ENTITY_ID++;
		this.loc = loc;
		this.dataWatcher = this.getDefaultDataWatcher();
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
	
	public DataWatcher getDataWatcher() {
		return this.dataWatcher;
	}
	
	public void spawn() {
		if(!spawned) {
			CommonPacket packet = packetGenerator.getSpawnPacket();
			Helper.sendPacketToWorld(packet, loc.getWorld());
			this.spawned = true;
		}
	}
	
	public void spawn(Player player) {
		if(spawned) {
			CommonPacket packet = packetGenerator.getSpawnPacket();
			PacketUtil.sendPacket(player, packet);
		}
	}
	
	public abstract DataWatcher getDefaultDataWatcher();
}