package com.lenis0012.bukkit.statues.core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.entity.Player;

public class StatueManager {
	private Map<Integer, Statue> statues = new HashMap<Integer, Statue>();
	private Map<String, Integer> selected = new HashMap<String, Integer>();
	
	public StatueManager() {
		//TODO: Load statues
	}
	
	public void addStatue(Statue statue) {
		statues.put(statue.getId(), statue);
	}
	
	public boolean removeStatue(int id) {
		return statues.remove(id) != null;
	}
	
	public Statue getStatue(int id) {
		return statues.get(id);
	}
	
	public void setSelected(Player player, Statue statue) {
		selected.put(player.getName(), statue.getId());
	}
	
	public Statue getSelected(Player player) {
		return getStatue(selected.get(player.getName()));
	}
	
	public boolean hasSelected(Player player) {
		return selected.containsKey(player.getName());
	}
	
	public void clearSelected(Player player) {
		selected.remove(player.getName());
	}
	
	public void clearSelections(Statue statue) {
		List<String> toRemove = new ArrayList<String>();
		for(Entry<String, Integer> entry : selected.entrySet()) {
			if(entry.getValue().equals(statue.getId())) {
				toRemove.add(entry.getKey());
			}
		}
		
		for(String key : toRemove) {
			selected.remove(key);
		}
	}
	
	public Collection<Statue> getStatues() {
		return statues.values();
	}
	
	public int getFreeId() {
		int i = 0;
		while(statues.containsKey(i)) {
			i += 1;
		}
		
		return i;
	}
}