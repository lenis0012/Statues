package com.lenis0012.bukkit.statues.core;

import org.bukkit.Location;

import com.bergerkiller.bukkit.common.protocol.CommonPacket;
import com.bergerkiller.bukkit.common.protocol.PacketType;
import com.lenis0012.bukkit.statues.Helper;

public class MobPacketGenerator extends PacketGenerator {
	private MobStatue statue;
	
	public MobPacketGenerator(MobStatue statue) {
		super(statue);
		this.statue = statue;
	}

	@Override
	public CommonPacket getSpawnPacket() {
		Location loc = statue.getLocation();
		int entityType = Helper.getEntityTypeId(statue.getType());
		int x = Helper.getFixedPoint(loc.getX());
		int y = Helper.getFixedPoint(loc.getY());
		int z = Helper.getFixedPoint(loc.getZ());
		byte yaw = Helper.getByteFromDegree(loc.getYaw());
		byte pitch = Helper.getByteFromDegree(loc.getPitch());
		
		CommonPacket packet = new CommonPacket(PacketType.OUT_ENTITY_SPAWN_LIVING);
		packet.write(PacketType.OUT_ENTITY_SPAWN_LIVING.entityId, statue.getEntityId());
		packet.write(PacketType.OUT_ENTITY_SPAWN_LIVING.entityType, entityType);
		packet.write(PacketType.OUT_ENTITY_SPAWN_LIVING.x, x);
		packet.write(PacketType.OUT_ENTITY_SPAWN_LIVING.y, y);
		packet.write(PacketType.OUT_ENTITY_SPAWN_LIVING.z, z);
		packet.write(PacketType.OUT_ENTITY_SPAWN_LIVING.yaw, yaw);
		packet.write(PacketType.OUT_ENTITY_SPAWN_LIVING.pitch, pitch);
		packet.write(PacketType.OUT_ENTITY_SPAWN_LIVING.headYaw, yaw);
		packet.setDatawatcher(statue.getDataWatcher());
		
		return packet;
	}
}