package com.lenis0012.bukkit.statues.core;

import com.bergerkiller.bukkit.common.protocol.CommonPacket;

public abstract class PacketGenerator {
	private Statue statue;
	
	public PacketGenerator(Statue statue) {
		this.statue = statue;
	}
	
	public abstract CommonPacket getSpawnPacket();
}