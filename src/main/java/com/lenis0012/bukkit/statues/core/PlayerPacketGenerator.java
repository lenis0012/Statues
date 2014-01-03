package com.lenis0012.bukkit.statues.core;

import org.bukkit.Location;

import com.bergerkiller.bukkit.common.protocol.CommonPacket;
import com.bergerkiller.bukkit.common.protocol.PacketType;
import com.lenis0012.bukkit.statues.Helper;

public class PlayerPacketGenerator extends PacketGenerator {
	private PlayerStatue statue;
	
	public PlayerPacketGenerator(PlayerStatue statue) {
		super(statue);
		this.statue = statue;
	}

	@Override
	public CommonPacket getSpawnPacket() {
		CommonPacket packet = new CommonPacket(PacketType.OUT_ENTITY_SPAWN_NAMED);
		Location loc = statue.getLocation();
		int x = Helper.floor(loc.getX() * 32.0D);
		int y = Helper.floor(loc.getY() * 32.0D);
		int z = Helper.floor(loc.getZ() * 32.0D);
		byte yaw = Helper.getByteFromDegree(loc.getYaw());
		byte pitch = Helper.getByteFromDegree(loc.getPitch());
		
		packet.write(PacketType.OUT_ENTITY_SPAWN_NAMED.entityId, statue.getEntityId());
		packet.write(PacketType.OUT_ENTITY_SPAWN_NAMED.profile, statue.getName());
		packet.write(PacketType.OUT_ENTITY_SPAWN_NAMED.heldItemId, statue.getItemInHand());
		packet.write(PacketType.OUT_ENTITY_SPAWN_NAMED.x, x);
		packet.write(PacketType.OUT_ENTITY_SPAWN_NAMED.y, y);
		packet.write(PacketType.OUT_ENTITY_SPAWN_NAMED.z, z);
		packet.write(PacketType.OUT_ENTITY_SPAWN_NAMED.yaw, yaw);
		packet.write(PacketType.OUT_ENTITY_SPAWN_NAMED.pitch, pitch);
		packet.setDatawatcher(null);
		
		return packet;
	}
}