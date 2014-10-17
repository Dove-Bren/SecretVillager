package com.SkyIsland.SecretVillager.Villager;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.util.Vector;

import com.SkyIsland.SecretVillager.SecretVillagerPlugin;
import com.SkyIsland.SecretVillager.VillagerType;

/**
 * A villager who is not affected by damage
 * @author Skyler
 *
 */
public class InvincibleVillager implements SecretVillager, Listener{
	
	private Villager villager = null;
	
	public InvincibleVillager(YamlConfiguration villagerInfo) {
		Bukkit.getPluginManager().registerEvents(this, SecretVillagerPlugin.plugin);
		this.load(villagerInfo);
	}
	
	@EventHandler
	public void onDamage(EntityDamageEvent e) {
		if (villager != null && !e.isCancelled() && e.getCause().compareTo(DamageCause.CUSTOM) != 0 && e.getEntity().getUniqueId().compareTo(this.getVillager().getUniqueId()) == 0) {
			e.setCancelled(true);
			return;
		}
	}

	@Override
	public YamlConfiguration toConfig() {
		/*
		 * type: 1
		 * world: WORLDNAME
		 * location: <>
		 * 							trades: {(INPUT1, INPUT2, REWARD), (...)}
		 * name: matilda
		 * profession: PROFENUM_NAME
		 */
		YamlConfiguration config = new YamlConfiguration();
		
		config.set("type", VillagerType.INVINCIBLE.getIndex());
		config.set("world", villager.getWorld().getName());
		config.set("location", villager.getLocation().toVector());
		config.set("name", villager.getCustomName());
		config.set("profession", villager.getProfession().toString());
		return config;
	}

	@Override
	public void load(YamlConfiguration config) {
		World world;
		Location location;
		String name;
		Villager.Profession prof;
		Vector vect;
		
		world = Bukkit.getWorld((String) config.get("world", null)); //homeworld for the lulz
		vect = config.getVector("location", null);
		
		prof = Villager.Profession.valueOf((String) config.get("profession", null));
		name = (String) config.get("name", null);
		
		//check that everything was successful
		if (prof == null || name == null || vect == null || world == null) {
			if (name == null) 
				name = "null"; //for effect
			SecretVillagerPlugin.plugin.getLogger().info("Error creating villager (" + name + ") from file due to"
					+ "bad parameters!" );
			villager = null;
			return;
		}
			

		location = new Location(world, vect.getBlockX(), vect.getBlockY(), vect.getBlockZ());
		
		//create villager based on our stats
		villager = (Villager) world.spawnEntity(location, EntityType.VILLAGER);
		villager.setCustomName(name);
		villager.setProfession(prof);
		
		//party
		//party() //removed because of excessive party'ing   sm
	}

	@Override
	public Entity getVillager() {
		return villager;
	}

	@Override
	public void setVillager(Entity e) {
		if (!(e instanceof Villager)) {
			SecretVillagerPlugin.plugin.getLogger().info("Unable to set passed entity to villager, as it's not"
					+ "a villager");
			return;
		}
		villager = (Villager) e;
	}
	
	@Override
	public void unload() {
		if (villager != null && !villager.isDead()) {
			villager.damage(villager.getMaxHealth());
		}
	}
}
