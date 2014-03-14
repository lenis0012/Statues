package com.lenis0012.bukkit.statues.core;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class StatueManager {
	private Map<Integer, Statue> statues = new HashMap<Integer, Statue>();
	
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