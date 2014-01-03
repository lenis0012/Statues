package com.lenis0012.bukkit.statues.core;

import com.bergerkiller.bukkit.common.protocol.CommonPacket;
import com.bergerkiller.bukkit.common.protocol.PacketType;

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
}