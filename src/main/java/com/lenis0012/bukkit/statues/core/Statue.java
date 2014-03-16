package com.lenis0012.bukkit.statues.core;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import com.bergerkiller.bukkit.common.protocol.CommonPacket;
import com.bergerkiller.bukkit.common.utils.PacketUtil;
import com.bergerkiller.bukkit.common.wrappers.DataWatcher;
import com.lenis0012.bukkit.statues.Helper;
import com.lenis0012.bukkit.statues.Statues;
import com.lenis0012.bukkit.statues.data.StatueData;

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
	}
	
	protected void initDataWatcher() {
		this.dataWatcher = this.getDefaultDataWatcher();
	}
	
	public int getId() {
		return this.id;
	}
	
	public int getEntityId() {
		return this.entityId;
	}
	
	public World getWorld() {
		return loc.getWorld();
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
			rotateHead();
			this.spawned = true;
		}
	}
	
	public void spawn(Player player) {
		if(spawned) {
			CommonPacket packet = packetGenerator.getSpawnPacket();
			PacketUtil.sendPacket(player, packet);
			rotateHead(player);
		}
	}
	
	public void despawn() {
		if(spawned) {
			CommonPacket packet = packetGenerator.getDestroyPacket();
			Helper.sendPacketToWorld(packet, loc.getWorld());
			this.spawned = false;
		}
	}
	
	public void despawn(Player player) {
		if(spawned) {
			CommonPacket packet = packetGenerator.getDestroyPacket();
			PacketUtil.sendPacket(player, packet);
		}
	}
	
	private void rotateHead() {
		Bukkit.getScheduler().runTaskLater(Statues.getInstance(), new Runnable() {

			@Override
			public void run() {
				CommonPacket headPacket = packetGenerator.getHeadRotationPacket();
				CommonPacket bodyPacket = packetGenerator.getLookPacket();
				Helper.sendPacketToWorld(headPacket, loc.getWorld());
				Helper.sendPacketToWorld(bodyPacket, loc.getWorld());
			}
		}, 5L);
	}
	
	private void rotateHead(final Player player) {
		Bukkit.getScheduler().runTaskLater(Statues.getInstance(), new Runnable() {

			@Override
			public void run() {
				CommonPacket headPacket = packetGenerator.getHeadRotationPacket();
				CommonPacket bodyPacket = packetGenerator.getLookPacket();
				PacketUtil.sendPacket(player, headPacket);
				PacketUtil.sendPacket(player, bodyPacket);
			}
		}, 5L);
	}
	
	public static StatueData saveStatue(Statue statue) {
		StatueData data = new StatueData();
		data.write("id", statue.getId());
		data.writeLocation("location", statue.getLocation());
		statue.saveKeys(data);
		
		return data;
	}
	
	public static Statue loadStatue(StatueData data) {
		int id = data.read("id", int.class);
		Location loc = data.readLocation("location");
		boolean isPlayer = data.read("isPlayer", boolean.class);
		if(isPlayer) {
			return new PlayerStatue(id, loc, data);
		} else {
			return new MobStatue(id, loc, data);
		}
	}
	
	public abstract void saveKeys(StatueData data);
	
	public abstract DataWatcher getDefaultDataWatcher();
}