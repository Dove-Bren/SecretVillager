package com.SkyIsland.SecretVillager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Set;
import java.util.LinkedList;
import java.util.List;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Villager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import com.SkyIsland.SecretVillager.Villager.InvincibleVillager;
import com.SkyIsland.SecretVillager.Villager.SecretVillager;

public class SecretVillagerPlugin extends JavaPlugin {
	
	public static SecretVillagerPlugin plugin;
	
	private static final double version = 0.143;
	
	private YamlConfiguration config;
	private YamlConfiguration villagerConfig;
	private File configFile, villagerFile;
	private List<SecretVillager> villagers;
	
	private BukkitRunnable waitForLoad = new BukkitRunnable(){
		
		public void run() {
			YamlConfiguration villager = new YamlConfiguration();
			Vector vect = new Vector(1494, 64, 60);
			villager.set("name", "the one");
			villager.set("world", "HomeWorld");
			villager.set("location", vect);
			villager.set("profession", Villager.Profession.BUTCHER.toString());
			
			villagers.add(new InvincibleVillager(villager));
			extractVillagers(config);
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
			} catch (IOException
					| InvalidConfigurationException e) {
				getLogger().info("Unable to load villager config from \"villagers.yml\" in resource folder.\nAborting plugin load...");
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
		SecretVillagerPlugin.plugin = this;
		villagers = new LinkedList<SecretVillager>();
		
		this.waitForLoad.runTaskLater(this, 10);
	}
	
	@Override
	public void onDisable() {
		try {
			config.save(configFile);
			villagerConfig.save(villagerFile);
		} catch (IOException e) {
			getLogger().info("\n\n\nUnable to save config files for SecretVillager!!!!!!!!!!!!!!!!!!!\n\n\n");
		}
		int i = 0;
		config = null;
		config = new YamlConfiguration();
		//Iterate over every villager and save out their data to a unique key
		for (SecretVillager SV : villagers) {
			//Saves Villager name as "Villager_n"
			config.set("Villager_" + i, SV.toConfig());
			//Unload Villager from memory
			SV.unload();
			i++;
		}
		try {
			config.save(villagerFile);
		} catch (IOException e) {
			//This should never ever happen unless something catastrophic happens
			//Pray to Jesus that it doesn't
			getLogger().info("\n\n\nCOULD NOT SAVE CONFIGURATION DATA ON DISABLE\n\n\n");
			e.printStackTrace();
		}
		villagers.clear();
	}
	
	
	
	
	private YamlConfiguration defaultConfig() {
		YamlConfiguration def = new YamlConfiguration();
		
		def.set("version", SecretVillagerPlugin.version);
		
		
		return def;
	}
	
	private void extractVillagers(YamlConfiguration villager) {
		Set <String> villagers;
		YamlConfiguration vilConfig = new YamlConfiguration();
		villagers = villager.getKeys(false);
		for (String s : villagers) {
			vilConfig.get(s);
			createFromConfig(vilConfig);
		}
	}
	
	private SecretVillager createFromConfig(YamlConfiguration villager) {
		if (villager.getInt("Type", -1) == -1) {
			//defaults to -1
			getLogger().info("Error when parsing villagers: Unable to find villager type in " + villager.getName());
			return null;
		}
		
		SecretVillager sVil = null;
		VillagerType type = VillagerType.fromIndex(villager.getInt("Type", 0));
		
		switch (type) {
		case INVINCIBLE:
			sVil = new InvincibleVillager(villager);
			break;
		case TRADE_SWAPPED:
			break;
		case BOTH:
			break;
		}
		
		
		
		return sVil;
	}
}
