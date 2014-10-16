package com.SkyIsland.SecretVillager;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class SecretVillagerPlugin extends JavaPlugin {
	
	public static SecretVillagerPlugin plugin;
	
	private YamlConfiguration config;
	private YamlConfiguration villagerConfig;
	
	@Override
	public void onLoad() {
		File configFile, villagerFile;
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
		
	}
	
	
	
	
	private YamlConfiguration defaultConfig() {
		
		return null;
	}
}
