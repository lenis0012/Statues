package com.lenis0012.bukkit.statues;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

import net.minecraft.util.com.mojang.authlib.GameProfile;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import com.bergerkiller.bukkit.common.utils.LogicUtil;
import com.lenis0012.bukkit.statues.api.StatueEventHandler;
import com.lenis0012.bukkit.statues.core.MobStatue;
import com.lenis0012.bukkit.statues.core.PlayerStatue;
import com.lenis0012.bukkit.statues.core.Statue;
import com.lenis0012.bukkit.statues.core.StatueManager;
import com.lenis0012.bukkit.statues.data.ProfileLoader;

public class StatueCommand implements CommandExecutor {
	private Map<String, Method> commands = new HashMap<String, Method>();
	private Statues plugin;
	
	public StatueCommand(Statues plugin) {
		this.plugin = plugin;
		for(Method method : this.getClass().getMethods()) {
			StatueCMD annotation = method.getAnnotation(StatueCMD.class);
			if(annotation != null) {
				for(String arg : annotation.aliases().split(",")) {
					commands.put(arg.toLowerCase(), method);
				}
			}
		}
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!(sender instanceof Player)) {
			sender.sendMessage("This command can only be executed as a player.");
			return true;
		}
		
		Player player = (Player) sender;
		String subCommand = null;
		if(args.length > 0) {
			subCommand = args[0].toLowerCase();
		} else {
			subCommand = "help";
		}
		
		Method method = commands.get(subCommand);
		if(method != null) {
			try {
				StatueCMD annotation = method.getAnnotation(StatueCMD.class);
				if(annotation.minArgs() < args.length) {
					String permission = annotation.permission();
					if(permission.isEmpty() || player.hasPermission(permission)) {
						method.invoke(this, player, args);
					} else {
						player.sendMessage(Helper.fixColors("&cYou don't have permission to perform this command."));
					}
				} else {
					player.sendMessage(Helper.fixColors("&cYou haven't used enough arguments."));
				}
			} catch(Exception e) {
				plugin.getLogger().log(Level.SEVERE, "Failed to handle command", e);
			}
		} else {
			player.sendMessage(Helper.fixColors("&cThat command doesn't exist."));
		}
		
		return true;
	}
	
	@StatueCMD(aliases = "help,?", minArgs = -1, permission = "")
	public void help(Player player, String[] args) {
		player.sendMessage(Helper.fixColors(
				"&6&lStatues command help:\n" +
				"&a/statue create player <name> &7- Create a player statue\n" +
				"&a/statue create mob <type> &7- Create a mob statue\n" +
				"&a/statue select &7- Select nearest statue\n" +
				"&a/statue remove &7- Remove selected statue"));
	}
	
	@StatueCMD(aliases = "create", minArgs = 2, permission = "statues.create")
	public void create(Player player, String[] args) {
		String type = args[1];
		StatueManager manager = plugin.getStatueManager();
		if(LogicUtil.contains(type, "player", "human")) {
			String name = args[2];
			final PlayerStatue statue = new PlayerStatue(manager.getFreeId(), player.getLocation(), name, 0);
			if(StatueEventHandler.callCreateEvent(player, statue)) {
				manager.addStatue(statue);
				player.sendMessage(Helper.fixColors("&aCreated player statue named &e" + name + "&a."));
				Bukkit.getScheduler().runTaskAsynchronously(Statues.getInstance(), new Runnable() {

					@Override
					public void run() {
						ProfileLoader loader = new ProfileLoader(null, statue.getName());
						GameProfile profile = loader.loadProfile();
						System.out.println(profile.getProperties().toString());
						statue.setProfile(profile);
						statue.spawn();
					}
				});
			}
		} else if(LogicUtil.contains(type, "mob", "animal", "monster")) {
			try {
				EntityType etype = EntityType.valueOf(args[2].toUpperCase());
				MobStatue statue = new MobStatue(manager.getFreeId(), player.getLocation(), etype);
				if(StatueEventHandler.callCreateEvent(player, statue)) {
					manager.addStatue(statue);
					statue.spawn();
					player.sendMessage(Helper.fixColors("&aCreated a &e" + args[2].toLowerCase() + " &astatue."));
				}
			} catch(Exception e) {
				player.sendMessage(Helper.fixColors("&cInvalid mob type, type &4/statue types &cto view all types."));
			}
		} else {
			player.sendMessage(Helper.fixColors("&cUnkown statue type, choose mob or player."));
		}
	}
	
	@StatueCMD(aliases = "select", minArgs = 0, permission = "statues.select")
	public void select(Player player, String[] args) {
		StatueManager manager = plugin.getStatueManager();
		Statue nearest = null;
		double distance = Double.MAX_VALUE;
		for(Statue statue : manager.getStatues()) {
			World world = statue.getWorld();
			Location loc = statue.getLocation();
			if(world.equals(player.getWorld())) {
				double dis = loc.distance(player.getLocation());
				if(dis < distance) {
					nearest = statue;
					distance = dis;
				}
			}
		}
		
		if(nearest != null) {
			manager.setSelected(player, nearest);
			player.sendMessage(Helper.fixColors("&aSelected statue &e" + nearest.getId()));
		} else {
			player.sendMessage(Helper.fixColors("&cNo near statues were found, try another world?"));
		}
	}
	
	@StatueCMD(aliases = "remove,delete", minArgs = 0, permission = "statues.remove")
	public void remove(Player player, String[] args) {
		StatueManager manager = plugin.getStatueManager();
		if(manager.hasSelected(player)) {
			Statue statue = manager.getSelected(player);
			if(StatueEventHandler.callRemoveEvent(player, statue)) {
				statue.despawn();
				manager.removeStatue(statue.getId());
				manager.clearSelections(statue);
				player.sendMessage(Helper.fixColors("&aRemoved statue &e" + statue.getId()));
			}
		} else {
			player.sendMessage(Helper.fixColors("&cYou haven't selected a statue."));
		}
	}
	
	@Target(ElementType.METHOD)
	@Retention(RetentionPolicy.RUNTIME)
	private static @interface StatueCMD {
		
		public String aliases();
		
		public int minArgs();
		
		public String permission();
	}
}