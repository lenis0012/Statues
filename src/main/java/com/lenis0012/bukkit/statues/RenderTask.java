package com.lenis0012.bukkit.statues;

import com.bergerkiller.bukkit.common.AsyncTask;
import com.lenis0012.bukkit.statues.core.PlayerStatue;
import com.lenis0012.bukkit.statues.core.Statue;
import com.lenis0012.bukkit.statues.core.StatueManager;

public class RenderTask extends AsyncTask {
	private StatueManager manager;
	
	public RenderTask(StatueManager manager) {
		this.manager = manager;
	}
	
	@Override
	public void run() {
		while(this.isRunning()) {
			for(Statue statue : manager.getStatues()) {
				if(!(statue instanceof PlayerStatue)) {
					statue.resendLocation();
				}
			}
			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				;
			}
		}
	}
}
