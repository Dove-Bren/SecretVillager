package com.SkyIsland.SecretVillager.Villager;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Entity;


/**
 * Represents a secret villager, which is both the in-game villager as well as that villager's API-defined
 * specs.
 * @author Skyler
 *
 */
public interface SecretVillager {
	
	public YamlConfiguration toConfig();
	
	public void load(YamlConfiguration config);
	
	public Entity getVillager();
	
	public void setVillager(Entity e);
	
	public void load();
	
	public void unload();
	
}
