package com.lenis0012.bukkit.statues.data;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.world.WorldLoadEvent;

import com.lenis0012.bukkit.statues.Statues;
import com.lenis0012.bukkit.statues.core.Statue;
import com.lenis0012.bukkit.statues.core.StatueManager;

public class DataManager implements Listener {
	private Statues plugin;
	
	public DataManager(Statues plugin) {
		this.plugin = plugin;
		Bukkit.getPluginManager().registerEvents(this, plugin);
		for(World world : Bukkit.getWorlds()) {
			this.loadStatues(world);
		}
	}
	
	@EventHandler(priority = EventPriority.MONITOR)
	public void onWorldLoad(WorldLoadEvent event) {
		loadStatues(event.getWorld());
	}
	
	@SuppressWarnings("unchecked")
	public void loadStatues(World world) {
		File dir = new File(plugin.getDataFolder(), "statues");
		dir.mkdirs();
		File file = new File(dir, world.getName() + ".bin");
		StatueManager manager = plugin.getStatueManager();
		try {
			int loadedStatues = 0;
			if(file.exists()) {
				FileInputStream fis = new FileInputStream(file);
				ObjectInputStream ois = new ObjectInputStream(fis);
				List<StatueData> obj = (List<StatueData>) ois.readObject();
				for(StatueData data : obj) {
					Statue statue = Statue.loadStatue(data);
					manager.addStatue(statue);
					ois.close();
					loadedStatues += 1;
				}
			}
			
			plugin.getLogger().log(Level.INFO, "Loaded " + loadedStatues + " statues in world " + world.getName());
		} catch(Throwable t) {
			plugin.getLogger().log(Level.SEVERE, "Failed to load statues", t);
		}
	}
	
	public void saveStatues() {
		File dir = new File(plugin.getDataFolder(), "statues");
		dir.mkdirs();
		StatueManager manager = plugin.getStatueManager();
		try {
			Map<String, List<StatueData>> worlds = new HashMap<String, List<StatueData>>();
			for(Statue statue : manager.getStatues()) {
				String world = statue.getWorld().getName();
				List<StatueData> list = null;
				if(worlds.containsKey(world)) {
					list = worlds.get(world);
				} else {
					list = new ArrayList<StatueData>();
				}
				
				list.add(Statue.saveStatue(statue));
				worlds.put(world, list);
			}
			
			for(Entry<String, List<StatueData>> entry : worlds.entrySet()) {
				File file = new File(dir, entry.getKey() + ".bin");
				if(!file.exists()) {
					file.createNewFile();
				}
				
				FileOutputStream fos = new FileOutputStream(file);
				ObjectOutputStream oos = new ObjectOutputStream(fos);
				oos.writeObject(entry.getValue());
				oos.close();
			}
		} catch(Throwable t) {
			plugin.getLogger().log(Level.SEVERE, "Failed to save statues", t);
		}
	}
}