package com.SkyIsland.SecretVillager;

import java.io.File;
import java.io.IOException;
import java.util.Set;
import java.util.LinkedList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import com.SkyIsland.SecretVillager.Villager.InvincibleTradeVillager;
import com.SkyIsland.SecretVillager.Villager.InvincibleVillager;
import com.SkyIsland.SecretVillager.Villager.SecretVillager;
import com.SkyIsland.SecretVillager.Villager.TradeVillager;

public class SecretVillagerPlugin extends JavaPlugin implements Listener {
	
	public static SecretVillagerPlugin plugin;
	
	private static final double version = 0.143;
	
	private YamlConfiguration config;
	private YamlConfiguration villagerConfig;
	private File configFile, villagerFile;
	private List<SecretVillager> villagers;
	private boolean update;
	
	private BukkitRunnable waitForLoad = new BukkitRunnable(){
		
		public void run() {			
			extractVillagers(villagerConfig);
		}
	};
	

	@Override
	public void onLoad() {
		if (!getDataFolder().exists()) {
			getDataFolder().mkdirs();
		}
		
		configFile = new File(this.getDataFolder(), "config.yml");
		if (!configFile.exists()) {
			getLogger().info("Configuration file for Secret Villager plugin not found in data folder!\n"
					+ "Making a default one...");
			config = defaultConfig();
			try {
				config.save(configFile);
			} catch (IOException e) {
				e.printStackTrace();
				getLogger().info("Unable to save default config!\nAborting plugin load...");
				config = null;
				return;
			}
		}
		else {
			try {
				config = new YamlConfiguration();
				config.load(configFile);
				if (config.getDouble("version", 0.00) - SecretVillagerPlugin.version > .00001) {
					//if versions aren't equal
					getLogger().info("Found a config file with an old version!\n"
							+ "Ignoring. Newer versions might specify more parameters that are required"
							+ "to properly use this plugin! Please upgrade your config file to version " + SecretVillagerPlugin.version  + "!");
										
				}
			} catch (IOException
					| InvalidConfigurationException e) {
				e.printStackTrace();
				getLogger().info("Unable to load config from config.yml!!\nAborting plugin load...");
				config = null;
				return;
			}
		}
		
		//config loaded/was created correctly
		//do villager config now
		villagerFile = new File(this.getDataFolder(), "villagers.yml");
		if (!villagerFile.exists()) {
			//we don't really have a default. Just make an empty one and have an empty config.
			try {
				villagerFile.createNewFile();
				villagerConfig = new YamlConfiguration();
			} catch (IOException e) {
				e.printStackTrace();
				getLogger().info("Unable to place a new file named 'villagers.yml' in resource folder.\nAborting plugin load...");
				config = null;
				villagerConfig = null;
				return;
			}
		}
		else {
			villagerConfig = new YamlConfiguration();
			try {
				villagerConfig.load(villagerFile);
			} catch (IOException e) {
				getLogger().info("Unable to load villager config from \"villagers.yml\" in resource folder.\nAborting plugin load...");
				config = null;
				villagerConfig = null;
			} catch (InvalidConfigurationException e) {
				getLogger().info("Unable to load villager config from \"villagers.yml\" in resource folder.\nAborting plugin load...");
				getLogger().info(e.getMessage());
				config = null;
				villagerConfig = null;
			}
		}
		
		
	}
	
	@Override
	public void onEnable() {
		//first thing we do is make sure config isn't null. If it is, there was a problem during
		//load and we just want to stop the plugin for even enabling
		if (config == null || villagerConfig == null) {
			this.getPluginLoader().disablePlugin(this);
			return;
		}
		//are we going to save out the new location of villagers each time the plugin is unloaded?
		this.update = config.getBoolean("update_villagers", false);
		getLogger().info("Update set to: " + update + "\n\n");
		SecretVillagerPlugin.plugin = this;
		villagers = new LinkedList<SecretVillager>();
		
		this.waitForLoad.runTaskLater(this, 10);
		Bukkit.getPluginManager().registerEvents(this, this);
	}
	
	@Override
	public void onDisable() {
		try {
			config.save(configFile);
			//villagerConfig.save(villagerFile); //whut it  gets saved below
		} catch (IOException e) {
			getLogger().info("\n\n\nUnable to save config files for SecretVillager!!!!!!!!!!!!!!!!!!!\n\n\n");
		}
		int i = 0;
		villagerConfig = null;
		villagerConfig = new YamlConfiguration();
		//Iterate over every villager and save out their data to a unique key
		for (SecretVillager SV : villagers) {
			if (update) {
				//Saves Villager name as "Villager_n"
				villagerConfig.set("Villager_" + i, SV.toConfig());
			}
			//Unload Villager from memory
			SV.unload();
			i++;
		}
		if (update) {
			try {
				villagerConfig.save(villagerFile);
			} catch (IOException e) {
				//This should never ever happen unless something catastrophic happens
				//Pray to Jesus that it doesn't
				getLogger().info("\n\n\nCOULD NOT SAVE CONFIGURATION DATA ON DISABLE\n\n\n");
				e.printStackTrace();
			}
		}
		villagers.clear();
	}
	
	
	
	
	private YamlConfiguration defaultConfig() {
		YamlConfiguration def = new YamlConfiguration();
		
		def.set("version", SecretVillagerPlugin.version);
		def.set("update_villagers", true);
		
		
		return def;
	}
	
	private void extractVillagers(YamlConfiguration villager) {
		Set <String> villagers;
		//YamlConfiguration vilConfig = new YamlConfiguration();
		villagers = villager.getKeys(false);
		for (String s : villagers) {
			if (s.trim().isEmpty())
				continue;
			createFromConfig(villager.getConfigurationSection(s));
		}
	}
	
	private SecretVillager createFromConfig(ConfigurationSection villager) {
		if (villager.getInt("type", -1) == -1) {
			//defaults to -1
			getLogger().info("Error when parsing villagers: Unable to find villager type in " + villager.getName());
			return null;
		}
		
		SecretVillager sVil = null;
		VillagerType type = VillagerType.fromIndex(villager.getInt("type", 0));
		
		switch (type) {
		case INVINCIBLE:
			sVil = new InvincibleVillager(villager);
			villagers.add(sVil);
			break;
		case TRADE_SWAPPED:
			sVil = new TradeVillager(villager);
			villagers.add(sVil);
			break;
		case BOTH:
			sVil = new InvincibleTradeVillager(villager);
			villagers.add(sVil);
			break;
		}
		
		
		
		return sVil;
	}
	
	@EventHandler(priority = EventPriority.LOWEST)
	public void onTeleportScroll(PlayerInteractEvent e) {
		if (!e.isCancelled()) {
			if (e.getAction().equals(Action.RIGHT_CLICK_AIR)) {
				if (!e.getPlayer().isDead()) {
					if (e.getItem().getType() == Material.MAP) {
						ItemStack map = e.getItem();
						ItemMeta meta = map.getItemMeta();
						if (meta.getDisplayName().compareTo("Return to Spawn Scroll") == 0) {
							//make sure the lore is right and it's not just something renamed to a return to...
							List<String> lore = meta.getLore();
							if (lore == null || lore.isEmpty() || lore.size() != 2) {
								return;
							}
							if (lore.get(0).compareTo(ChatColor.GREEN + "Teleports the user back to spawn.") == 0)
							if (lore.get(1).compareTo(ChatColor.RED + "Scroll is destroyed upon use!" + ChatColor.RESET) == 0) {
								//everything is good AS long as they are in the homeoworld
								e.setCancelled(true);
								Player player = e.getPlayer();
								if (player.getWorld().getName().compareTo("HomeWorld") != 0) {
									player.sendMessage("I'm sorry, but you must be in the HomeWorld to use this scroll.");
									return;
								}
								//else
								player.teleport(player.getWorld().getSpawnLocation());
								return;
							}
						}
					}
				}
			}
		}
	}
}
