package com.lenis0012.bukkit.statues.data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

public class StatueData implements Serializable {
	private static final long serialVersionUID = 6773382761956168082L;
	private Map<String, Object> data = new HashMap<String, Object>();
	
	public void write(String key, Object value) {
		data.put(key, value);
	}
	
	public void writeLocation(String key, Location loc) {
		write(key + ".world", loc.getWorld().getName());
		write(key + ".x", loc.getX());
		write(key + ".y", loc.getY());
		write(key + ".z", loc.getZ());
		write(key + ".yaw", loc.getYaw());
		write(key + ".pitch", loc.getPitch());
	}
	
	@SuppressWarnings("unchecked")
	public <T> T read(String key, Class<T> type) {
		return (T) data.get(key);
	}
	
	public Location readLocation(String key) {
		World world = Bukkit.getWorld(read(key + ".world", String.class));
		double x = read(key + ".x", double.class);
		double y = read(key + ".y", double.class);
		double z = read(key + ".z", double.class);
		float yaw = read(key + ".yaw", float.class);
		float pitch = read(key + ".pitch", float.class);
		
		return new Location(world, x, y, z, yaw, pitch);
	}
}