package com.lenis0012.bukkit.statues.data;

import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;
import java.util.UUID;
import java.util.logging.Level;

import net.minecraft.util.com.mojang.authlib.GameProfile;
import net.minecraft.util.com.mojang.authlib.properties.Property;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.bergerkiller.bukkit.common.utils.PlayerUtil;
import com.lenis0012.bukkit.statues.Helper;
import com.lenis0012.bukkit.statues.Statues;

public class ProfileLoader {
	private final String uuid;
	private final String name;
	private final String displayName;
	
	public ProfileLoader(String uuid, String name) {
		this.uuid = uuid.replaceAll("-", ""); //We add these later
		this.displayName = ChatColor.translateAlternateColorCodes('&', name);
		this.name = ChatColor.stripColor(displayName);
	}
	
	public GameProfile loadProfile() {
		Player player = Helper.getPlayer(name);
		if(player != null) {
			return PlayerUtil.getGameProfile(player);
		} else {
			UUID id = uuid == null ? parseUUID(getUUID(name)) : parseUUID(uuid);
			GameProfile profile = new GameProfile(id, name);
			addProperties(profile);
			return profile;
		}
	}
	
	private void addProperties(GameProfile profile) {
       String uuid = profile.getId().toString().replaceAll("-", "");
        try {
            // Get the name from SwordPVP
            URL url = new URL("https://uuid.swordpvp.com/session/" + uuid);
            URLConnection uc = url.openConnection();
            uc.setUseCaches(false);
            uc.setDefaultUseCaches(false);
            uc.addRequestProperty("User-Agent", "Mozilla/5.0");
            uc.addRequestProperty("Cache-Control", "no-cache, no-store, must-revalidate");
            uc.addRequestProperty("Pragma", "no-cache");

            // Parse it
            String json = new Scanner(uc.getInputStream(), "UTF-8").useDelimiter("\\A").next();
            JSONParser parser = new JSONParser();
            Object obj = parser.parse(json);
            JSONArray properties = (JSONArray) ((JSONObject) obj).get("properties");
            for(int i = 0; i < properties.size(); i++) {
            	try {
	            	JSONObject property = (JSONObject) properties.get(i);
	            	String name = (String) property.get("name");
	            	String value = (String) property.get("value");
	            	String signature = property.containsKey("signature") ? (String) property.get("signature") : null;
	            	if(signature != null) {
	            		profile.getProperties().put(name, new Property(name, value, signature));
	            	} else {
	            		profile.getProperties().put(name, new Property(value, name));
	            	}
            	} catch(Exception e) {
            		Statues.getInstance().getLogger().log(Level.WARNING, "Failed to apply auth property", e);
            	}
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
	
	private String getUUID(String name) {
        String uuid = null;
        try {
            // Get the UUID from SwordPVP
            URL url = new URL("https://uuid.swordpvp.com/uuid/" + name);
            URLConnection uc = url.openConnection();
            uc.setUseCaches(false);
            uc.setDefaultUseCaches(false);
            uc.addRequestProperty("User-Agent", "Mozilla/5.0");
            uc.addRequestProperty("Cache-Control", "no-cache, no-store, must-revalidate");
            uc.addRequestProperty("Pragma", "no-cache");

            // Parse it
            String json = new Scanner(uc.getInputStream(), "UTF-8").useDelimiter("\\A").next();
            JSONParser parser = new JSONParser();
            Object obj = parser.parse(json);
            uuid = (String) ((JSONObject) ((JSONArray) ((JSONObject) obj).get("profiles")).get(0)).get("id");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        return uuid;
    }
	
	private UUID parseUUID(String uuidStr) {
		//Split uuid in to 5 components
		String[] uuidComponents = new String[] {
				uuidStr.substring(0, 8),
				uuidStr.substring(8, 12),
				uuidStr.substring(12, 16),
				uuidStr.substring(16, 20),
				uuidStr.substring(20, uuidStr.length())
		};
		
		//Combine components with a dash
		StringBuilder builder = new StringBuilder();
		for(String component : uuidComponents) {
			builder.append(component).append('-');
		}
		
		//Correct uuid length, remove last dash
		builder.setLength(builder.length() - 1);
		return UUID.fromString(builder.toString());
	}
}