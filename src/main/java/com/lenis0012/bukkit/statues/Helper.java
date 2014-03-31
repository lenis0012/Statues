package com.lenis0012.bukkit.statues;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import com.bergerkiller.bukkit.common.protocol.CommonPacket;
import com.bergerkiller.bukkit.common.utils.PacketUtil;

import net.minecraft.util.com.google.common.base.Charsets;
import net.minecraft.util.com.mojang.authlib.GameProfile;

public class Helper {
	/*
	 * Helps to build da statue hueue
	 * #badhumor
	 */
	
	public static GameProfile getGameProfile(String name) {
		UUID uuid = UUID.nameUUIDFromBytes(("OfflinePlayer:" + name).getBytes(Charsets.UTF_8));
		String uuidStr = uuid.toString().replaceAll("-", "");
		return new GameProfile(uuidStr, name);
	}
	
	public static void sendPacketToWorld(CommonPacket packet, World world) {
		List<Player> players = new ArrayList<Player>(world.getPlayers());
		for(Player player : players) {
			if(player.isOnline()) {
				PacketUtil.sendPacket(player, packet);
			}
		}
	}
	
	public static int floor(double val) {
		int valInt = (int) val;
		return valInt > val ? valInt - 1 : valInt;
	}
	
	public static int getFixedPoint(double value) {
		return floor(value * 32.0D);
	}
	
	public static byte getByteFromDegree(float deg) {
		return (byte) (int) (deg * 256.0F / 360.0F);
	}
	
	public static String fixColors(String from) {
		return ChatColor.translateAlternateColorCodes('&', from);
	}
	
	@SuppressWarnings("deprecation")
	public static int getEntityTypeId(EntityType type) {
		return type.getTypeId();
	}
}