package com.SkyIsland.SecretVillager;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import com.SkyIsland.SecretVillager.Villager.InvincibleVillager;
import com.SkyIsland.SecretVillager.Villager.SecretVillager;

public class SecretVillagerPlugin extends JavaPlugin {
	
	public static SecretVillagerPlugin plugin;
	
	private static final double version = 1.00;
	
	private YamlConfiguration config;
	private YamlConfiguration villagerConfig;
	private File configFile, villagerFile;
	
	@Override
	public void onLoad() {
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
		
		
	}
	
	@Override
	public void onDisable() {
		try {
			config.save(configFile);
			villagerConfig.save(villagerFile);
		} catch (IOException e) {
			getLogger().info("\n\n\nUnable to save config files for SecretVillager!!!!!!!!!!!!!!!!!!!\n\n\n");
		}
		
		
	}
	
	
	
	
	private YamlConfiguration defaultConfig() {
		
		return null;
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
