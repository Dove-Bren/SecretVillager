package com.SkyIsland.SecretVillager.Villager;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

import com.SkyIsland.SecretVillager.SecretVillagerPlugin;

/**
 * A villager who is not affected by damage
 * @author Skyler
 *
 */
public class InvicibleVillager implements SecretVillager, Listener{
	
	private Villager villager;
	
	public InvicibleVillager(YamlConfiguration villagerInfo) {
		Bukkit.getPluginManager().registerEvents(this, SecretVillagerPlugin.plugin);
		this.load(villagerInfo);
	}
	
	@EventHandler
	public void onDamage(EntityDamageEvent e) {
		if (!e.isCancelled() && e.getEntity().getUniqueId().compareTo(this.getVillager().getUniqueId()) == 0) {
			e.setCancelled(true);
			return;
		}
	}

	@Override
	public YamlConfiguration toConfig() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void load(YamlConfiguration config) {
		// TODO Auto-generated method stub
		
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
}
