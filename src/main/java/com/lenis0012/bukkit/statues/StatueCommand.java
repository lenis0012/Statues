package com.lenis0012.bukkit.statues;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import com.bergerkiller.bukkit.common.utils.LogicUtil;
import com.lenis0012.bukkit.statues.api.StatueEventHandler;
import com.lenis0012.bukkit.statues.core.MobStatue;
import com.lenis0012.bukkit.statues.core.PlayerStatue;
import com.lenis0012.bukkit.statues.core.StatueManager;

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
						player.sendMessage("\247cYou don't have permission to perform this command.");
					}
				} else {
					player.sendMessage("\247cYou haven't used enough arguments.");
				}
			} catch(Exception e) {
				plugin.getLogger().log(Level.SEVERE, "Failed to handle command", e);
			}
		} else {
			player.sendMessage("\247cThat command doesn't exist.");
		}
		
		return true;
	}
	
	@StatueCMD(aliases = "help,?", minArgs = -1, permission = "")
	public void help(Player player, String[] args) {
		player.sendMessage(Helper.fixColors(
				"&6&lStatues command help:\n" +
				"&a/statue create player <name> &7- Create a player statue\n" +
				"&a/statue create mob <type> &7- Create a mob statue"));
	}
	
	@StatueCMD(aliases = "create", minArgs = 2, permission = "statues.create")
	public void create(Player player, String[] args) {
		String type = args[1];
		StatueManager manager = plugin.getStatueManager();
		if(LogicUtil.contains(type, "player", "human")) {
			String name = args[2];
			PlayerStatue statue = new PlayerStatue(manager.getFreeId(), player.getLocation(), name, 0);
			if(StatueEventHandler.callCreateEvent(player, statue)) {
				manager.addStatue(statue);
				statue.spawn();
				player.sendMessage("\247aCreated player statue named \247e" + name + "\247a.");
			}
		} else if(LogicUtil.contains(type, "mob", "animal", "monster")) {
			try {
				EntityType etype = EntityType.valueOf(args[2].toUpperCase());
				MobStatue statue = new MobStatue(manager.getFreeId(), player.getLocation(), etype);
				if(StatueEventHandler.callCreateEvent(player, statue)) {
					manager.addStatue(statue);
					statue.spawn();
					player.sendMessage("\247aCreated a \247e" + args[2].toLowerCase() + " \247astatue.");
				}
			} catch(Exception e) {
				player.sendMessage("\247cInvalid mob type, type \2474/statue types \247cto view all types.");
			}
		} else {
			player.sendMessage("\247cUnkown statue type, choose mob or player.");
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