package com.lenis0012.bukkit.statues.core;

import org.bukkit.Location;

import com.bergerkiller.bukkit.common.protocol.CommonPacket;
import com.bergerkiller.bukkit.common.protocol.PacketType;
import com.lenis0012.bukkit.statues.Helper;

public abstract class PacketGenerator {
	private Statue statue;
	
	public PacketGenerator(Statue statue) {
		this.statue = statue;
	}
	
	public abstract CommonPacket getSpawnPacket();
	
	public CommonPacket getDestroyPacket() {
		CommonPacket packet = new CommonPacket(PacketType.OUT_ENTITY_DESTROY);
		packet.write(PacketType.OUT_ENTITY_DESTROY.entityIds, new int[] { statue.getEntityId() });
		
		return packet;
	}
	
	public CommonPacket getLookPacket() {
		Location loc = statue.getLocation();
		byte yaw = Helper.getByteFromDegree(loc.getYaw());
		byte pitch = Helper.getByteFromDegree(loc.getPitch());
		
		CommonPacket packet = new CommonPacket(PacketType.OUT_ENTITY_LOOK);
		packet.write(PacketType.OUT_ENTITY_LOOK.entityId, statue.getEntityId());
		packet.write(PacketType.OUT_ENTITY_LOOK.yaw, yaw);
		packet.write(PacketType.OUT_ENTITY_LOOK.pitch, pitch);
		
		return packet;
	}
	
	public CommonPacket getHeadRotationPacket() {
		Location loc = statue.getLocation();
		byte yaw = Helper.getByteFromDegree(loc.getYaw());
		
		CommonPacket packet = new CommonPacket(PacketType.OUT_ENTITY_HEAD_ROTATION);
		packet.write(PacketType.OUT_ENTITY_HEAD_ROTATION.entityId, statue.getEntityId());
		packet.write(PacketType.OUT_ENTITY_HEAD_ROTATION.headYaw, yaw);
		
		return packet;
	}
	
	public CommonPacket getTeleportPacket() {
		Location loc = statue.getLocation();
		int x = Helper.getFixedPoint(loc.getX());
		int y = Helper.getFixedPoint(loc.getY());
		int z = Helper.getFixedPoint(loc.getZ());
		byte yaw = Helper.getByteFromDegree(loc.getYaw());
		byte pitch = Helper.getByteFromDegree(loc.getPitch());
		
		CommonPacket packet = new CommonPacket(PacketType.OUT_ENTITY_TELEPORT);
		packet.write(PacketType.OUT_ENTITY_TELEPORT.entityId, statue.getEntityId());
		packet.write(PacketType.OUT_ENTITY_TELEPORT.x, x);
		packet.write(PacketType.OUT_ENTITY_TELEPORT.y, y);
		packet.write(PacketType.OUT_ENTITY_TELEPORT.z, z);
		packet.write(PacketType.OUT_ENTITY_TELEPORT.yaw, yaw);
		packet.write(PacketType.OUT_ENTITY_TELEPORT.pitch, pitch);
		
		return packet;
	}
}