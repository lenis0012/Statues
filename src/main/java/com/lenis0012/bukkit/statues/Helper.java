package com.lenis0012.bukkit.statues;

import java.util.UUID;

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
}